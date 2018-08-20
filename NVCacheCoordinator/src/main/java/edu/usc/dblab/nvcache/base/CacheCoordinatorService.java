package edu.usc.dblab.nvcache.base;

import org.apache.log4j.Logger;

import edu.usc.dblab.cache.base.HelloReply;
import edu.usc.dblab.cache.base.HelloRequest;
import edu.usc.dblab.cache.base.NVCacheCoordinatorGrpc.NVCacheCoordinatorImplBase;
import edu.usc.dblab.cache.base.ReadConfigRequest;
import edu.usc.dblab.cache.base.ReadConfigResponse;
import edu.usc.dblab.nvcache.config.CacheWorkloadConfigurationManagement;
import edu.usc.dblab.nvcache.config.ConfigurationManagement;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;

public class CacheCoordinatorService extends NVCacheCoordinatorImplBase {

	private final Logger logger = Logger.getLogger(CacheCoordinatorService.class);

	private final ConfigurationManagement configurationManagement;
	private final CacheWorkloadConfigurationManagement workloadConfigurationManagement;

	public CacheCoordinatorService(ConfigurationManagement configurationManagement,
			CacheWorkloadConfigurationManagement workloadConfigurationManagement) {
		super();
		this.configurationManagement = configurationManagement;
		this.workloadConfigurationManagement = workloadConfigurationManagement;
	}

	@Override
	public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
		super.sayHello(request, responseObserver);
	}

	@Override
	public void readConfig(ReadConfigRequest request, StreamObserver<ReadConfigResponse> responseObserver) {
		ReadConfigResponse response = ReadConfigResponse.newBuilder()
				.setConfiguration(this.configurationManagement.getConfig())
				.setWorkload(this.workloadConfigurationManagement.getAllConfigurations()
						.get(this.workloadConfigurationManagement.getCurrentConfigNumber().get()))
				.build();
		ServerCallStreamObserver.class.cast(responseObserver).setCompression("gzip");
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}
