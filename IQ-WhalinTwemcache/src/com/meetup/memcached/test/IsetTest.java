package com.meetup.memcached.test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.meetup.memcached.IQException;
import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

public class IsetTest {

	private static MemcachedClient init() throws Exception {

		ConsoleAppender console = new ConsoleAppender(); // create appender
		// configure the appender
		String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.ALL);
		console.activateOptions();
		// add appender to any Logger (here is root)
		Logger.getRootLogger().removeAllAppenders();
		Logger.getRootLogger().addAppender(console);

		String[] serverlist = { "127.0.0.1:11211" };

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

	@After
	public void after() throws Exception {
		MemcachedClient client = init();
		client.delete("test");
		client.cleanup();
	}

	@Before
	public void b() throws Exception {
		MemcachedClient client = init();
		client.delete("test");
		client.cleanup();
	}

	@Test
	public void test() throws Exception {
		MemcachedClient client = init();
		System.out.println(client.iset("test"));
		System.out.println(client.iqset("test", "test2"));
		System.out.println(client.get("test"));
		client.cleanup();
	}

	@Test
	public void test2() throws Exception {
		MemcachedClient client = init();
		System.out.println(client.iset("test"));
		System.out.println(client.iset("test"));

		System.out.println(client.iqset("test", "test2"));
		System.out.println(client.get("test"));
		client.cleanup();
	}

	@Test
	public void test3() throws Exception {
		MemcachedClient client = init();
		System.out.println(client.iset("test"));

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					MemcachedClient client2 = init();
					System.out.println(client2.iset("test"));
					System.out.println("Finish");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		Thread.sleep(5000);

		System.out.println(client.iqset("test", "test2"));
		System.out.println(client.get("test"));
		client.cleanup();
	}

	@Test
	public void test4() throws Exception {
		MemcachedClient client = init();
		// System.out.println(client.set("test", "test1"));
		// System.out.println(client.get("test"));
		System.out.println(client.iset("test"));
		// System.out.println(client.iset("test"));
		// System.out.println("121feqfqw#######" + client.get("test"));
		//
		System.out.println(client.iqset("test", "test2"));
		System.out.println(client.get("test"));
		client.cleanup();
	}

	@Test
	public void test5() throws Exception {
		MemcachedClient client = init();
		System.out.println(client.set("test", "test1"));
		System.out.println(client.get("test"));
		System.out.println(client.iset("test"));
		System.out.println(client.quarantineAndRegister(client.generateSID(), "test"));
		System.out.println("121feqfqw#######" + client.get("test"));

		System.out.println(client.iqset("test", "test2"));
		System.out.println(client.get("test"));
		client.cleanup();
	}

	// @Test
	public void test6() throws Exception {
		MemcachedClient client = init();
		System.out.println(client.set("test", "test1"));
		System.out.println(client.get("test"));

		System.out.println(client.quarantineAndRegister(client.generateSID(), "test"));
		System.out.println(client.iset("test"));
		System.out.println("121feqfqw#######" + client.get("test"));

		System.out.println(client.iqset("test", "test2"));
		System.out.println(client.get("test"));
		client.cleanup();
	}

	// @Test
	public void test7() throws Exception {
		MemcachedClient client = init();
		// System.out.println(client.set("test", "test1"));
		System.out.println(client.get("test"));

		System.out.println(client.quarantineAndRegister(client.generateSID(), "test"));

		// Thread.sleep(5000);

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					MemcachedClient client = init();
					System.out.println(client.iset("test"));
					System.out.println("121feqfqw#######" + client.get("test"));

					System.out.println(client.iqset("test", "test2"));
					System.out.println(client.get("test"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		thread.start();

		thread.join();

		client.cleanup();
	}

	@Test
	public void test8() throws Exception {
		MemcachedClient client = init();
		System.out.println(client.set("test", "test1"));
		System.out.println(client.get("test"));
		String id = client.generateSID();
		System.out.println(client.quarantineAndRegister(id, "test"));

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					MemcachedClient client = init();
					System.out.println(client.iset("test"));
					System.out.println("121feqfqw#######" + client.get("test"));

					System.out.println(client.iqset("test", "test2"));
					System.out.println(client.get("test"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		thread.start();

		Thread.sleep(2000);

		System.out.println(client.deleteAndRelease(id));

		thread.join();

		client.cleanup();
	}

	@Test
	public void test9() throws Exception {
		MemcachedClient client = init();
		System.out.println(client.set("test", "test1"));
		System.out.println(client.get("test"));
		String id = client.generateSID();
		System.out.println(client.iset("test"));

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					MemcachedClient client = init();
					System.out.println(client.quarantineAndRegister(client.generateSID(), "test"));
					System.out.println("121feqfqw#######" + client.get("test"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		thread.start();

		Thread.sleep(2000);

		System.out.println(client.iqset("test", "test2"));
		System.out.println(client.get("test"));

		thread.join();

		client.cleanup();
	}

	@Test
	public void test10() throws Exception {
		MemcachedClient client = init();
		System.out.println(client.set("test", "test1"));
		System.out.println(client.get("test"));
		String id = client.generateSID();
		System.out.println(client.iset("test"));

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					MemcachedClient client = init();
					System.out.println(client.iqget("test", true));
					System.out.println("121feqfqw#######" + client.get("test"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		thread.start();

		Thread.sleep(2000);

		System.out.println(client.iqset("test", "test2"));
		System.out.println(client.get("test"));

		thread.join();

		client.cleanup();
	}

	@Test
	public void test11() throws Exception {
		// lease expire.
		MemcachedClient client = init();
		client.quarantineAndRegister(client.generateSID(), "test");
		client.iset("test");
		client.iqset("test", "t");
		System.out.println(client.get("test"));

		// Thread.sleep(10000);

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					MemcachedClient client = init();
					client.iset("test");
					client.iqset("test", "t");
					System.out.println(client.get("test"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IQException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// t.start();
		// t.join();
	}

	@Test
	public void test12() throws Exception {
		// lease expire.
		MemcachedClient client = init();
		client.set("test", "t1");
		String tid = client.generateSID();
		client.quarantineAndRegister(tid, "test");

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					MemcachedClient client = init();
					System.out.println("###### " + client.get("test"));
					client.delete("test", false);
					System.out.println("###### " + client.get("test"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		Thread.sleep(1000);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					MemcachedClient client = init();
					System.out.println("!!!!!! " + client.iset("test"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		Thread.sleep(1000);

		System.out.println(client.deleteAndRelease(tid));
		System.out.println(client.get("test"));
	}

	@Test
	public void test14() throws Exception {
		// lease expire.
		MemcachedClient client = init();
		client.set("test", "t1");
		String tid = client.generateSID();
		client.quarantineAndRegister(tid, "test");

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					MemcachedClient client = init();
					System.out.println("###### " + client.get("test"));
					client.delete("test", true);
					System.out.println("###### " + client.iset("test"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		Thread.sleep(5000);

		System.out.println(client.deleteAndRelease(tid));
		System.out.println(client.get("test"));
	}

	@Test
	public void test13() throws Exception {
		// lease expire.
		List<String> tids = Collections.synchronizedList(Lists.newArrayList());
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						MemcachedClient client = init();
						String tid = client.generateSID();
						client.quarantineAndRegister(tid, "test");
						tids.add(tid);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}

		Thread.sleep(1000);

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					MemcachedClient client = init();
					System.out.println(client.delete("test", false));
					System.out.println("!!!!!! " + client.iset("test"));
					System.out.println("#############");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();

		Thread.sleep(1000);

		for (int i = 0; i < 10; i++) {
			System.out.println("^^^^^^^^^^^^^^ release " + i);
			try {
				MemcachedClient client = init();
				client.deleteAndRelease(tids.get(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		t.join();
	}

	@Test
	public void test16() throws Exception {
		MemcachedClient client = init();
		client.updateServerConfigurationNumber(1);
		client.updateLocalConfigurationNumber(0);

		try {
			client.add("test", "test");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			client.append("test", "test");
		} catch (Exception e) {
			e.printStackTrace();
		}
		client.updateLocalConfigurationNumber(1);
		try {
			client.add("test", "test");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			client.append("test", "test");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(client.get("test"));
	}

	@Test
	public void test17() throws Exception {
		MemcachedClient client = init();
		System.out.println(client.iqget("test", true));
		System.out.println(client.quarantineAndRegister(client.generateSID(), "test"));
		System.out.println(client.quarantineAndRegister(client.generateSID(), "test"));
		System.out.println(client.get("test"));
	}

	@Test
	public void test18() throws Exception {
		MemcachedClient client = init();
		client.updateServerConfigurationNumber(0);
		client.updateLocalConfigurationNumber(0);
		client.iqget("test", true);
		client.updateServerConfigurationNumber(1);
		try {
			client.iqset("test", "v1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		client.updateLocalConfigurationNumber(1);
		System.out.println("###########");
		client.quarantineAndRegister("1", "test");
	}

	@Test
	public void test19() throws Exception {
		MemcachedClient client = init();
		client.set("k1", "v");
		client.set("k2", "v");
		Assert.assertEquals("v", client.get("k1"));
		Assert.assertEquals("v", client.get("k2"));
		client.deleteMulti(Lists.newArrayList("k1", "k2", "k3"), false);
		Assert.assertEquals(null, client.get("k1"));
		Assert.assertEquals(null, client.get("k2"));
		client.set("k2", "v");
		Assert.assertEquals("v", client.get("k2"));
	}
	
	@Test
	public void test20() throws Exception {
		MemcachedClient client = init();
		System.out.println(client.stats().get("127.0.0.1:11211"));
	}

}
