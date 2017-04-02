package org.linuxalert.lab.grpc.client;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.linuxalert.lab.grpc.model.Data;
import org.linuxalert.lab.grpc.model.RequestData;

import java.util.Date;

public class FeignClient {

  public static void main(String[] args) throws Exception {
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

}
