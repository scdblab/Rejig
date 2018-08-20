package edu.usc.dblab.nvcache.config;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import edu.usc.dblab.cache.base.AssignmentConfiguration;
import edu.usc.dblab.cache.base.AssignmentConfiguration.ServerState;
import edu.usc.dblab.cache.base.Fragment;
import edu.usc.dblab.cache.base.Fragment.FragmentState;
import edu.usc.dblab.nvcache.config.CacheStaticConfiguration.StaticConfiguration;

public class ConfigurationManagement {

	private final StaticConfiguration staticConfiguration;
	private final List<AssignmentConfiguration> allConfigurations = Lists.newArrayList();
	private final TreeSet<Integer> timeToUpdateConfig = Sets.newTreeSet();
	private final AtomicInteger currentConfigNumber = new AtomicInteger(0);

	public ConfigurationManagement(StaticConfiguration staticConfiguration) {
		super();
		this.staticConfiguration = staticConfiguration;
		timeToUpdateConfig.addAll(staticConfiguration.configurationUpdateTime);
	}

	public void incrementCfgId() {
		currentConfigNumber.getAndIncrement();
	}

	public AssignmentConfiguration getConfig() {
		return allConfigurations.get(currentConfigNumber.get());
	}

	public StaticConfiguration getStaticConfiguration() {
		return staticConfiguration;
	}

	public TreeSet<Integer> getTimeToUpdateConfig() {
		return timeToUpdateConfig;
	}

	public AtomicInteger getCurrentConfigNumber() {
		return currentConfigNumber;
	}

	private AssignmentConfiguration buildInitConfig(List<String> newServers, Map<String, Integer> fragsPerServer) {
		List<Fragment> fragments = Lists.newArrayList();
		Map<String, ServerState> serverState = Maps.newHashMap();
		int fragId = 0;
		for (int i = 0; i < newServers.size(); i++) {
			String server = newServers.get(i);
			System.out.println(server);
			int frags = fragsPerServer.get(server);
			serverState.put(server, ServerState.NORMAL);
			for (int j = 0; j < frags; j++) {
				// int32 config_number = 1;
				// string physical_server = 2;
				// string migrate_server = 3;
				// int32 fragment_id = 4;
				// int32 prev_fragment_cfg_id = 5;
				// int32 fragment_cfg_id = 6;
				// int32 next_fragment_cfg_id = 7;
				// enum FragmentState {
				// NORMAL = 0;
				// MIGRATE_TO = 1;
				// MIGRATE_FROM = 2;
				// }
				//
				// FragmentState state = 8;
				fragments.add(Fragment.newBuilder().setFragmentId(fragId).setPhysicalServer(server)
						.setMigrateServer("-1").setFragmentCfgId(0).setPrevFragmentCfgId(-1).setNextFragmentCfgId(-1)
						.setState(FragmentState.NORMAL).build());
				fragId++;
			}
		}
		return AssignmentConfiguration.newBuilder().setConfigNumber(0).addAllFragments(fragments)
				.putAllServerStateMap(serverState).build();
	}

	private AssignmentConfiguration buildNextConfig(List<Fragment> prevFragments, List<Fragment.Builder> newFragments,
			int newConfigId) {
		Map<String, ServerState> serverState = Maps.newHashMap();
		for (int i = 0; i < prevFragments.size(); i++) {
			Fragment prevFrag = prevFragments.get(i);
			Fragment.Builder newFrag = newFragments.get(i);

			if (prevFrag.getPhysicalServer().equals(newFrag.getPhysicalServer())) {
				// copy fields over
				newFrag.setMigrateServer(prevFrag.getMigrateServer());
				newFrag.setPhysicalServer(prevFrag.getPhysicalServer());
				newFrag.setFragmentCfgId(prevFrag.getFragmentCfgId());
				newFrag.setPrevFragmentCfgId(prevFrag.getPrevFragmentCfgId());
				newFrag.setNextFragmentCfgId(prevFrag.getNextFragmentCfgId());
				newFrag.setState(prevFrag.getState());
			} else {
				newFrag.setMigrateServer(prevFrag.getPhysicalServer());

				if (prevFrag.getNextFragmentCfgId() != -1) {
					newFrag.setFragmentCfgId(prevFrag.getNextFragmentCfgId());
				} else {
					newFrag.setFragmentCfgId(newConfigId);
				}

				newFrag.setPrevFragmentCfgId(prevFrag.getFragmentCfgId());
				newFrag.setNextFragmentCfgId(-1);
				newFrag.setState(FragmentState.MIGRATE);
			}

			newFrag.setFragmentId(i);
			ServerState state = from(newFrag.getState());
			if (!serverState.containsKey(newFrag.getPhysicalServer())) {
				serverState.put(newFrag.getPhysicalServer(), state);
			} else {
				if (ServerState.MIGRATION.equals(state)) {
					serverState.put(newFrag.getPhysicalServer(), state);
				}
			}
		}
		return AssignmentConfiguration.newBuilder().setConfigNumber(newConfigId)
				.addAllFragments(newFragments.stream().map(i -> {
					return i.build();
				}).collect(Collectors.toList())).putAllServerStateMap(serverState).build();
	}

	public static ServerState from(FragmentState state) {
		switch (state) {
		case MIGRATE:
			return ServerState.MIGRATION;
		case NORMAL:
			return ServerState.NORMAL;
		case UNRECOGNIZED:
			break;
		default:
			break;
		}
		return null;
	}

	public List<Fragment.Builder> copyFragments(List<Fragment.Builder> fragments) {
		List<Fragment.Builder> newFragments = Lists.newArrayList();
		for (int i = 0; i < fragments.size(); i++) {
			Fragment.Builder prevFrag = fragments.get(i);
			Fragment.Builder newFrag = Fragment.newBuilder();

			newFrag.setMigrateServer(prevFrag.getMigrateServer());
			newFrag.setPhysicalServer(prevFrag.getPhysicalServer());
			newFrag.setFragmentCfgId(prevFrag.getFragmentCfgId());
			newFrag.setPrevFragmentCfgId(prevFrag.getPrevFragmentCfgId());
			newFrag.setNextFragmentCfgId(prevFrag.getNextFragmentCfgId());
			newFrag.setState(prevFrag.getState());
			newFrag.setFragmentId(i);

			newFragments.add(newFrag);
		}
		return newFragments;
	}

	private AssignmentConfiguration buildNextNormalConfig(List<Fragment> prevFragments,
			List<Fragment.Builder> newFragments, int newConfigId) {
		Map<String, ServerState> serverState = Maps.newHashMap();
		for (int i = 0; i < prevFragments.size(); i++) {
			Fragment prevFrag = prevFragments.get(i);
			Fragment.Builder newFrag = newFragments.get(i);

			if (prevFrag.getPhysicalServer().equals(newFrag.getPhysicalServer())) {
				newFrag.setFragmentCfgId(prevFrag.getFragmentCfgId());
			} else {
				newFrag.setFragmentCfgId(newConfigId);
			}

			newFrag.setPrevFragmentCfgId(-1);
			newFrag.setNextFragmentCfgId(-1);
			newFrag.setState(FragmentState.NORMAL);
			newFrag.setFragmentId(i);
			if (!serverState.containsKey(newFrag.getPhysicalServer())) {
				serverState.put(newFrag.getPhysicalServer(), ServerState.NORMAL);
			}
		}
		return AssignmentConfiguration.newBuilder().setConfigNumber(newConfigId)
				.addAllFragments(newFragments.stream().map(i -> {
					return i.build();
				}).collect(Collectors.toList())).putAllServerStateMap(serverState).build();
	}

	public void populate() {
		List<String> servers = staticConfiguration.initServers;
		allConfigurations.add(buildInitConfig(servers, staticConfiguration.initFragmentsPerServer));
		if (staticConfiguration.newServers.isEmpty()) {
			return;
		}

		int globalConfigId = 0;

		List<Fragment.Builder> newFragments = null;

		if (staticConfiguration.loadPerServer.isEmpty()) {
			newFragments = ConfigurationAssignment.assign(allConfigurations.get(globalConfigId).getFragmentsList(),
					staticConfiguration.newServers, staticConfiguration.totalNumberOfFragments,
					staticConfiguration.totalNumberOfKeys, staticConfiguration.assignmentAlgorithm);
		} else {
			newFragments = ConfigurationAssignment.assignBalanceLoad(
					allConfigurations.get(globalConfigId).getFragmentsList(), staticConfiguration.newServers,
					staticConfiguration.totalNumberOfFragments, staticConfiguration.loadPerServer);
		}

		if (!staticConfiguration.enableMigration) {
			allConfigurations.add(buildNextNormalConfig(allConfigurations.get(globalConfigId).getFragmentsList(),
					newFragments, globalConfigId + 1));
			return;
		}

		allConfigurations.add(buildNextConfig(allConfigurations.get(globalConfigId).getFragmentsList(),
				copyFragments(newFragments), globalConfigId + 1));
	}

	public List<AssignmentConfiguration> getAllConfigurations() {
		return allConfigurations;
	}

}
