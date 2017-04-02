package org.linuxalert.lab.grpc.server;

import org.linuxalert.lab.grpc.model.Data;
import org.linuxalert.lab.grpc.model.RequestData;
import org.linuxalert.lab.grpc.model.SubData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@EnableAutoConfiguration
@SpringBootApplication
public class BootServer {

  @RequestMapping(value="/request", method= RequestMethod.POST, consumes = "application/json;charset=UTF-8")
  @ResponseBody Data home(@RequestBody RequestData request) {
    int length = request.length();
    String prefix = request.prefix();
    Data response = Data.builder().setPrefix(prefix).setLength(length).setList(new ArrayList<>()).build();
    while(length > 0) {
      response.list().add(
          SubData.builder().setPrefix(prefix + length).setLength(length).build());
      length--;
    }
    return response;
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(BootServer.class, args);
  }
  
}
