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

package io.polygenesis.core.data;

import io.polygenesis.core.iomodel.DataKind;
import io.polygenesis.core.iomodel.VariableName;
import java.util.Set;

/**
 * The type Object data.
 *
 * @author Christos Tsakostas
 */
public class ObjectData extends Data {

  // ===============================================================================================
  // STATE
  // ===============================================================================================
  private final ObjectName objectName;
  private final Set<Data> dataSet;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Object data.
   *
   * @param variableName the variable name
   * @param objectName the object name
   * @param dataSet the data set
   */
  public ObjectData(VariableName variableName, ObjectName objectName, Set<Data> dataSet) {
    super(DataKind.CLASS, variableName);
    this.objectName = objectName;
    this.dataSet = dataSet;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets object name.
   *
   * @return the object name
   */
  public ObjectName getObjectName() {
    return objectName;
  }

  /**
   * Gets data set.
   *
   * @return the data set
   */
  public Set<Data> getDataSet() {
    return dataSet;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public String getDataType() {
    return objectName.getText();
  }
}
