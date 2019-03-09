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

package io.polygenesis.core.dsl;

import io.polygenesis.core.data.IoModel;
import io.polygenesis.core.data.IoModelArray;
import io.polygenesis.core.data.VariableName;

/**
 * The type Data array builder.
 *
 * @author Christos Tsakostas
 */
public class DataArrayBuilder {

  private final String name;
  private final IoModel arrayElement;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private DataArrayBuilder(String arrayName, IoModel arrayElement) {
    this.name = arrayName;
    this.arrayElement = arrayElement;
  }

  // ===============================================================================================
  // START
  // ===============================================================================================

  /**
   * Create data array builder.
   *
   * @param arrayName the array name
   * @param arrayElement the array element
   * @return the data array builder
   */
  public static DataArrayBuilder create(String arrayName, IoModel arrayElement) {
    return new DataArrayBuilder(arrayName, arrayElement);
  }

  // ===============================================================================================
  // END
  // ===============================================================================================

  /**
   * Build io model group.
   *
   * @return the io model group
   */
  public final IoModelArray build() {
    return new IoModelArray(new VariableName(this.name), this.arrayElement);
  }
}
