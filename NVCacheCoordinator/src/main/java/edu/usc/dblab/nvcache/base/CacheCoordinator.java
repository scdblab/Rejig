package edu.usc.dblab.nvcache.base;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import edu.usc.dblab.nvcache.config.CacheStaticConfiguration.StaticConfiguration;
import edu.usc.dblab.nvcache.config.CacheStaticConfiguration;
import edu.usc.dblab.nvcache.config.CacheWorkloadConfigurationManagement;
import edu.usc.dblab.nvcache.config.ConfigurationManagement;
import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class CacheCoordinator {
	private static final Logger logger = Logger.getLogger(CacheCoordinator.class.getName());

	private Server server;
	private final StaticConfiguration staticConfiguration;
	private final ConfigurationManagement configurationManagement;
	private CacheWorkloadConfigurationManagement workloadConfigurationManagement;
	private CacheStateSimulator stateSimulator;
	private CacheCoordinatorService coordinatorService;
	private ExecutorService executorService = Executors.newFixedThreadPool(1);

	public CacheCoordinator(String cacheConfigPath, String workloadConfigPath, Properties props) {
		staticConfiguration = CacheStaticConfiguration.read(cacheConfigPath, props);
		logger.info(staticConfiguration);
		
		CacheCoordinatorConfig config = new CacheCoordinatorConfig(props);
		this.configurationManagement = new ConfigurationManagement(staticConfiguration);
		this.configurationManagement.populate();
		this.workloadConfigurationManagement = new CacheWorkloadConfigurationManagement(staticConfiguration, configurationManagement);
		this.workloadConfigurationManagement.prepareConfigs(workloadConfigPath);
		this.stateSimulator = new CacheStateSimulator(workloadConfigurationManagement, config, configurationManagement);
		this.executorService.submit(this.stateSimulator);
		this.workloadConfigurationManagement.print();
	}

	public void start() throws IOException {
		this.coordinatorService = new CacheCoordinatorService(configurationManagement, workloadConfigurationManagement);
		/* The port on which the server should run */
		server = ServerBuilder.forPort(staticConfiguration.port).addService(this.coordinatorService).build().start();
		logger.info("Server started, listening on " + staticConfiguration.port);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its
				// JVM shutdown hook.
				logger.error("*** shutting down gRPC server since JVM is shutting down");
				CacheCoordinator.this.stop();
				logger.error("*** server shut down");
			}
		});
	}

	public void stop() {
		if (server != null) {
			server.shutdown();
		}
	}

	/**
	 * Await termination on the main thread since the grpc library uses daemon
	 * threads.
	 */
	public void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	public Server getServer() {
		return server;
	}

	public CacheStateSimulator getStateSimulator() {
		return stateSimulator;
	}

	public CacheCoordinatorService getCoordinatorService() {
		return coordinatorService;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	/**
	 * Main launches the server from the command line.
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		BasicConfigurator.configure();
		ConsoleAppender console = new ANSIConsoleAppender(); // create appender
		// configure the appender
		String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.INFO);
		console.activateOptions();
		// add appender to any Logger (here is root)
		Logger.getRootLogger().removeAllAppenders();
		Logger.getRootLogger().addAppender(console);
		Logger.getLogger("io.grpc").setLevel(Level.INFO);

		Properties props = new Properties();
		for (int i = 2; i < args.length; i++) {
			props.put(args[i].split("=")[0], args[i].split("=")[1]);
		}

		final CacheCoordinator server = new CacheCoordinator(args[0], args[1], props);
		server.start();
		server.blockUntilShutdown();
	}
}
