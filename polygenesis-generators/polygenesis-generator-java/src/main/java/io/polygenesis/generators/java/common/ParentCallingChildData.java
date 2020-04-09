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
 * The type Parent calling child data.
 *
 * @author Christos Tsakostas
 */
public class ParentCallingChildData {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String parentMethodName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Parent calling child data.
   *
   * @param parentMethodName the parent method name
   */
  public ParentCallingChildData(String parentMethodName) {
    this.parentMethodName = parentMethodName;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets parent method name.
   *
   * @return the parent method name
   */
  public String getParentMethodName() {
    return parentMethodName;
  }
}
