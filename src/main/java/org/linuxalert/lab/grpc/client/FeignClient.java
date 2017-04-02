package org.linuxalert.lab.grpc.client;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.linuxalert.lab.grpc.model.Data;
import org.linuxalert.lab.grpc.model.RequestData;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class FeignClient {

  public static void run() {
    long startTime = new Date().getTime();
    BootClient client = Feign
        .builder()
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
        .target(BootClient.class, "http://localhost:8080");
    RequestData requestData = RequestData.create("hej", 10);
    for (int i=0 ; i < 10000 ; i++) {
      Data r = client.request(requestData);
    }
    long stopTime = new Date().getTime();
    System.out.println(stopTime-startTime);
  }

  public static void main(String[] args) throws Exception {
    Executor e = new ScheduledThreadPoolExecutor(10);
    e.execute(FeignClient::run);
    e.execute(FeignClient::run);
    e.execute(FeignClient::run);
    e.execute(FeignClient::run);
    e.execute(FeignClient::run);
    e.execute(FeignClient::run);
    e.execute(FeignClient::run);
    e.execute(FeignClient::run);
    e.execute(FeignClient::run);
    e.execute(FeignClient::run);
  }

}
