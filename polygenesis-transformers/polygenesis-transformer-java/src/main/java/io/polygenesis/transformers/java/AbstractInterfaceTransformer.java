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

package io.polygenesis.transformers.java;

import io.polygenesis.abstraction.thing.FunctionProvider;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Nameable;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.shared.transformer.InterfaceTransformer;
import io.polygenesis.generators.java.shared.transformer.MethodTransformer;
import io.polygenesis.representations.code.InterfaceRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Abstract interface representable.
 *
 * @param <S> the type parameter
 * @author Christos Tsakostas
 */
public abstract class AbstractInterfaceTransformer<S extends Nameable, F extends FunctionProvider>
    extends AbstractTransformer implements InterfaceTransformer<S> {

  /** The constant MODIFIER_PUBLIC. */
  protected static final String MODIFIER_PUBLIC = "public";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  protected final MethodTransformer<F> methodTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  public AbstractInterfaceTransformer(
      DataTypeTransformer dataTypeTransformer, MethodTransformer<F> methodTransformer) {
    super(dataTypeTransformer);
    this.methodTransformer = methodTransformer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(S source, Object... args) {
    throw new UnsupportedOperationException("Must be implemented");
  }

  @Override
  public InterfaceRepresentation create(S source, Object... args) {
    return new InterfaceRepresentation(
        packageName(source, args),
        imports(source, args),
        annotations(source, args),
        description(source, args),
        modifiers(source, args),
        simpleObjectName(source, args),
        fullObjectName(source, args),
        methodRepresentations(source, args));
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

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Make canonical object name string.
   *
   * @param packageName the package name
   * @param dataType the data type
   * @return the string
   */
  @SuppressWarnings("CPD-END")
  protected String makeCanonicalObjectName(PackageName packageName, String dataType) {
    return packageName.getText() + "." + TextConverter.toUpperCamel(dataType);
  }
}
