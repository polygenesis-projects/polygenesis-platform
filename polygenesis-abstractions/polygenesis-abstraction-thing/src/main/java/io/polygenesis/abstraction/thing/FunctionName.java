/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.abstraction.thing;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.text.TextConverter;
import java.io.Serializable;
import java.util.Objects;

public class FunctionName implements Serializable {

  private static final long serialVersionUID = 1L;

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String verb;
  private String object;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Of verb only function name.
   *
   * @param verb the verb
   * @return the function name
   */
  public static FunctionName ofVerbOnly(String verb) {
    return new FunctionName(verb, "");
  }

  /**
   * Of verb and object function name.
   *
   * @param verb the verb
   * @param object the object
   * @return the function name
   */
  public static FunctionName ofVerbAndObject(String verb, String object) {
    return new FunctionName(verb, object);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Function name.
   *
   * @param verb the verb
   * @param object the object
   */
  private FunctionName(String verb, String object) {
    setVerb(verb);
    setObject(object);
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets full name.
   *
   * @return the full name
   */
  public String getFullName() {
    return String.format("%s%s", verb, TextConverter.toUpperCamel(object));
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets verb.
   *
   * @return the verb
   */
  public String getVerb() {
    return verb;
  }

  /**
   * Gets object.
   *
   * @return the object
   */
  public String getObject() {
    return object;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setVerb(String verb) {
    Assertion.isNotNull(verb, "verb is required");
    this.verb = TextConverter.toLowerCamel(verb);
  }

  private void setObject(String object) {
    Assertion.isNotNull(object, "object is required");
    this.object = TextConverter.toLowerCamel(object);
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
    FunctionName that = (FunctionName) o;
    return Objects.equals(verb, that.verb) && Objects.equals(object, that.object);
  }

  @Override
  public int hashCode() {
    return Objects.hash(verb, object);
  }
}
