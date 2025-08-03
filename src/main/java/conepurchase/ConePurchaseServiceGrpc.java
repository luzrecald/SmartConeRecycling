package conepurchase;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.52.1)",
    comments = "Source: purchase.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ConePurchaseServiceGrpc {

  private ConePurchaseServiceGrpc() {}

  public static final String SERVICE_NAME = "conepurchase.ConePurchaseService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<conepurchase.ConeSaleRequest,
      conepurchase.ConeSaleResponse> getRequestConeSaleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RequestConeSale",
      requestType = conepurchase.ConeSaleRequest.class,
      responseType = conepurchase.ConeSaleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<conepurchase.ConeSaleRequest,
      conepurchase.ConeSaleResponse> getRequestConeSaleMethod() {
    io.grpc.MethodDescriptor<conepurchase.ConeSaleRequest, conepurchase.ConeSaleResponse> getRequestConeSaleMethod;
    if ((getRequestConeSaleMethod = ConePurchaseServiceGrpc.getRequestConeSaleMethod) == null) {
      synchronized (ConePurchaseServiceGrpc.class) {
        if ((getRequestConeSaleMethod = ConePurchaseServiceGrpc.getRequestConeSaleMethod) == null) {
          ConePurchaseServiceGrpc.getRequestConeSaleMethod = getRequestConeSaleMethod =
              io.grpc.MethodDescriptor.<conepurchase.ConeSaleRequest, conepurchase.ConeSaleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RequestConeSale"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  conepurchase.ConeSaleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  conepurchase.ConeSaleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ConePurchaseServiceMethodDescriptorSupplier("RequestConeSale"))
              .build();
        }
      }
    }
    return getRequestConeSaleMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ConePurchaseServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConePurchaseServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConePurchaseServiceStub>() {
        @java.lang.Override
        public ConePurchaseServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConePurchaseServiceStub(channel, callOptions);
        }
      };
    return ConePurchaseServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ConePurchaseServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConePurchaseServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConePurchaseServiceBlockingStub>() {
        @java.lang.Override
        public ConePurchaseServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConePurchaseServiceBlockingStub(channel, callOptions);
        }
      };
    return ConePurchaseServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ConePurchaseServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConePurchaseServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConePurchaseServiceFutureStub>() {
        @java.lang.Override
        public ConePurchaseServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConePurchaseServiceFutureStub(channel, callOptions);
        }
      };
    return ConePurchaseServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ConePurchaseServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void requestConeSale(conepurchase.ConeSaleRequest request,
        io.grpc.stub.StreamObserver<conepurchase.ConeSaleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRequestConeSaleMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRequestConeSaleMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                conepurchase.ConeSaleRequest,
                conepurchase.ConeSaleResponse>(
                  this, METHODID_REQUEST_CONE_SALE)))
          .build();
    }
  }

  /**
   */
  public static final class ConePurchaseServiceStub extends io.grpc.stub.AbstractAsyncStub<ConePurchaseServiceStub> {
    private ConePurchaseServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConePurchaseServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConePurchaseServiceStub(channel, callOptions);
    }

    /**
     */
    public void requestConeSale(conepurchase.ConeSaleRequest request,
        io.grpc.stub.StreamObserver<conepurchase.ConeSaleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getRequestConeSaleMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ConePurchaseServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ConePurchaseServiceBlockingStub> {
    private ConePurchaseServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConePurchaseServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConePurchaseServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<conepurchase.ConeSaleResponse> requestConeSale(
        conepurchase.ConeSaleRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getRequestConeSaleMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ConePurchaseServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ConePurchaseServiceFutureStub> {
    private ConePurchaseServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConePurchaseServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConePurchaseServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_REQUEST_CONE_SALE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ConePurchaseServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ConePurchaseServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQUEST_CONE_SALE:
          serviceImpl.requestConeSale((conepurchase.ConeSaleRequest) request,
              (io.grpc.stub.StreamObserver<conepurchase.ConeSaleResponse>) responseObserver);
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

  private static abstract class ConePurchaseServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ConePurchaseServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return conepurchase.Purchase.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ConePurchaseService");
    }
  }

  private static final class ConePurchaseServiceFileDescriptorSupplier
      extends ConePurchaseServiceBaseDescriptorSupplier {
    ConePurchaseServiceFileDescriptorSupplier() {}
  }

  private static final class ConePurchaseServiceMethodDescriptorSupplier
      extends ConePurchaseServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ConePurchaseServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ConePurchaseServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ConePurchaseServiceFileDescriptorSupplier())
              .addMethod(getRequestConeSaleMethod())
              .build();
        }
      }
    }
    return result;
  }
}
