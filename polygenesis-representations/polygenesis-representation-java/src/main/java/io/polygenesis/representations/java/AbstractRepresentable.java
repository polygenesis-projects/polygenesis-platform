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

import io.polygenesis.core.data.Data;

/**
 * The type Abstract representable.
 *
 * @author Christos Tsakostas
 */
public class AbstractRepresentable {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  /** The From data type to java converter. */
  protected final FromDataTypeToJavaConverter fromDataTypeToJavaConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public AbstractRepresentable(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    this.fromDataTypeToJavaConverter = fromDataTypeToJavaConverter;
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Make variable data type string.
   *
   * @param model the model
   * @return the string
   */
  protected String makeVariableDataType(Data model) {
    if (model.isDataArray()) {
      return String.format(
          "List<%s>",
          fromDataTypeToJavaConverter.getDeclaredVariableType(
              model.getAsDataArray().getArrayElement().getDataType()));
    } else if (model.isDataMap()) {
      return String.format(
          "Map<%s, %s>",
          fromDataTypeToJavaConverter.getDeclaredVariableType(
              model.getAsDataMap().getKey().getDataType()),
          fromDataTypeToJavaConverter.getDeclaredVariableType(
              model.getAsDataMap().getValue().getDataType()));
    } else {
      return fromDataTypeToJavaConverter.getDeclaredVariableType(model.getDataType());
    }
  }

  /**
   * Make variable name string.
   *
   * @param model the model
   * @return the string
   */
  protected String makeVariableName(Data model) {
    return model.getVariableName().getText();
  }
}
