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
 * The type Method output descriptor.
 *
 * @author Christos Tsakostas
 */
public class MethodOutputDescriptor {

  private final String genericType;
  private final String dataType;
  private final Class<?> clazz;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Method output descriptor.
   *
   * @param genericType the generic type
   * @param dataType the data type
   * @param clazz the clazz
   */
  public MethodOutputDescriptor(String genericType, String dataType, Class<?> clazz) {
    this.genericType = genericType;
    this.dataType = dataType;
    this.clazz = clazz;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets generic type.
   *
   * @return the generic type
   */
  public String getGenericType() {
    return genericType;
  }

  /**
   * Gets data type.
   *
   * @return the data type
   */
  public String getDataType() {
    return dataType;
  }

  /**
   * Gets clazz.
   *
   * @return the clazz
   */
  public Class<?> getClazz() {
    return clazz;
  }
}
