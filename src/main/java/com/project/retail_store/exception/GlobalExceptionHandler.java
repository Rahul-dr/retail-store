package com.project.retail_store.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.retail_store.dtos.ApiResponse;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody ApiResponse<Void> handleException(Exception ex) {
    String errorMsg =
        "Internal Server Error: "
            + (ex.getLocalizedMessage() == null
                ? "Unexpected error occurred"
                : ex.getLocalizedMessage());
    log.error("Exception occurred: {}", ex.getMessage(), ex);

    return ApiResponse.<Void>builder()
        .success(false)
        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .errorMessage(errorMsg)
        .build();
  }

  @ExceptionHandler(RetailStoreException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public @ResponseBody ApiResponse<Void> handleRetailStoreException(RetailStoreException ex) {
    String errorMsg = ex.getLocalizedMessage() == null ? "Error" : ex.getLocalizedMessage();
    log.error("RetailStoreException occurred: {}", ex.getMessage(), ex);

    return ApiResponse.<Void>builder()
        .success(false)
        .errorCode(
            Objects.nonNull(ex.getHttpStatus())
                ? ex.getHttpStatus().value()
                : HttpStatus.BAD_REQUEST.value())
        .errorMessage(errorMsg)
        .build();
  }

  @ExceptionHandler(JsonProcessingException.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody ApiResponse<Void> handleJsonProcessingException(JsonProcessingException ex) {
    log.error("JsonProcessingException occurred: {}", ex.getMessage(), ex);
    return ApiResponse.<Void>builder()
        .success(false)
        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .errorMessage("Error processing JSON data.")
        .build();
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public @ResponseBody ApiResponse<Void> handleNoHandlerFoundException(NoHandlerFoundException ex) {
    log.error("NoHandlerFoundException: {}", ex.getMessage(), ex);
    return ApiResponse.<Void>builder()
        .success(false)
        .errorCode(HttpStatus.NOT_FOUND.value())
        .errorMessage("API endpoint not found: " + ex.getRequestURL())
        .build();
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
  public @ResponseBody ApiResponse<Void> handleMethodNotAllowedException(
      HttpRequestMethodNotSupportedException ex) {
    log.error("HttpRequestMethodNotSupportedException: {}", ex.getMessage(), ex);
    return ApiResponse.<Void>builder()
        .success(false)
        .errorCode(HttpStatus.METHOD_NOT_ALLOWED.value())
        .errorMessage("Request method '" + ex.getMethod() + "' is not supported.")
        .build();
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public @ResponseBody ApiResponse<Void> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException ex) {
    log.error("MissingServletRequestParameterException: {}", ex.getMessage(), ex);
    return ApiResponse.<Void>builder()
        .success(false)
        .errorCode(HttpStatus.BAD_REQUEST.value())
        .errorMessage("Required parameter '" + ex.getParameterName() + "' is missing.")
        .build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public @ResponseBody ApiResponse<Void> handleValidationException(
      MethodArgumentNotValidException ex) {
    log.error("Validation failed: {}", ex.getMessage(), ex);
    return ApiResponse.<Void>builder()
        .success(false)
        .errorCode(HttpStatus.BAD_REQUEST.value())
        .errorMessage("Validation failed for one or more fields.")
        .build();
  }
}
