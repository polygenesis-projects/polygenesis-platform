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
import io.polygenesis.core.Function;
import io.polygenesis.representations.commons.ParameterRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Function to method representation converter.
 *
 * @author Christos Tsakostas
 */
public class FunctionToMethodRepresentationConverter extends AbstractMethodRepresentable<Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Function to method representation converter.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public FunctionToMethodRepresentationConverter(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public MethodType methodType(Function source, Object... args) {
    return MethodType.ANY;
  }

  @Override
  public Set<String> annotations(Function source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String description(Function source, Object... args) {
    return "Some description here please.";
  }

  @Override
  public String modifiers(Function source, Object... args) {
    return "";
  }

  @Override
  public String methodName(Function source, Object... args) {
    return source.getName().getText();
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(Function source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    source
        .getArguments()
        .forEach(
            argument -> {
              parameterRepresentations.add(
                  new ParameterRepresentation(
                      fromDataTypeToJavaConverter.getDeclaredVariableType(argument.getModel()),
                      argument.getModel().getVariableName().getText()));
            });

    return parameterRepresentations;
  }

  @Override
  public String returnValue(Function source, Object... args) {
    // TODO - primitives
    return TextConverter.toUpperCamel(
        source.getReturnValue().getModel().getDataType().getDataTypeName().getText());
  }

  @Override
  public String implementation(Function source, Object... args) {
    return "";
  }
}
