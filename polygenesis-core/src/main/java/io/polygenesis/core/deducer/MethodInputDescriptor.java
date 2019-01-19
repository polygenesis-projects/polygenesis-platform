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

package io.polygenesis.core.deducer;

/**
 * The type Method input descriptor.
 *
 * @author Christos Tsakostas
 */
public class MethodInputDescriptor {

  private final String typeName;
  private final String name;
  private final Class<?> type;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Method input descriptor.
   *
   * @param typeName the type name
   * @param name the name
   * @param type the type
   */
  public MethodInputDescriptor(String typeName, String name, Class<?> type) {
    this.typeName = typeName;
    this.name = name;
    this.type = type;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets type name.
   *
   * @return the type name
   */
  public String getTypeName() {
    return typeName;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets type.
   *
   * @return the type
   */
  public Class<?> getType() {
    return type;
  }
}
