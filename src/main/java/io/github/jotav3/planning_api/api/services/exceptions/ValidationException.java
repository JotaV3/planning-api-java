package io.github.jotav3.planning_api.api.services.exceptions;

import io.github.jotav3.planning_api.api.services.validation.InvalidField;

import java.util.List;

public class ValidationException extends RuntimeException {
    private final List<InvalidField> invalidFieldsList;

    public ValidationException(List<InvalidField> invalidFieldList) {
        super("Validation error");
        this.invalidFieldsList = invalidFieldList;
    }

    public List<InvalidField> getInvalidFieldsList(){
        return invalidFieldsList;
    }
}
