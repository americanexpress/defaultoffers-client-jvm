/*
 * Global Default Offers API
 * This is the Offers API providing the default offers for partners across various applications in Acquisition portfolio
 *
 * OpenAPI spec version: 1.0
 * Contact: DAPXServices@aexp.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.americanexpress.sdk.models.default_offers;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import javax.validation.constraints.*;
import javax.validation.Valid;

/**
 * ErrorMessage
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-07-28T10:45:02.623-04:00[America/New_York]")
public class ErrorMessage {
  public static final String SERIALIZED_NAME_CODE = "code";
  @SerializedName(SERIALIZED_NAME_CODE)
  private Integer code;

  public static final String SERIALIZED_NAME_USER_MESSAGE = "user_message";
  @SerializedName(SERIALIZED_NAME_USER_MESSAGE)
  private String userMessage;

  public static final String SERIALIZED_NAME_LINK = "link";
  @SerializedName(SERIALIZED_NAME_LINK)
  private String link;

  public static final String SERIALIZED_NAME_DEVELOPER_MESSAGE = "developer_message";
  @SerializedName(SERIALIZED_NAME_DEVELOPER_MESSAGE)
  private String developerMessage;

  public ErrorMessage code(Integer code) {
    this.code = code;
    return this;
  }

   /**
   * Error Code   Possible values    1410 : Errors related to request validation   1420  :Errors related to request parsing 1440 :Client specific repository errors  1430 :Client specific security errors  1510 :Offers API : API service errors   1540 : Offers API : API specific repository errors    1530  : Offers API : API specific security errors
   * @return code
  **/
  @ApiModelProperty(value = "Error Code   Possible values    1410 : Errors related to request validation   1420  :Errors related to request parsing 1440 :Client specific repository errors  1430 :Client specific security errors  1510 :Offers API : API service errors   1540 : Offers API : API specific repository errors    1530  : Offers API : API specific security errors")
  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public ErrorMessage userMessage(String userMessage) {
    this.userMessage = userMessage;
    return this;
  }

   /**
   * User Message
   * @return userMessage
  **/
  @ApiModelProperty(value = "User Message")
  public String getUserMessage() {
    return userMessage;
  }

  public void setUserMessage(String userMessage) {
    this.userMessage = userMessage;
  }

  public ErrorMessage link(String link) {
    this.link = link;
    return this;
  }

   /**
   * Get link
   * @return link
  **/
  @ApiModelProperty(value = "")
  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public ErrorMessage developerMessage(String developerMessage) {
    this.developerMessage = developerMessage;
    return this;
  }

   /**
   * Detailed Message
   * @return developerMessage
  **/
  @ApiModelProperty(value = "Detailed Message")
  public String getDeveloperMessage() {
    return developerMessage;
  }

  public void setDeveloperMessage(String developerMessage) {
    this.developerMessage = developerMessage;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorMessage errorMessage = (ErrorMessage) o;
    return Objects.equals(this.code, errorMessage.code) &&
        Objects.equals(this.userMessage, errorMessage.userMessage) &&
        Objects.equals(this.link, errorMessage.link) &&
        Objects.equals(this.developerMessage, errorMessage.developerMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, userMessage, link, developerMessage);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorMessage {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    userMessage: ").append(toIndentedString(userMessage)).append("\n");
    sb.append("    link: ").append(toIndentedString(link)).append("\n");
    sb.append("    developerMessage: ").append(toIndentedString(developerMessage)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

