package edu.usc.dblab.cache.base;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 * <pre>
 * The greeting service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.5.0)",
    comments = "Source: nvcache.proto")
public final class NVCacheCoordinatorGrpc {

  private NVCacheCoordinatorGrpc() {}

  public static final String SERVICE_NAME = "nvcache.NVCacheCoordinator";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<edu.usc.dblab.cache.base.HelloRequest,
      edu.usc.dblab.cache.base.HelloReply> METHOD_SAY_HELLO =
      io.grpc.MethodDescriptor.<edu.usc.dblab.cache.base.HelloRequest, edu.usc.dblab.cache.base.HelloReply>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "nvcache.NVCacheCoordinator", "SayHello"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              edu.usc.dblab.cache.base.HelloRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              edu.usc.dblab.cache.base.HelloReply.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<edu.usc.dblab.cache.base.ReadConfigRequest,
      edu.usc.dblab.cache.base.ReadConfigResponse> METHOD_READ_CONFIG =
      io.grpc.MethodDescriptor.<edu.usc.dblab.cache.base.ReadConfigRequest, edu.usc.dblab.cache.base.ReadConfigResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "nvcache.NVCacheCoordinator", "ReadConfig"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              edu.usc.dblab.cache.base.ReadConfigRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              edu.usc.dblab.cache.base.ReadConfigResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NVCacheCoordinatorStub newStub(io.grpc.Channel channel) {
    return new NVCacheCoordinatorStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NVCacheCoordinatorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new NVCacheCoordinatorBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NVCacheCoordinatorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new NVCacheCoordinatorFutureStub(channel);
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class NVCacheCoordinatorImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void sayHello(edu.usc.dblab.cache.base.HelloRequest request,
        io.grpc.stub.StreamObserver<edu.usc.dblab.cache.base.HelloReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SAY_HELLO, responseObserver);
    }

    /**
     */
    public void readConfig(edu.usc.dblab.cache.base.ReadConfigRequest request,
        io.grpc.stub.StreamObserver<edu.usc.dblab.cache.base.ReadConfigResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_READ_CONFIG, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SAY_HELLO,
            asyncUnaryCall(
              new MethodHandlers<
                edu.usc.dblab.cache.base.HelloRequest,
                edu.usc.dblab.cache.base.HelloReply>(
                  this, METHODID_SAY_HELLO)))
          .addMethod(
            METHOD_READ_CONFIG,
            asyncUnaryCall(
              new MethodHandlers<
                edu.usc.dblab.cache.base.ReadConfigRequest,
                edu.usc.dblab.cache.base.ReadConfigResponse>(
                  this, METHODID_READ_CONFIG)))
          .build();
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class NVCacheCoordinatorStub extends io.grpc.stub.AbstractStub<NVCacheCoordinatorStub> {
    private NVCacheCoordinatorStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NVCacheCoordinatorStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NVCacheCoordinatorStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NVCacheCoordinatorStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void sayHello(edu.usc.dblab.cache.base.HelloRequest request,
        io.grpc.stub.StreamObserver<edu.usc.dblab.cache.base.HelloReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SAY_HELLO, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void readConfig(edu.usc.dblab.cache.base.ReadConfigRequest request,
        io.grpc.stub.StreamObserver<edu.usc.dblab.cache.base.ReadConfigResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_READ_CONFIG, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class NVCacheCoordinatorBlockingStub extends io.grpc.stub.AbstractStub<NVCacheCoordinatorBlockingStub> {
    private NVCacheCoordinatorBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NVCacheCoordinatorBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NVCacheCoordinatorBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NVCacheCoordinatorBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public edu.usc.dblab.cache.base.HelloReply sayHello(edu.usc.dblab.cache.base.HelloRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SAY_HELLO, getCallOptions(), request);
    }

    /**
     */
    public edu.usc.dblab.cache.base.ReadConfigResponse readConfig(edu.usc.dblab.cache.base.ReadConfigRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_READ_CONFIG, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class NVCacheCoordinatorFutureStub extends io.grpc.stub.AbstractStub<NVCacheCoordinatorFutureStub> {
    private NVCacheCoordinatorFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NVCacheCoordinatorFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NVCacheCoordinatorFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NVCacheCoordinatorFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<edu.usc.dblab.cache.base.HelloReply> sayHello(
        edu.usc.dblab.cache.base.HelloRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SAY_HELLO, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<edu.usc.dblab.cache.base.ReadConfigResponse> readConfig(
        edu.usc.dblab.cache.base.ReadConfigRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_READ_CONFIG, getCallOptions()), request);
    }
  }

  private static final int METHODID_SAY_HELLO = 0;
  private static final int METHODID_READ_CONFIG = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final NVCacheCoordinatorImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(NVCacheCoordinatorImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAY_HELLO:
          serviceImpl.sayHello((edu.usc.dblab.cache.base.HelloRequest) request,
              (io.grpc.stub.StreamObserver<edu.usc.dblab.cache.base.HelloReply>) responseObserver);
          break;
        case METHODID_READ_CONFIG:
          serviceImpl.readConfig((edu.usc.dblab.cache.base.ReadConfigRequest) request,
              (io.grpc.stub.StreamObserver<edu.usc.dblab.cache.base.ReadConfigResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class NVCacheCoordinatorDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return edu.usc.dblab.cache.base.CacheProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NVCacheCoordinatorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NVCacheCoordinatorDescriptorSupplier())
              .addMethod(METHOD_SAY_HELLO)
              .addMethod(METHOD_READ_CONFIG)
              .build();
        }
      }
    }
    return result;
  }
}
