/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ===========================LICENSE_END==================================
 */

package io.polygenesis.commons.text;

import com.google.common.base.CaseFormat;
import io.polygenesis.commons.assertions.Assertion;
import java.io.Serializable;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.atteo.evo.inflector.English;

/**
 * Value object encapsulating string conversions required in code generation.
 *
 * @author Christos Tsakostas
 */
public class Text implements Serializable {

  private static final long serialVersionUID = 1L;

  private String original;
  private String modified;
  private String lowerCase;
  private String upperCase;
  private String upperUnderscore;
  private String lowerCamel;
  private String upperCamel;
  private String lowerHyphen;
  private String lowerCamelSpaces;
  private String upperCamelSpaces;

  private Text plural;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Default constructor for {@link Text} object.
   *
   * @param text the input string
   */
  public Text(String text) {
    this(text, true);
  }

  private Text(String text, boolean pluralize) {
    Assertion.isNotEmpty(text, "text cannot be empty");

    setOriginal(text);

    if (pluralize) {
      String pluralized = English.plural(this.getOriginal());
      this.plural = new Text(pluralized, false);
    }
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets original.
   *
   * @return the original
   */
  public String getOriginal() {
    return original;
  }

  /**
   * Gets modified.
   *
   * @return the modified
   */
  public String getModified() {
    return modified;
  }

  /**
   * Gets lower case.
   *
   * @return the lower case
   */
  public String getLowerCase() {
    return lowerCase;
  }

  /**
   * Gets upper case.
   *
   * @return the upper case
   */
  public String getUpperCase() {
    return upperCase;
  }

  /**
   * Gets upper underscore.
   *
   * @return the upper underscore
   */
  public String getUpperUnderscore() {
    return upperUnderscore;
  }

  /**
   * Gets lower camel.
   *
   * @return the lower camel
   */
  public String getLowerCamel() {
    return lowerCamel;
  }

  /**
   * Gets upper camel.
   *
   * @return the upper camel
   */
  public String getUpperCamel() {
    return upperCamel;
  }

  /**
   * Gets lower hyphen.
   *
   * @return the lower hyphen
   */
  public String getLowerHyphen() {
    return lowerHyphen;
  }

  /**
   * Gets lower camel spaces.
   *
   * @return the lower camel spaces
   */
  public String getLowerCamelSpaces() {
    return lowerCamelSpaces;
  }

  /**
   * Gets upper camel spaces.
   *
   * @return the upper camel spaces
   */
  public String getUpperCamelSpaces() {
    return upperCamelSpaces;
  }

  /**
   * Gets plural.
   *
   * @return the plural
   */
  public Text getPlural() {
    return plural;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private String underscoreToLowerCamel(String input) {
    if (input.contains("_")) {
      StringBuilder sb = new StringBuilder(input.toLowerCase());

      for (int i = 0; i < sb.length(); i++) {
        if (sb.charAt(i) == '_') {
          sb.deleteCharAt(i);
          sb.replace(i, i + 1, String.valueOf(Character.toUpperCase(sb.charAt(i))));
        }
      }

      return sb.toString();
    } else {
      return input;
    }
  }

  private String allUpperCaseToLowerCase(String input) {
    if (StringUtils.isAllUpperCase(input)) {
      return input.toLowerCase();
    } else {
      return input;
    }
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================
  private void setOriginal(String text) {
    this.original = text;

    setModified(text);

    setLowerCamel(this.getModified());

    setUpperCamel(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.getModified()));

    setUpperUnderscore(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, this.getModified()));

    setLowerHyphen(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, this.getModified()));

    setLowerCase(this.getModified().toLowerCase());

    setUpperCase(this.getModified().toUpperCase());

    setLowerCamelSpaces(
        StringUtils.replaceAll(
            CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, this.getModified()), "-", " "));

    setUpperCamelSpaces(
        StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(this.getUpperCamel()), ' '));
  }

  private void setModified(String original) {
    // Packages i.e. com.oregor.CreateUserRequest
    int index = original.lastIndexOf('.');
    if (index > 0) {
      this.modified = original.substring(index + 1);
    } else {
      this.modified = original;
    }

    // i.e. USER_REGISTER
    this.modified = this.underscoreToLowerCamel(this.modified);

    // i.e. COLLECTION
    this.modified = allUpperCaseToLowerCase(this.modified);

    // Lower first
    this.modified = this.modified.substring(0, 1).toLowerCase() + this.modified.substring(1);
  }

  private void setUpperCamelSpaces(String upperCamelSpaces) {
    this.upperCamelSpaces = upperCamelSpaces;
  }

  private void setLowerCamelSpaces(String lowerCamelSpaces) {
    this.lowerCamelSpaces = lowerCamelSpaces;
  }

  private void setUpperCase(String upper) {
    this.upperCase = upper;
  }

  private void setLowerHyphen(String lowerHyphen) {
    this.lowerHyphen = lowerHyphen;
  }

  private void setLowerCase(String lower) {
    this.lowerCase = lower;
  }

  private void setLowerCamel(String lowerCamel) {
    this.lowerCamel = lowerCamel;
  }

  private void setUpperUnderscore(String upperUnderscore) {
    this.upperUnderscore = upperUnderscore;
  }

  private void setUpperCamel(String upperCamel) {
    this.upperCamel = upperCamel;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Text text = (Text) o;
    return Objects.equals(original, text.original)
        && Objects.equals(modified, text.modified)
        && Objects.equals(lowerCase, text.lowerCase)
        && Objects.equals(upperCase, text.upperCase)
        && Objects.equals(upperUnderscore, text.upperUnderscore)
        && Objects.equals(lowerCamel, text.lowerCamel)
        && Objects.equals(upperCamel, text.upperCamel)
        && Objects.equals(lowerHyphen, text.lowerHyphen)
        && Objects.equals(lowerCamelSpaces, text.lowerCamelSpaces)
        && Objects.equals(upperCamelSpaces, text.upperCamelSpaces)
        && Objects.equals(plural, text.plural);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        original,
        modified,
        lowerCase,
        upperCase,
        upperUnderscore,
        lowerCamel,
        upperCamel,
        lowerHyphen,
        lowerCamelSpaces,
        upperCamelSpaces,
        plural);
  }
}
