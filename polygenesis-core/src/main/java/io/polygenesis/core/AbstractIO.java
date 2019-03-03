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

package io.polygenesis.core;

import io.polygenesis.core.iomodel.IoModel;
import io.polygenesis.core.iomodel.IoModelArray;
import io.polygenesis.core.iomodel.IoModelGroup;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The base class for Function Input and Output.
 *
 * @author Christos Tsakostas
 */
abstract class AbstractIO {

  private IoModel model;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Argument.
   *
   * @param model the model
   */
  AbstractIO(IoModel model) {
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
  public IoModel getModel() {
    return model;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets as io model group.
   *
   * @return the as io model group
   */
  public IoModelGroup getAsIoModelGroup() {
    if (model.isIoModelArray()) {
      return (IoModelArray) model;
    }

    if (model.isIoModelGroup()) {
      return (IoModelGroup) model;
    } else {
      throw new IllegalStateException("Model is not IoModelGroup");
    }
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setModel(IoModel model) {
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

    AbstractIO argument = (AbstractIO) o;

    return new EqualsBuilder().append(model, argument.model).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(model).toHashCode();
  }
}
