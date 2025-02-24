package com.project.retail_store.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private Integer errorCode;
    private String errorMessage;
    private T data;

    public ApiResponse(T data) {
        this.success = true;
        this.data = data;
    }

    public ApiResponse(String message, T data) {
        this.success = true;
        this.errorMessage = message;
        this.data = data;
    }
}
