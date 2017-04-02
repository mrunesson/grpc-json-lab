package org.linuxalert.lab.grpc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
@JsonSerialize
@JsonDeserialize(builder = AutoValue_SubData.Builder.class)
public abstract class SubData {

  public static SubData.Builder builder() {
    return new AutoValue_SubData.Builder();
  }

  @JsonProperty
  public abstract int length();
  @JsonProperty
  public abstract String prefix();

  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("length")
    public abstract Builder setLength(int value);
    @JsonProperty("prefix")
    public abstract Builder setPrefix(String value);

    public abstract SubData build();
  }

}
