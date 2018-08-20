package edu.usc.dblab.nvcache.base;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import com.google.common.collect.Maps;

import edu.usc.dblab.cache.base.AssignmentConfiguration;
import edu.usc.dblab.nvcache.config.CacheStaticConfiguration;
import edu.usc.dblab.nvcache.config.CacheWorkloadConfigurationManagement;
import edu.usc.dblab.nvcache.config.CacheStaticConfiguration.StaticConfiguration;
import edu.usc.dblab.nvcache.config.ConfigurationManagement;

public class MigrationConfigTest {

	private final String uniform_config = "/Users/haoyuh/Documents/PhdUSC/CADS_YCSB/migration/src/main/resources/cache_coordinator_migrate_from_shift_100.conf";
	private final String workload_config = "/Users/haoyuh/Documents/PhdUSC/CADS_YCSB/migration/src/main/resources/cache_normal_workload.conf";

	@Test
	public void testMigrationConfig() {
		StaticConfiguration config = CacheStaticConfiguration.read(uniform_config, new Properties());
		System.out.println(config);

		ConfigurationManagement management = new ConfigurationManagement(config);
		management.populate();
		CacheWorkloadConfigurationManagement workload = new CacheWorkloadConfigurationManagement(config, management);
		workload.prepareConfigs(workload_config);

		System.out.println(workload.getAllConfigurations());

		List<AssignmentConfiguration> configs = management.getAllConfigurations();

		for (int i = 0; i < configs.size(); i++) {
			Map<String, Integer> serverFrags = Maps.newHashMap();
			configs.get(i).getFragmentsList().forEach(frag -> {
				serverFrags.compute(frag.getPhysicalServer(), (k, v) -> {
					if (v == null) {
						return 1;
					}
					return v + 1;
				});
			});

			System.out.println(serverFrags);
		}
	}
}
