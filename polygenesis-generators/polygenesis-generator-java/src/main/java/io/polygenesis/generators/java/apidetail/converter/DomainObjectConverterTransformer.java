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

package io.polygenesis.generators.java.apidetail.converter;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.models.apiimpl.DomainObjectConverter;
import io.polygenesis.models.apiimpl.DomainObjectConverterMethod;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DomainObjectConverterTransformer
    extends AbstractClassTransformer<DomainObjectConverter, DomainObjectConverterMethod> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain object converter transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public DomainObjectConverterTransformer(
      DataTypeTransformer dataTypeTransformer,
      DomainObjectConverterMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(DomainObjectConverter source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(
      DomainObjectConverter source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      DomainObjectConverter source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      DomainObjectConverter source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    source
        .getMethods()
        .forEach(method -> methodRepresentations.add(methodTransformer.create(method)));

    return methodRepresentations;
  }

  @Override
  public String packageName(DomainObjectConverter source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(DomainObjectConverter source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.commons.assertion.Assertion");
    imports.add("org.springframework.stereotype.Component");

    Set<DataObject> candidates = new LinkedHashSet<>();

    candidates.addAll(
        source.getMethods().stream()
            .map(DomainObjectConverterMethod::getFunction)
            .flatMap(function -> function.getArguments().getData().stream())
            .filter(Data::isDataGroup)
            .map(DataObject.class::cast)
            .filter(dataGroup -> !dataGroup.getPackageName().equals(source.getPackageName()))
            .collect(Collectors.toSet()));

    candidates.addAll(
        source.getMethods().stream()
            .map(DomainObjectConverterMethod::getFunction)
            .filter(function -> function.getReturnValue() != null)
            .map(function -> function.getReturnValue())
            .filter(Data::isDataGroup)
            .map(DataObject.class::cast)
            .filter(dataGroup -> !dataGroup.getPackageName().equals(source.getPackageName()))
            .collect(Collectors.toSet()));

    candidates.forEach(
        candidate ->
            imports.add(
                String.format(
                    "%s.%s",
                    candidate.getPackageName().getText(),
                    TextConverter.toUpperCamel(candidate.getObjectName().getText()))));

    return imports;
  }

  @Override
  public Set<String> annotations(DomainObjectConverter source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Component");

    return annotations;
  }

  @Override
  public String description(DomainObjectConverter source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(DomainObjectConverter source, Object... args) {
    return dataTypeTransformer.getModifierPublic();
  }

  @Override
  public String simpleObjectName(DomainObjectConverter source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(DomainObjectConverter source, Object... args) {
    return simpleObjectName(source);
  }
}
