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

package io.polygenesis.generators.java.transformers.domain.aggregateroot;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.shared.transformer.FromDataTypeToJavaConverter;
import io.polygenesis.generators.java.transformers.domain.DomainObjectLegacyClassTransformer;
import io.polygenesis.generators.java.transformers.domain.StateMutationLegacyMethodTransformer;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Abstract aggregate root class representable.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootLegacyClassTransformer
    extends DomainObjectLegacyClassTransformer<AggregateRoot> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final StateMutationLegacyMethodTransformer stateMutationMethodRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param stateMutationMethodRepresentable the state mutation method representable
   */
  public AggregateRootLegacyClassTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      StateMutationLegacyMethodTransformer stateMutationMethodRepresentable) {
    super(fromDataTypeToJavaConverter);
    this.stateMutationMethodRepresentable = stateMutationMethodRepresentable;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<MethodRepresentation> methodRepresentations(AggregateRoot source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.addAll(super.methodRepresentations(source, args));

    source
        .getStateMutationMethods()
        .forEach(
            stateMutationMethod ->
                methodRepresentations.add(
                    stateMutationMethodRepresentable.create(stateMutationMethod)));

    return methodRepresentations;
  }

  @Override
  public Set<String> annotations(AggregateRoot source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    if (source.getInstantiationType().equals(InstantiationType.CONCRETE)) {
      annotations.add("@Entity");
      annotations.add(
          String.format(
              "@Table(name = Constants.DEFAULT_TABLE_PREFIX + \"%s\")",
              TextConverter.toLowerUnderscore(source.getObjectName().getText())));
    } else {
      annotations.add("@MappedSuperclass");
    }

    return annotations;
  }

  @Override
  public String description(AggregateRoot source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Aggregate Root.");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(AggregateRoot source, Object... args) {
    if (source.getInstantiationType().equals(InstantiationType.CONCRETE)) {
      return MODIFIER_PUBLIC;
    } else {
      return MODIFIER_PUBLIC + " " + MODIFIER_ABSTRACT;
    }
  }

  @Override
  public String simpleObjectName(AggregateRoot source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(source.getObjectName().getText()));

    return stringBuilder.toString();
  }
}
