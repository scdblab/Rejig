package edu.usc.dblab.nvcache.base;

public class HashingTest {

//	@Test
//	public void test() {
//		ConsistentHashing hashing = new ConsistentHashing();
//		Map<String, Integer> count = Maps.newHashMap();
//		for (int i = 0; i < 5; i++) {
//			hashing.add("t" + i, 10000);
//			count.put("t" + i, 0);
//		}
//		for (int i = 0; i < 1000; i++) {
//			String server = hashing.get("user" + i);
//			count.put(server, count.get(server) + 1);
//		}
//		count.forEach((server, c) -> {
//			System.out.println(server + ", " + c);
//		});
//	}
//
//	@Test
//	public void testDHashing() {
//		List<String> servers = Lists.newArrayList("1", "2", "3", "4", "5");
//		int numberOfVirtualization = 10000;
//		Map<String, ServerInfo> serverInfo = Maps.newHashMap();
//		Map<String, Integer> count = Maps.newHashMap();
//		servers.forEach(server -> {
//			serverInfo.put(server,
//					new ServerConfigurationManagement.CacheServerInfo(server, -1, -1, CacheServerState.NORMAL));
//			count.put(server, 0);
//		});
//		DeterministicConsistentHashing hashing = new DeterministicConsistentHashing(servers, numberOfVirtualization,
//				serverInfo);
//		for (int i = 0; i < 1000000; i++) {
//			String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//			count.put(server, count.get(server) + 1);
//		}
//
//		count.forEach((server, c) -> {
//			System.out.println(server + ", " + c);
//		});
//	}
//
//	@Test
//	public void testDHashingUpdateFail() {
//		List<String> servers = Lists.newArrayList("1", "2", "3", "4", "5");
//		int numberOfVirtualization = 10000;
//
//		List<List<String>> failedServers = Lists.newArrayList();
//		failedServers.add(Lists.newArrayList("1"));
//		failedServers.add(Lists.newArrayList("1", "2"));
//		failedServers.add(Lists.newArrayList("1", "2", "3"));
//		failedServers.add(Lists.newArrayList("1", "2", "3", "4"));
//
//		for (List<String> failedServer : failedServers) {
//			Map<String, ServerInfo> serverInfo = Maps.newHashMap();
//			Map<Integer, String> keyServer = Maps.newHashMap();
//			servers.forEach(server -> {
//				serverInfo.put(server,
//						new ServerConfigurationManagement.CacheServerInfo(server, -1, -1, CacheServerState.NORMAL));
//			});
//			DeterministicConsistentHashing hashing = new DeterministicConsistentHashing(servers, numberOfVirtualization,
//					serverInfo);
//			for (int i = 0; i < 1000000; i++) {
//				String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//				keyServer.put(i, server);
//			}
//
//			Assert.assertEquals(0, hashing.getCurrentConfiguration().getFailNodeHashConfigMap().size());
//			Assert.assertEquals(0, hashing.getCurrentConfiguration().getRecoverNodeConfigMap().size());
//
//			hashing.updateHashing(failedServer, Maps.newHashMap(), Lists.newArrayList(), Lists.newArrayList(),
//					new Function<ConfigurationUpdate, Map<String, ServerInfo>>() {
//						@Override
//						public Map<String, ServerInfo> apply(ConfigurationUpdate t) {
//							return serverInfo;
//						}
//					});
//			// mapping should not change for existing servers.
//			for (int i = 0; i < 1000000; i++) {
//				String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//				Assert.assertFalse(failedServer.contains(server));
//				if (!failedServer.contains(keyServer.get(i))) {
//					Assert.assertEquals(server, keyServer.get(i));
//				}
//			}
//			Assert.assertEquals(failedServer.size(),
//					hashing.getCurrentConfiguration().getFailNodeHashConfigMap().size());
//			failedServer.forEach(server -> {
//				Assert.assertEquals(0,
//						hashing.getCurrentConfiguration().getFailNodeHashConfigMap().get(server).intValue());
//			});
//			Assert.assertEquals(servers.size() - failedServer.size(),
//					hashing.getCurrentConfiguration().getServers().size());
//			Assert.assertEquals(1, hashing.getCurrentConfigurationNumber());
//			DeterministicConsistentHashing oHashing = new DeterministicConsistentHashing(servers,
//					numberOfVirtualization, serverInfo);
//			for (int i = 0; i < numberOfVirtualization; i++) {
//				Assert.assertEquals(oHashing.getCurrentConfiguration().getServersHashingSpace().get(i),
//						hashing.getConfiguration(0).getServersHashingSpace().get(i));
//			}
//
//		}
//	}
//
//	@Test
//	public void testDHashingUpdateFailWithRecover() {
//		List<String> servers = Lists.newArrayList("1", "2", "3", "4", "5");
//		int numberOfVirtualization = 10000;
//
//		List<List<String>> failedServers = Lists.newArrayList();
//		failedServers.add(Lists.newArrayList("1"));
//		failedServers.add(Lists.newArrayList("1", "2"));
//		failedServers.add(Lists.newArrayList("1", "2", "3"));
//		failedServers.add(Lists.newArrayList("1", "2", "3", "4"));
//
//		for (List<String> failedServer : failedServers) {
//			Map<String, ServerInfo> serverInfo = Maps.newHashMap();
//			Map<Integer, String> keyServer = Maps.newHashMap();
//			servers.forEach(server -> {
//				serverInfo.put(server,
//						new ServerConfigurationManagement.CacheServerInfo(server, -1, -1, CacheServerState.NORMAL));
//			});
//			DeterministicConsistentHashing hashing = new DeterministicConsistentHashing(servers, numberOfVirtualization,
//					serverInfo);
//			for (int i = 0; i < 1000000; i++) {
//				String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//				keyServer.put(i, server);
//			}
//
//			Assert.assertEquals(0, hashing.getCurrentConfiguration().getFailNodeHashConfigMap().size());
//			Assert.assertEquals(0, hashing.getCurrentConfiguration().getRecoverNodeConfigMap().size());
//
//			hashing.updateHashing(failedServer, Maps.newHashMap(), Lists.newArrayList(), Lists.newArrayList(),
//					new Function<ConfigurationUpdate, Map<String, ServerInfo>>() {
//						@Override
//						public Map<String, ServerInfo> apply(ConfigurationUpdate t) {
//							return serverInfo;
//						}
//					});
//			// mapping should not change for existing servers.
//			for (int i = 0; i < 1000000; i++) {
//				String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//				Assert.assertFalse(failedServer.contains(server));
//				if (!failedServer.contains(keyServer.get(i))) {
//					Assert.assertEquals(server, keyServer.get(i));
//				}
//			}
//			Assert.assertEquals(failedServer.size(),
//					hashing.getCurrentConfiguration().getFailNodeHashConfigMap().size());
//			failedServer.forEach(server -> {
//				Assert.assertEquals(0,
//						hashing.getCurrentConfiguration().getFailNodeHashConfigMap().get(server).intValue());
//			});
//			Assert.assertEquals(servers.size() - failedServer.size(),
//					hashing.getCurrentConfiguration().getServers().size());
//			Assert.assertEquals(1, hashing.getCurrentConfigurationNumber());
//			DeterministicConsistentHashing oHashing = new DeterministicConsistentHashing(servers,
//					numberOfVirtualization, serverInfo);
//			for (int i = 0; i < numberOfVirtualization; i++) {
//				Assert.assertEquals(oHashing.getCurrentConfiguration().getServersHashingSpace().get(i),
//						hashing.getConfiguration(0).getServersHashingSpace().get(i));
//			}
//
//			hashing.updateHashing(Lists.newArrayList(), Maps.newHashMap(), failedServer, Lists.newArrayList(),
//					new Function<ConfigurationUpdate, Map<String, ServerInfo>>() {
//						@Override
//						public Map<String, ServerInfo> apply(ConfigurationUpdate t) {
//							return serverInfo;
//						}
//					});
//			// mapping should not change at all.
//			for (int i = 0; i < 1000000; i++) {
//				String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//				Assert.assertEquals(server, keyServer.get(i));
//			}
//		}
//	}
//
//	@Test
//	public void testDHashingUpdateMultiFailWithMultiRecover() {
//		List<String> servers = Lists.newArrayList("1", "2", "3", "4", "5");
//		int numberOfVirtualization = 10000;
//
//		Map<String, ServerInfo> serverInfo = Maps.newHashMap();
//		Map<Integer, String> keyServer = Maps.newHashMap();
//		Map<String, List<Integer>> serverKeys = Maps.newHashMap();
//		servers.forEach(server -> {
//			serverInfo.put(server,
//					new ServerConfigurationManagement.CacheServerInfo(server, -1, -1, CacheServerState.NORMAL));
//			serverKeys.put(server, Lists.newArrayList());
//		});
//		DeterministicConsistentHashing hashing = new DeterministicConsistentHashing(servers, numberOfVirtualization,
//				serverInfo);
//		for (int i = 0; i < 1000000; i++) {
//			String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//			keyServer.put(i, server);
//			serverKeys.get(server).add(i);
//		}
//		System.out.println(hashing.getCurrentConfiguration().getServersHashingSpace());
//		int expectedConfigNumber = 0;
//		Assert.assertEquals(expectedConfigNumber, hashing.getCurrentConfigurationNumber());
//		Assert.assertEquals(0, hashing.getCurrentConfiguration().getFailNodeHashConfigMap().size());
//		Assert.assertEquals(0, hashing.getCurrentConfiguration().getRecoverNodeConfigMap().size());
//
//		List<List<String>> failedServerTimeline = Lists.newArrayList(Lists.newArrayList("1"),
//				Lists.newArrayList("2", "3"), Lists.newArrayList("4"));
//
//		Set<String> allFailedServer = Sets.newHashSet();
//		Map<String, Integer> failConfig = Maps.newHashMap();
//		failConfig.put("1", 0);
//		failConfig.put("2", 1);
//		failConfig.put("3", 1);
//		failConfig.put("4", 2);
//		for (int i = 0; i < failedServerTimeline.size(); i++) {
//			List<String> failedServer = failedServerTimeline.get(i);
//			allFailedServer.addAll(failedServer);
//
//			hashing.updateHashing(failedServer, Maps.newHashMap(), Lists.newArrayList(), Lists.newArrayList(),
//					new Function<ConfigurationUpdate, Map<String, ServerInfo>>() {
//						@Override
//						public Map<String, ServerInfo> apply(ConfigurationUpdate t) {
//							return serverInfo;
//						}
//					});
//			System.out.println("fail " + failedServer);
//			System.out.println(hashing.getCurrentConfiguration().getServersHashingSpace());
//			expectedConfigNumber++;
//			Assert.assertEquals(expectedConfigNumber, hashing.getCurrentConfigurationNumber());
//			// mapping should not change for existing servers.
//			for (int j = 0; j < 1000000; j++) {
//				String server = hashing.getCurrentConfiguration().get(String.valueOf(j));
//				Assert.assertFalse(allFailedServer.contains(server));
//				if (!allFailedServer.contains(keyServer.get(j))) {
//					Assert.assertEquals(server, keyServer.get(j));
//				}
//			}
//
//			Assert.assertEquals(allFailedServer.size(),
//					hashing.getCurrentConfiguration().getFailNodeHashConfigMap().size());
//			hashing.getCurrentConfiguration().getFailNodeHashConfigMap().forEach((f, c) -> {
//				Assert.assertEquals(failConfig.get(f), c);
//			});
//			Assert.assertEquals(0, hashing.getCurrentConfiguration().getRecoverNodeConfigMap().size());
//		}
//
//		List<String> recover = Lists.newArrayList("4", "3", "2", "1");
//		Map<String, Integer> recoverConfig = Maps.newHashMap();
//		recoverConfig.put("4", 3);
//		recoverConfig.put("3", 4);
//		recoverConfig.put("2", 5);
//		recoverConfig.put("1", 6);
//		for (int i = 0; i < recover.size(); i++) {
//			hashing.updateHashing(Lists.newArrayList(), Maps.newHashMap(), Lists.newArrayList(recover.get(i)),
//					Lists.newArrayList(), new Function<ConfigurationUpdate, Map<String, ServerInfo>>() {
//						@Override
//						public Map<String, ServerInfo> apply(ConfigurationUpdate t) {
//							return serverInfo;
//						}
//					});
//			System.out.println("recover " + recover.get(i));
//			System.out.println(hashing.getCurrentConfiguration().getServersHashingSpace());
//			expectedConfigNumber++;
//			Assert.assertEquals(expectedConfigNumber, hashing.getCurrentConfigurationNumber());
//			final String rServer = recover.get(i);
//			allFailedServer.remove(rServer);
//			serverKeys.get(recover.get(i)).forEach(key -> {
//				String server = hashing.getCurrentConfiguration().get(String.valueOf(key));
//				Assert.assertEquals(rServer, server);
//			});
//
//			Assert.assertEquals(allFailedServer.size(),
//					hashing.getCurrentConfiguration().getFailNodeHashConfigMap().size());
//			hashing.getCurrentConfiguration().getFailNodeHashConfigMap().forEach((f, c) -> {
//				Assert.assertEquals(failConfig.get(f), c);
//			});
//			Assert.assertEquals(i + 1, hashing.getCurrentConfiguration().getRecoverNodeConfigMap().size());
//			hashing.getCurrentConfiguration().getRecoverNodeConfigMap().forEach((r, c) -> {
//				Assert.assertEquals(recoverConfig.get(r), c);
//			});
//		}
//		Assert.assertEquals(0, hashing.getCurrentConfiguration().getFailNodeHashConfigMap().size());
//		Assert.assertEquals(7, hashing.getCurrentConfigurationNumber());
//		System.out.println(hashing.getCurrentConfiguration().getServersHashingSpace());
//		// mapping should not change at all.
//		for (int i = 0; i < 1000000; i++) {
//			String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//			Assert.assertEquals(server, keyServer.get(i));
//		}
//	}
//
//	@Test
//	public void testDHashingUpdateFailWithStandby() {
//		List<String> servers = Lists.newArrayList("1", "2", "3", "4", "5");
//		int numberOfVirtualization = 10000;
//
//		Map<String, String> failStandby = Maps.newHashMap();
//		failStandby.put("1", "6");
//		failStandby.put("2", "7");
//
//		Map<String, String> standbyFail = Maps.newHashMap();
//		standbyFail.put("6", "1");
//		standbyFail.put("7", "2");
//
//		Map<String, ServerInfo> serverInfo = Maps.newHashMap();
//		Map<Integer, String> keyServer = Maps.newHashMap();
//		servers.forEach(server -> {
//			serverInfo.put(server,
//					new ServerConfigurationManagement.CacheServerInfo(server, -1, -1, CacheServerState.NORMAL));
//		});
//		DeterministicConsistentHashing hashing = new DeterministicConsistentHashing(servers, numberOfVirtualization,
//				serverInfo);
//		for (int i = 0; i < 1000000; i++) {
//			String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//			keyServer.put(i, server);
//		}
//		hashing.updateHashing(Lists.newArrayList(), failStandby, Lists.newArrayList(), Lists.newArrayList(),
//				new Function<ConfigurationUpdate, Map<String, ServerInfo>>() {
//					@Override
//					public Map<String, ServerInfo> apply(ConfigurationUpdate t) {
//						return serverInfo;
//					}
//				});
//		// mapping should not change for existing servers.
//		for (int i = 0; i < 1000000; i++) {
//			String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//			Assert.assertFalse(failStandby.containsKey(server));
//			if (standbyFail.containsKey(server)) {
//				Assert.assertEquals(server, failStandby.get(keyServer.get(i)));
//			} else {
//				Assert.assertEquals(server, keyServer.get(i));
//			}
//		}
//		Assert.assertEquals(failStandby.size(), hashing.getCurrentConfiguration().getFailNodeHashConfigMap().size());
//		failStandby.keySet().forEach(server -> {
//			Assert.assertEquals(0, hashing.getCurrentConfiguration().getFailNodeHashConfigMap().get(server).intValue());
//		});
//		Assert.assertEquals(servers.size(), hashing.getCurrentConfiguration().getServers().size());
//		Assert.assertEquals(1, hashing.getCurrentConfigurationNumber());
//		DeterministicConsistentHashing oHashing = new DeterministicConsistentHashing(servers, numberOfVirtualization,
//				serverInfo);
//		for (int i = 0; i < numberOfVirtualization; i++) {
//			Assert.assertEquals(oHashing.getCurrentConfiguration().getServersHashingSpace().get(i),
//					hashing.getConfiguration(0).getServersHashingSpace().get(i));
//		}
//	}
//
//	@Test
//	public void testDHashingUpdateFailWithStandbyRecover() {
//		List<String> servers = Lists.newArrayList("1", "2", "3", "4", "5");
//		int numberOfVirtualization = 10000;
//
//		Map<String, String> failStandby = Maps.newHashMap();
//		failStandby.put("1", "6");
//		failStandby.put("2", "7");
//
//		Map<String, String> standbyFail = Maps.newHashMap();
//		standbyFail.put("6", "1");
//		standbyFail.put("7", "2");
//
//		Map<String, ServerInfo> serverInfo = Maps.newHashMap();
//		Map<Integer, String> keyServer = Maps.newHashMap();
//		servers.forEach(server -> {
//			serverInfo.put(server,
//					new ServerConfigurationManagement.CacheServerInfo(server, -1, -1, CacheServerState.NORMAL));
//		});
//		DeterministicConsistentHashing hashing = new DeterministicConsistentHashing(servers, numberOfVirtualization,
//				serverInfo);
//		for (int i = 0; i < 1000000; i++) {
//			String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//			keyServer.put(i, server);
//		}
//		hashing.updateHashing(Lists.newArrayList(), failStandby, Lists.newArrayList(), Lists.newArrayList(),
//				new Function<ConfigurationUpdate, Map<String, ServerInfo>>() {
//					@Override
//					public Map<String, ServerInfo> apply(ConfigurationUpdate t) {
//						return serverInfo;
//					}
//				});
//		// mapping should not change for existing servers.
//		for (int i = 0; i < 1000000; i++) {
//			String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//			Assert.assertFalse(failStandby.containsKey(server));
//			if (standbyFail.containsKey(server)) {
//				Assert.assertEquals(server, failStandby.get(keyServer.get(i)));
//			} else {
//				Assert.assertEquals(server, keyServer.get(i));
//			}
//		}
//		Assert.assertEquals(failStandby.size(), hashing.getCurrentConfiguration().getFailNodeHashConfigMap().size());
//		failStandby.keySet().forEach(server -> {
//			Assert.assertEquals(0, hashing.getCurrentConfiguration().getFailNodeHashConfigMap().get(server).intValue());
//		});
//		Assert.assertEquals(servers.size(), hashing.getCurrentConfiguration().getServers().size());
//		Assert.assertEquals(1, hashing.getCurrentConfigurationNumber());
//		DeterministicConsistentHashing oHashing = new DeterministicConsistentHashing(servers, numberOfVirtualization,
//				serverInfo);
//		for (int i = 0; i < numberOfVirtualization; i++) {
//			Assert.assertEquals(oHashing.getCurrentConfiguration().getServersHashingSpace().get(i),
//					hashing.getConfiguration(0).getServersHashingSpace().get(i));
//		}
//		
//		hashing.updateHashing(Lists.newArrayList(), Maps.newHashMap(), Lists.newArrayList("1", "2"), Lists.newArrayList(),
//				new Function<ConfigurationUpdate, Map<String, ServerInfo>>() {
//					@Override
//					public Map<String, ServerInfo> apply(ConfigurationUpdate t) {
//						return serverInfo;
//					}
//				});
//		System.out.println(hashing.getCurrentConfiguration().getServersHashingSpace());
//	}
//
//	@Test
//	public void testDHashingUpdateFailWithNewNodeAndRecover() {
//		List<String> servers = Lists.newArrayList("1", "2", "3", "4", "5");
//		int numberOfVirtualization = 10000;
//
//		List<String> failNodes = Lists.newArrayList();
//		failNodes.add("1");
//
//		Map<String, ServerInfo> serverInfo = Maps.newHashMap();
//		Map<Integer, String> keyServer = Maps.newHashMap();
//		servers.forEach(server -> {
//			serverInfo.put(server,
//					new ServerConfigurationManagement.CacheServerInfo(server, -1, -1, CacheServerState.NORMAL));
//		});
//		DeterministicConsistentHashing hashing = new DeterministicConsistentHashing(servers, numberOfVirtualization,
//				serverInfo);
//		for (int i = 0; i < 1000000; i++) {
//			String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//			keyServer.put(i, server);
//		}
//		hashing.updateHashing(failNodes, Maps.newHashMap(), Lists.newArrayList(), Lists.newArrayList(),
//				new Function<ConfigurationUpdate, Map<String, ServerInfo>>() {
//					@Override
//					public Map<String, ServerInfo> apply(ConfigurationUpdate t) {
//						return serverInfo;
//					}
//				});
//		int diff = 0;
//		for (int i = 0; i < 1000000; i++) {
//			String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//			if (!keyServer.get(i).equals(server)) {
//				diff++;
//			}
//		}
//		System.out.println(diff);
//		List<String> newNodes = Lists.newArrayList();
//		newNodes.add("6");
//		hashing.updateHashing(Lists.newArrayList(), Maps.newHashMap(), Lists.newArrayList(), newNodes,
//				new Function<ConfigurationUpdate, Map<String, ServerInfo>>() {
//					@Override
//					public Map<String, ServerInfo> apply(ConfigurationUpdate t) {
//						return serverInfo;
//					}
//				});
//		Assert.assertEquals(2, hashing.getCurrentConfigurationNumber());
//		diff = 0;
//		for (int i = 0; i < 1000000; i++) {
//			String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//			if (!keyServer.get(i).equals(server)) {
//				diff++;
//			}
//		}
//		System.out.println(diff);
//		Assert.assertEquals(servers.size(), hashing.getCurrentConfiguration().getServers().size());
//		hashing.updateHashing(Lists.newArrayList(), Maps.newHashMap(), failNodes, Lists.newArrayList(),
//				new Function<ConfigurationUpdate, Map<String, ServerInfo>>() {
//					@Override
//					public Map<String, ServerInfo> apply(ConfigurationUpdate t) {
//						return serverInfo;
//					}
//				});
//		Assert.assertEquals(3, hashing.getCurrentConfigurationNumber());
//		diff = 0;
//		for (int i = 0; i < 1000000; i++) {
//			String server = hashing.getCurrentConfiguration().get(String.valueOf(i));
//			if (!keyServer.get(i).equals(server)) {
//				diff++;
//			}
//		}
//		System.out.println(diff);
//	}

}
