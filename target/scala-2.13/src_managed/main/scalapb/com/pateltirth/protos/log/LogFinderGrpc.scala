package com.pateltirth.protos.log

object LogFinderGrpc {
  val METHOD_CHECK_LOGS: _root_.io.grpc.MethodDescriptor[com.pateltirth.protos.log.Request, com.pateltirth.protos.log.Response] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("LogFinder", "checkLogs"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[com.pateltirth.protos.log.Request])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[com.pateltirth.protos.log.Response])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(com.pateltirth.protos.log.LogProto.javaDescriptor.getServices().get(0).getMethods().get(0)))
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("LogFinder")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(com.pateltirth.protos.log.LogProto.javaDescriptor))
      .addMethod(METHOD_CHECK_LOGS)
      .build()
  
  trait LogFinder extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = LogFinder
    def checkLogs(request: com.pateltirth.protos.log.Request): scala.concurrent.Future[com.pateltirth.protos.log.Response]
  }
  
  object LogFinder extends _root_.scalapb.grpc.ServiceCompanion[LogFinder] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[LogFinder] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = com.pateltirth.protos.log.LogProto.javaDescriptor.getServices().get(0)
    def scalaDescriptor: _root_.scalapb.descriptors.ServiceDescriptor = com.pateltirth.protos.log.LogProto.scalaDescriptor.services(0)
    def bindService(serviceImpl: LogFinder, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
      _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
      .addMethod(
        METHOD_CHECK_LOGS,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[com.pateltirth.protos.log.Request, com.pateltirth.protos.log.Response] {
          override def invoke(request: com.pateltirth.protos.log.Request, observer: _root_.io.grpc.stub.StreamObserver[com.pateltirth.protos.log.Response]): _root_.scala.Unit =
            serviceImpl.checkLogs(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .build()
  }
  
  trait LogFinderBlockingClient {
    def serviceCompanion = LogFinder
    def checkLogs(request: com.pateltirth.protos.log.Request): com.pateltirth.protos.log.Response
  }
  
  class LogFinderBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[LogFinderBlockingStub](channel, options) with LogFinderBlockingClient {
    override def checkLogs(request: com.pateltirth.protos.log.Request): com.pateltirth.protos.log.Response = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_CHECK_LOGS, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): LogFinderBlockingStub = new LogFinderBlockingStub(channel, options)
  }
  
  class LogFinderStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[LogFinderStub](channel, options) with LogFinder {
    override def checkLogs(request: com.pateltirth.protos.log.Request): scala.concurrent.Future[com.pateltirth.protos.log.Response] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_CHECK_LOGS, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): LogFinderStub = new LogFinderStub(channel, options)
  }
  
  def bindService(serviceImpl: LogFinder, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition = LogFinder.bindService(serviceImpl, executionContext)
  
  def blockingStub(channel: _root_.io.grpc.Channel): LogFinderBlockingStub = new LogFinderBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): LogFinderStub = new LogFinderStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = com.pateltirth.protos.log.LogProto.javaDescriptor.getServices().get(0)
  
}