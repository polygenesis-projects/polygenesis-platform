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

package io.polygenesis.generators.java.apiimpl;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.models.api.Method;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.representations.java.AbstractClassRepresentable;
import io.polygenesis.representations.java.ConstructorRepresentation;
import io.polygenesis.representations.java.FieldRepresentation;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodRepresentation;
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
  private final ServiceImplementationMethodRepresentable serviceImplementationMethodRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service implementation class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param serviceImplementationMethodRepresentable the api impl method projection converter
   */
  public ServiceImplementationClassRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      ServiceImplementationMethodRepresentable serviceImplementationMethodRepresentable) {
    super(fromDataTypeToJavaConverter);
    this.serviceImplementationMethodRepresentable = serviceImplementationMethodRepresentable;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(
      ServiceImplementation source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    fieldRepresentations.add(
        new FieldRepresentation(
            TextConverter.toUpperCamel(source.getService().getThingName().getText())
                + "Persistence",
            TextConverter.toLowerCamel(source.getService().getThingName().getText())
                + "Persistence"));

    source
        .getDependencies()
        .forEach(
            dependency ->
                fieldRepresentations.add(
                    new FieldRepresentation(
                        TextConverter.toUpperCamel(
                            dependency.getDataType().getDataTypeName().getText()),
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
        .getService()
        .getMethods()
        .forEach(
            method ->
                methodRepresentations.add(
                    serviceImplementationMethodRepresentable.create(
                        method, source.getAggregateRoot(), source.getAggregateRootConverter())));

    return methodRepresentations;
  }

  @Override
  public String packageName(ServiceImplementation source, Object... args) {
    return source.getService().getPackageName().getText();
  }

  @Override
  public Set<String> imports(ServiceImplementation source, Object... args) {
    // TODO: inherit imports from Service Interface
    // Set<String> imports = super.projectImports(source.getService());
    Set<String> imports = new LinkedHashSet<>();

    imports.addAll(detectImportsForAggregateRoot(source.getAggregateRoot()));
    imports.addAll(detectImportsForMethods(source.getService()));

    imports.add("com.oregor.ddd4j.check.assertion.Assertion");
    imports.add("org.springframework.stereotype.Service");
    imports.add("org.springframework.transaction.annotation.Transactional");

    Optional<Method> optionalMethodFetchPagedCollection =
        source
            .getService()
            .getMethods()
            .stream()
            .filter(method -> method.getFunction().getGoal().isFetchPagedCollection())
            .findFirst();

    if (optionalMethodFetchPagedCollection.isPresent()) {
      imports.add("java.util.stream.Collectors");
      imports.add("java.util.stream.StreamSupport");
      imports.add("com.oregor.ddd4j.core.Paginated");
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
    return "No description yet.";
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
   * Detect imports for aggregate root set.
   *
   * @param aggregateRoot the aggregate root
   * @return the set
   */
  protected Set<String> detectImportsForAggregateRoot(AggregateRoot aggregateRoot) {
    Set<String> imports = new LinkedHashSet<>();

    // TODO: refactor the following implementation
    aggregateRoot
        .getProperties()
        .forEach(
            property -> {
              if (property.getPropertyType().equals(PropertyType.VALUE_OBJECT)) {
                IoModelGroup modelGroup = property.getIoModelGroupAsOptional().get();
                Optional<PackageName> optionalPackageName =
                    modelGroup.getClassDataType().getOptionalPackageName();
                if (optionalPackageName.isPresent()
                    && !optionalPackageName.get().equals(aggregateRoot.getPackageName())) {
                  imports.add(
                      optionalPackageName.get().getText()
                          + "."
                          + TextConverter.toUpperCamel(
                              modelGroup.getClassDataType().getDataTypeName().getText()));
                }
              }
            });

    return imports;
  }

  /**
   * Detect imports for methods set.
   *
   * @param service the service
   * @return the set
   */
  protected Set<String> detectImportsForMethods(Service service) {
    Set<String> imports = new LinkedHashSet<>();

    service
        .getMethods()
        .forEach(
            method -> {
              if (method.getFunction().getGoal().isFetchOne()
                  || method.getFunction().getGoal().isModify()) {
                imports.add("java.util.UUID");
              }
            });

    return imports;
  }
}
