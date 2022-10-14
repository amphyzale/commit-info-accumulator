package ru.solarsecurity.inrights.commitinfoaccumulator.facade.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

public record CommitInfo(
        @JsonProperty("id")
        String id,
        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSz")
        ZonedDateTime createdAt,
        @JsonProperty("message")
        String message,
        @JsonProperty("author_name")
        String authorName,
        @JsonProperty("author_email")
        String authorEmail) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
