// util/ApiResponseUtil.java
package com.project.retail_store.util;

import com.project.retail_store.dtos.ApiResponse;
import org.springframework.http.HttpStatus;

public class ApiResponseUtil {

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(data);
  }

  public static <T> ApiResponse<T> success(String message, T data) {
    return new ApiResponse<>(message, data);
  }

  public static <T> ApiResponse<T> error(HttpStatus status, String message) {
    ApiResponse<T> response = new ApiResponse<>();
    response.setSuccess(false);
    response.setErrorCode(status.value());
    response.setErrorMessage(message);
    return response;
  }

  public static <T> ApiResponse<T> error(Integer errorCode, String errorMessage) {
    ApiResponse<T> response = new ApiResponse<>();
    response.setSuccess(false);
    response.setErrorCode(errorCode);
    response.setErrorMessage(errorMessage);
    return response;
  }
}
