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

package io.polygenesis.generators.java.rest;

import static java.util.stream.Collectors.toCollection;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.models.rest.HttpMethod;
import io.polygenesis.models.rest.Resource;
import io.polygenesis.representations.java.AbstractClassRepresentable;
import io.polygenesis.representations.java.ConstructorRepresentation;
import io.polygenesis.representations.java.FieldRepresentation;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Resource class representable.
 *
 * @author Christos Tsakostas
 */
public class ResourceClassRepresentable extends AbstractClassRepresentable<Resource> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final EndpointMethodRepresentable endpointMethodRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public ResourceClassRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      EndpointMethodRepresentable endpointMethodRepresentable) {
    super(fromDataTypeToJavaConverter);
    this.endpointMethodRepresentable = endpointMethodRepresentable;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(Resource source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    source
        .getServices()
        .forEach(
            service ->
                fieldRepresentations.add(
                    new FieldRepresentation(
                        TextConverter.toUpperCamel(service.getServiceName().getText()),
                        TextConverter.toLowerCamel(service.getServiceName().getText()))));

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      Resource source, Object... args) {
    return new LinkedHashSet<>(
        Arrays.asList(
            createConstructorWithDirectAssignmentFromFieldRepresentations(
                source.getName().getText(), fieldRepresentations(source))));
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Resource source, Object... args) {
    return source
        .getEndpoints()
        .stream()
        .map(endpoint -> endpointMethodRepresentable.create(endpoint))
        .collect(toCollection(LinkedHashSet::new));
  }

  @Override
  public String packageName(Resource source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(Resource source, Object... args) {
    PackageName rootPackageName = (PackageName) args[0];
    Set<String> imports = new TreeSet<>();

    imports.add("org.springframework.web.bind.annotation.RestController");
    imports.add("org.springframework.web.bind.annotation.RequestMapping");
    imports.add("com.oregor.ddd4j.rest.AbstractRestController");
    imports.add(String.format("%s.RestConstants", rootPackageName.getText()));

    source
        .getEndpoints()
        .forEach(
            endpoint -> {
              if (endpoint.getHttpMethod().equals(HttpMethod.POST)) {
                imports.add("org.springframework.web.bind.annotation.PostMapping");
                imports.add("org.springframework.web.bind.annotation.RequestBody");
              } else if (endpoint.getHttpMethod().equals(HttpMethod.PUT)) {
                imports.add("org.springframework.web.bind.annotation.PutMapping");
                imports.add("org.springframework.web.bind.annotation.RequestBody");
              } else if (endpoint.getHttpMethod().equals(HttpMethod.GET)) {
                imports.add("org.springframework.web.bind.annotation.GetMapping");
              } else if (endpoint.getHttpMethod().equals(HttpMethod.DELETE)) {
                imports.add("org.springframework.web.bind.annotation.DeleteMapping");
              }
            });

    return imports;
  }

  @Override
  public Set<String> annotations(Resource source, Object... args) {
    return new LinkedHashSet<>(
        Arrays.asList("@RestController", "@RequestMapping(RestConstants.CONTEXT_REQUEST_MAPPING)"));
  }

  @Override
  public String description(Resource source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The Spring REST Controller for ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getName().getText()));

    stringBuilder.append(" Services.");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(Resource source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(Resource source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(source.getName().getText()));
    stringBuilder.append("RestService");

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(Resource source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getName().getText()));
    stringBuilder.append("RestService");
    stringBuilder.append(" extends AbstractRestController");

    return stringBuilder.toString();
  }
}
