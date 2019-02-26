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

package io.polygenesis.generators.java.shared;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Function;
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Abstract function projection maker.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractFunctionProjectionMaker implements FunctionProjectionMaker {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  protected final FromDataTypeToJavaConverter fromDataTypeToJavaConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract function projection maker.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public AbstractFunctionProjectionMaker(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    this.fromDataTypeToJavaConverter = fromDataTypeToJavaConverter;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public FunctionProjection make(Function function) {
    return new FunctionProjection(
        function,
        projectName(function),
        projectDescription(),
        projectReturnValue(function),
        projectArguments(function),
        projectAnnotations());
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Project arguments set.
   *
   * @param function the function
   * @return the set
   */
  protected Set<ArgumentProjection> projectArguments(Function function) {
    Set<ArgumentProjection> arguments = new LinkedHashSet<>();

    function
        .getArguments()
        .forEach(
            argument -> {
              arguments.add(
                  new ArgumentProjection(
                      fromDataTypeToJavaConverter.getDeclaredVariableType(argument.getModel()),
                      argument.getModel().getVariableName().getText()));
            });

    return arguments;
  }

  /**
   * Project name string.
   *
   * @param function the function
   * @return the string
   */
  protected String projectName(Function function) {
    return function.getName().getText();
  }

  /**
   * Project description string.
   *
   * @return the string
   */
  protected String projectDescription() {
    return "Some description here please.";
  }

  /**
   * Project return value string.
   *
   * @param function the function
   * @return the string
   */
  protected String projectReturnValue(Function function) {
    // TODO - primitives
    return TextConverter.toUpperCamel(
        function.getReturnValue().getModel().getDataType().getDataTypeName().getText());
  }

  /**
   * Project annotations set.
   *
   * @return the set
   */
  protected Set<String> projectAnnotations() {
    return new LinkedHashSet<>();
  }
}
