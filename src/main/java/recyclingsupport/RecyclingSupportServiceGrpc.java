package recyclingsupport;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Servicio gRPC para el soporte de reciclaje inteligente
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.52.1)",
    comments = "Source: recyclingsupport.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class RecyclingSupportServiceGrpc {

  private RecyclingSupportServiceGrpc() {}

  public static final String SERVICE_NAME = "recyclingsupport.RecyclingSupportService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<recyclingsupport.ChatMessage,
      recyclingsupport.ChatMessage> getChatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Chat",
      requestType = recyclingsupport.ChatMessage.class,
      responseType = recyclingsupport.ChatMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<recyclingsupport.ChatMessage,
      recyclingsupport.ChatMessage> getChatMethod() {
    io.grpc.MethodDescriptor<recyclingsupport.ChatMessage, recyclingsupport.ChatMessage> getChatMethod;
    if ((getChatMethod = RecyclingSupportServiceGrpc.getChatMethod) == null) {
      synchronized (RecyclingSupportServiceGrpc.class) {
        if ((getChatMethod = RecyclingSupportServiceGrpc.getChatMethod) == null) {
          RecyclingSupportServiceGrpc.getChatMethod = getChatMethod =
              io.grpc.MethodDescriptor.<recyclingsupport.ChatMessage, recyclingsupport.ChatMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Chat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  recyclingsupport.ChatMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  recyclingsupport.ChatMessage.getDefaultInstance()))
              .setSchemaDescriptor(new RecyclingSupportServiceMethodDescriptorSupplier("Chat"))
              .build();
        }
      }
    }
    return getChatMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RecyclingSupportServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RecyclingSupportServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RecyclingSupportServiceStub>() {
        @java.lang.Override
        public RecyclingSupportServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RecyclingSupportServiceStub(channel, callOptions);
        }
      };
    return RecyclingSupportServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RecyclingSupportServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RecyclingSupportServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RecyclingSupportServiceBlockingStub>() {
        @java.lang.Override
        public RecyclingSupportServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RecyclingSupportServiceBlockingStub(channel, callOptions);
        }
      };
    return RecyclingSupportServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RecyclingSupportServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RecyclingSupportServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RecyclingSupportServiceFutureStub>() {
        @java.lang.Override
        public RecyclingSupportServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RecyclingSupportServiceFutureStub(channel, callOptions);
        }
      };
    return RecyclingSupportServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Servicio gRPC para el soporte de reciclaje inteligente
   * </pre>
   */
  public static abstract class RecyclingSupportServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Comunicación bidireccional por streaming
     * </pre>
     */
    public io.grpc.stub.StreamObserver<recyclingsupport.ChatMessage> chat(
        io.grpc.stub.StreamObserver<recyclingsupport.ChatMessage> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getChatMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getChatMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                recyclingsupport.ChatMessage,
                recyclingsupport.ChatMessage>(
                  this, METHODID_CHAT)))
          .build();
    }
  }

  /**
   * <pre>
   * Servicio gRPC para el soporte de reciclaje inteligente
   * </pre>
   */
  public static final class RecyclingSupportServiceStub extends io.grpc.stub.AbstractAsyncStub<RecyclingSupportServiceStub> {
    private RecyclingSupportServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RecyclingSupportServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RecyclingSupportServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Comunicación bidireccional por streaming
     * </pre>
     */
    public io.grpc.stub.StreamObserver<recyclingsupport.ChatMessage> chat(
        io.grpc.stub.StreamObserver<recyclingsupport.ChatMessage> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getChatMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * Servicio gRPC para el soporte de reciclaje inteligente
   * </pre>
   */
  public static final class RecyclingSupportServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<RecyclingSupportServiceBlockingStub> {
    private RecyclingSupportServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RecyclingSupportServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RecyclingSupportServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   * <pre>
   * Servicio gRPC para el soporte de reciclaje inteligente
   * </pre>
   */
  public static final class RecyclingSupportServiceFutureStub extends io.grpc.stub.AbstractFutureStub<RecyclingSupportServiceFutureStub> {
    private RecyclingSupportServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RecyclingSupportServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RecyclingSupportServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_CHAT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RecyclingSupportServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(RecyclingSupportServiceImplBase serviceImpl, int methodId) {
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
        case METHODID_CHAT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.chat(
              (io.grpc.stub.StreamObserver<recyclingsupport.ChatMessage>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class RecyclingSupportServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RecyclingSupportServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return recyclingsupport.Recyclingsupport.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RecyclingSupportService");
    }
  }

  private static final class RecyclingSupportServiceFileDescriptorSupplier
      extends RecyclingSupportServiceBaseDescriptorSupplier {
    RecyclingSupportServiceFileDescriptorSupplier() {}
  }

  private static final class RecyclingSupportServiceMethodDescriptorSupplier
      extends RecyclingSupportServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    RecyclingSupportServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (RecyclingSupportServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RecyclingSupportServiceFileDescriptorSupplier())
              .addMethod(getChatMethod())
              .build();
        }
      }
    }
    return result;
  }
}
