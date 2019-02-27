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

package io.polygenesis.representations.java;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.datatype.DataTypeName;
import io.polygenesis.core.datatype.PackageName;

/**
 * The type Abstract interface representable.
 *
 * @param <S> the type parameter
 * @author Christos Tsakostas
 */
public abstract class AbstractInterfaceRepresentable<S> implements InterfaceRepresentable<S> {

  /** The constant MODIFIER_PUBLIC. */
  protected static final String MODIFIER_PUBLIC = "public";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  /** The From data type to java converter. */
  protected final FromDataTypeToJavaConverter fromDataTypeToJavaConverter;
  /** The Function to method representation converter. */
  protected final FunctionToMethodRepresentationConverter functionToMethodRepresentationConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract interface representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param functionToMethodRepresentationConverter the function to method representation converter
   */
  public AbstractInterfaceRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      FunctionToMethodRepresentationConverter functionToMethodRepresentationConverter) {
    this.fromDataTypeToJavaConverter = fromDataTypeToJavaConverter;
    this.functionToMethodRepresentationConverter = functionToMethodRepresentationConverter;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

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
   * @param dataTypeName the data type name
   * @return the string
   */
  protected String makeCanonicalObjectName(PackageName packageName, DataTypeName dataTypeName) {
    return packageName.getText() + "." + TextConverter.toUpperCamel(dataTypeName.getText());
  }
}
