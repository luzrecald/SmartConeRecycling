package coneclassification;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.52.1)",
    comments = "Source: coneclassification.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ConeClassificationServiceGrpc {

  private ConeClassificationServiceGrpc() {}

  public static final String SERVICE_NAME = "coneclassification.ConeClassificationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<coneclassification.ConeInfo,
      coneclassification.Suggestion> getClassifyConeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ClassifyCone",
      requestType = coneclassification.ConeInfo.class,
      responseType = coneclassification.Suggestion.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<coneclassification.ConeInfo,
      coneclassification.Suggestion> getClassifyConeMethod() {
    io.grpc.MethodDescriptor<coneclassification.ConeInfo, coneclassification.Suggestion> getClassifyConeMethod;
    if ((getClassifyConeMethod = ConeClassificationServiceGrpc.getClassifyConeMethod) == null) {
      synchronized (ConeClassificationServiceGrpc.class) {
        if ((getClassifyConeMethod = ConeClassificationServiceGrpc.getClassifyConeMethod) == null) {
          ConeClassificationServiceGrpc.getClassifyConeMethod = getClassifyConeMethod =
              io.grpc.MethodDescriptor.<coneclassification.ConeInfo, coneclassification.Suggestion>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ClassifyCone"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  coneclassification.ConeInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  coneclassification.Suggestion.getDefaultInstance()))
              .setSchemaDescriptor(new ConeClassificationServiceMethodDescriptorSupplier("ClassifyCone"))
              .build();
        }
      }
    }
    return getClassifyConeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ConeClassificationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConeClassificationServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConeClassificationServiceStub>() {
        @java.lang.Override
        public ConeClassificationServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConeClassificationServiceStub(channel, callOptions);
        }
      };
    return ConeClassificationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ConeClassificationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConeClassificationServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConeClassificationServiceBlockingStub>() {
        @java.lang.Override
        public ConeClassificationServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConeClassificationServiceBlockingStub(channel, callOptions);
        }
      };
    return ConeClassificationServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ConeClassificationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConeClassificationServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConeClassificationServiceFutureStub>() {
        @java.lang.Override
        public ConeClassificationServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConeClassificationServiceFutureStub(channel, callOptions);
        }
      };
    return ConeClassificationServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ConeClassificationServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void classifyCone(coneclassification.ConeInfo request,
        io.grpc.stub.StreamObserver<coneclassification.Suggestion> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getClassifyConeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getClassifyConeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                coneclassification.ConeInfo,
                coneclassification.Suggestion>(
                  this, METHODID_CLASSIFY_CONE)))
          .build();
    }
  }

  /**
   */
  public static final class ConeClassificationServiceStub extends io.grpc.stub.AbstractAsyncStub<ConeClassificationServiceStub> {
    private ConeClassificationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConeClassificationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConeClassificationServiceStub(channel, callOptions);
    }

    /**
     */
    public void classifyCone(coneclassification.ConeInfo request,
        io.grpc.stub.StreamObserver<coneclassification.Suggestion> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getClassifyConeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ConeClassificationServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ConeClassificationServiceBlockingStub> {
    private ConeClassificationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConeClassificationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConeClassificationServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public coneclassification.Suggestion classifyCone(coneclassification.ConeInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getClassifyConeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ConeClassificationServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ConeClassificationServiceFutureStub> {
    private ConeClassificationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConeClassificationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConeClassificationServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<coneclassification.Suggestion> classifyCone(
        coneclassification.ConeInfo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getClassifyConeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CLASSIFY_CONE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ConeClassificationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ConeClassificationServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CLASSIFY_CONE:
          serviceImpl.classifyCone((coneclassification.ConeInfo) request,
              (io.grpc.stub.StreamObserver<coneclassification.Suggestion>) responseObserver);
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

  private static abstract class ConeClassificationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ConeClassificationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return coneclassification.Coneclassification.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ConeClassificationService");
    }
  }

  private static final class ConeClassificationServiceFileDescriptorSupplier
      extends ConeClassificationServiceBaseDescriptorSupplier {
    ConeClassificationServiceFileDescriptorSupplier() {}
  }

  private static final class ConeClassificationServiceMethodDescriptorSupplier
      extends ConeClassificationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ConeClassificationServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ConeClassificationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ConeClassificationServiceFileDescriptorSupplier())
              .addMethod(getClassifyConeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
