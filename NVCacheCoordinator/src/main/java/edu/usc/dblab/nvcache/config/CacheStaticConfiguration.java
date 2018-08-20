package edu.usc.dblab.nvcache.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.usc.dblab.nvcache.config.CacheStaticConfiguration.StaticConfiguration.AccessDist;
import edu.usc.dblab.nvcache.config.CacheStaticConfiguration.StaticConfiguration.AssignmentAlgorithm;

public class CacheStaticConfiguration {

	public static final class StaticConfiguration {
		public List<String> initServers = Lists.newArrayList();
		public Map<String, Integer> initFragmentsPerServer = Maps.newHashMap();
		public int totalNumberOfFragments = 0;
		public int totalNumberOfKeys = 0;
		public AssignmentAlgorithm assignmentAlgorithm;
		public AccessDist accessDist;
		public int port;
		public boolean enableMigration;
		public Map<String, Integer> loadPerServer = Maps.newHashMap();
		public int shifts = 0;

		public static enum AssignmentAlgorithm {
			GREEDY_EXTENDED, UNIFORM
		}

		public static enum AccessDist {
			ZIPFIAN, UNIFORM
		}

		public List<String> newServers = Lists.newArrayList();
		public List<Integer> configurationUpdateTime = Lists.newArrayList();
		public List<Integer> loadUpdateTime = Lists.newArrayList();
	}

	public static StaticConfiguration read(String propFile, Properties props) {
		// cache servers in config-0.
		// number of fragments to each server.
		// total number of fragments.
		// reassign algorithm.
		// cache servers in config-1.

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(propFile)));
			String line = null;
			Map<String, String> configProp = Maps.newHashMap();
			while ((line = reader.readLine()) != null) {
				configProp.put(line.split("=")[0], line.split("=")[1]);
			}
			props.forEach((k, v) -> {
				configProp.put(String.valueOf(k), String.valueOf(v));
			});
			StaticConfiguration config = new StaticConfiguration();

			config.initServers = Lists.newArrayList(configProp.get("initServers").split(","));
			String[] initFragServer = configProp.get("initFragmentsPerServer").split(",");
			int i = 0;
			while (i < initFragServer.length) {
				String server = initFragServer[i];
				i++;
				String fragCount = initFragServer[i];
				i++;
				config.initFragmentsPerServer.put(server, Integer.parseInt(fragCount));
			}

			if (configProp.containsKey("imbalanceLoadPerServer")) {
				String[] loadPerServer = configProp.get("imbalanceLoadPerServer").split(",");
				i = 0;
				while (i < loadPerServer.length) {
					String server = loadPerServer[i];
					i++;
					String fragCount = loadPerServer[i];
					i++;
					config.loadPerServer.put(server, Integer.parseInt(fragCount));
				}
			}

			config.totalNumberOfFragments = Integer.parseInt(configProp.get("totalNumberOfFragments"));
			config.totalNumberOfKeys = Integer.parseInt(configProp.get("totalNumberOfKeys"));

			config.assignmentAlgorithm = AssignmentAlgorithm
					.valueOf(configProp.get("assignmentAlgorithm").toUpperCase());
			config.accessDist = AccessDist.valueOf(configProp.get("accessDist").toUpperCase());
			if (configProp.containsKey("newServers")) {
				config.newServers = Lists.newArrayList(configProp.get("newServers").split(","));
			}
			if (configProp.containsKey("configurationUpdateTime")) {
				config.configurationUpdateTime = Lists
						.newArrayList(configProp.get("configurationUpdateTime").split(",")).stream().map(j -> {
							return Integer.parseInt(j);
						}).collect(Collectors.toList());
			}
			if (configProp.containsKey("loadUpdateTime")) {
				config.loadUpdateTime = Lists.newArrayList(configProp.get("loadUpdateTime").split(",")).stream()
						.map(j -> {
							return Integer.parseInt(j);
						}).collect(Collectors.toList());
			}

			config.port = Integer.parseInt(configProp.get("port"));
			config.enableMigration = Boolean.parseBoolean(configProp.get("enableMigration"));
			config.shifts = Integer.parseInt(configProp.get("shifts"));

			reader.close();
			return config;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
