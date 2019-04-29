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

package io.polygenesis.core;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.core.data.Data;
import java.util.Objects;

/**
 * The base class for any {@link Data} containers.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractDataContainer {

  private Data data;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract data container.
   *
   * @param data the data
   */
  public AbstractDataContainer(Data data) {
    setData(data);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets data.
   *
   * @return the data
   */
  public Data getData() {
    return data;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets data.
   *
   * @param data the data
   */
  private void setData(Data data) {
    Assertion.isNotNull(data, "data is required");
    this.data = data;
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
    AbstractDataContainer that = (AbstractDataContainer) o;
    return Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data);
  }
}
