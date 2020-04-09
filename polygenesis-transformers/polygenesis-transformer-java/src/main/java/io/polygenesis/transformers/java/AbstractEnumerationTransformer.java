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

package io.polygenesis.transformers.java;

import io.polygenesis.abstraction.thing.FunctionProvider;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Nameable;
import io.polygenesis.generators.java.shared.transformer.EnumerationTransformer;
import io.polygenesis.generators.java.shared.transformer.MethodTransformer;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.EnumerationRepresentation;
import io.polygenesis.representations.code.EnumerationValueRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractEnumerationTransformer<S extends Nameable, F extends FunctionProvider>
    extends AbstractTransformer implements EnumerationTransformer<S> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  /** The Method transformer. */
  protected final MethodTransformer<F> methodTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract enumeration transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public AbstractEnumerationTransformer(
      DataTypeTransformer dataTypeTransformer, MethodTransformer<F> methodTransformer) {
    super(dataTypeTransformer);
    this.methodTransformer = methodTransformer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public EnumerationRepresentation create(S source, Object... args) {
    return new EnumerationRepresentation(
        packageName(source, args),
        imports(source, args),
        annotations(source, args),
        description(source, args),
        modifiers(source, args),
        simpleObjectName(source, args),
        fullObjectName(source, args),
        staticFieldRepresentations(source, args),
        stateFieldRepresentations(source, args),
        dependencyFieldRepresentations(source, args),
        constructorRepresentations(source, args),
        methodRepresentations(source, args),
        enumerationValueRepresentations(source, args));
  }

  @Override
  public Set<EnumerationValueRepresentation> enumerationValueRepresentations(
      S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<FieldRepresentation> staticFieldRepresentations(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<FieldRepresentation> dependencyFieldRepresentations(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String packageName(S source, Object... args) {
    return "";
  }

  @Override
  public Set<String> imports(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<String> annotations(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String description(S source, Object... args) {
    return String.format(
        "The %s type.", TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));
  }

  @Override
  public String modifiers(S source, Object... args) {
    return dataTypeTransformer.getModifierPublic();
  }

  @Override
  public String simpleObjectName(S source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(S source, Object... args) {
    return simpleObjectName(source);
  }
}
