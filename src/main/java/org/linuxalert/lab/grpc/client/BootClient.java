package org.linuxalert.lab.grpc.client;

import feign.Headers;
import feign.RequestLine;
import org.linuxalert.lab.grpc.model.Data;
import org.linuxalert.lab.grpc.model.RequestData;

public interface BootClient {
  @RequestLine("POST /request")
  @Headers("Content-Type: application/json")
  Data request(RequestData requestData);

}
