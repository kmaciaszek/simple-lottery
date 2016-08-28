package com.kaz.lottery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

@ApiModel(description = "The model object for an error.")
public class ErrorModel {
  
  private Integer httpStatus = null;
  private String error = null;
  private String errorCode = null;
  private String errorDetail = null;

  /**
   * The http status code.
   */
  @ApiModelProperty(required = true, value = "The http status code")
  @JsonProperty("httpStatus")
  public Integer getHttpStatus() {
    return httpStatus;
  }
  public void setHttpStatus(Integer httpStatus) {
    this.httpStatus = httpStatus;
  }

  
  /**
   * The error message describing the nature of the error.
   **/
  @ApiModelProperty(required = true, value = "The error message describing the nature of the error.")
  @JsonProperty("error")
  public String getError() {
    return error;
  }
  public void setError(String error) {
    this.error = error;
  }

  
  /**
   * The error code.
   **/
  @ApiModelProperty(required = true, value = "The error code.")
  @JsonProperty("errorCode")
  public String getErrorCode() {
    return errorCode;
  }
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  
  /**
   * A more detailed error message.
   **/
  @ApiModelProperty(value = "A more detailed error message.")
  @JsonProperty("errorDetail")
  public String getErrorDetail() {
    return errorDetail;
  }
  public void setErrorDetail(String errorDetail) {
    this.errorDetail = errorDetail;
  }
}
