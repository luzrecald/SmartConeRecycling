package wasteanalytics;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.52.1)",
    comments = "Source: wasteanalytics.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class WasteAnalyticsServiceGrpc {

  private WasteAnalyticsServiceGrpc() {}

  public static final String SERVICE_NAME = "wasteanalytics.WasteAnalyticsService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<wasteanalytics.WasteRecord,
      wasteanalytics.DailyReport> getSubmitDailyWasteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubmitDailyWaste",
      requestType = wasteanalytics.WasteRecord.class,
      responseType = wasteanalytics.DailyReport.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<wasteanalytics.WasteRecord,
      wasteanalytics.DailyReport> getSubmitDailyWasteMethod() {
    io.grpc.MethodDescriptor<wasteanalytics.WasteRecord, wasteanalytics.DailyReport> getSubmitDailyWasteMethod;
    if ((getSubmitDailyWasteMethod = WasteAnalyticsServiceGrpc.getSubmitDailyWasteMethod) == null) {
      synchronized (WasteAnalyticsServiceGrpc.class) {
        if ((getSubmitDailyWasteMethod = WasteAnalyticsServiceGrpc.getSubmitDailyWasteMethod) == null) {
          WasteAnalyticsServiceGrpc.getSubmitDailyWasteMethod = getSubmitDailyWasteMethod =
              io.grpc.MethodDescriptor.<wasteanalytics.WasteRecord, wasteanalytics.DailyReport>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SubmitDailyWaste"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  wasteanalytics.WasteRecord.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  wasteanalytics.DailyReport.getDefaultInstance()))
              .setSchemaDescriptor(new WasteAnalyticsServiceMethodDescriptorSupplier("SubmitDailyWaste"))
              .build();
        }
      }
    }
    return getSubmitDailyWasteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WasteAnalyticsServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WasteAnalyticsServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WasteAnalyticsServiceStub>() {
        @java.lang.Override
        public WasteAnalyticsServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WasteAnalyticsServiceStub(channel, callOptions);
        }
      };
    return WasteAnalyticsServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WasteAnalyticsServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WasteAnalyticsServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WasteAnalyticsServiceBlockingStub>() {
        @java.lang.Override
        public WasteAnalyticsServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WasteAnalyticsServiceBlockingStub(channel, callOptions);
        }
      };
    return WasteAnalyticsServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WasteAnalyticsServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WasteAnalyticsServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WasteAnalyticsServiceFutureStub>() {
        @java.lang.Override
        public WasteAnalyticsServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WasteAnalyticsServiceFutureStub(channel, callOptions);
        }
      };
    return WasteAnalyticsServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class WasteAnalyticsServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<wasteanalytics.WasteRecord> submitDailyWaste(
        io.grpc.stub.StreamObserver<wasteanalytics.DailyReport> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getSubmitDailyWasteMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSubmitDailyWasteMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                wasteanalytics.WasteRecord,
                wasteanalytics.DailyReport>(
                  this, METHODID_SUBMIT_DAILY_WASTE)))
          .build();
    }
  }

  /**
   */
  public static final class WasteAnalyticsServiceStub extends io.grpc.stub.AbstractAsyncStub<WasteAnalyticsServiceStub> {
    private WasteAnalyticsServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WasteAnalyticsServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WasteAnalyticsServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<wasteanalytics.WasteRecord> submitDailyWaste(
        io.grpc.stub.StreamObserver<wasteanalytics.DailyReport> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getSubmitDailyWasteMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class WasteAnalyticsServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<WasteAnalyticsServiceBlockingStub> {
    private WasteAnalyticsServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WasteAnalyticsServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WasteAnalyticsServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class WasteAnalyticsServiceFutureStub extends io.grpc.stub.AbstractFutureStub<WasteAnalyticsServiceFutureStub> {
    private WasteAnalyticsServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WasteAnalyticsServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WasteAnalyticsServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SUBMIT_DAILY_WASTE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WasteAnalyticsServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WasteAnalyticsServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBMIT_DAILY_WASTE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.submitDailyWaste(
              (io.grpc.stub.StreamObserver<wasteanalytics.DailyReport>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class WasteAnalyticsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WasteAnalyticsServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return wasteanalytics.Wasteanalytics.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("WasteAnalyticsService");
    }
  }

  private static final class WasteAnalyticsServiceFileDescriptorSupplier
      extends WasteAnalyticsServiceBaseDescriptorSupplier {
    WasteAnalyticsServiceFileDescriptorSupplier() {}
  }

  private static final class WasteAnalyticsServiceMethodDescriptorSupplier
      extends WasteAnalyticsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WasteAnalyticsServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (WasteAnalyticsServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WasteAnalyticsServiceFileDescriptorSupplier())
              .addMethod(getSubmitDailyWasteMethod())
              .build();
        }
      }
    }
    return result;
  }
}
