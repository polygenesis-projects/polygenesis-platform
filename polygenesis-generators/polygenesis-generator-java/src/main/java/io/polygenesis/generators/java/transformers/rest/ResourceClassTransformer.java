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

package io.polygenesis.generators.java.transformers.rest;

import static java.util.stream.Collectors.toCollection;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.rest.HttpMethod;
import io.polygenesis.models.rest.Resource;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformer.code.AbstractClassTransformer;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Resource class representable.
 *
 * @author Christos Tsakostas
 */
public class ResourceClassTransformer extends AbstractClassTransformer<Resource> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final EndpointMethodTransformer endpointMethodRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public ResourceClassTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      EndpointMethodTransformer endpointMethodRepresentable) {
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
                source.getObjectName().getText(), fieldRepresentations(source))));
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Resource source, Object... args) {
    return source
        .getEndpoints()
        .stream()
        .map(endpointMethodRepresentable::create)
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

    imports.add(String.format("%s.RestConstants", rootPackageName.getText()));
    imports.add("org.springframework.web.bind.annotation.RestController");
    imports.add("org.springframework.web.bind.annotation.RequestMapping");
    imports.add("com.oregor.trinity4j.api.AbstractRestController");
    imports.add("javax.servlet.http.HttpServletRequest");
    imports.add("com.oregor.trinity4j.commons.assertion.Assertion");

    source
        .getEndpoints()
        .forEach(
            endpoint -> {
              imports.addAll(endpointMethodRepresentable.imports(endpoint));

              if (endpoint.getHttpMethod().equals(HttpMethod.POST)) {
                imports.add("org.springframework.web.bind.annotation.PostMapping");
                imports.add("org.springframework.web.bind.annotation.RequestBody");
              } else if (endpoint.getHttpMethod().equals(HttpMethod.PUT)) {
                imports.add("org.springframework.web.bind.annotation.PutMapping");
                imports.add("org.springframework.web.bind.annotation.RequestBody");
                imports.add("org.springframework.web.bind.annotation.PathVariable");
              } else if (endpoint.getHttpMethod().equals(HttpMethod.GET)) {
                imports.add("org.springframework.web.bind.annotation.GetMapping");
                imports.add("org.springframework.web.bind.annotation.RequestParam");
                imports.add("org.springframework.web.bind.annotation.PathVariable");
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

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

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

    stringBuilder.append(TextConverter.toLowerCamel(source.getObjectName().getText()));
    stringBuilder.append("RestService");

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(Resource source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
    stringBuilder.append("RestService");
    stringBuilder.append(" extends AbstractRestController");

    return stringBuilder.toString();
  }
}
