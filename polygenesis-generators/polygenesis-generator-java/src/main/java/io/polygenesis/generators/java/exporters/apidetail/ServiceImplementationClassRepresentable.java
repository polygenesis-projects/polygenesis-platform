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

package io.polygenesis.generators.java.exporters.apidetail;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.generators.commons.representations.FieldRepresentation;
import io.polygenesis.generators.java.skeletons.AbstractClassRepresentable;
import io.polygenesis.generators.java.skeletons.ConstructorRepresentation;
import io.polygenesis.generators.java.skeletons.FromDataTypeToJavaConverter;
import io.polygenesis.generators.java.skeletons.MethodRepresentation;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.domain.BaseDomainEntity;
import io.polygenesis.models.domain.PropertyType;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The type Api impl service projection maker.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementationClassRepresentable
    extends AbstractClassRepresentable<ServiceImplementation> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final ServiceMethodImplementationRepresentable serviceMethodImplementationRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service implementation class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param serviceMethodImplementationRepresentable the api impl method projection converter
   */
  public ServiceImplementationClassRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      ServiceMethodImplementationRepresentable serviceMethodImplementationRepresentable) {
    super(fromDataTypeToJavaConverter);
    this.serviceMethodImplementationRepresentable = serviceMethodImplementationRepresentable;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(
      ServiceImplementation source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    source
        .getDependencies()
        .forEach(
            dependency ->
                fieldRepresentations.add(
                    new FieldRepresentation(
                        TextConverter.toUpperCamel(dependency.getObjectName().getText()),
                        TextConverter.toLowerCamel(dependency.getVariableName().getText()))));

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      ServiceImplementation source, Object... args) {
    return new LinkedHashSet<>(
        Arrays.asList(
            createConstructorWithDirectAssignmentFromFieldRepresentations(
                simpleObjectName(source), fieldRepresentations(source))));
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      ServiceImplementation source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    source
        .getServiceMethodImplementations()
        .forEach(
            method ->
                methodRepresentations.add(
                    serviceMethodImplementationRepresentable.create(method, source)));

    return methodRepresentations;
  }

  @Override
  public String packageName(ServiceImplementation source, Object... args) {
    return source.getService().getPackageName().getText();
  }

  @Override
  public Set<String> imports(ServiceImplementation source, Object... args) {
    // TODO: inherit imports from Service Interface ?
    Set<String> imports = new LinkedHashSet<>();

    source
        .getOptionalDomainEntity()
        .ifPresent(domainEntity -> imports.addAll(detectImportsForDomainEntity(domainEntity)));

    source
        .getOptionalParentAggregateRoot()
        .ifPresent(domainEntity -> imports.addAll(detectImportsForDomainEntity(domainEntity)));

    imports.addAll(detectImportsForMethods(source));

    imports.add("com.oregor.trinity4j.commons.assertion.Assertion");
    imports.add("org.springframework.stereotype.Service");
    imports.add("org.springframework.transaction.annotation.Transactional");

    Optional<ServiceMethod> optionalMethodFetchPagedCollection =
        source
            .getService()
            .getServiceMethods()
            .stream()
            .filter(method -> method.getFunction().getGoal().isFetchPagedCollection())
            .findFirst();

    if (optionalMethodFetchPagedCollection.isPresent()) {
      imports.add("java.util.stream.Collectors");
      imports.add("java.util.stream.StreamSupport");
      imports.add("com.oregor.trinity4j.domain.Paginated");
    }

    return imports;
  }

  @Override
  public Set<String> annotations(ServiceImplementation source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Service");
    annotations.add("@Transactional");

    return annotations;
  }

  @Override
  public String description(ServiceImplementation source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("Implements the ");
    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(source.getService().getServiceName().getText()));
    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(ServiceImplementation source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(ServiceImplementation source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamel(source.getService().getServiceName().getText()));
    stringBuilder.append("Impl");

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(ServiceImplementation source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamel(source.getService().getServiceName().getText()));
    stringBuilder.append("Impl");
    stringBuilder.append(" implements ");
    stringBuilder.append(
        TextConverter.toUpperCamel(source.getService().getServiceName().getText()));

    return stringBuilder.toString();
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Detect imports for domain entity.
   *
   * @param domainObject the aggregate root
   * @return the set
   */
  protected Set<String> detectImportsForDomainEntity(BaseDomainEntity domainObject) {
    Set<String> imports = new LinkedHashSet<>();

    // TODO: refactor the following implementation
    domainObject
        .getProperties()
        .forEach(
            property -> {
              if (property.getPropertyType().equals(PropertyType.VALUE_OBJECT)) {
                DataGroup modelGroup = property.getData().getAsDataGroup();

                if (modelGroup.getPackageName().equals(domainObject.getPackageName())) {
                  imports.add(
                      modelGroup.getPackageName().getText()
                          + "."
                          + TextConverter.toUpperCamel(modelGroup.getDataType()));
                }
              }
            });

    return imports;
  }

  /**
   * Detect imports for methods set.
   *
   * @param serviceImplementation the service implementation
   * @return the set
   */
  protected Set<String> detectImportsForMethods(ServiceImplementation serviceImplementation) {
    Set<String> imports = new LinkedHashSet<>();

    serviceImplementation
        .getServiceMethodImplementations()
        .forEach(
            method -> {
              imports.addAll(serviceMethodImplementationRepresentable.imports(method));

              if (method.getServiceMethod().getFunction().getGoal().isFetchOne()
                  || method.getServiceMethod().getFunction().getGoal().isModify()) {
                imports.add("java.util.UUID");
              }
            });

    return imports;
  }
}
