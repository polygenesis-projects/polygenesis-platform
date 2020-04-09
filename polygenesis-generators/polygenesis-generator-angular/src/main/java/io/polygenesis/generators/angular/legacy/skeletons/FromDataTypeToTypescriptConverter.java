/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.generators.angular.legacy.skeletons;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.text.TextConverter;
import java.util.EnumMap;
import java.util.stream.Stream;

public class FromDataTypeToTypescriptConverter {

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
   * Gets declared variable type.
   *
   * @param model the model
   * @return the declared variable type
   */
  public String getDeclaredVariableType(Data model) {
    String candidate = TextConverter.toUpperCamel(model.getDataType());

    return Stream.of(PrimitiveType.values())
        .filter(value -> value.name().equalsIgnoreCase(candidate))
        .findFirst()
        .map(primaryType -> dataTypeMap.get(primaryType))
        .orElseGet(() -> candidate);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private static void initialize() {
    dataTypeMap = new EnumMap<>(PrimitiveType.class);

    dataTypeMap.put(PrimitiveType.VOID, "void");
    dataTypeMap.put(PrimitiveType.STRING, "string");
    dataTypeMap.put(PrimitiveType.INTEGER, "number");
    dataTypeMap.put(PrimitiveType.LONG, "number");
    dataTypeMap.put(PrimitiveType.BOOLEAN, "boolean");
  }

  // TODO: remove
  public String getModifierPrivate() {
    return "private";
  }
}
