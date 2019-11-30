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

package io.polygenesis.abstraction.data;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.Set;

/**
 * The type Data enum.
 *
 * @author Christos Tsakostas
 */
public class DataEnum extends DataObject {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Data enum.
   *
   * @param objectName the object name
   * @param packageName the package name
   */
  public DataEnum(ObjectName objectName, PackageName packageName) {
    super(objectName, packageName);
  }

  /**
   * Instantiates a new Data enum.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param variableName the variable name
   */
  public DataEnum(ObjectName objectName, PackageName packageName, VariableName variableName) {
    super(objectName, packageName, variableName);
  }

  /**
   * Instantiates a new Data enum.
   *
   * @param variableName the variable name
   * @param dataPurpose the data purpose
   * @param dataValidator the data validator
   * @param objectName the object name
   * @param packageName the package name
   * @param models the models
   */
  public DataEnum(
      VariableName variableName,
      DataPurpose dataPurpose,
      DataValidator dataValidator,
      ObjectName objectName,
      PackageName packageName,
      Set<Data> models) {
    super(variableName, dataPurpose, dataValidator, objectName, packageName, models);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public String getDataType() {
    return DataPrimaryType.ENUM.name();
  }
}
