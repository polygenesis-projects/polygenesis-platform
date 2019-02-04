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

package io.polygenesis.models.domain;

import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.core.iomodel.IoModelPrimitive;
import io.polygenesis.core.iomodel.VariableName;
import java.util.Optional;

/**
 * The type Primitive.
 *
 * @author Christos Tsakostas
 */
public class Primitive extends AbstractProperty {

  private IoModelPrimitive ioModelPrimitive;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Primitive.
   *
   * @param ioModelPrimitive the io model primitive
   * @param variableName the variable name
   */
  public Primitive(IoModelPrimitive ioModelPrimitive, VariableName variableName) {
    super(variableName);
    setIoModelPrimitive(ioModelPrimitive);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets io model primitive.
   *
   * @return the io model primitive
   */
  public IoModelPrimitive getIoModelPrimitive() {
    return ioModelPrimitive;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets io model primitive.
   *
   * @param ioModelPrimitive the io model primitive
   */
  public void setIoModelPrimitive(IoModelPrimitive ioModelPrimitive) {
    this.ioModelPrimitive = ioModelPrimitive;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Optional<IoModelGroup> getIoModelGroupAsOptional() {
    return Optional.empty();
  }
}
