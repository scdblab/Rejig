package edu.usc.dblab.nvcache.base;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

import edu.usc.dblab.nvcache.config.CacheWorkloadConfigurationManagement;
import edu.usc.dblab.nvcache.config.ConfigurationManagement;

public class CacheStateSimulator implements Callable<Void> {

	private final Logger logger = Logger.getLogger(CacheStateSimulator.class);

	private final ConfigurationManagement configurationManagement;
	private final CacheWorkloadConfigurationManagement workloadConfigurationManagement;
	private final Map<String, MemcachedClient> clients;
	private final ExecutorService executorService;
	private boolean isRunning;

	public CacheStateSimulator(CacheWorkloadConfigurationManagement workloadConfigurationManagement,
			CacheCoordinatorConfig config, ConfigurationManagement configurationManagement) {
		super();
		this.configurationManagement = configurationManagement;
		this.workloadConfigurationManagement = workloadConfigurationManagement;
		this.isRunning = true;
		this.clients = Maps.newHashMap();
		List<String> servers = Lists.newArrayList(configurationManagement.getStaticConfiguration().newServers);
		servers.addAll(configurationManagement.getStaticConfiguration().initServers);
		servers.forEach(server -> {
			clients.put(server, init(server));
			try {
				clients.get(server).updateServerConfigurationNumber(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		this.executorService = Executors.newFixedThreadPool(clients.size() * 2);
	}

	public MemcachedClient init(String server) {
		String[] serverlist = { server };

		// initialize the pool for memcache servers
		String uid = UUID.randomUUID().toString();
		SockIOPool pool = SockIOPool.getInstance(uid);
		pool.setServers(serverlist);

		pool.setInitConn(1);
		pool.setMinConn(1);
		pool.setMaxConn(10);
		pool.setMaintSleep(0);
		pool.setNagle(false);
		pool.initialize();

		// get client instance
		MemcachedClient mc = new MemcachedClient(uid);
		mc.setCompressEnable(false);
		return mc;
	}

	@Override
	public Void call() throws Exception {
		long startTime = System.currentTimeMillis();
		while (isRunning) {
			long now = System.currentTimeMillis();
			int currentSecond = (int) ((now - startTime) / 1000);
			Integer cacheConfigTime = this.configurationManagement.getTimeToUpdateConfig().lower(currentSecond);
			Integer workloadConfigTime = workloadConfigurationManagement.getTimeToUpdateConfig().lower(currentSecond);
			if (workloadConfigTime != null) {
				logger.info("Detected new workload configuration "
						+ (workloadConfigurationManagement.getCurrentConfigNumber().get() + 1));
				workloadConfigurationManagement.getTimeToUpdateConfig().remove(workloadConfigTime);
				workloadConfigurationManagement.getCurrentConfigNumber().incrementAndGet();
				logger.info("Moved to new workload configuration");
			}

			if (cacheConfigTime != null) {
				System.out.println("#######Moved to next config");
				configurationManagement.getTimeToUpdateConfig().remove(cacheConfigTime);
				int nextConfigNumber = configurationManagement.getCurrentConfigNumber().get() + 1;
				logger.info("Detected new configuration " + nextConfigNumber);
				// update all servers.
				List<Future<Void>> futures = Lists.newArrayList();
				clients.values().forEach(client -> {
					futures.add(this.executorService.submit(new Callable<Void>() {
						@Override
						public Void call() throws Exception {
							try {
								client.updateServerConfigurationNumber(nextConfigNumber);
							} catch (Exception e) {
								e.printStackTrace();
							}
							return null;
						}
					}));
				});

				futures.forEach(future -> {
					try {
						future.get();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				logger.info("Moved to new configuration " + nextConfigNumber);
				configurationManagement.incrementCfgId();
			}
			Thread.sleep(100);
		}
		return null;
	}

}
