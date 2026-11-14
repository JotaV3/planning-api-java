package io.github.jotav3.planning_api.api.controllers.handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.jotav3.planning_api.api.services.validation.InvalidField;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter @Setter
public class FieldError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy:MM:dd'T'HH:mm:ssZ", timezone = "GMT")
    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;
    private List<InvalidField> invalidFieldList;

    public FieldError(Instant timestamp, Integer status, String error, String path, List<InvalidField> invalidFieldList) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
        this.invalidFieldList = invalidFieldList;
    }
}
