package edu.usc.dblab.nvcache.base;

import edu.usc.dblab.cache.base.ReadConfigResponse;

public interface CacheCoordinatorClientAPI {
	public void shutdown();

	public void greet(String name);
	
	public ReadConfigResponse readConfig();
}
