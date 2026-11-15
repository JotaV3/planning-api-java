package io.github.jotav3.planning_api.api.handlers;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter @Setter
public class StandardError {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;

    public StandardError(Integer status, String error, String path) {
        this.timestamp = Instant.now();
        this.status = status;
        this.error = error;
        this.path = path;
    }
}
