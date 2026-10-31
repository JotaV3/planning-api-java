package io.github.jotav3.planning_api.entities.controllers.handlers;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter @Setter
public class StandardError {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;

    public StandardError(Instant timestamp, Integer status, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }
}
