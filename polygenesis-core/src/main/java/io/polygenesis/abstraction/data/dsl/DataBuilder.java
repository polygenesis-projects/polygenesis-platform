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

package io.polygenesis.abstraction.data.dsl;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataArray;
import io.polygenesis.abstraction.data.DataMap;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
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
   * With text property data text builder.
   *
   * @param propertyName the property name
   * @param objectName the object name
   * @param packageName the package name
   * @return the data text builder
   */
  public final DataTextBuilder withTextPropertyToValueObject(
      String propertyName, ObjectName objectName, PackageName packageName) {
    DataTextBuilder dataTextBuilder =
        DataTextBuilder.create(this, propertyName, objectName, packageName);

    this.models.add(dataTextBuilder.getModel());

    return dataTextBuilder;
  }

  /**
   * With big decimal property data big decimal builder.
   *
   * @param propertyName the property name
   * @return the data big decimal builder
   */
  public final DataDecimalBuilder withDecimalProperty(String propertyName) {
    DataDecimalBuilder dataDecimalBuilder = DataDecimalBuilder.create(this, propertyName);

    this.models.add(dataDecimalBuilder.getModel());

    return dataDecimalBuilder;
  }

  /**
   * With integer property data integer builder.
   *
   * @param propertyName the property name
   * @return the data integer builder
   */
  public final DataIntegerBuilder withIntegerProperty(String propertyName) {
    DataIntegerBuilder dataIntegerBuilder = DataIntegerBuilder.create(this, propertyName);

    this.models.add(dataIntegerBuilder.getModel());

    return dataIntegerBuilder;
  }

  /**
   * With long property data long builder.
   *
   * @param propertyName the property name
   * @return the data long builder
   */
  public final DataLongBuilder withLongProperty(String propertyName) {
    DataLongBuilder dataLongBuilder = DataLongBuilder.create(this, propertyName);

    this.models.add(dataLongBuilder.getModel());

    return dataLongBuilder;
  }

  /**
   * With uuid property data uuid builder.
   *
   * @param propertyName the property name
   * @return the data uuid builder
   */
  public final DataUuidBuilder withUuidProperty(String propertyName) {
    DataUuidBuilder dataUuidBuilder = DataUuidBuilder.create(this, propertyName);

    this.models.add(dataUuidBuilder.getModel());

    return dataUuidBuilder;
  }

  /**
   * With date time property data date time builder.
   *
   * @param propertyName the property name
   * @return the data date time builder
   */
  public final DataDateTimeBuilder withDateTimeProperty(String propertyName) {
    DataDateTimeBuilder dataDateTimeBuilder = DataDateTimeBuilder.create(this, propertyName);

    this.models.add(dataDateTimeBuilder.getModel());

    return dataDateTimeBuilder;
  }

  /**
   * With date property data date builder.
   *
   * @param propertyName the property name
   * @return the data date builder
   */
  public final DataDateBuilder withDateProperty(String propertyName) {
    DataDateBuilder dataDateBuilder = DataDateBuilder.create(this, propertyName);

    this.models.add(dataDateBuilder.getModel());

    return dataDateBuilder;
  }

  /**
   * With thing identity data builder.
   *
   * @param data the data
   * @return the data builder
   */
  public final DataBuilder withThingIdentity(Data data) {
    this.models.add(data);
    return this;
  }

  /**
   * With group data data builder.
   *
   * @param dataObject the data group
   * @return the data builder
   */
  public final DataBuilder withGroupData(DataObject dataObject) {
    this.models.add(dataObject);
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

  /**
   * With data primitive data builder.
   *
   * @param dataArray the data array
   * @return the data builder
   */
  public final DataBuilder withDataPrimitive(DataPrimitive dataArray) {
    this.models.add(dataArray);
    return this;
  }

  /**
   * With map data builder.
   *
   * @param dataMap the data map
   * @return the data builder
   */
  public final DataBuilder withMap(DataMap dataMap) {
    this.models.add(dataMap);
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
