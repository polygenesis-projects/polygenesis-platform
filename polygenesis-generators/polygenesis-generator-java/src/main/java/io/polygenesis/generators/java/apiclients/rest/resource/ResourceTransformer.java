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

package io.polygenesis.generators.java.apiclients.rest.resource;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.shared.transformer.MethodTransformer;
import io.polygenesis.models.rest.Endpoint;
import io.polygenesis.models.rest.HttpMethod;
import io.polygenesis.models.rest.Resource;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ResourceTransformer extends AbstractClassTransformer<Resource, Endpoint> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Resource transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public ResourceTransformer(
      DataTypeTransformer dataTypeTransformer, MethodTransformer<Endpoint> methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(Resource source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> staticFieldRepresentations(Resource source, Object... args) {
    return super.staticFieldRepresentations(source, args);
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(Resource source, Object... args) {
    return super.stateFieldRepresentations(source, args);
  }

  @Override
  public Set<FieldRepresentation> dependencyFieldRepresentations(Resource source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    source
        .getServices()
        .forEach(
            service ->
                fieldRepresentations.add(
                    FieldRepresentation.withModifiers(
                        TextConverter.toUpperCamel(service.getServiceName().getText()),
                        TextConverter.toLowerCamel(service.getServiceName().getText()),
                        dataTypeTransformer.getModifierPrivate())));

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      Resource source, Object... args) {
    return new LinkedHashSet<>(
        Arrays.asList(
            createConstructorWithDirectAssignmentFromFieldRepresentations(
                source.getObjectName().getText(), dependencyFieldRepresentations(source))));
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Resource source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    source
        .getEndpoints()
        .forEach(endpoint -> methodRepresentations.add(methodTransformer.create(endpoint, args)));

    return methodRepresentations;
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
              // imports.addAll(methodTransformer.imports(endpoint));

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
    return super.modifiers(source, args);
  }

  @Override
  public String simpleObjectName(Resource source, Object... args) {
    return super.simpleObjectName(source, args);
  }

  @Override
  public String fullObjectName(Resource source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
    stringBuilder.append(" extends AbstractRestController");

    return stringBuilder.toString();
  }
}
