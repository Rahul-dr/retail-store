package com.project.retail_store.exception;

import com.project.retail_store.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class RetailStoreException extends RuntimeException {
  private Integer errorCode;
  private String errorMessage;
  private HttpStatus httpStatus;

  public RetailStoreException(ErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode.getCode();
    this.errorMessage = errorCode.getErrorMessage();
  }

  public RetailStoreException(ErrorCode errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode.getCode();
    this.errorMessage = errorMessage;
  }

  public RetailStoreException(String errorMessage) {
    super(errorMessage);
    this.errorCode = ErrorCode.BAD_REQUEST.getCode();
    this.errorMessage = errorMessage;
  }

  public RetailStoreException(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage);
    this.httpStatus = httpStatus;
    this.errorCode = ErrorCode.BAD_REQUEST.getCode();
    this.errorMessage = errorMessage;
  }

  public RetailStoreException(String errorMessage, Exception e) {
    super(e);
    this.errorCode = ErrorCode.BAD_REQUEST.getCode();
    this.errorMessage = errorMessage;
  }

  public RetailStoreException(Integer customErrorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = customErrorCode;
    this.errorMessage = errorMessage;
  }

  public RetailStoreException(Exception e, String errorMessage) {
    super(e);
    this.errorMessage = errorMessage;
  }

  public RetailStoreException(Exception e) {
    super(e);
    this.errorMessage = e.getMessage();
  }
}
