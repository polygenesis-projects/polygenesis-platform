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

package io.polygenesis.generators.java.shared;

import java.util.Set;

/**
 * The type Constructor projection.
 *
 * @author Christos Tsakostas
 */
public class ConstructorProjection {

  private Set<ArgumentProjection> parameters;
  private String superImpl;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Constructor projection.
   *
   * @param parameters the parameters
   * @param superImpl the super
   */
  public ConstructorProjection(Set<ArgumentProjection> parameters, String superImpl) {
    setParameters(parameters);
    setSuperImpl(superImpl);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets parameters.
   *
   * @return the parameters
   */
  public Set<ArgumentProjection> getParameters() {
    return parameters;
  }

  /**
   * Gets super.
   *
   * @return the super
   */
  public String getSuperImpl() {
    return superImpl;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets parameters.
   *
   * @param parameters the parameters
   */
  private void setParameters(Set<ArgumentProjection> parameters) {
    this.parameters = parameters;
  }

  /**
   * Sets super.
   *
   * @param superImpl the super
   */
  private void setSuperImpl(String superImpl) {
    this.superImpl = superImpl;
  }
}
