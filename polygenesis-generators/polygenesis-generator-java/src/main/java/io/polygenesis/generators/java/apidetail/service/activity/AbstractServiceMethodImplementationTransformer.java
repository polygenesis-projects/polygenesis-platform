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

package io.polygenesis.generators.java.apidetail.service.activity;

import static io.polygenesis.models.domain.PropertyType.ABSTRACT_AGGREGATE_ROOT_ID;
import static io.polygenesis.models.domain.PropertyType.AGGREGATE_ROOT_ID;
import static io.polygenesis.models.domain.PropertyType.TENANT_ID;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.common.AggregateEntityData;
import io.polygenesis.generators.java.common.AggregateRootData;
import io.polygenesis.generators.java.common.ParentCallingChildDataService;
import io.polygenesis.models.apiimpl.DomainEntityConverter;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.apiimpl.ServiceImplementationMetamodelRepository;
import io.polygenesis.models.apiimpl.ServiceMethodImplementation;
import io.polygenesis.models.domain.AggregateEntity;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.AggregateRootMetamodelRepository;
import io.polygenesis.models.domain.Constructor;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The type Abstract service method implementation transformer.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractServiceMethodImplementationTransformer {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  /** The Parent calling child data service. */
  protected final ParentCallingChildDataService parentCallingChildDataService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract service method implementation transformer.
   *
   * @param parentCallingChildDataService the parent calling child data service
   */
  protected AbstractServiceMethodImplementationTransformer(
      ParentCallingChildDataService parentCallingChildDataService) {
    this.parentCallingChildDataService = parentCallingChildDataService;
  }

  // ===============================================================================================
  // AGGREGATE ROOT DATA
  // ===============================================================================================

  /**
   * Gets aggregate root data.
   *
   * @param source the source
   * @return the aggregate root data
   */
  public AggregateRootData getAggregateRootData(ServiceMethodImplementation source) {
    Thing currentThing = source.getFunction().getThing();
    Thing aggregateRootThing = getTopAggregateRootThing(currentThing);

    return new AggregateRootData(
        getAggregateRootDataTypeByThing(aggregateRootThing),
        getAggregateRootVariableByThing(aggregateRootThing),
        getAggregateRootIdDataTypeByThing(aggregateRootThing),
        getAggregateRootIdVariableByThing(aggregateRootThing),
        getRepositoryVariableByThing(aggregateRootThing),
        aggregateRootThing.getMultiTenant());
  }

  private Thing getTopAggregateRootThing(Thing thing) {
    if (thing.getOptionalParent() != null) {
      return getTopAggregateRootThing(thing.getOptionalParent());
    } else {
      return thing;
    }
  }

  private String getAggregateRootDataTypeByThing(Thing aggregateRootThing) {
    return TextConverter.toUpperCamel(aggregateRootThing.getThingName().getText());
  }

  private String getAggregateRootVariableByThing(Thing aggregateRootThing) {
    return TextConverter.toLowerCamel(aggregateRootThing.getThingName().getText());
  }

  private String getAggregateRootIdDataTypeByThing(Thing aggregateRootThing) {
    return TextConverter.toUpperCamel(
        String.format("%sId", aggregateRootThing.getThingName().getText()));
  }

  private String getAggregateRootIdVariableByThing(Thing aggregateRootThing) {
    return TextConverter.toLowerCamel(
        String.format("%sId", aggregateRootThing.getThingName().getText()));
  }

  private String getRepositoryVariableByThing(Thing aggregateRootThing) {
    return TextConverter.toLowerCamel(
        String.format("%sRepository", aggregateRootThing.getThingName().getText()));
  }

  // ===============================================================================================
  // AGGREGATE ROOT DATA
  // ===============================================================================================

  /**
   * Gets aggregate entity data.
   *
   * @param source the source
   * @return the aggregate entity data
   */
  @SuppressWarnings("CPD-START")
  public AggregateEntityData getAggregateEntityData(ServiceMethodImplementation source) {
    Thing currentThing = source.getFunction().getThing();

    return new AggregateEntityData(
        getAggregateEntityDataTypeByThing(currentThing),
        getAggregateEntityVariableByThing(currentThing),
        TextConverter.toPlural(TextConverter.toLowerCamel(currentThing.getThingName().getText())),
        getAggregateEntityIdDataTypeByThing(currentThing),
        getAggregateEntityIdVariableByThing(currentThing));
  }

  private String getAggregateEntityDataTypeByThing(Thing aggregateEntityThing) {
    return TextConverter.toUpperCamel(aggregateEntityThing.getThingName().getText());
  }

  private String getAggregateEntityVariableByThing(Thing aggregateEntityThing) {
    return TextConverter.toLowerCamel(aggregateEntityThing.getThingName().getText());
  }

  private String getAggregateEntityIdDataTypeByThing(Thing aggregateEntityThing) {
    return TextConverter.toUpperCamel(
        String.format("%sId", aggregateEntityThing.getThingName().getText()));
  }

  private String getAggregateEntityIdVariableByThing(Thing aggregateEntityThing) {
    return TextConverter.toLowerCamel(
        String.format("%sId", aggregateEntityThing.getThingName().getText()));
  }

  // ===============================================================================================
  //
  // ===============================================================================================

  /**
   * Gets parameter representations.
   *
   * @param source the source
   * @return the parameter representations
   */
  protected Set<ParameterRepresentation> getParameterRepresentations(
      ServiceMethodImplementation source) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation(
            source.getServiceMethod().getRequestDto().getDataObject().getDataType(),
            TextConverter.toLowerCamel(
                source
                    .getServiceMethod()
                    .getRequestDto()
                    .getDataObject()
                    .getVariableName()
                    .getText())));

    return parameterRepresentations;
  }

  /**
   * Gets aggregate root data type.
   *
   * @param source the source
   * @return the aggregate root data type
   */
  protected String getAggregateRootDataType(ServiceMethodImplementation source) {
    return TextConverter.toUpperCamel(source.getFunction().getThing().getThingName().getText());
  }

  /**
   * Gets aggregate root id data type.
   *
   * @param source the source
   * @return the aggregate root id data type
   */
  protected String getAggregateRootIdDataType(ServiceMethodImplementation source) {
    return TextConverter.toUpperCamel(
        String.format("%sId", source.getFunction().getThing().getThingName().getText()));
  }

  /**
   * Gets aggregate root variable.
   *
   * @param source the source
   * @return the aggregate root variable
   */
  protected String getAggregateRootVariable(ServiceMethodImplementation source) {
    return TextConverter.toLowerCamel(source.getFunction().getThing().getThingName().getText());
  }

  /**
   * Gets repository variable.
   *
   * @param source the source
   * @return the repository variable
   */
  protected String getRepositoryVariable(ServiceMethodImplementation source) {
    return TextConverter.toLowerCamel(
        String.format("%sRepository", source.getFunction().getThing().getThingName().getText()));
  }

  /**
   * Gets return value.
   *
   * @param source the source
   * @return the return value
   */
  protected String getReturnValue(ServiceMethodImplementation source) {
    return TextConverter.toUpperCamel(
        source.getServiceMethod().getResponseDto().getDataObject().getObjectName().getText());
  }

  /**
   * Gets thing identity.
   *
   * @param source the source
   * @return the thing identity
   */
  protected Data getThingIdentity(ServiceMethodImplementation source) {
    return source
        .getServiceMethod()
        .getRequestDto()
        .getThingIdentityAsOptional()
        .orElseThrow(IllegalArgumentException::new);
  }

  /**
   * Gets parent thing identity.
   *
   * @return the parent thing identity
   */
  // TODO
  protected Data getParentThingIdentity() {
    return DataPrimitive.of(PrimitiveType.STRING, new VariableName("parentThingIdentity"));
  }

  /**
   * Gets converter variable.
   *
   * @param source the source
   * @param metamodelRepositories the metamodel repositories
   * @return the converter variable
   */
  protected String getConverterVariable(
      ServiceMethodImplementation source, Set<MetamodelRepository<?>> metamodelRepositories) {
    DomainEntityConverter domainEntityConverter =
        getServiceImplementation(source, metamodelRepositories).domainObjectConverter();

    return domainEntityConverter != null ? domainEntityConverter.getVariableName().getText() : null;
  }

  /**
   * Gets properties from constructor.
   *
   * @param source the source
   * @param metamodelRepositories the metamodel repositories
   * @return the properties from constructor
   */
  protected Set<DomainObjectProperty<?>> getPropertiesFromConstructor(
      ServiceMethodImplementation source, Set<MetamodelRepository<?>> metamodelRepositories) {

    Optional<AggregateRoot> optionalAggregateRoot = getAggregateRoot(source, metamodelRepositories);

    if (optionalAggregateRoot.isPresent()) {
      Constructor constructorFound =
          optionalAggregateRoot
              .get()
              .getConstructors()
              .stream()
              .filter(constructor -> constructor.getFunction().equals(source.getFunction()))
              .findFirst()
              .orElseThrow(
                  () ->
                      new IllegalArgumentException(
                          String.format(
                              "Cannot get constructor for '%s'",
                              optionalAggregateRoot.get().getObjectName().getText())));

      return getReorderedPropertiesBySuperClass(
          constructorFound.getProperties(), constructorFound.getSuperClassProperties());
    } else {
      throw new IllegalStateException("should find domain aggregate root");
    }
  }

  /**
   * Gets properties from state mutation method.
   *
   * @param source the source
   * @param metamodelRepositories the metamodel repositories
   * @return the properties from state mutation method
   */
  protected Set<DomainObjectProperty<?>> getPropertiesFromStateMutationMethod(
      ServiceMethodImplementation source, Set<MetamodelRepository<?>> metamodelRepositories) {

    Optional<AggregateRoot> optionalAggregateRoot = getAggregateRoot(source, metamodelRepositories);

    if (optionalAggregateRoot.isPresent()) {
      return optionalAggregateRoot
          .get()
          .getStateMutationMethods()
          .stream()
          .filter(
              stateMutationMethod ->
                  stateMutationMethod
                      .getFunction()
                      .getName()
                      .equals(source.getFunction().getName()))
          .findFirst()
          .orElseThrow(
              () ->
                  new IllegalArgumentException(
                      String.format(
                          "Cannot get state mutation method '%s' for '%s'. "
                              + "Check the state mutation deducer.",
                          source.getFunction().getName().getText(),
                          optionalAggregateRoot.get().getObjectName().getText())))
          .getProperties();
    } else {
      throw new IllegalStateException("should find domain aggregate root");
    }
  }

  /**
   * Gets aggregate root.
   *
   * @param source the source
   * @param metamodelRepositories the metamodel repositories
   * @return the aggregate root
   */
  protected Optional<AggregateRoot> getAggregateRoot(
      ServiceMethodImplementation source, Set<MetamodelRepository<?>> metamodelRepositories) {
    AggregateRootMetamodelRepository aggregateRootMetamodelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, AggregateRootMetamodelRepository.class);

    return aggregateRootMetamodelRepository
        .getItems()
        .stream()
        .filter(
            aggregateRoot ->
                aggregateRoot
                    .getObjectName()
                    .getText()
                    .equals(source.getFunction().getThing().getThingName().getText()))
        .findFirst();
  }

  /**
   * Gets service implementation.
   *
   * @param source the source
   * @param metamodelRepositories the metamodel repositories
   * @return the service implementation
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  protected ServiceImplementation getServiceImplementation(
      ServiceMethodImplementation source, Set<MetamodelRepository<?>> metamodelRepositories) {

    ServiceImplementationMetamodelRepository serviceImplementationMetamodelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, ServiceImplementationMetamodelRepository.class);

    return serviceImplementationMetamodelRepository
        .getItems()
        .stream()
        .filter(
            serviceImplementation ->
                serviceImplementation.getService().equals(source.getServiceMethod().getService()))
        .findFirst()
        .orElseThrow(IllegalStateException::new);
  }

  /**
   * Gets reordered properties by super class.
   *
   * @param thisClassProperties the this class properties
   * @param superClassProperties the super class properties
   * @return the reordered properties by super class
   */
  protected Set<DomainObjectProperty<?>> getReorderedPropertiesBySuperClass(
      Set<DomainObjectProperty<?>> thisClassProperties,
      Set<DomainObjectProperty<?>> superClassProperties) {
    Set<DomainObjectProperty<?>> domainObjectProperties = new LinkedHashSet<>();

    // 1. Start by adding IDs
    thisClassProperties
        .stream()
        .filter(
            property ->
                property.getPropertyType().equals(ABSTRACT_AGGREGATE_ROOT_ID)
                    || property.getPropertyType().equals(AGGREGATE_ROOT_ID)
                    || property.getPropertyType().equals(TENANT_ID))
        .forEach(property -> domainObjectProperties.add(property));

    // 2. Continue with superClass properties
    if (superClassProperties != null) {
      superClassProperties
          .stream()
          .filter(
              property ->
                  !property.getPropertyType().equals(ABSTRACT_AGGREGATE_ROOT_ID)
                      && !property.getPropertyType().equals(AGGREGATE_ROOT_ID)
                      && !property.getPropertyType().equals(TENANT_ID))
          .forEach(property -> domainObjectProperties.add(property));
    }

    // 3. Finish with this class properties
    thisClassProperties
        .stream()
        .filter(
            property ->
                !property.getPropertyType().equals(ABSTRACT_AGGREGATE_ROOT_ID)
                    && !property.getPropertyType().equals(AGGREGATE_ROOT_ID)
                    && !property.getPropertyType().equals(TENANT_ID))
        .forEach(property -> domainObjectProperties.add(property));

    return domainObjectProperties;
  }

  /**
   * Gets properties.
   *
   * @param source the source
   * @param aggregateEntity the aggregate entity
   * @return the properties
   */
  protected Set<DomainObjectProperty<?>> getProperties(
      ServiceMethodImplementation source, AggregateEntity aggregateEntity) {

    if (source.getFunction().getPurpose().isCreate()) {
      return aggregateEntity
          .getConstructors()
          .stream()
          .filter(mutationMethod -> mutationMethod.getFunction().equals(source.getFunction()))
          .map(stateMutationMethod -> stateMutationMethod.getProperties())
          .findFirst()
          .orElseThrow();
    } else if (source.getFunction().getPurpose().isModify()) {
      return aggregateEntity
          .getStateMutationMethods()
          .stream()
          .filter(mutationMethod -> mutationMethod.getFunction().equals(source.getFunction()))
          .map(stateMutationMethod -> stateMutationMethod.getProperties())
          .findFirst()
          .orElseThrow();
    } else {
      return new LinkedHashSet<>();
    }
  }
}
