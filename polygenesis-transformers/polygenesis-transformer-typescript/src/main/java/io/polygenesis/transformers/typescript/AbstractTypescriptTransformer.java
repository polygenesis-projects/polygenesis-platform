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

package io.polygenesis.transformers.typescript;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.core.DataTypeTransformer;

/**
 * The type Abstract typescript transformer.
 *
 * @author Christos Tsakostas
 */
public class AbstractTypescriptTransformer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  /** The Data type transformer. */
  protected final DataTypeTransformer dataTypeTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract typescript transformer.
   *
   * @param dataTypeTransformer the data type transformer
   */
  public AbstractTypescriptTransformer(DataTypeTransformer dataTypeTransformer) {
    this.dataTypeTransformer = dataTypeTransformer;
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
          dataTypeTransformer.convert(model.getAsDataArray().getArrayElement().getDataType()));
    } else if (model.isDataMap()) {
      return String.format(
          "Map<%s, %s>",
          dataTypeTransformer.convert(model.getAsDataMap().getKey().getDataType()),
          dataTypeTransformer.convert(model.getAsDataMap().getValue().getDataType()));
    } else {
      return dataTypeTransformer.convert(model.getDataType());
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
