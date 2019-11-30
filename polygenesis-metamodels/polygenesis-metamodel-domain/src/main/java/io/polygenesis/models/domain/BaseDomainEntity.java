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

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Base domain entity.
 *
 * @author Christos Tsakostas
 */
public abstract class BaseDomainEntity extends BaseDomainObject {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<StateMutationMethod> stateMutationMethods;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Base domain entity.
   *
   * @param domainObjectType the domain object type
   * @param instantiationType the instantiation type
   * @param objectName the object name
   * @param packageName the package name
   * @param properties the properties
   * @param constructors the constructors
   * @param multiTenant the multi tenant
   */
  public BaseDomainEntity(
      DomainObjectType domainObjectType,
      InstantiationType instantiationType,
      ObjectName objectName,
      PackageName packageName,
      Set<DomainObjectProperty<?>> properties,
      Set<Constructor> constructors,
      Boolean multiTenant) {
    super(
        domainObjectType,
        instantiationType,
        objectName,
        packageName,
        properties,
        constructors,
        multiTenant);
    setStateMutationMethods(new LinkedHashSet<>());
  }

  // ===============================================================================================
  // BEHAVIOR
  // ===============================================================================================

  /**
   * Add state mutation method.
   *
   * @param stateMutationMethod the state mutation method
   */
  public void addStateMutationMethod(StateMutationMethod stateMutationMethod) {
    getStateMutationMethods().add(stateMutationMethod);
  }

  /**
   * Add state mutation methods.
   *
   * @param stateMutationMethods the state mutation methods
   */
  public void addStateMutationMethods(Set<StateMutationMethod> stateMutationMethods) {
    getStateMutationMethods().addAll(stateMutationMethods);
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Find state mutation methods by purpose set.
   *
   * @param purpose the purpose
   * @return the set
   */
  public Set<StateMutationMethod> findStateMutationMethodsByPurpose(Purpose purpose) {
    return getStateMutationMethods()
        .stream()
        .filter(
            stateMutationMethod -> stateMutationMethod.getFunction().getPurpose().equals(purpose))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  /**
   * Find one state mutation method by purpose state mutation method.
   *
   * @param purpose the purpose
   * @return the state mutation method
   */
  public StateMutationMethod findOneStateMutationMethodByPurpose(Purpose purpose) {
    Set<StateMutationMethod> stateMutationMethods = findStateMutationMethodsByPurpose(purpose);

    if (stateMutationMethods.isEmpty()) {
      // TODO: Fallback to constructors for now
      Constructor constructor =
          getConstructors().stream().findFirst().orElseThrow(IllegalStateException::new);

      return constructor;
      //      throw new IllegalStateException(
      //          String.format("No stateMutationMethods found for purpose=%s", purpose.getText()));
    } else if (stateMutationMethods.size() == 1) {
      return stateMutationMethods.stream().findFirst().orElseThrow(IllegalStateException::new);
    } else {
      throw new IllegalStateException(
          String.format(
              "More than one stateMutationMethods found for purpose=%s", purpose.getText()));
    }
  }

  // ===============================================================================================
  // TRANSFORMATIONS
  // ===============================================================================================

  /**
   * As data group data object.
   *
   * @return the data object
   */
  public DataObject asDataGroup() {

    DataObject dataObject =
        new DataObject(new ObjectName(getObjectName().getText()), getPackageName());

    getProperties().forEach(property -> dataObject.addData(property.getData()));

    return dataObject;
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
    BaseDomainEntity that = (BaseDomainEntity) o;
    return Objects.equals(stateMutationMethods, that.stateMutationMethods);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), stateMutationMethods);
  }
}
