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
import io.polygenesis.core.Generatable;
import io.polygenesis.core.Metamodel;
import io.polygenesis.core.Nameable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Domain object.
 *
 * @author Christos Tsakostas
 */
public abstract class DomainObject
    implements Generatable, Nameable, SubClassable, Metamodel, DomainObjectProperty<DataObject> {

  // ===============================================================================================
  // REQUIRED STATE
  // ===============================================================================================
  private DomainObjectType domainObjectType;
  private InstantiationType instantiationType;
  private ObjectName objectName;
  private PackageName packageName;
  private Set<DomainObjectProperty<?>> properties;
  private Set<Constructor> constructors;
  private Set<StateMutationMethod> stateMutationMethods;
  private Set<StateQueryMethod> stateQueryMethods;
  private Set<FactoryMethod> factoryMethods;
  private Boolean multiTenant;
  private Boolean exportable;

  // ===============================================================================================
  // OPTIONAL STATE
  // ===============================================================================================
  private DomainObject parent;
  private DomainObject superClass;

  // ===============================================================================================
  // STATE FOR AGGREGATE ROOTS AND PROJECTIONS ONLY
  // ===============================================================================================
  private Persistence persistence;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain object.
   *
   * @param domainObjectType the domain object type
   * @param instantiationType the instantiation type
   * @param objectName the object name
   * @param packageName the package name
   * @param multiTenant the multi tenant
   */
  public DomainObject(
      DomainObjectType domainObjectType,
      InstantiationType instantiationType,
      ObjectName objectName,
      PackageName packageName,
      Boolean multiTenant) {
    setDomainObjectType(domainObjectType);
    setInstantiationType(instantiationType);
    setObjectName(objectName);
    setPackageName(packageName);
    setMultiTenant(multiTenant);

    setExportable(true);
    setProperties(new LinkedHashSet<>());
    setConstructors(new LinkedHashSet<>());
    setStateMutationMethods(new LinkedHashSet<>());
    setStateQueryMethods(new LinkedHashSet<>());
    setFactoryMethods(new LinkedHashSet<>());
  }

  /**
   * Instantiates a new Domain object.
   *
   * @param domainObjectType the domain object type
   * @param instantiationType the instantiation type
   * @param objectName the object name
   * @param packageName the package name
   * @param parent the parent
   */
  public DomainObject(
      DomainObjectType domainObjectType,
      InstantiationType instantiationType,
      ObjectName objectName,
      PackageName packageName,
      DomainObject parent) {
    setDomainObjectType(domainObjectType);
    setInstantiationType(instantiationType);
    setObjectName(objectName);
    setPackageName(packageName);

    setExportable(true);
    setProperties(new LinkedHashSet<>());
    setConstructors(new LinkedHashSet<>());
    setStateMutationMethods(new LinkedHashSet<>());
    setStateQueryMethods(new LinkedHashSet<>());
    setFactoryMethods(new LinkedHashSet<>());

    if (!instantiationType.equals(InstantiationType.ABSTRACT)) {
      setParent(parent);
      setMultiTenant(parent.getMultiTenant());
    }
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

  /**
   * Add constructors.
   *
   * @param constructors the constructors
   */
  public void addConstructors(Set<Constructor> constructors) {
    getConstructors().addAll(constructors);
  }

  /**
   * Assign persistence.
   *
   * @param persistence the persistence
   */
  public void assignPersistence(Persistence persistence) {
    setPersistence(persistence);
  }

  /**
   * Assign super class.
   *
   * @param domainObject the domain object
   */
  public void assignSuperClass(DomainObject domainObject) {
    setSuperClass(domainObject);
  }

  /**
   * Assign parent.
   *
   * @param domainObject the domain object
   */
  public void assignParent(DomainObject domainObject) {
    setParent(domainObject);
  }

  /**
   * Assign properties.
   *
   * @param properties the properties
   */
  public void assignProperties(Set<DomainObjectProperty<?>> properties) {
    setProperties(properties);
  }

  /**
   * Change exportable to.
   *
   * @param exportable the exportable
   */
  public void changeExportableTo(Boolean exportable) {
    setExportable(exportable);
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Is abstract boolean.
   *
   * @return the boolean
   */
  public Boolean isAbstract() {
    return isAbstractAggregateRoot() || isAbstractAggregateEntity();
  }

  /**
   * Is aggregate root boolean.
   *
   * @return the boolean
   */
  public Boolean isAggregateRoot() {
    return getDomainObjectType().equals(DomainObjectType.AGGREGATE_ROOT)
        && getInstantiationType().equals(InstantiationType.CONCRETE);
  }

  /**
   * Is abstract aggregate root boolean.
   *
   * @return the boolean
   */
  public Boolean isAbstractAggregateRoot() {
    return getDomainObjectType().equals(DomainObjectType.ABSTRACT_AGGREGATE_ROOT)
        || getDomainObjectType().equals(DomainObjectType.AGGREGATE_ROOT)
            && getInstantiationType().equals(InstantiationType.ABSTRACT);
  }

  /**
   * Is aggregate entity boolean.
   *
   * @return the boolean
   */
  public Boolean isAggregateEntity() {
    return getDomainObjectType().equals(DomainObjectType.AGGREGATE_ENTITY)
        && getInstantiationType().equals(InstantiationType.CONCRETE);
  }

  /**
   * Is abstract aggregate entity boolean.
   *
   * @return the boolean
   */
  public Boolean isAbstractAggregateEntity() {
    return getDomainObjectType().equals(DomainObjectType.ABSTRACT_AGGREGATE_ENTITY)
        || getDomainObjectType().equals(DomainObjectType.AGGREGATE_ENTITY)
            && getInstantiationType().equals(InstantiationType.ABSTRACT);
  }

  /**
   * Is supportive entity boolean.
   *
   * @return the boolean
   */
  public Boolean isSupportiveEntity() {
    return getDomainObjectType().equals(DomainObjectType.SUPPORTIVE_ENTITY)
        && getInstantiationType().equals(InstantiationType.CONCRETE);
  }

  /**
   * Is projection boolean.
   *
   * @return the boolean
   */
  public Boolean isProjection() {
    return getDomainObjectType().equals(DomainObjectType.PROJECTION)
        && getInstantiationType().equals(InstantiationType.CONCRETE);
  }

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

  /**
   * Gets optional persistence.
   *
   * @return the optional persistence
   */
  public Optional<Persistence> getOptionalPersistence() {
    return getPersistence() != null ? Optional.of(getPersistence()) : Optional.empty();
  }

  // ===============================================================================================
  // AGGREGATE ROOT QUERIES
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
   * Gets domain object type.
   *
   * @return the domain object type
   */
  public DomainObjectType getDomainObjectType() {
    return domainObjectType;
  }

  /**
   * Gets instantiation type.
   *
   * @return the instantiation type
   */
  public InstantiationType getInstantiationType() {
    return instantiationType;
  }

  /**
   * Gets object name.
   *
   * @return the object name
   */
  public ObjectName getObjectName() {
    return objectName;
  }

  /**
   * Gets package name.
   *
   * @return the package name
   */
  public PackageName getPackageName() {
    return packageName;
  }

  /**
   * Gets properties.
   *
   * @return the properties
   */
  public Set<DomainObjectProperty<?>> getProperties() {
    return properties;
  }

  /**
   * Gets constructors.
   *
   * @return the constructors
   */
  public Set<Constructor> getConstructors() {
    return constructors;
  }

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

  /**
   * Gets multi tenant.
   *
   * @return the multi tenant
   */
  public Boolean getMultiTenant() {
    return multiTenant;
  }

  /**
   * Gets exportable.
   *
   * @return the exportable
   */
  public Boolean getExportable() {
    return exportable;
  }

  /**
   * Gets parent.
   *
   * @return the parent
   */
  public DomainObject getParent() {
    return parent;
  }

  @Override
  public DomainObject getSuperClass() {
    return superClass;
  }

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
   * Sets domain object type.
   *
   * @param domainObjectType the domain object type
   */
  private void setDomainObjectType(DomainObjectType domainObjectType) {
    Assertion.isNotNull(domainObjectType, "domainObjectType is required");
    this.domainObjectType = domainObjectType;
  }

  /**
   * Sets instantiation type.
   *
   * @param instantiationType the instantiation type
   */
  private void setInstantiationType(InstantiationType instantiationType) {
    Assertion.isNotNull(instantiationType, "instantiationType is required");
    this.instantiationType = instantiationType;
  }

  /**
   * Sets object name.
   *
   * @param objectName the object name
   */
  private void setObjectName(ObjectName objectName) {
    Assertion.isNotNull(objectName, "objectName is required");
    this.objectName = objectName;
  }

  /**
   * Sets package name.
   *
   * @param packageName the package name
   */
  private void setPackageName(PackageName packageName) {
    Assertion.isNotNull(packageName, "packageName is required");
    this.packageName = packageName;
  }

  /**
   * Sets properties.
   *
   * @param properties the properties
   */
  private void setProperties(Set<DomainObjectProperty<?>> properties) {
    Assertion.isNotNull(properties, "properties is required");
    this.properties = properties;
  }

  /**
   * Sets constructors.
   *
   * @param constructors the constructors
   */
  private void setConstructors(Set<Constructor> constructors) {
    Assertion.isNotNull(constructors, "constructors is required");
    this.constructors = constructors;
  }

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

  /**
   * Sets multi tenant.
   *
   * @param multiTenant the multi tenant
   */
  private void setMultiTenant(Boolean multiTenant) {
    Assertion.isNotNull(multiTenant, "multiTenant is required");
    this.multiTenant = multiTenant;
  }

  /**
   * Sets exportable.
   *
   * @param exportable the exportable
   */
  private void setExportable(Boolean exportable) {
    Assertion.isNotNull(exportable, "exportable is required");
    this.exportable = exportable;
  }

  /**
   * Sets parent.
   *
   * @param parent the parent
   */
  private void setParent(DomainObject parent) {
    Assertion.isNotNull(parent, "parent is required");
    this.parent = parent;
  }

  /**
   * Sets super class.
   *
   * @param superClass the super class
   */
  private void setSuperClass(DomainObject superClass) {
    Assertion.isNotNull(superClass, "superClass is required");
    this.superClass = superClass;
  }

  /**
   * Sets persistence.
   *
   * @param persistence the persistence
   */
  private void setPersistence(Persistence persistence) {
    Assertion.isNotNull(persistence, "persistence is required");
    this.persistence = persistence;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Boolean isExportable() {
    return getExportable();
  }

  @Override
  public boolean hasSuperclass() {
    return getSuperClass() != null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DomainObject that = (DomainObject) o;
    return domainObjectType == that.domainObjectType
        && instantiationType == that.instantiationType
        && Objects.equals(objectName, that.objectName)
        && Objects.equals(packageName, that.packageName)
        && Objects.equals(properties, that.properties)
        && Objects.equals(constructors, that.constructors)
        && Objects.equals(stateMutationMethods, that.stateMutationMethods)
        && Objects.equals(stateQueryMethods, that.stateQueryMethods)
        && Objects.equals(factoryMethods, that.factoryMethods)
        && Objects.equals(multiTenant, that.multiTenant)
        && Objects.equals(parent, that.parent)
        && Objects.equals(superClass, that.superClass)
        && Objects.equals(persistence, that.persistence);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        domainObjectType,
        instantiationType,
        objectName,
        packageName,
        properties,
        constructors,
        stateMutationMethods,
        stateQueryMethods,
        factoryMethods,
        multiTenant,
        parent,
        superClass,
        persistence);
  }
}
