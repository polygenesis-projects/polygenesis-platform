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

/**
 * The type Sql table deducer factory.
 *
 * @author Christos Tsakostas
 */
public final class SqlTableDeducerFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static final TableDeducer tableDeducer;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    FromDataTypeToSqlColumnConverter fromDataTypeToSqlColumnConverter =
        new FromDataTypeToSqlColumnConverter();

    tableDeducer = new TableDeducer(fromDataTypeToSqlColumnConverter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================
  private SqlTableDeducerFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * New instance sql table deducer.
   *
   * @return the sql table deducer
   */
  public static SqlTableDeducer newInstance() {
    return new SqlTableDeducer(tableDeducer);
  }
}
