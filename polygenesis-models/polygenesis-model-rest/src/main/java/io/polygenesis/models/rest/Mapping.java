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

package io.polygenesis.models.rest;

import java.util.Objects;
import java.util.Set;

/**
 * The type Mapping.
 *
 * @author Christos Tsakostas
 */
public class Mapping {

  private Set<AbstractPathContent> pathContents;

  // ===============================================================================================
  // CONSTRUCTORS
  // ===============================================================================================

  /**
   * Instantiates a new Mapping.
   *
   * @param pathContents the path contents
   */
  public Mapping(Set<AbstractPathContent> pathContents) {
    setPathContents(pathContents);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets path contents.
   *
   * @return the path contents
   */
  public Set<AbstractPathContent> getPathContents() {
    return pathContents;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets path contents.
   *
   * @param pathContents the path contents
   */
  private void setPathContents(Set<AbstractPathContent> pathContents) {
    this.pathContents = pathContents;
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
    Mapping mapping = (Mapping) o;
    return Objects.equals(pathContents, mapping.pathContents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pathContents);
  }
}
