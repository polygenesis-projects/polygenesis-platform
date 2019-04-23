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

package io.polygenesis.core;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import java.util.Objects;

/**
 * The type Abstract metamodel.
 *
 * @author Christos Tsakostas
 */
public class AbstractMetamodel implements Metamodel {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private ObjectName objectName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract metamodel.
   *
   * @param objectName the object name
   */
  public AbstractMetamodel(ObjectName objectName) {
    setObjectName(objectName);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return objectName;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets object name.
   *
   * @param objectName the object name
   */
  private void setObjectName(ObjectName objectName) {
    Assertion.isNotNull(objectName, "objectName is required");
    this.objectName = objectName;
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
    AbstractMetamodel that = (AbstractMetamodel) o;
    return Objects.equals(objectName, that.objectName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectName);
  }
}
