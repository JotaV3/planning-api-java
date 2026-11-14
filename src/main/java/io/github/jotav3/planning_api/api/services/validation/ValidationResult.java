package io.github.jotav3.planning_api.api.services.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationResult {
    private List<InvalidField> invalidFieldList;

    private ValidationResult(List<InvalidField> invalidFieldList){
        this.invalidFieldList = invalidFieldList;
    }

    public static ValidationResult newValidationResult(){
        return new ValidationResult(new ArrayList<>());
    }

    public void add(InvalidField invalidField){
        invalidFieldList.add(invalidField);
    }

    public boolean isInvalid(){
        return !invalidFieldList.isEmpty();
    }
}