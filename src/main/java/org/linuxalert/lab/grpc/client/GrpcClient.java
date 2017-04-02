package org.linuxalert.lab.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.linuxalert.lab.ServiceGrpc;
import org.linuxalert.lab.grpc.model.Data;
import org.linuxalert.lab.grpc.model.SubData;
import org.linuxalert.lab.proto;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrpcClient {

  private final ManagedChannel channel;
  private final ServiceGrpc.ServiceBlockingStub stub;

  public GrpcClient(String host, int port) {
    ManagedChannelBuilder<?> channelBuilder =
        ManagedChannelBuilder.forAddress(host, port).usePlaintext(true);
    channel = channelBuilder.build();
    stub = ServiceGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public Data request(String prefix, int length) {
    proto.ResponseData response =
        stub.query(proto.RequestData.newBuilder().setListLength(length).setPrefix(prefix).build());
    Data d = Data.builder().setPrefix(response.getMyString()).setLength(response.getMyInt()).setList(new ArrayList<>()).build();
    for(proto.SubRecord r: response.getMyListList()) {
      d.list().add(SubData.builder().setPrefix(r.getMyString()).setLength(r.getMyInt()).build());
    }
    return d;
  }

  public static void main(String[] args) throws Exception {
    Logger.getLogger("io.grpc").setLevel(Level.WARNING);

    long startTime = new Date().getTime();
    GrpcClient grpcClient = new GrpcClient("localhost", 8981);
    for (int i=0 ; i < 10000 ; i++) {
      Data r = grpcClient.request("hej", 10);
    }
    long stopTime = new Date().getTime();
    System.out.println(stopTime-startTime);
    grpcClient.shutdown();
  }

}
