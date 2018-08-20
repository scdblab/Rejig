package edu.usc.dblab.nvcache.base;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.usc.dblab.cache.base.WorkloadConfiguration.Workload;

public class CacheCoordinatorConfig {
	private final Optional<Workload> workload;
	private final Optional<Integer> base;
	private final Optional<Integer> max;
	private final Optional<Integer> numberOfShiftColdKeys;
	private final Optional<Integer> numberOfShiftHotKeys;
	private final Optional<Integer> candidatePopularKeys;
	private final Optional<Integer> numberOfReplacement;

	private final Optional<Integer> maxWorkingset;
	private final Optional<Integer> shiftWorkingsetDelta;

	private final Optional<Integer> shiftFreqSecond;
	private final Optional<List<Integer>> shiftTimeline;
	private final Optional<Integer> expTime;

	public CacheCoordinatorConfig(Properties props) {
		this.base = convert(props.getProperty("base"));
		this.max = convert(props.getProperty("max"));
		
		if (props.containsKey("workload")) {
			this.workload = Optional.ofNullable(Workload.valueOf(props.getProperty("workload")));
		} else {
			this.workload = Optional.empty();
		}
		this.numberOfShiftColdKeys = convert(props.getProperty("numberOfShiftColdKeys"));
		this.numberOfShiftHotKeys = convert(props.getProperty("numberOfShiftHotKeys"));
		this.candidatePopularKeys = convert(props.getProperty("candidatePopularKeys"));
		this.numberOfReplacement = convert(props.getProperty("numberOfReplacement"));
		this.shiftFreqSecond = convert(props.getProperty("shiftFreqSecond"));

		this.maxWorkingset = convert(props.getProperty("maxWorkingset"));
		this.shiftWorkingsetDelta = convert(props.getProperty("shiftWorkingsetDelta"));

		this.expTime = convert(props.getProperty("expTime"));
		if (props.containsKey("shitfTimeline")) {
			this.shiftTimeline = Optional.of(Lists.newArrayList(props.getProperty("shitfTimeline").split(",")).stream()
					.map(Integer::parseInt).collect(Collectors.toList()));
		} else {
			this.shiftTimeline = Optional.empty();
		}
		Map<String, Integer> fragL = Maps.newHashMap();
		if (props.containsKey("fragLimitOnFailed")) {
			String[] limits = props.getProperty("fragLimitOnFailed").split(",");

			for (String limit : limits) {
				String server = limit.split("!")[0];
				int l = Integer.parseInt(limit.split("!")[1]);
				fragL.put(server, l);
			}
		}
	}

	public static Optional<Integer> convert(String text) {
		if (text == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(Integer.parseInt(text));
	}

	public static Optional<Boolean> convertBool(String text) {
		if (text == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(Boolean.parseBoolean(text));
	}

	public Optional<Workload> getWorkload() {
		return workload;
	}

	public Optional<Integer> getBase() {
		return base;
	}

	public Optional<Integer> getMax() {
		return max;
	}

	public Optional<Integer> getNumberOfShiftColdKeys() {
		return numberOfShiftColdKeys;
	}

	public Optional<Integer> getNumberOfShiftHotKeys() {
		return numberOfShiftHotKeys;
	}

	public Optional<Integer> getCandidatePopularKeys() {
		return candidatePopularKeys;
	}

	public Optional<Integer> getNumberOfReplacement() {
		return numberOfReplacement;
	}

	public Optional<Integer> getShiftFreqSecond() {
		return shiftFreqSecond;
	}

	public Optional<Integer> getExpTime() {
		return expTime;
	}

	public Optional<Integer> getMaxWorkingset() {
		return maxWorkingset;
	}

	public Optional<Integer> getShiftWorkingsetDelta() {
		return shiftWorkingsetDelta;
	}

	public Optional<List<Integer>> getShiftTimeline() {
		return shiftTimeline;
	}

}
