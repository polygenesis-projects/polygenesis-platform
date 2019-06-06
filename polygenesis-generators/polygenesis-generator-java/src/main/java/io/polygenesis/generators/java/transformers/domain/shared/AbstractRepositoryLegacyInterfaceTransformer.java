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

package io.polygenesis.generators.java.transformers.domain.shared;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.shared.transformer.AbstractLegacyInterfaceTransformer;
import io.polygenesis.generators.java.shared.transformer.FromDataTypeToJavaConverter;
import io.polygenesis.generators.java.shared.transformer.FunctionToLegacyMethodRepresentationTransformer;
import io.polygenesis.models.domain.Persistence;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Abstract repository interface representable.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractRepositoryLegacyInterfaceTransformer
    extends AbstractLegacyInterfaceTransformer<Persistence> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract repository interface representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param functionToMethodRepresentationTransformer the function to method representation
   *     converter
   */
  public AbstractRepositoryLegacyInterfaceTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      FunctionToLegacyMethodRepresentationTransformer functionToMethodRepresentationTransformer) {
    super(fromDataTypeToJavaConverter, functionToMethodRepresentationTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<MethodRepresentation> methodRepresentations(Persistence source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String packageName(Persistence source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> annotations(Persistence source, Object... args) {
    return new LinkedHashSet<>();
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String description(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Contract.");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(Persistence source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(source.getObjectName().getText()));

    return stringBuilder.toString();
  }
}
