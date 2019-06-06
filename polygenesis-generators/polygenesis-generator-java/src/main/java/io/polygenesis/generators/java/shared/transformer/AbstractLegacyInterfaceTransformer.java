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

package io.polygenesis.generators.java.shared.transformer;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.TemplateData;
import io.polygenesis.representations.code.InterfaceRepresentation;

/**
 * The type Abstract interface representable.
 *
 * @param <S> the type parameter
 * @author Christos Tsakostas
 */
public abstract class AbstractLegacyInterfaceTransformer<S> extends AbstractTransformer
    implements InterfaceTransformer<S> {

  /** The constant MODIFIER_PUBLIC. */
  protected static final String MODIFIER_PUBLIC = "public";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  /** The Function to method representation converter. */
  @SuppressWarnings("CPD-START")
  protected final FunctionToLegacyMethodRepresentationTransformer
      functionToMethodRepresentationTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract interface representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param functionToMethodRepresentationTransformer the function to method representation
   *     converter
   */
  public AbstractLegacyInterfaceTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      FunctionToLegacyMethodRepresentationTransformer functionToMethodRepresentationTransformer) {
    super(fromDataTypeToJavaConverter);
    this.functionToMethodRepresentationTransformer = functionToMethodRepresentationTransformer;
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
