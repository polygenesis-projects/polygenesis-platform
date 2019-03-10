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

import io.polygenesis.core.data.DataPrimitive;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.core.data.VariableName;

/**
 * The type Data boolean builder.
 *
 * @author Christos Tsakostas
 */
public class DataBooleanBuilder {

  private final DataPrimitive model;

  // Keep a back reference to the DataBuilder.
  private final DataBuilder dataBuilder;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private DataBooleanBuilder(DataBuilder dataBuilder, String propertyName) {
    this.dataBuilder = dataBuilder;
    model = DataPrimitive.of(PrimitiveType.BOOLEAN, new VariableName(propertyName));
  }

  // ===============================================================================================
  // START
  // ===============================================================================================

  /**
   * Create data boolean builder.
   *
   * @param dataBuilder the data builder
   * @param propertyName the property name
   * @return the data model builder
   */
  public static DataBooleanBuilder create(DataBuilder dataBuilder, String propertyName) {
    return new DataBooleanBuilder(dataBuilder, propertyName);
  }

  // ===============================================================================================
  // WITH
  // ===============================================================================================

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets model.
   *
   * @return the model
   */
  final DataPrimitive getModel() {
    return model;
  }

  // ===============================================================================================
  // END
  // ===============================================================================================

  /**
   * Build data builder.
   *
   * @return the data builder
   */
  public final DataBuilder build() {
    return dataBuilder;
  }
}
