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

package io.polygenesis.metamodels.spreadsheet;

import io.polygenesis.commons.assertion.Assertion;
import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractIndex implements Serializable {

  private static final long serialVersionUID = 1L;

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Integer index;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract index.
   *
   * @param index the index
   */
  public AbstractIndex(Integer index) {
    setIndex(index);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets index.
   *
   * @return the index
   */
  public Integer getIndex() {
    return index;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets index.
   *
   * @param index the index
   */
  private void setIndex(Integer index) {
    Assertion.isNotNull(index, "index is required");
    this.index = index;
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
    AbstractIndex that = (AbstractIndex) o;
    return Objects.equals(index, that.index);
  }

  @Override
  public int hashCode() {
    return Objects.hash(index);
  }
}
