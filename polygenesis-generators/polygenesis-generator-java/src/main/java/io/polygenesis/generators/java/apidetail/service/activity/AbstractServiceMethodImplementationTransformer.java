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

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.apiimpl.DomainEntityConverter;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.apiimpl.ServiceImplementationMetamodelRepository;
import io.polygenesis.models.apiimpl.ServiceMethodImplementation;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.DomainMetamodelRepository;
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

  /**
   * Gets parameter representations.
   *
   * @param source the source
   * @return the parameter representations
   */
  protected Set<ParameterRepresentation> getParameterRepresentations(
      ServiceMethodImplementation source) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();
    source
        .getServiceMethod()
        .getFunction()
        .getArguments()
        .forEach(
            argument -> {
              parameterRepresentations.add(
                  new ParameterRepresentation(
                      argument.getData().getDataType(),
                      TextConverter.toLowerCamel(argument.getData().getVariableName().getText())));
            });

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
      return optionalAggregateRoot
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
                          optionalAggregateRoot.get().getObjectName().getText())))
          .getProperties();
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
                          "Cannot get state mutation method '%s' for '%s'",
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
    DomainMetamodelRepository domainMetamodelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, DomainMetamodelRepository.class);

    return domainMetamodelRepository
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
}
