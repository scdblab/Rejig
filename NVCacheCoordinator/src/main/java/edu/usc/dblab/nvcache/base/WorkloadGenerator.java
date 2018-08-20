package edu.usc.dblab.nvcache.base;

import java.util.List;

import com.google.common.collect.Lists;

import edu.usc.dblab.cache.base.WorkloadConfiguration;
import edu.usc.dblab.cache.base.WorkloadConfiguration.Workload;

public class WorkloadGenerator {
	public static List<WorkloadConfiguration> generateHotIn(int base, int max, int numberOfShiftColdKeys,
			int shiftFreqSecond, int expTime) {
		int time = expTime / shiftFreqSecond;
		return generateHotIn(base, max, numberOfShiftColdKeys, time);
	}

	public static List<WorkloadConfiguration> generateHotIn(int base, int max, int numberOfShiftColdKeys, int time) {
		List<WorkloadConfiguration> configs = Lists.newArrayList();
		for (int round = 1; round <= time; round++) {
			configs.add(WorkloadConfiguration.newBuilder().setConfigNumber(round).setBase(base).setMax(max)
					.setParam1(numberOfShiftColdKeys).setParam2(round).setWorkload(Workload.HOT_IN).build());
		}
		return configs;
	}

	public static List<WorkloadConfiguration> generateHotOut(int base, int max, int numberOfShiftHotKeys,
			int shiftFreqSecond, int expTime) {
		int time = expTime / shiftFreqSecond;
		return generateHotOut(base, max, numberOfShiftHotKeys, time);
	}

	public static List<WorkloadConfiguration> generateHotOut(int base, int max, int numberOfShiftHotKeys, int time) {
		List<WorkloadConfiguration> configs = Lists.newArrayList();
		for (int round = 1; round <= time; round++) {
			configs.add(WorkloadConfiguration.newBuilder().setConfigNumber(round).setBase(base).setMax(max)
					.setParam1(numberOfShiftHotKeys).setParam2(round).setWorkload(Workload.HOT_OUT).build());
		}
		return configs;
	}

	public static List<WorkloadConfiguration> generateRandom(int base, int max, int candidatePopularKeys,
			int numberOfReplacement, int shiftFreqSecond, int expTime) {
		int time = expTime / shiftFreqSecond;
		return generateRandom(base, max, candidatePopularKeys, numberOfReplacement, time);
	}

	public static List<WorkloadConfiguration> generateRandom(int base, int max, int candidatePopularKeys,
			int numberOfReplacement, int time) {
		List<WorkloadConfiguration> configs = Lists.newArrayList();
		for (int round = 1; round <= time; round++) {
			configs.add(WorkloadConfiguration.newBuilder().setConfigNumber(round).setBase(base).setMax(max)
					.setParam1(candidatePopularKeys).setParam2(numberOfReplacement).setWorkload(Workload.RANDOM)
					.build());
		}
		return configs;
	}

	public static List<WorkloadConfiguration> generateShift(int base, int max, int maxWorkingSet,
			int shiftWorkingsetDelta, int shiftFreqSecond, int expTime) {
		int time = (expTime / shiftFreqSecond) + 1;
		return generateShift(base, max, maxWorkingSet, shiftWorkingsetDelta, time);
	}

	public static List<WorkloadConfiguration> generateShift(int base, int max, int maxWorkingSet,
			int shiftWorkingsetDelta, int time) {
		List<WorkloadConfiguration> configs = Lists.newArrayList();
		int baseWorkingSet = base;
		int shift = (int) (maxWorkingSet * ((double) shiftWorkingsetDelta / 100.0));
		for (int round = 0; round <= time; round++) {
			configs.add(WorkloadConfiguration.newBuilder().setConfigNumber(round).setBase(base).setMax(max)
					.setParam1(baseWorkingSet).setParam2(maxWorkingSet).setWorkload(Workload.SHIFT).build());
			baseWorkingSet += shift;
			if (baseWorkingSet + maxWorkingSet > max) {
				baseWorkingSet = base;
			}
		}
		return configs;
	}

}
