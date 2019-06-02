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
import io.polygenesis.abstraction.data.DataGroup;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.Metamodel;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Projection.
 *
 * @author Christos Tsakostas
 */
public class Projection extends BaseDomainEntity
    implements DomainObjectProperty<DataGroup>, Metamodel {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Persistence persistence;
  private Projection superClass;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Projection.
   *
   * @param instantiationType the instantiation type
   * @param objectName the object name
   * @param packageName the package name
   * @param properties the properties
   * @param constructors the constructors
   * @param multiTenant the multi tenant
   * @param persistence the persistence
   * @param superClass the super class
   */
  @SuppressWarnings("rawtypes")
  public Projection(
      InstantiationType instantiationType,
      ObjectName objectName,
      PackageName packageName,
      Set<DomainObjectProperty> properties,
      Set<Constructor> constructors,
      Boolean multiTenant,
      Persistence persistence,
      Projection superClass) {
    super(
        DomainObjectType.PROJECTION,
        instantiationType,
        objectName,
        packageName,
        properties,
        constructors,
        multiTenant);
    if (persistence != null) {
      setPersistence(persistence);
    }
    if (superClass != null) {
      setSuperClass(superClass);
    }
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets persistence.
   *
   * @return the persistence
   */
  public Persistence getPersistence() {
    return persistence;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets persistence.
   *
   * @param persistence the persistence
   */
  private void setPersistence(Persistence persistence) {
    Assertion.isNotNull(persistence, "persistence is required");
    this.persistence = persistence;
  }

  /**
   * Sets super class.
   *
   * @param superClass the super class
   */
  private void setSuperClass(Projection superClass) {
    Assertion.isNotNull(superClass, "superClass is required");
    this.superClass = superClass;
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public boolean hasSuperclass() {
    return superClass != null;
  }

  @Override
  public BaseDomainObject getSuperClass() {
    return superClass;
  }

  @Override
  public PropertyType getPropertyType() {
    return PropertyType.REFERENCE;
  }

  @Override
  public DataGroup getData() {
    Set<Data> models = new LinkedHashSet<>();

    return new DataGroup(
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
}
