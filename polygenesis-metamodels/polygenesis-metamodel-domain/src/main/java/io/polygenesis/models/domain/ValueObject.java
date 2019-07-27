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

package io.polygenesis.models.domain;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Nameable;
import io.polygenesis.core.Packageable;

/**
 * The type Value object.
 *
 * @author Christos Tsakostas
 */
public class ValueObject extends BaseProperty<DataObject> implements Nameable, Packageable {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Value object.
   *
   * @param data the data
   */
  public ValueObject(DataObject data) {
    super(PropertyType.VALUE_OBJECT, data);
  }

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  /**
   * With variable name equal to object name value object.
   *
   * @return the value object
   */
  public ValueObject withVariableNameEqualToObjectName() {
    return new ValueObject(getData().withVariableNameEqualToObjectName());
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public Data getTypeParameterData() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ObjectName getObjectName() {
    return getData().getObjectName();
  }

  @Override
  public PackageName getPackageName() {
    return getData().getPackageName();
  }
}
