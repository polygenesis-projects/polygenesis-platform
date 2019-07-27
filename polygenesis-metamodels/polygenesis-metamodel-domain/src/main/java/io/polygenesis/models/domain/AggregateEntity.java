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
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Aggregate entity.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntity extends BaseDomainEntity implements DomainObjectProperty<DataObject> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private AggregateEntity superClass;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity.
   *
   * @param instantiationType the instantiation type
   * @param objectName the object name
   * @param packageName the package name
   * @param properties the properties
   * @param constructors the constructors
   * @param multiTenant the multi tenant
   */
  public AggregateEntity(
      InstantiationType instantiationType,
      ObjectName objectName,
      PackageName packageName,
      Set<DomainObjectProperty<?>> properties,
      Set<Constructor> constructors,
      Boolean multiTenant) {
    super(
        DomainObjectType.AGGREGATE_ENTITY,
        instantiationType,
        objectName,
        packageName,
        properties,
        constructors,
        multiTenant);
  }

  /**
   * Instantiates a new Aggregate entity.
   *
   * @param instantiationType the instantiation type
   * @param objectName the object name
   * @param packageName the package name
   * @param properties the properties
   * @param constructors the constructors
   * @param multiTenant the multi tenant
   * @param superClass the super class
   */
  public AggregateEntity(
      InstantiationType instantiationType,
      ObjectName objectName,
      PackageName packageName,
      Set<DomainObjectProperty<?>> properties,
      Set<Constructor> constructors,
      Boolean multiTenant,
      AggregateEntity superClass) {
    super(
        DomainObjectType.AGGREGATE_ENTITY,
        instantiationType,
        objectName,
        packageName,
        properties,
        constructors,
        multiTenant);
    setSuperClass(superClass);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets super class.
   *
   * @return the super class
   */
  public AggregateEntity getSuperClass() {
    return superClass;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets super class.
   *
   * @param superClass the super class
   */
  private void setSuperClass(AggregateEntity superClass) {
    Assertion.isNotNull(superClass, "superClass is required");
    this.superClass = superClass;
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public boolean hasSuperclass() {
    return getSuperClass() != null;
  }

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
        models);
  }

  @Override
  public Data getTypeParameterData() {
    throw new UnsupportedOperationException();
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
    if (!super.equals(o)) {
      return false;
    }
    AggregateEntity that = (AggregateEntity) o;
    return Objects.equals(superClass, that.superClass);
  }

  @Override
  @SuppressWarnings("CPD-END")
  public int hashCode() {
    return Objects.hash(super.hashCode(), superClass);
  }
}
