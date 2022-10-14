package ru.solarsecurity.inrights.commitinfoaccumulator.facade.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

public record FileInfo(
        @JsonProperty("id")
        String id,
        @JsonProperty("name")
        String name,
        @JsonProperty("type")
        String type,
        @JsonProperty("path")
        String path) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
