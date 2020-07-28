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
 * Holds card content disclosure
 */
@ApiModel(description = "Holds card content disclosure")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-07-28T10:45:02.623-04:00[America/New_York]")
public class Disclosure {
  public static final String SERIALIZED_NAME_TEXT = "text";
  @SerializedName(SERIALIZED_NAME_TEXT)
  private String text;

  public Disclosure text(String text) {
    this.text = text;
    return this;
  }

   /**
   * A Disclosure text to be displayed
   * @return text
  **/
  @ApiModelProperty(value = "A Disclosure text to be displayed")
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Disclosure disclosure = (Disclosure) o;
    return Objects.equals(this.text, disclosure.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Disclosure {\n");
    
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
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

