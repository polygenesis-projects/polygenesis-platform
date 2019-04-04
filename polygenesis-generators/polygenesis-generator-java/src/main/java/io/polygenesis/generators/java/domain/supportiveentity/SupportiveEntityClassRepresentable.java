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

package io.polygenesis.generators.java.domain.supportiveentity;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.domain.ConstructorRepresentable;
import io.polygenesis.generators.java.domain.DomainObjectClassRepresentable;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.models.domain.SupportiveEntity;
import io.polygenesis.representations.java.ConstructorRepresentation;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Supportive entity class representable.
 *
 * @author Christos Tsakostas
 */
public class SupportiveEntityClassRepresentable
    extends DomainObjectClassRepresentable<SupportiveEntity> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private ConstructorRepresentable constructorRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Supportive entity class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param constructorRepresentable the constructor representable
   */
  public SupportiveEntityClassRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      ConstructorRepresentable constructorRepresentable) {
    super(fromDataTypeToJavaConverter);
    this.constructorRepresentable = constructorRepresentable;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      SupportiveEntity source, Object... args) {
    return super.constructorRepresentations(source, args);
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(SupportiveEntity source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.addAll(super.methodRepresentations(source, args));

    source
        .getConstructors()
        .forEach(
            constructor -> methodRepresentations.add(constructorRepresentable.create(constructor)));

    return methodRepresentations;
  }

  @Override
  public Set<String> annotations(SupportiveEntity source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();
    return annotations;
  }

  @Override
  public String description(SupportiveEntity source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Supportive Entity.");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(SupportiveEntity source, Object... args) {
    if (source.getInstantiationType().equals(InstantiationType.CONCRETE)) {
      return MODIFIER_PUBLIC;
    } else {
      return MODIFIER_PUBLIC + " " + MODIFIER_ABSTRACT;
    }
  }

  @Override
  public String simpleObjectName(SupportiveEntity source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(SupportiveEntity source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));

    stringBuilder.append(" extends SupportiveEntity<String>");

    return stringBuilder.toString();
  }
}
