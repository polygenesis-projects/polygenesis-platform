/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LTD
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
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.atteo.evo.inflector.English;

/**
 * Text conversion methods, necessary for code generation.
 *
 * <p>All the methods, except #toLowerCamel, expect a lower camel input. If you are not sure if your
 * input is already in lower camel format, you can call the other methods as following:
 *
 * <pre>
 *   TextConverter.toUpperUnderscore(TextConverter.toLowerCamel(&quot;input&quot;));
 * </pre>
 *
 * @author Christos Tsakostas
 */
public final class TextConverter {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================
  private static final Set<String> pluralBlacklist = new LinkedHashSet<>(Arrays.asList("jms"));

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================
  private TextConverter() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * To lower camel string.
   *
   * @param input the input
   * @return the string
   */
  public static String toLowerCamel(String input) {
    return enforceLoweCamel(input);
  }

  /**
   * To lower case string.
   *
   * @param input the input
   * @return the string
   */
  public static String toLowerCase(String input) {
    return input.toLowerCase();
  }

  /**
   * To upper case string.
   *
   * @param input the input
   * @return the string
   */
  public static String toUpperCase(String input) {
    return input.toUpperCase();
  }

  /**
   * To upper underscore string.
   *
   * @param input the input
   * @return the string
   */
  public static String toUpperUnderscore(String input) {
    return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, input);
  }

  /**
   * To upper camel string.
   *
   * @param input the input
   * @return the string
   */
  public static String toUpperCamel(String input) {
    return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, input);
  }

  /**
   * To lower hyphen.
   *
   * @param input the input
   * @return the string
   */
  public static String toLowerHyphen(String input) {
    return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, input);
  }

  /**
   * To lower underscore string.
   *
   * @param input the input
   * @return the string
   */
  public static String toLowerUnderscore(String input) {
    return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input);
  }

  /**
   * To lower camel spaces string.
   *
   * @param input the input
   * @return the string
   */
  public static String toLowerCamelSpaces(String input) {
    return StringUtils.replaceAll(
        CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, input), "-", " ");
  }

  /**
   * To upper camel spaces string.
   *
   * @param input the input
   * @return the string
   */
  public static String toUpperCamelSpaces(String input) {
    return StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(toUpperCamel(input)), ' ');
  }

  /**
   * To plural.
   *
   * @param input the input
   * @return the string
   */
  public static String toPlural(String input) {
    if (input.isEmpty()) {
      return input;
    }
    if (pluralBlacklist.contains(input.toLowerCase())) {
      return input;
    }
    return English.plural(input);
  }

  /**
   * To past tense string.
   *
   * @param input the input
   * @return the string
   */
  public static String toPastTense(String input) {
    return PastTense.make(input);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Enforce lowe camel string.
   *
   * @param original the original
   * @return the string
   */
  private static String enforceLoweCamel(String original) {
    String enforced = original;

    // Packages i.e. com.oregor.CreateUserRequest
    int index = enforced.lastIndexOf('.');
    if (index > 0) {
      enforced = enforced.substring(index + 1);
    }

    // i.e. USER_REGISTER
    enforced = delimiterToLowerCamel(enforced, "_");

    // i.e. USER-REGISTER
    enforced = delimiterToLowerCamel(enforced, "-");

    // i.e. USER REGISTER
    enforced = delimiterToLowerCamel(enforced, " ");

    // i.e. COLLECTION
    enforced = allUpperCaseToLowerCase(enforced);

    // Lower first
    if (!enforced.isEmpty()) {
      enforced = enforced.substring(0, 1).toLowerCase() + enforced.substring(1);
    }

    return enforced;
  }

  /**
   * Delimiter to lower camel string.
   *
   * @param input the input
   * @return the string
   */
  private static String delimiterToLowerCamel(String input, String delimiterCharacter) {
    if (input.contains(delimiterCharacter)) {
      StringBuilder sb = new StringBuilder(input.toLowerCase());

      for (int i = 0; i < sb.length(); i++) {
        if (sb.charAt(i) == delimiterCharacter.charAt(0)) {
          sb.deleteCharAt(i);
          sb.replace(i, i + 1, String.valueOf(Character.toUpperCase(sb.charAt(i))));
        }
      }

      return sb.toString();
    } else {
      return input;
    }
  }

  /**
   * All upper case to lower case string.
   *
   * @param input the input
   * @return the string
   */
  private static String allUpperCaseToLowerCase(String input) {
    if (StringUtils.isAllUpperCase(input)) {
      return input.toLowerCase();
    } else {
      return input;
    }
  }
}
