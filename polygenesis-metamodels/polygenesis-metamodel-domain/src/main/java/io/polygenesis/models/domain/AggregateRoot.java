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
import io.polygenesis.core.Metamodel;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Aggregate root.
 *
 * @author Christos Tsakostas
 */
public class AggregateRoot extends BaseDomainEntity
    implements DomainObjectProperty<DataObject>, Metamodel {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<StateQueryMethod> stateQueryMethods;
  private Set<FactoryMethod> factoryMethods;
  private AggregateRoot superClass;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root.
   *
   * @param instantiationType the instantiation type
   * @param objectName the object name
   * @param packageName the package name
   * @param properties the properties
   * @param constructors the constructors
   * @param multiTenant the multi tenant
   * @param stateQueryMethods the state query methods
   * @param factoryMethods the factory methods
   */
  public AggregateRoot(
      InstantiationType instantiationType,
      ObjectName objectName,
      PackageName packageName,
      Set<DomainObjectProperty<?>> properties,
      Set<Constructor> constructors,
      Boolean multiTenant,
      Set<StateQueryMethod> stateQueryMethods,
      Set<FactoryMethod> factoryMethods) {
    super(
        DomainObjectType.AGGREGATE_ROOT,
        instantiationType,
        objectName,
        packageName,
        properties,
        constructors,
        multiTenant);
    setStateQueryMethods(stateQueryMethods);
    setFactoryMethods(factoryMethods);
  }

  /**
   * Instantiates a new Aggregate root.
   *
   * @param instantiationType the instantiation type
   * @param objectName the object name
   * @param packageName the package name
   * @param properties the properties
   * @param constructors the constructors
   * @param multiTenant the multi tenant
   * @param stateQueryMethods the state query methods
   * @param factoryMethods the factory methods
   * @param superClass the super class
   */
  public AggregateRoot(
      InstantiationType instantiationType,
      ObjectName objectName,
      PackageName packageName,
      Set<DomainObjectProperty<?>> properties,
      Set<Constructor> constructors,
      Boolean multiTenant,
      Set<StateQueryMethod> stateQueryMethods,
      Set<FactoryMethod> factoryMethods,
      AggregateRoot superClass) {
    super(
        DomainObjectType.AGGREGATE_ROOT,
        instantiationType,
        objectName,
        packageName,
        properties,
        constructors,
        multiTenant);
    setStateQueryMethods(stateQueryMethods);
    setFactoryMethods(factoryMethods);
    setSuperClass(superClass);
  }

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  /**
   * Change superclass to.
   *
   * @param superclass the superclass
   */
  public void changeSuperclassTo(AggregateRoot superclass) {
    setSuperClass(superclass);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets state query methods.
   *
   * @return the state query methods
   */
  public Set<StateQueryMethod> getStateQueryMethods() {
    return stateQueryMethods;
  }

  /**
   * Gets factory methods.
   *
   * @return the factory methods
   */
  public Set<FactoryMethod> getFactoryMethods() {
    return factoryMethods;
  }

  /**
   * Gets super class.
   *
   * @return the super class
   */
  public AggregateRoot getSuperClass() {
    return superClass;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Aggregate root id aggregate root id.
   *
   * @return the aggregate root id
   */
  public AggregateRootId aggregateRootId() {
    return getProperties()
        .stream()
        .filter(property -> property.getPropertyType().equals(PropertyType.AGGREGATE_ROOT_ID))
        .map(AggregateRootId.class::cast)
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalStateException(
                    String.format(
                        "No AggregateRootId defined for AggregateRoot=%s",
                        getObjectName().getText())));
  }

  /**
   * Contains domain entity.
   *
   * @param domainEntity the domain entity
   * @return the boolean
   */
  public boolean contains(BaseDomainEntity domainEntity) {
    return getProperties()
        .stream()
        .filter(
            domainObjectProperty ->
                domainObjectProperty
                    .getPropertyType()
                    .equals(PropertyType.AGGREGATE_ENTITY_COLLECTION))
        .map(AggregateEntityCollection.class::cast)
        .filter(
            aggregateEntityCollection ->
                aggregateEntityCollection.getAggregateEntity().equals(domainEntity))
        .anyMatch(aggregateEntityCollection -> true);
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets state query methods.
   *
   * @param stateQueryMethods the state query methods
   */
  private void setStateQueryMethods(Set<StateQueryMethod> stateQueryMethods) {
    Assertion.isNotNull(stateQueryMethods, "stateQueryMethods is required");
    this.stateQueryMethods = stateQueryMethods;
  }

  /**
   * Sets factory methods.
   *
   * @param factoryMethods the factory methods
   */
  private void setFactoryMethods(Set<FactoryMethod> factoryMethods) {
    Assertion.isNotNull(factoryMethods, "factoryMethods is required");
    this.factoryMethods = factoryMethods;
  }

  /**
   * Sets super class.
   *
   * @param superClass the super class
   */
  private void setSuperClass(AggregateRoot superClass) {
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
    return PropertyType.REFERENCE_TO_AGGREGATE_ROOT;
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
    AggregateRoot that = (AggregateRoot) o;
    return Objects.equals(stateQueryMethods, that.stateQueryMethods)
        && Objects.equals(factoryMethods, that.factoryMethods)
        && Objects.equals(superClass, that.superClass);
  }

  @Override
  @SuppressWarnings("CPD-END")
  public int hashCode() {
    return Objects.hash(super.hashCode(), stateQueryMethods, factoryMethods, superClass);
  }
}
