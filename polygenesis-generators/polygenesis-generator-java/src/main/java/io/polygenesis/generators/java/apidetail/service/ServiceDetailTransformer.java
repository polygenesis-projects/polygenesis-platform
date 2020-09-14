/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.generators.java.apidetail.service;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.apiimpl.ServiceMethodImplementation;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class ServiceDetailTransformer
    extends AbstractClassTransformer<ServiceImplementation, ServiceMethodImplementation> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service detail transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public ServiceDetailTransformer(
      DataTypeTransformer dataTypeTransformer, ServiceDetailMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(ServiceImplementation source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));
    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(
      ServiceImplementation source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    source
        .getDependencies()
        .forEach(
            dependency ->
                fieldRepresentations.add(
                    FieldRepresentation.withModifiers(
                        TextConverter.toUpperCamel(dependency.getObjectName().getText()),
                        TextConverter.toLowerCamel(dependency.getVariableName().getText()),
                        dataTypeTransformer.getModifierPrivate())));

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      ServiceImplementation source, Object... args) {
    return new LinkedHashSet<>(
        Arrays.asList(
            createConstructorWithDirectAssignmentFromFieldRepresentations(
                simpleObjectName(source), stateFieldRepresentations(source))));
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      ServiceImplementation source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    source
        .getServiceMethodImplementations()
        .forEach(method -> methodRepresentations.add(methodTransformer.create(method, args)));

    return methodRepresentations;
  }

  @Override
  public String packageName(ServiceImplementation source, Object... args) {
    return source.getService().getPackageName().getText();
  }

  @Override
  public Set<String> imports(ServiceImplementation source, Object... args) {
    // TODO: inherit imports from Service Interface ?
    Set<String> imports = new TreeSet<>();

    if (source.getDomainObject() != null) {
      imports.addAll(detectImportsForDomainObject(source.getDomainObject()));
    }

    if (source.getParentAggregateRoot() != null) {
      imports.addAll(detectImportsForDomainObject(source.getParentAggregateRoot()));
    }

    imports.addAll(detectImportsForMethods(source));

    imports.add("com.oregor.trinity4j.domain.DomainAssertion");
    imports.add("org.springframework.stereotype.Service");
    imports.add("org.springframework.transaction.annotation.Transactional");

    Optional<ServiceMethod> optionalMethodFetchPagedCollection =
        source.getService().getServiceMethods().stream()
            .filter(method -> method.getFunction().getPurpose().isFetchPagedCollection())
            .findFirst();

    if (optionalMethodFetchPagedCollection.isPresent()) {
      imports.add("java.util.stream.Collectors");
      imports.add("java.util.stream.StreamSupport");
      imports.add("com.oregor.trinity4j.domain.Paginated");
    }

    if (source.getDomainObject().getMultiTenant()) {
      imports.add("com.oregor.trinity4j.domain.TenantId");
    }

    // TODO: if has method ensure existence
    imports.add("java.util.Optional");

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
    return dataTypeTransformer.getModifierPublic();
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
   * Detect imports for domain object set.
   *
   * @param domainObject the domain object
   * @return the set
   */
  protected Set<String> detectImportsForDomainObject(DomainObject domainObject) {
    Set<String> imports = new TreeSet<>();

    // TODO: refactor the following implementation
    domainObject
        .getProperties()
        .forEach(
            property -> {
              if (property.getPropertyType().equals(PropertyType.VALUE_OBJECT)) {
                DataObject modelGroup = property.getData().getAsDataObject();

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
    Set<String> imports = new TreeSet<>();

    serviceImplementation
        .getServiceMethodImplementations()
        .forEach(
            method -> {
              imports.addAll(methodTransformer.imports(method));

              if (method.getServiceMethod().getFunction().getPurpose().isFetchOne()
                  || method.getServiceMethod().getFunction().getPurpose().isModify()
                  || method.getServiceMethod().getFunction().getPurpose().isCreate()) {
                imports.add("java.util.UUID");
              }
            });

    return imports;
  }
}
