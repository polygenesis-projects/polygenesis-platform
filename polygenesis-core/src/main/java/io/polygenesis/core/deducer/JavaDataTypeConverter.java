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

package io.polygenesis.core.deducer;

import io.polygenesis.core.datatype.PrimitiveType;
import java.util.HashMap;
import java.util.Map;

/**
 * Converts java types to PolyGenesis data types.
 *
 * @author Christos Tsakostas
 */
public class JavaDataTypeConverter {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static Map<String, PrimitiveType> dataTypeMap;

  static {
    initialize();
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Convert data type.
   *
   * @param type the type
   * @return the data type
   */
  PrimitiveType convert(String type) {
    if (dataTypeMap.containsKey(type)) {
      return dataTypeMap.get(type);
    } else {
      throw new IllegalArgumentException(String.format("Type=%s could not be converted", type));
    }
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private static void initialize() {
    dataTypeMap = new HashMap<>();

    dataTypeMap.put("void", PrimitiveType.VOID);

    dataTypeMap.put("java.lang.String", PrimitiveType.STRING);

    dataTypeMap.put("int", PrimitiveType.INTEGER);
    dataTypeMap.put("java.lang.Integer", PrimitiveType.INTEGER);

    dataTypeMap.put("long", PrimitiveType.LONG);
    dataTypeMap.put("java.lang.Long", PrimitiveType.LONG);

    dataTypeMap.put("boolean", PrimitiveType.BOOLEAN);
    dataTypeMap.put("java.lang.Boolean", PrimitiveType.BOOLEAN);

    dataTypeMap.put("java.util.List", PrimitiveType.ARRAY);
  }
}
