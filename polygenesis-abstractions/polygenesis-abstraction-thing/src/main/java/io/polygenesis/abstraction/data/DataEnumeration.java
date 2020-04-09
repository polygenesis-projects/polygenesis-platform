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

package io.polygenesis.abstraction.data;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.Objects;
import java.util.Set;

public class DataEnumeration extends AbstractData {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final ObjectName objectName;
  private final PackageName packageName;
  private final Set<EnumerationValue> enumerationValues;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Of purpose data enumeration.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param variableName the variable name
   * @param enumerationValues the enumeration values
   * @param dataPurpose the data purpose
   * @return the data enumeration
   */
  public static DataEnumeration ofPurpose(
      ObjectName objectName,
      PackageName packageName,
      VariableName variableName,
      Set<EnumerationValue> enumerationValues,
      DataPurpose dataPurpose) {
    return new DataEnumeration(
        variableName,
        dataPurpose,
        DataValidator.empty(),
        DataSourceType.DEFAULT,
        objectName,
        packageName,
        enumerationValues);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Data enum.
   *
   * @param variableName the variable name
   * @param dataPurpose the data purpose
   * @param dataValidator the data validator
   * @param dataSourceType the data source type
   * @param objectName the object name
   * @param packageName the package name
   * @param enumerationValues the enumeration values
   */
  public DataEnumeration(
      VariableName variableName,
      DataPurpose dataPurpose,
      DataValidator dataValidator,
      DataSourceType dataSourceType,
      ObjectName objectName,
      PackageName packageName,
      Set<EnumerationValue> enumerationValues) {
    super(DataPrimaryType.ENUMERATION, variableName, dataPurpose, dataValidator, dataSourceType);
    this.objectName = objectName;
    this.packageName = packageName;
    this.enumerationValues = enumerationValues;
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
   * Gets package name.
   *
   * @return the package name
   */
  public PackageName getPackageName() {
    return packageName;
  }

  /**
   * Gets enumeration values.
   *
   * @return the enumeration values
   */
  public Set<EnumerationValue> getEnumerationValues() {
    return enumerationValues;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public String getDataType() {
    // TODO vo
    // return DataPrimaryType.ENUM.name();
    return getObjectName().getText();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    DataEnumeration dataEnumeration = (DataEnumeration) o;
    return Objects.equals(objectName, dataEnumeration.objectName)
        && Objects.equals(packageName, dataEnumeration.packageName)
        && Objects.equals(enumerationValues, dataEnumeration.enumerationValues);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), objectName, packageName, enumerationValues);
  }
}
