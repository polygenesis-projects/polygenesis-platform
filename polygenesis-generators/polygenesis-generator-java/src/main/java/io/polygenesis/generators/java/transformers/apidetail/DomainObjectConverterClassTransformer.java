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

package io.polygenesis.generators.java.transformers.apidetail;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataGroup;
import io.polygenesis.abstraction.thing.Argument;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.apiimpl.DomainEntityConverter;
import io.polygenesis.models.apiimpl.DomainEntityConverterMethod;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformer.code.AbstractClassTransformer;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * The type Domain object converter class representable.
 *
 * @author Christos Tsakostas
 */
public class DomainObjectConverterClassTransformer
    extends AbstractClassTransformer<DomainEntityConverter> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final DomainObjectConverterMethodTransformer domainObjectConverterMethodTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root converter class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param domainObjectConverterMethodTransformer the aggregate root converter method representable
   */
  public DomainObjectConverterClassTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      DomainObjectConverterMethodTransformer domainObjectConverterMethodTransformer) {
    super(fromDataTypeToJavaConverter);
    this.domainObjectConverterMethodTransformer = domainObjectConverterMethodTransformer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(
      DomainEntityConverter source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      DomainEntityConverter source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      DomainEntityConverter source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    source
        .getMethods()
        .forEach(
            method ->
                methodRepresentations.add(domainObjectConverterMethodTransformer.create(method)));

    return methodRepresentations;
  }

  @Override
  public String packageName(DomainEntityConverter source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(DomainEntityConverter source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.commons.assertion.Assertion");
    imports.add("org.springframework.stereotype.Component");

    Set<DataGroup> candidates = new LinkedHashSet<>();

    candidates.addAll(
        source
            .getMethods()
            .stream()
            .map(DomainEntityConverterMethod::getFunction)
            .flatMap(function -> function.getArguments().stream())
            .map(Argument::getData)
            .filter(Data::isDataGroup)
            .map(DataGroup.class::cast)
            .filter(dataGroup -> !dataGroup.getPackageName().equals(source.getPackageName()))
            .collect(Collectors.toSet()));

    candidates.addAll(
        source
            .getMethods()
            .stream()
            .map(DomainEntityConverterMethod::getFunction)
            .filter(function -> function.getReturnValue() != null)
            .map(function -> function.getReturnValue().getData())
            .filter(Data::isDataGroup)
            .map(DataGroup.class::cast)
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
  public Set<String> annotations(DomainEntityConverter source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Component");

    return annotations;
  }

  @Override
  public String description(DomainEntityConverter source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(DomainEntityConverter source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(DomainEntityConverter source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(DomainEntityConverter source, Object... args) {
    return simpleObjectName(source);
  }
}
