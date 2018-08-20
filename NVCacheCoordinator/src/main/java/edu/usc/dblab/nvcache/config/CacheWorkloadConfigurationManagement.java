package edu.usc.dblab.nvcache.config;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import edu.usc.dblab.cache.base.Fragment;
import edu.usc.dblab.cache.base.Range;
import edu.usc.dblab.cache.base.WorkloadConfiguration;
import edu.usc.dblab.cache.base.Fragment.FragmentState;
import edu.usc.dblab.cache.base.WorkloadConfiguration.Workload;
import edu.usc.dblab.nvcache.config.CacheStaticConfiguration.StaticConfiguration;

public class CacheWorkloadConfigurationManagement {
	private final Logger logger = Logger.getLogger(CacheWorkloadConfigurationManagement.class);

	private final List<WorkloadConfiguration> allConfigurations = Lists.newArrayList();
	private final TreeSet<Integer> timeToUpdateConfig = Sets.newTreeSet();
	private final AtomicInteger currentConfigNumber = new AtomicInteger(0);
	private final StaticConfiguration coordinatorConfig;
	private final ConfigurationManagement configurationManagement;

	public CacheWorkloadConfigurationManagement(StaticConfiguration coordinatorConfig,
			ConfigurationManagement configurationManagement) {
		super();
		this.coordinatorConfig = coordinatorConfig;
		this.configurationManagement = configurationManagement;
	}

	public void print() {
		logger.info("time to update: " + timeToUpdateConfig);
		for (int i = 0; i < allConfigurations.size(); i++) {
			// logger.info("Config " + i + ": " + allConfigurations.get(i));
		}
	}

	public void prepareConfigs(String file) {
		int base = 0;
		int totalKeys = coordinatorConfig.totalNumberOfKeys;

		List<Fragment> frags = this.configurationManagement.getAllConfigurations().get(0).getFragmentsList();
		List<Fragment> freqFrags = Lists.newArrayList();
		double freq = 100.0 / frags.size();
		int start = base;
		int interval = totalKeys / frags.size();

		for (int i = 0; i < frags.size(); i++) {
			int end = start + interval;
			freqFrags.add(Fragment.newBuilder().setFragmentId(i).setFrequency(freq)
					.addRange(Range.newBuilder().setStart(start).setEnd(end).build()).build());
			start += interval;
		}
		allConfigurations.add(WorkloadConfiguration.newBuilder().setConfigNumber(0).setBase(base).setMax(totalKeys)
				.setWorkload(Workload.NORMAL).addAllFragments(freqFrags).build());

		// apply to the first config.
		frags = this.configurationManagement.getAllConfigurations().get(0).getFragmentsList();
		Map<String, List<Fragment>> serverFragments = Maps.newHashMap();
		frags.forEach(frag -> {
			serverFragments.compute(frag.getPhysicalServer(), (k, v) -> {
				if (v == null) {
					v = Lists.newArrayList();
				}
				v.add(frag);
				return v;
			});
		});

		freqFrags = Lists.newArrayList();

		start = base;
		int newKeyStart = totalKeys;
		int remain = 100 - coordinatorConfig.shifts;
		int firstInt = (int) ((double) interval * (double) remain / 100.0);
		int secondInt = interval - firstInt;

		for (int i = 0; i < frags.size(); i++) {
			Fragment frag = frags.get(i);
			String server = frag.getPhysicalServer();
			Fragment newFrag = this.configurationManagement.getAllConfigurations().get(1).getFragmentsList().get(i);
			List<Range> range = Lists.newArrayList();
			int end = start + interval;

			if (FragmentState.MIGRATE.equals(newFrag.getState())) {

				if (firstInt == 0) {
					Range secondRange = Range.newBuilder().setStart(newKeyStart).setEnd(newKeyStart + secondInt)
							.build();
					range.add(secondRange);
				} else {
					Range firstRange = Range.newBuilder().setStart(start).setEnd(firstInt + start).build();
					range.add(firstRange);

					if (secondInt != 0) {
						Range secondRange = Range.newBuilder().setStart(newKeyStart).setEnd(newKeyStart + secondInt)
								.build();
						range.add(secondRange);
					}
				}
				start += interval;
				newKeyStart += secondInt;
			} else {
				range.add(Range.newBuilder().setStart(start).setEnd(end).build());
				start += interval;
			}

			int load = coordinatorConfig.loadPerServer.get(server);
			freq = (double) load / (double) serverFragments.get(server).size();

			freqFrags.add(Fragment.newBuilder().setFragmentId(i).setFrequency(freq).addAllRange(range).build());
		}

		allConfigurations.add(WorkloadConfiguration.newBuilder().setConfigNumber(1).setBase(base).setMax(totalKeys)
				.setWorkload(Workload.NORMAL).addAllFragments(freqFrags).build());
		timeToUpdateConfig.addAll(coordinatorConfig.loadUpdateTime);
	}

	public int override(int original, Optional<Integer> override) {
		return override.orElse(original);
	}

	public List<WorkloadConfiguration> getAllConfigurations() {
		return allConfigurations;
	}

	public TreeSet<Integer> getTimeToUpdateConfig() {
		return timeToUpdateConfig;
	}

	public AtomicInteger getCurrentConfigNumber() {
		return currentConfigNumber;
	}

}
