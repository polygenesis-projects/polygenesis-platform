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

package io.polygenesis.generators.java.api;

import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.models.api.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Method projection maker.
 *
 * @author Christos Tsakostas
 */
public class MethodProjectionMaker {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FromDataTypeToJavaConverter fromDataTypeToJavaConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Method projection maker.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public MethodProjectionMaker(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    this.fromDataTypeToJavaConverter = fromDataTypeToJavaConverter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Make method projection.
   *
   * @param method the method
   * @return the method projection
   */
  public MethodProjection make(Method method) {
    return new MethodProjection(
        method,
        projectName(method),
        projectDescription(),
        projectReturnValue(method),
        projectArguments(method));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private String projectName(Method method) {
    return method.getMethodName().getText();
  }

  private String projectDescription() {
    return "Some description here please!";
  }

  private String projectReturnValue(Method method) {
    // TODO - primitives
    return TextConverter.toUpperCamel(
        method.getReturnValue().getModel().getDataType().getDataTypeName().getText());
  }

  private Set<KeyValue> projectArguments(Method method) {
    Set<KeyValue> arguments = new LinkedHashSet<>();

    method
        .getArguments()
        .forEach(
            argument -> {
              arguments.add(
                  new KeyValue(
                      fromDataTypeToJavaConverter.getDeclaredVariableType(argument.getModel()),
                      argument.getModel().getVariableName().getText()));
            });

    return arguments;
  }
}
