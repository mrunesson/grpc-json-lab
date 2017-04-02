package org.linuxalert.lab.grpc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.List;

@AutoValue
@JsonSerialize
@JsonDeserialize(builder = AutoValue_Data.Builder.class)
public abstract class Data {

  public static Builder builder() {
    return new AutoValue_Data.Builder();
  }

  @JsonProperty
  public abstract int length();
  @JsonProperty
  public abstract String prefix();
  @JsonProperty
  public abstract List<SubData> list();

  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("length")
    public abstract Builder setLength(int value);
    @JsonProperty("prefix")
    public abstract Builder setPrefix(String value);
    @JsonProperty("list")
    public abstract Builder setList(List<SubData> value);

    public abstract Data build();
  }

}
