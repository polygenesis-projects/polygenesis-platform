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

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/** The type Function role. */
public class FunctionRole implements Serializable {

  private static final long serialVersionUID = 1L;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /** The constant ADMIN. */
  public static final String ADMIN = "ADMIN";
  /** The constant USER. */
  public static final String USER = "USER";
  /** The constant SYSTEM. */
  public static final String SYSTEM = "SYSTEM";

  /** The constant ANONYMOUS. */
  public static final String ANONYMOUS = "ANONYMOUS";

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String text;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Custom function role.
   *
   * @param text the text
   * @return the function role
   */
  public static FunctionRole custom(String text) {
    return new FunctionRole(text);
  }

  /**
   * User function role.
   *
   * @return the function role
   */
  public static FunctionRole user() {
    return new FunctionRole(USER);
  }

  /**
   * User as set set.
   *
   * @return the set
   */
  public static Set<FunctionRole> userAsSet() {
    return new LinkedHashSet<>(Collections.singletonList(new FunctionRole(USER)));
  }

  /**
   * Admin function role.
   *
   * @return the function role
   */
  public static FunctionRole admin() {
    return new FunctionRole(ADMIN);
  }

  /**
   * Admin as set set.
   *
   * @return the set
   */
  public static Set<FunctionRole> adminAsSet() {
    return new LinkedHashSet<>(Collections.singletonList(new FunctionRole(ADMIN)));
  }

  /**
   * System function role.
   *
   * @return the function role
   */
  public static FunctionRole system() {
    return new FunctionRole(SYSTEM);
  }

  /**
   * System as set set.
   *
   * @return the set
   */
  public static Set<FunctionRole> systemAsSet() {
    return new LinkedHashSet<>(Collections.singletonList(new FunctionRole(SYSTEM)));
  }

  /**
   * Anonymous function role.
   *
   * @return the function role
   */
  public static FunctionRole anonymous() {
    return new FunctionRole(ANONYMOUS);
  }

  /**
   * Anonymous as set set.
   *
   * @return the set
   */
  public static Set<FunctionRole> anonymousAsSet() {
    return new LinkedHashSet<>(Collections.singletonList(new FunctionRole(ANONYMOUS)));
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Function role.
   *
   * @param text the text
   */
  public FunctionRole(String text) {
    this.text = text;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets text.
   *
   * @return the text
   */
  public String getText() {
    return text;
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
    FunctionRole that = (FunctionRole) o;
    return Objects.equals(text, that.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text);
  }
}
