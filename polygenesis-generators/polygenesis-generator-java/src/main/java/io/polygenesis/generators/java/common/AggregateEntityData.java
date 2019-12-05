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

package io.polygenesis.generators.java.common;

/**
 * The type Aggregate entity data.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntityData {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String dataType;
  private String variable;
  private String variablePlural;
  private String idDataType;
  private String idVariable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity data.
   *
   * @param dataType the data type
   * @param variable the variable
   * @param variablePlural the variable plural
   * @param idDataType the id data type
   * @param idVariable the id variable
   */
  public AggregateEntityData(
      String dataType,
      String variable,
      String variablePlural,
      String idDataType,
      String idVariable) {
    this.dataType = dataType;
    this.variable = variable;
    this.variablePlural = variablePlural;
    this.idDataType = idDataType;
    this.idVariable = idVariable;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets data type.
   *
   * @return the data type
   */
  public String getDataType() {
    return dataType;
  }

  /**
   * Gets variable.
   *
   * @return the variable
   */
  public String getVariable() {
    return variable;
  }

  /**
   * Gets variable plural.
   *
   * @return the variable plural
   */
  public String getVariablePlural() {
    return variablePlural;
  }

  /**
   * Gets id data type.
   *
   * @return the id data type
   */
  public String getIdDataType() {
    return idDataType;
  }

  /**
   * Gets id variable.
   *
   * @return the id variable
   */
  public String getIdVariable() {
    return idVariable;
  }
}
