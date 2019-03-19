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

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataBusinessType;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.core.data.DataSource;
import io.polygenesis.core.data.DataValidator;
import io.polygenesis.core.data.VariableName;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * The type Aggregate root.
 *
 * @author Christos Tsakostas
 */
public class AggregateRoot extends BaseDomainObject<AggregateRoot>
    implements DomainObjectProperty<DataGroup> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================
  private Set<StateMutationMethod> stateMutationMethods;
  private Set<StateQueryMethod> stateQueryMethods;
  private Set<FactoryMethod> factoryMethods;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root.
   *
   * @param instantiationType the instantiation type
   * @param optionalSuperClass the optional super class
   * @param objectName the object name
   * @param packageName the package name
   * @param properties the properties
   * @param constructors the constructors
   * @param stateMutationMethods the state mutation methods
   * @param stateQueryMethods the state query methods
   * @param factoryMethods the factory methods
   * @param multiTenant the multi tenant
   */
  public AggregateRoot(
      InstantiationType instantiationType,
      Optional<AggregateRoot> optionalSuperClass,
      ObjectName objectName,
      PackageName packageName,
      Set<DomainObjectProperty> properties,
      Set<Constructor> constructors,
      Set<StateMutationMethod> stateMutationMethods,
      Set<StateQueryMethod> stateQueryMethods,
      Set<FactoryMethod> factoryMethods,
      Boolean multiTenant) {
    super(
        DomainObjectType.AGGREGATE_ROOT,
        instantiationType,
        optionalSuperClass,
        objectName,
        packageName,
        properties,
        constructors,
        multiTenant);
    setStateMutationMethods(stateMutationMethods);
    setStateQueryMethods(stateQueryMethods);
    setFactoryMethods(factoryMethods);
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
    setOptionalSuperClass(Optional.of(superclass));
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets state mutation methods.
   *
   * @return the state mutation methods
   */
  public Set<StateMutationMethod> getStateMutationMethods() {
    return stateMutationMethods;
  }

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

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets state mutation methods.
   *
   * @param stateMutationMethods the state mutation methods
   */
  private void setStateMutationMethods(Set<StateMutationMethod> stateMutationMethods) {
    Assertion.isNotNull(stateMutationMethods, "stateMutationMethods is required");
    this.stateMutationMethods = stateMutationMethods;
  }

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

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public PropertyType getPropertyType() {
    return PropertyType.REFERENCE_TO_AGGREGATE_ROOT;
  }

  @Override
  public DataGroup getData() {
    Set<Data> models = new LinkedHashSet<>();

    return new DataGroup(
        DataSource.user(),
        new VariableName(getObjectName().getText()),
        DataBusinessType.ANY,
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
    return Objects.equals(stateMutationMethods, that.stateMutationMethods)
        && Objects.equals(stateQueryMethods, that.stateQueryMethods)
        && Objects.equals(factoryMethods, that.factoryMethods);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), stateMutationMethods, stateQueryMethods, factoryMethods);
  }
}
