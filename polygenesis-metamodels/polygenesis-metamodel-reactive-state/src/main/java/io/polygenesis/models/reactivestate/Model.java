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

package io.polygenesis.models.reactivestate;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.commons.assertion.Assertion;
import java.util.Objects;

/**
 * The type Model.
 *
 * @author Christos Tsakostas
 */
public class Model {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Data data;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Model.
   *
   * @param data the model
   */
  public Model(Data data) {
    setData(data);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets model.
   *
   * @return the model
   */
  public Data getData() {
    return data;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets model.
   *
   * @param data the model
   */
  public void setData(Data data) {
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
    Model model1 = (Model) o;
    return Objects.equals(data, model1.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data);
  }
}
