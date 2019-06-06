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

package io.polygenesis.deducers.sql;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.sql.ColumnDataType;
import java.util.HashMap;
import java.util.Map;

/**
 * The type From data type to sql column converter.
 *
 * @author Christos Tsakostas
 */
public class FromDataTypeToSqlColumnConverter {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static Map<String, ColumnDataType> dataTypeMap;

  static {
    initialize();
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Gets column data type by.
   *
   * @param model the model
   * @return the column data type by
   */
  public ColumnDataType getColumnDataTypeBy(Data model) {
    if (dataTypeMap.containsKey(model.getDataType())) {
      return dataTypeMap.get(model.getDataType());
    } else {
      throw new IllegalArgumentException(
          String.format(
              "Cannot get ColumnDataType for primitive data type=%s",
              TextConverter.toUpperCamel(model.getDataType())));
    }
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private static void initialize() {
    dataTypeMap = new HashMap<>();
    dataTypeMap.put(PrimitiveType.STRING.name(), ColumnDataType.VARCHAR);
    dataTypeMap.put(PrimitiveType.INTEGER.name(), ColumnDataType.INTEGER);
    dataTypeMap.put(PrimitiveType.LONG.name(), ColumnDataType.BIG_INTEGER);
    dataTypeMap.put(PrimitiveType.BOOLEAN.name(), ColumnDataType.BIT);
    dataTypeMap.put(PrimitiveType.DATETIME.name(), ColumnDataType.DATETIME);
    dataTypeMap.put(PrimitiveType.DATE.name(), ColumnDataType.DATE);
    dataTypeMap.put(PrimitiveType.DECIMAL.name(), ColumnDataType.DECIMAL);
    dataTypeMap.put(PrimitiveType.UUID.name(), ColumnDataType.BINARY);
  }
}
