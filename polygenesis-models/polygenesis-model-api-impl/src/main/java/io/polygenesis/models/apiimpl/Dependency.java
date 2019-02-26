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

package io.polygenesis.models.apiimpl;

import io.polygenesis.core.datatype.ClassDataType;
import io.polygenesis.core.iomodel.VariableName;
import java.util.Objects;

/**
 * The type Dependency.
 *
 * @author Christos Tsakostas
 */
public class Dependency {

  private ClassDataType dataType;
  private VariableName variableName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Dependency.
   *
   * @param dataType the data type
   * @param variableName the variable name
   */
  public Dependency(ClassDataType dataType, VariableName variableName) {
    setDataType(dataType);
    setVariableName(variableName);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets data type.
   *
   * @return the data type
   */
  public ClassDataType getDataType() {
    return dataType;
  }

  /**
   * Gets variable name.
   *
   * @return the variable name
   */
  public VariableName getVariableName() {
    return variableName;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets data type.
   *
   * @param dataType the data type
   */
  private void setDataType(ClassDataType dataType) {
    this.dataType = dataType;
  }

  /**
   * Sets variable name.
   *
   * @param variableName the variable name
   */
  private void setVariableName(VariableName variableName) {
    this.variableName = variableName;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Dependency that = (Dependency) o;
    return Objects.equals(dataType, that.dataType)
        && Objects.equals(variableName, that.variableName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataType, variableName);
  }
}
