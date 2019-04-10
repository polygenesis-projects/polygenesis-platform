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

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.core.data.Data;
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

  private Data model;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Model.
   *
   * @param model the model
   */
  public Model(Data model) {
    setModel(model);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets model.
   *
   * @return the model
   */
  public Data getModel() {
    return model;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets model.
   *
   * @param model the model
   */
  public void setModel(Data model) {
    Assertion.isNotNull(model, "model is required");
    this.model = model;
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
    return Objects.equals(model, model1.model);
  }

  @Override
  public int hashCode() {
    return Objects.hash(model);
  }
}
