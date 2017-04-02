package org.linuxalert.lab.grpc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.List;

@AutoValue
@JsonSerialize
public abstract class RequestData {

  @JsonProperty
  public abstract int length();
  @JsonProperty
  public abstract String prefix();

  @JsonCreator
  public static RequestData create(@JsonProperty("prefix") String prefix, @JsonProperty("length") int length) {
    return new AutoValue_RequestData(length, prefix);
  }

}
