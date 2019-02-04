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

package io.polygenesis.models.domain;

import io.polygenesis.core.datatype.ClassDataType;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.core.iomodel.VariableName;
import java.util.Optional;

/**
 * The type Value object.
 *
 * @author Christos Tsakostas
 */
public class ValueObject extends AbstractProperty {

  private IoModelGroup ioModelGroup;
  private ClassDataType classDataType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Value object.
   *
   * @param ioModelGroup the io model group
   * @param variableName the variable name
   */
  public ValueObject(IoModelGroup ioModelGroup, VariableName variableName) {
    super(variableName);
    setIoModelGroup(ioModelGroup);
    setClassDataType((ClassDataType) ioModelGroup.getDataType());
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets io model group.
   *
   * @return the io model group
   */
  public IoModelGroup getIoModelGroup() {
    return ioModelGroup;
  }

  /**
   * Gets class data type.
   *
   * @return the class data type
   */
  public ClassDataType getClassDataType() {
    return classDataType;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets io model group.
   *
   * @param ioModelGroup the io model group
   */
  private void setIoModelGroup(IoModelGroup ioModelGroup) {
    this.ioModelGroup = ioModelGroup;
  }

  /**
   * Sets class data type.
   *
   * @param classDataType the class data type
   */
  private void setClassDataType(ClassDataType classDataType) {
    this.classDataType = classDataType;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Optional<IoModelGroup> getIoModelGroupAsOptional() {
    return Optional.of(getIoModelGroup());
  }
}
