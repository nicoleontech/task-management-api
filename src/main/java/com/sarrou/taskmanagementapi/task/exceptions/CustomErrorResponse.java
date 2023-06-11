package com.sarrou.taskmanagementapi.task.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {

    private int status;
    private String message;
    private List<FieldError> fieldErrors = new ArrayList<>();

    public CustomErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public void addFieldError(String name, String path, String message) {
        FieldError error;
        error = new FieldError(name, path, message);
        fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
