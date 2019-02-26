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

import io.polygenesis.commons.converter.Converter;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.generators.java.shared.AbstractServiceProjectionMaker;
import io.polygenesis.generators.java.shared.ArgumentProjection;
import io.polygenesis.generators.java.shared.ConstructorProjection;
import io.polygenesis.generators.java.shared.FunctionProjection;
import io.polygenesis.generators.java.shared.ObjectProjection;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.domain.AggregateRoot;
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
public class ApiImplServiceProjectionConverter extends AbstractServiceProjectionMaker
    implements Converter<ServiceImplementation, ObjectProjection> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final ApiImplMethodProjectionConverter apiImplMethodProjectionConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Api impl service projection maker.
   *
   * @param apiImplMethodProjectionConverter the method projection maker
   */
  public ApiImplServiceProjectionConverter(
      ApiImplMethodProjectionConverter apiImplMethodProjectionConverter) {
    super(apiImplMethodProjectionConverter);
    this.apiImplMethodProjectionConverter = apiImplMethodProjectionConverter;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectProjection convert(ServiceImplementation serviceImplementation, Object... args) {
    AggregateRoot aggregateRoot = (AggregateRoot) args[0];

    return new ObjectProjection(
        projectPackageName(serviceImplementation.getService()),
        projectImports(serviceImplementation, aggregateRoot),
        projectDescription(serviceImplementation.getService()),
        projectObjectName(serviceImplementation),
        projectObjectNameWithOptionalExtendsImplements(serviceImplementation),
        projectVariables(serviceImplementation),
        projectConstructors(serviceImplementation),
        fillFunctionProjections(serviceImplementation, aggregateRoot));
  }

  /**
   * Project imports set.
   *
   * @param serviceImplementation the service implementation
   * @param aggregateRoot the aggregate root
   * @return the set
   */
  protected Set<String> projectImports(
      ServiceImplementation serviceImplementation, AggregateRoot aggregateRoot) {
    Set<String> imports = super.projectImports(serviceImplementation.getService());

    imports.addAll(detectImportsForAggregateRoot(aggregateRoot));
    imports.addAll(detectImportsForMethods(serviceImplementation.getService()));

    imports.add("com.oregor.ddd4j.check.assertion.Assertion");
    imports.add("org.springframework.stereotype.Service");
    imports.add("org.springframework.transaction.annotation.Transactional");

    return imports;
  }

  /**
   * Detect imports for aggregate root set.
   *
   * @param aggregateRoot the aggregate root
   * @return the set
   */
  protected Set<String> detectImportsForAggregateRoot(AggregateRoot aggregateRoot) {
    Set<String> imports = new LinkedHashSet<>();

    // TODO
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

  protected Set<String> detectImportsForMethods(Service service) {
    Set<String> imports = new LinkedHashSet<>();

    service
        .getMethods()
        .forEach(
            method -> {
              if (method.getFunction().getGoal().isFetchOne()
                  || method.getFunction().getGoal().isModify()) {
                imports.add("java.util.Optional");
                imports.add("java.util.UUID");
              }
            });

    return imports;
  }

  /**
   * Project object name string.
   *
   * @param serviceImplementation the service implementation
   * @return the string
   */
  protected String projectObjectName(ServiceImplementation serviceImplementation) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamel(serviceImplementation.getService().getServiceName().getText()));
    stringBuilder.append("Impl");

    return stringBuilder.toString();
  }

  /**
   * Project object name with optional extends implements string.
   *
   * @param serviceImplementation the service implementation
   * @return the string
   */
  protected String projectObjectNameWithOptionalExtendsImplements(
      ServiceImplementation serviceImplementation) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamel(serviceImplementation.getService().getServiceName().getText()));
    stringBuilder.append("Impl");
    stringBuilder.append(" implements ");
    stringBuilder.append(
        TextConverter.toUpperCamel(serviceImplementation.getService().getServiceName().getText()));

    return stringBuilder.toString();
  }

  /**
   * Project variables set.
   *
   * @param serviceImplementation the service implementation
   * @return the set
   */
  protected Set<ArgumentProjection> projectVariables(ServiceImplementation serviceImplementation) {
    Set<ArgumentProjection> keyValues = new LinkedHashSet<>();

    keyValues.add(
        new ArgumentProjection(
            TextConverter.toUpperCamel(serviceImplementation.getService().getThingName().getText())
                + "Persistence",
            TextConverter.toLowerCamel(serviceImplementation.getService().getThingName().getText())
                + "Persistence"));

    serviceImplementation
        .getDependencies()
        .forEach(
            dependency ->
                keyValues.add(
                    new ArgumentProjection(
                        TextConverter.toUpperCamel(
                            dependency.getDataType().getDataTypeName().getText()),
                        TextConverter.toLowerCamel(dependency.getVariableName().getText()))));

    return keyValues;
  }

  /**
   * Project constructors set.
   *
   * @param serviceImplementation the service implementation
   * @return the set
   */
  protected Set<ConstructorProjection> projectConstructors(
      ServiceImplementation serviceImplementation) {
    return new LinkedHashSet<>(
        Arrays.asList(new ConstructorProjection(projectVariables(serviceImplementation), "")));
  }

  /**
   * Fill function projections set.
   *
   * @param serviceImplementation the service implementation
   * @param aggregateRoot the aggregate root
   * @return the set
   */
  protected Set<FunctionProjection> fillFunctionProjections(
      ServiceImplementation serviceImplementation, AggregateRoot aggregateRoot) {
    Set<FunctionProjection> functionProjections = new LinkedHashSet<>();

    serviceImplementation
        .getService()
        .getMethods()
        .forEach(
            method ->
                functionProjections.add(
                    apiImplMethodProjectionConverter.convert(
                        method, aggregateRoot, serviceImplementation.getAggregateRootConverter())));

    return functionProjections;
  }
}
