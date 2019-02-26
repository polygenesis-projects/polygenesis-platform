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

import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.core.iomodel.IoModel;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.core.iomodel.VariableName;
import java.util.Optional;

/**
 * The type Primitive collection.
 *
 * @author Christos Tsakostas
 */
public class PrimitiveCollection extends AbstractProperty {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Primitive collection.
   *
   * @param variableName the variable name
   */
  public PrimitiveCollection(VariableName variableName) {
    super(PropertyType.PRIMITIVE_COLLECTION, variableName);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Optional<IoModelGroup> getIoModelGroupAsOptional() {
    return Optional.empty();
  }

  @Override
  public IoModel getIoModel() {
    throw new UnsupportedOperationException();
  }

  @Override
  public KeyValue getAsKeyValue() {
    throw new UnsupportedOperationException();
  }
}
