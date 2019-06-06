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

import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.text.TextConverter;
import java.util.EnumMap;
import java.util.stream.Stream;

/**
 * The type From data type to java converter.
 *
 * @author Christos Tsakostas
 */
public class FromDataTypeToJavaConverter {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static EnumMap<PrimitiveType, String> dataTypeMap;

  static {
    initialize();
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Convert string.
   *
   * @param dataType the data type
   * @return the string
   */
  public String convert(String dataType) {
    String candidate = TextConverter.toUpperCamel(dataType);

    return Stream.of(PrimitiveType.values())
        .filter(value -> value.name().equalsIgnoreCase(candidate))
        .findFirst()
        .map(primaryType -> dataTypeMap.get(primaryType))
        .orElse(candidate);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private static void initialize() {
    dataTypeMap = new EnumMap<>(PrimitiveType.class);

    dataTypeMap.put(PrimitiveType.VOID, "void");
    dataTypeMap.put(PrimitiveType.STRING, "String");
    dataTypeMap.put(PrimitiveType.INTEGER, "Integer");
    dataTypeMap.put(PrimitiveType.LONG, "Long");
    dataTypeMap.put(PrimitiveType.BOOLEAN, "Boolean");
    dataTypeMap.put(PrimitiveType.DATE, "LocalDate");
    dataTypeMap.put(PrimitiveType.DATETIME, "LocalDateTime");
    dataTypeMap.put(PrimitiveType.DECIMAL, "BigDecimal");
    dataTypeMap.put(PrimitiveType.UUID, "UUID");
  }
}
