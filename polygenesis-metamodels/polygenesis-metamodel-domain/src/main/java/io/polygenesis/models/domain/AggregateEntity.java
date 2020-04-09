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

package io.polygenesis.models.domain;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.LinkedHashSet;
import java.util.Set;

public class AggregateEntity extends DomainObject {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity.
   *
   * @param instantiationType the instantiation type
   * @param objectName the object name
   * @param packageName the package name
   * @param parent the parent
   */
  public AggregateEntity(
      InstantiationType instantiationType,
      ObjectName objectName,
      PackageName packageName,
      DomainObject parent) {
    super(DomainObjectType.AGGREGATE_ENTITY, instantiationType, objectName, packageName, parent);
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public PropertyType getPropertyType() {
    return PropertyType.AGGREGATE_ENTITY;
  }

  @Override
  public DataObject getData() {
    Set<Data> models = new LinkedHashSet<>();

    return new DataObject(
        new VariableName(getObjectName().getText()),
        DataPurpose.any(),
        DataValidator.empty(),
        getObjectName(),
        getPackageName(),
        models,
        DataSourceType.DEFAULT);
  }

  @Override
  public Data getTypeParameterData() {
    throw new UnsupportedOperationException();
  }
}
