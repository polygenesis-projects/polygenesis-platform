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
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.models.apiimpl.DomainObjectConverter;
import io.polygenesis.representations.commons.FieldRepresentation;
import io.polygenesis.representations.java.AbstractClassRepresentable;
import io.polygenesis.representations.java.ConstructorRepresentation;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * The type Domain object converter class representable.
 *
 * @author Christos Tsakostas
 */
public class DomainObjectConverterClassRepresentable
    extends AbstractClassRepresentable<DomainObjectConverter> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final DomainObjectConverterMethodRepresentable domainObjectConverterMethodRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root converter class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param domainObjectConverterMethodRepresentable the aggregate root converter method
   *     representable
   */
  public DomainObjectConverterClassRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      DomainObjectConverterMethodRepresentable domainObjectConverterMethodRepresentable) {
    super(fromDataTypeToJavaConverter);
    this.domainObjectConverterMethodRepresentable = domainObjectConverterMethodRepresentable;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(
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
        .forEach(
            method -> {
              methodRepresentations.add(domainObjectConverterMethodRepresentable.create(method));
            });

    return methodRepresentations;
  }

  @Override
  public String packageName(DomainObjectConverter source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(DomainObjectConverter source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.ddd4j.check.assertion.Assertion");
    imports.add("org.springframework.stereotype.Component");

    Set<DataGroup> candidates = new LinkedHashSet<>();

    candidates.addAll(
        source
            .getMethods()
            .stream()
            .map(domainObjectConverterMethod -> domainObjectConverterMethod.getFunction())
            .flatMap(function -> function.getArguments().stream())
            .map(argument -> argument.getData())
            .filter(data -> data.isDataGroup())
            .map(DataGroup.class::cast)
            .filter(dataGroup -> !dataGroup.getPackageName().equals(source.getPackageName()))
            .collect(Collectors.toSet()));

    candidates.addAll(
        source
            .getMethods()
            .stream()
            .map(domainObjectConverterMethod -> domainObjectConverterMethod.getFunction())
            .filter(function -> function.getReturnValue() != null)
            .map(function -> function.getReturnValue().getData())
            .filter(data -> data.isDataGroup())
            .map(DataGroup.class::cast)
            .filter(dataGroup -> !dataGroup.getPackageName().equals(source.getPackageName()))
            .collect(Collectors.toSet()));

    candidates.forEach(
        candidate -> {
          imports.add(
              String.format(
                  "%s.%s",
                  candidate.getPackageName().getText(),
                  TextConverter.toUpperCamel(candidate.getObjectName().getText())));
        });

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
    return MODIFIER_PUBLIC;
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
