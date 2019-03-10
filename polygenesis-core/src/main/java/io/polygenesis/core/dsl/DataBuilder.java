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

import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataArray;
import io.polygenesis.core.data.DataGroup;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Data model builder.
 *
 * @author Christos Tsakostas
 */
public class DataBuilder {

  private final Set<Data> models;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private DataBuilder() {
    this.models = new LinkedHashSet<>();
  }

  // ===============================================================================================
  // START
  // ===============================================================================================

  /**
   * Empty data model builder.
   *
   * @return the data model builder
   */
  public static DataBuilder create() {
    return new DataBuilder();
  }

  // ===============================================================================================
  // WITH
  // ===============================================================================================

  /**
   * With boolean property data boolean builder.
   *
   * @param propertyName the property name
   * @return the data boolean builder
   */
  public final DataBooleanBuilder withBooleanProperty(String propertyName) {
    DataBooleanBuilder dataBooleanBuilder = DataBooleanBuilder.create(this, propertyName);

    this.models.add(dataBooleanBuilder.getModel());

    return dataBooleanBuilder;
  }

  /**
   * With text property data text builder.
   *
   * @param propertyName the property name
   * @return the data text builder
   */
  public final DataTextBuilder withTextProperty(String propertyName) {
    DataTextBuilder dataTextBuilder = DataTextBuilder.create(this, propertyName);

    this.models.add(dataTextBuilder.getModel());

    return dataTextBuilder;
  }

  /**
   * With group data data builder.
   *
   * @param dataGroup the data group
   * @return the data builder
   */
  public final DataBuilder withGroupData(DataGroup dataGroup) {
    this.models.add(dataGroup);
    return this;
  }

  /**
   * With array data data builder.
   *
   * @param dataArray the data array
   * @return the data builder
   */
  public final DataBuilder withArrayData(DataArray dataArray) {
    this.models.add(dataArray);
    return this;
  }

  // ===============================================================================================
  // END
  // ===============================================================================================

  /**
   * Build set.
   *
   * @return the set
   */
  public final Set<Data> build() {
    return models;
  }
}
