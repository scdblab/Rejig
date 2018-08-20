package edu.usc.dblab.nvcache.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.usc.dblab.cache.base.Fragment;
import edu.usc.dblab.cache.base.Fragment.FragmentState;
import edu.usc.dblab.nvcache.config.CacheStaticConfiguration.StaticConfiguration.AssignmentAlgorithm;

public class ConfigurationAssignment {

	public static List<Fragment.Builder> assignBalanceLoad(List<Fragment> originalFragments, List<String> servers,
			int totalFrags, Map<String, Integer> initLoadPerServer) {
		List<Fragment.Builder> newFragments = Lists.newArrayList();

		double balanceLoad = 100.0 / servers.size();
		System.out.println(balanceLoad);
		Map<String, List<Integer>> serverFragments = Maps.newHashMap();
		originalFragments.forEach(frag -> {
			List<Integer> frags = serverFragments.get(frag.getPhysicalServer());
			if (frags == null) {
				frags = Lists.newArrayList();
				serverFragments.put(frag.getPhysicalServer(), frags);
			}
			frags.add(frag.getFragmentId());
		});

		List<Pair<Integer, Double>> reassignFragIds = Lists.newArrayList();
		Map<String, List<Integer>> newServerFragments = Maps.newHashMap();
		List<String> underLoadServers = Lists.newArrayList();
		serverFragments.forEach((server, frags) -> {
			double loadPerFrag = (double) ((double) initLoadPerServer.get(server) / (double) frags.size());
			int curLoad = initLoadPerServer.get(server);
			if (curLoad > balanceLoad) {
				int moveFrags = frags.size() - (int) (balanceLoad / loadPerFrag);
				for (int i = 0; i < moveFrags; i++) {
					reassignFragIds.add(Pair.of(frags.get(i), loadPerFrag));
				}
				newServerFragments.put(server, Lists.newArrayList());
				for (int i = moveFrags; i < frags.size(); i++) {
					newServerFragments.get(server).add(frags.get(i));
				}
			} else {
				newServerFragments.put(server, frags);
				underLoadServers.add(server);
			}
		});

		AtomicInteger reassignIndex = new AtomicInteger(0);
		newServerFragments.forEach((server, frags) -> {
			double curLoad = initLoadPerServer.get(server);
			while (curLoad < balanceLoad && reassignIndex.get() < reassignFragIds.size()) {
				frags.add(reassignFragIds.get(reassignIndex.get()).getLeft());
				curLoad += reassignFragIds.get(reassignIndex.get()).getRight();
				reassignIndex.incrementAndGet();
			}
		});

		int i = 0;
		while (reassignIndex.get() < reassignFragIds.size()) {
			i = i % underLoadServers.size();
			String server = underLoadServers.get(i);
			i += 1;
			newServerFragments.get(server).add(reassignFragIds.get(reassignIndex.get()).getLeft());
			reassignIndex.incrementAndGet();
		}

		Map<Integer, String> fragIdServer = Maps.newHashMap();
		newServerFragments.forEach((server, frags) -> {
			frags.forEach(fragId -> {
				fragIdServer.put(fragId, server);
			});
		});

		fragIdServer.forEach((fragId, server) -> {
			newFragments.add(Fragment.newBuilder().setFragmentId(fragId).setPhysicalServer(server)
					.setMigrateServer("-1").setFragmentCfgId(0).setPrevFragmentCfgId(-1).setNextFragmentCfgId(-1)
					.setState(FragmentState.NORMAL));
		});
		return newFragments;
	}

	/**
	 * Only uniform for now.
	 * 
	 * @param originalFragments
	 * @param servers
	 * @param totalFrags
	 * @param totalKeys
	 * @param algorithm
	 * @return
	 */
	public static List<Fragment.Builder> assign(List<Fragment> originalFragments, List<String> servers, int totalFrags,
			int totalKeys, AssignmentAlgorithm algorithm) {
		List<Fragment.Builder> newFragments = Lists.newArrayList();
		int fragsPerServer = totalFrags / servers.size();

		Map<String, List<Integer>> serverFragments = Maps.newHashMap();
		List<Integer> reassignFragIds = Lists.newArrayList();
		originalFragments.forEach(frag -> {
			List<Integer> frags = serverFragments.get(frag.getPhysicalServer());
			if (frags == null) {
				frags = Lists.newArrayList();
				serverFragments.put(frag.getPhysicalServer(), frags);
			}

			if (frags.size() == fragsPerServer) {
				reassignFragIds.add(frag.getFragmentId());
			} else {
				frags.add(frag.getFragmentId());
			}
		});

		AtomicInteger reassignIndex = new AtomicInteger(0);
		serverFragments.forEach((server, frags) -> {
			while (frags.size() < fragsPerServer && reassignIndex.get() < reassignFragIds.size()) {
				frags.add(reassignFragIds.get(reassignIndex.get()));
				reassignIndex.incrementAndGet();
			}
		});
		Map<Integer, String> fragIdServer = Maps.newHashMap();
		serverFragments.forEach((server, frags) -> {
			frags.forEach(fragId -> {
				fragIdServer.put(fragId, server);
			});
		});

		fragIdServer.forEach((fragId, server) -> {
			newFragments.add(Fragment.newBuilder().setFragmentId(fragId).setPhysicalServer(server)
					.setMigrateServer("-1").setFragmentCfgId(0).setPrevFragmentCfgId(-1).setNextFragmentCfgId(-1)
					.setState(FragmentState.NORMAL));
		});
		return newFragments;
	}
}
