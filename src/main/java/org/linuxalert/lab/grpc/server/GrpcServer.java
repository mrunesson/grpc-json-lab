package org.linuxalert.lab.grpc.server;

import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.linuxalert.lab.ServiceGrpc;
import org.linuxalert.lab.proto;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrpcServer {

  private final io.grpc.Server server;

  public GrpcServer(int port) {
    server = ServerBuilder.forPort(port).addService(new Service()).build();
  }

  public void start() throws IOException {
    server.start();
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.err.println("*** shutting down gRPC server since JVM is shutting down");
        GrpcServer.this.stop();
        System.err.println("*** server shut down");
      }
    });
  }

  public void stop() {
    if (server != null) {
      server.shutdown();
    }
  }

  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  public static void main(String[] args) throws Exception {
    Logger.getLogger("io.grpc").setLevel(Level.WARNING);
    Logger.getLogger("io.netty").setLevel(Level.WARNING);
    GrpcServer grpcServer = new GrpcServer(8981);
    grpcServer.start();
    grpcServer.blockUntilShutdown();
  }

  private static class Service extends ServiceGrpc.ServiceImplBase {

    @Override
    public void query(proto.RequestData requestData, StreamObserver<proto.ResponseData> responseObserver) {
      proto.ResponseData.Builder builder = proto.ResponseData.newBuilder();
      int length = requestData.getListLength();
      String prefix = requestData.getPrefix();
      builder = builder
          .setMyInt(length)
          .setMyString(prefix);
      while(length > 0) {
        proto.SubRecord.Builder subRecordBuilder = proto.SubRecord.newBuilder();
        builder.addMyList(subRecordBuilder.setMyInt(length).setMyString(prefix + length).build());
        length--;
      }
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }

  }

}
