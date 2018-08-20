package com.meetup.memcached.test;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.MemcachedLease;
import com.meetup.memcached.SockIOPool;

public class IQGetConfigTest {

	private static MemcachedClient init() throws Exception {
		ConsoleAppender console = new ConsoleAppender(); // create appender
		// configure the appender
		String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.DEBUG);
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

	@Test
	public void iqget() throws Exception {
		MemcachedClient mc = init();
		mc.delete("test");
		mc.updateServerConfigurationNumber(1);
		mc.updateLocalConfigurationNumber(1);
		mc.iqget("test", true);
		mc.iqset("test", "test");
		System.out.println(mc.iqget("test", true));
		mc.updateServerConfigurationNumber(2);
		mc.updateLocalConfigurationNumber(2);
		System.out.println(mc.iqget("test", true));
		mc.cleanup();
	}

	@Test
	public void iqset() throws Exception {
		MemcachedClient mc = init();
		mc.delete("test");
		mc.updateServerConfigurationNumber(1);
		mc.updateLocalConfigurationNumber(1);
		mc.iqget("test", true);
		mc.iqset("test", "test", 3);
		System.out.println(mc.iqget("test", true));
		System.out.println(mc.get("test", 0));
		mc.cleanup();
	}

	@Test
	public void iqgetM() throws Exception {
		List<Thread> ts = Lists.newArrayList();
		for (int i = 0; i < 2; i++) {
			ts.add(new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						MemcachedClient mc = init();
						Random r = new Random();
						while (true) {
							int key = r.nextInt(1000);
							System.out.println(Thread.currentThread().getId() + ", " + key);
							mc.updateLocalConfigurationNumber(0);
							Object v = mc.iqget(String.valueOf(key), Integer.MAX_VALUE, 0, -1, true);
							if (v == null) {
								mc.iqset(String.valueOf(key), "t");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}));
		}
		ts.forEach(t -> {
			t.start();
		});
		ts.forEach(t -> {
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void writeThroughCfg() throws Exception {
		MemcachedClient mc1 = init();
		MemcachedClient mc2 = init();
		mc1.updateServerConfigurationNumber(0);
		mc1.updateLocalConfigurationNumber(0);
		mc2.updateLocalConfigurationNumber(0);

		String key = "test";
		String val = "val";
		String tid = "asd";
		
		mc1.delete(key);
		mc1.ewread(tid, key);
		mc1.ewswap(tid, key, val, 3);
		mc1.ewcommit(tid, 3, false);
		System.out.println(mc1.iqget(key));
		
		mc1.delete(key);
		mc1.ewread(tid, key);
		mc1.ewswap(tid, key, "", -1);
		mc1.ewcommit(tid, -1, false);
		System.out.println(mc1.iqget(key));
	}
	
	@Test
	public void writeBack() throws Exception {
		MemcachedClient mc1 = init();
		MemcachedClient mc2 = init();
		mc1.updateServerConfigurationNumber(0);
		mc1.updateLocalConfigurationNumber(0);
		mc2.updateLocalConfigurationNumber(0);

		String key = "test";
		String val = "val";
		String tid = "asd";
		
		mc1.delete(key);
//		mc1.set(key, val);
		
		System.out.println(mc1.ewreadvalue(tid, key));
//		mc1.ewswap(tid, key, "");
		mc1.ewcommit(tid, -1, true);
		
		System.out.println(mc1.iqget(key));
		
		
		
//		CLValue cval = mc1.ewappend(key, null, val, tid);
//		if ((boolean) cval.getValue() == false) {
//			mc1.ewswap(tid, key, val, -1);
//		}
//		mc1.ewcommit(tid, -1, false);
//		System.out.println(mc1.iqget(key));
//		
////		mc1.delete(key);
//		mc1.ewread(tid, key);
////		mc1.ewswap(tid, key, "", -1);
//		mc1.ewcommit(tid, null, false);
//		System.out.println(mc1.iqget(key));
	}
	
	@Test
	public void writeBackLease() throws Exception {
		MemcachedClient mc1 = init();
		MemcachedClient mc2 = init();
		MemcachedLease lease1 = new MemcachedLease(0, mc1, false);
		MemcachedLease lease2 = new MemcachedLease(0, mc2, false);
		mc1.updateServerConfigurationNumber(0);
		mc1.updateLocalConfigurationNumber(0);
		mc2.updateLocalConfigurationNumber(0);

		String key = "test";
		String val = "val";
		String tid = "asd";
		
		mc1.delete(key);
		
		System.out.println(lease1.acquireLease(key, Long.MAX_VALUE));
		System.out.println(lease2.acquireLease(key, Long.MAX_VALUE));
		System.out.println(lease1.releaseLease(key));;
		
//		mc1.set(key, val);
		
//		System.out.println(mc1.ewreadvalue(tid, key));
//		mc1.ewswap(tid, key, "");
//		mc1.ewcommit(tid, -1, true);
		
//		System.out.println(mc1.iqget(key));
		
		
		
//		CLValue cval = mc1.ewappend(key, null, val, tid);
//		if ((boolean) cval.getValue() == false) {
//			mc1.ewswap(tid, key, val, -1);
//		}
//		mc1.ewcommit(tid, -1, false);
//		System.out.println(mc1.iqget(key));
//		
////		mc1.delete(key);
//		mc1.ewread(tid, key);
////		mc1.ewswap(tid, key, "", -1);
//		mc1.ewcommit(tid, null, false);
//		System.out.println(mc1.iqget(key));
	}

	@Test
	public void writeThrough() throws Exception {
		MemcachedClient mc1 = init();
		MemcachedClient mc2 = init();
		mc1.updateServerConfigurationNumber(0);
		mc1.updateLocalConfigurationNumber(0);
		mc2.updateLocalConfigurationNumber(0);

		String key = "test";
		String val = "val";
		mc1.delete(key);
		String tid = "asd";
		mc1.ewread(tid, key);
		mc1.ewswap(tid, key, val);
		mc1.ewcommit(tid);
		System.out.println(mc1.iqget(key));

		// q voids i
//		mc1.delete(key);
//		mc1.iqget(key);
//		mc1.ewread(tid, key);
//		mc1.iqset(key, val);
//		mc1.ewswap(tid, key, val);
//		mc1.ewcommit(tid);
//		System.out.println(mc1.iqget(key));
//
//		// q voids q
//		mc1.delete(key);
//		mc1.ewread(tid, key);
//
//		try {
//			mc2.ewread("asd2", key);
//		} catch (IQException e) {
//			e.printStackTrace();
//		}
//
//		mc1.ewswap(tid, key, val);
//		mc1.ewcommit(tid);
//		System.out.println(mc1.iqget(key));
//
//		// stale configs.
//		mc1.updateServerConfigurationNumber(1);
//		try {
//			mc1.ewread(tid, key);
//		} catch (StaleConfigurationException e) {
//			e.printStackTrace();
//		}
//		mc1.updateServerConfigurationNumber(0);
//		try {
//			mc1.ewread(tid, key);
//			mc1.updateServerConfigurationNumber(1);
//			mc1.ewswap(tid, key, val);
//		} catch (StaleConfigurationException e) {
//			e.printStackTrace();
//			mc1.updateLocalConfigurationNumber(1);
//			mc1.ewswap(tid, key, val);
//			mc1.ewcommit(tid);
//		}
//		mc1.updateLocalConfigurationNumber(0);
//		mc1.updateServerConfigurationNumber(0);
//		try {
//			mc1.ewread(tid, key);
//			mc1.ewswap(tid, key, val);
//			mc1.updateServerConfigurationNumber(1);
//			mc1.ewcommit(tid);
//		} catch (StaleConfigurationException e) {
//			e.printStackTrace();
//			mc1.updateLocalConfigurationNumber(1);
//			mc1.ewcommit(tid);
//		}
//		System.out.println(mc1.iqget(key));
	}
}
