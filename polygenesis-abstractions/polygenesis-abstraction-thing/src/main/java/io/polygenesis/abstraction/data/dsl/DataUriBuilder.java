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

package io.polygenesis.abstraction.data.dsl;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;

public class DataUriBuilder {

  private final DataPrimitive model;

  // Keep a back reference to the DataBuilder.
  private final DataBuilder dataBuilder;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private DataUriBuilder(DataBuilder dataBuilder, String propertyName) {
    this.dataBuilder = dataBuilder;
    model = DataPrimitive.of(PrimitiveType.URI, new VariableName(propertyName));
  }

  private DataUriBuilder(
      DataBuilder dataBuilder,
      String propertyName,
      ObjectName objectName,
      PackageName packageName) {
    this.dataBuilder = dataBuilder;
    DataObject dataObject = new DataObject(objectName, packageName);
    model = DataPrimitive.of(PrimitiveType.URI, new VariableName(propertyName), dataObject);
  }

  // ===============================================================================================
  // START
  // ===============================================================================================

  /**
   * Create data uri builder.
   *
   * @param dataBuilder the data builder
   * @param propertyName the property name
   * @return the data uri builder
   */
  public static DataUriBuilder create(DataBuilder dataBuilder, String propertyName) {
    return new DataUriBuilder(dataBuilder, propertyName);
  }

  /**
   * Create data uri builder.
   *
   * @param dataBuilder the data builder
   * @param propertyName the property name
   * @param objectName the object name
   * @param packageName the package name
   * @return the data uri builder
   */
  public static DataUriBuilder create(
      DataBuilder dataBuilder,
      String propertyName,
      ObjectName objectName,
      PackageName packageName) {
    return new DataUriBuilder(dataBuilder, propertyName, objectName, packageName);
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
