package edu.usc.dblab.nvcache.base;

import org.apache.log4j.Logger;

import edu.usc.dblab.cache.base.NVCacheCoordinatorGrpc;
import edu.usc.dblab.cache.base.ReadConfigRequest;
import edu.usc.dblab.cache.base.ReadConfigResponse;
import io.grpc.ManagedChannelBuilder;

/**
 * 
 * @author haoyuh
 *
 */
public class CacheCoordinatorClientImpl implements CacheCoordinatorClientAPI {

	private final Logger logger = Logger.getLogger(CacheCoordinatorClientImpl.class);
	private final NVCacheCoordinatorGrpc.NVCacheCoordinatorBlockingStub client;

	public CacheCoordinatorClientImpl(String peer) {
		String host = peer.split(":")[0];
		int port = Integer.parseInt(peer.split(":")[1]);
		client = NVCacheCoordinatorGrpc
				.newBlockingStub(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build());
		logger.info("Created coordinator client for " + host + " " + port);
	}

	@Override
	public void shutdown() {

	}

	@Override
	public void greet(String name) {

	}

	@Override
	public ReadConfigResponse readConfig() {
		ReadConfigRequest req = ReadConfigRequest.newBuilder().build();
		return client.readConfig(req);
	}
}
