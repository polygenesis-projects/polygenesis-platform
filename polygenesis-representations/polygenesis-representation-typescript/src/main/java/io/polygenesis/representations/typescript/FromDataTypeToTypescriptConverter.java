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

package io.polygenesis.representations.typescript;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.datatype.PrimitiveType;
import io.polygenesis.core.iomodel.IoModel;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The type From data type to typescript converter.
 *
 * @author Christos Tsakostas
 */
public class FromDataTypeToTypescriptConverter {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static Map<PrimitiveType, String> dataTypeMap;

  static {
    initialize();
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Gets declared variable type.
   *
   * @param model the model
   * @return the declared variable type
   */
  public String getDeclaredVariableType(IoModel model) {
    String candidate = TextConverter.toUpperCamel(model.getDataType());

    return Stream.of(PrimitiveType.values())
        .filter(value -> value.name().equals(candidate.toUpperCase()))
        .findFirst()
        .map(primaryType -> dataTypeMap.get(primaryType))
        .orElseGet(() -> candidate);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private static void initialize() {
    dataTypeMap = new HashMap<>();

    dataTypeMap.put(PrimitiveType.VOID, "void");

    dataTypeMap.put(PrimitiveType.STRING, "string");

    dataTypeMap.put(PrimitiveType.INTEGER, "number");
    dataTypeMap.put(PrimitiveType.LONG, "number");

    dataTypeMap.put(PrimitiveType.BOOLEAN, "boolean");

    dataTypeMap.put(PrimitiveType.ARRAY, "java.util.List");
  }
}
