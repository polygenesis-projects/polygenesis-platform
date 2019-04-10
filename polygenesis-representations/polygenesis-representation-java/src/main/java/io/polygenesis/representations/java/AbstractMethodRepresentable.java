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

package io.polygenesis.representations.java;

import io.polygenesis.representations.commons.ParameterRepresentation;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Abstract method representable.
 *
 * @param <S> the type parameter
 * @author Christos Tsakostas
 */
public abstract class AbstractMethodRepresentable<S> extends AbstractRepresentable
    implements MethodRepresentable<S> {

  /** The constant MODIFIER_PUBLIC. */
  protected static final String MODIFIER_PUBLIC = "public";

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract method representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public AbstractMethodRepresentable(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public MethodRepresentation create(S source, Object... args) {
    return new MethodRepresentation(
        methodType(source, args),
        imports(source, args),
        annotations(source, args),
        description(source, args),
        modifiers(source, args),
        methodName(source, args),
        parameterRepresentations(source, args),
        returnValue(source, args),
        implementation(source, args));
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Gets parameters comma separated.
   *
   * @param parameterRepresentations the parameter representations
   * @return the parameters comma separated
   */
  protected String getParametersCommaSeparated(
      Set<ParameterRepresentation> parameterRepresentations) {
    return parameterRepresentations
        .stream()
        .map(parameterRepresentation -> parameterRepresentation.getVariableName())
        .collect(Collectors.joining(", "));
  }
}
