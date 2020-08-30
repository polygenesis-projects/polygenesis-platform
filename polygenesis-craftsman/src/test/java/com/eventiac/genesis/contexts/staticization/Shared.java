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

package com.eventiac.genesis.contexts.staticization;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.Set;

/**
 * The type Shared.
 *
 * @author Christos Tsakostas
 */
public class Shared {

  public static DataObject demoId(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("value").build().build();

    return new DataObject(
        new VariableName("demoId"),
        DataPurpose.thingIdentity(),
        DataValidator.empty(),
        new ObjectName("demoId"),
        rootPackageName.withSubPackage("demo"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject email(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("value").build().build();

    return new DataObject(
        new VariableName("email"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("email"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject title(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("value").build().build();

    return new DataObject(
        new VariableName("value"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("title"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject description(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("value").build().build();

    return new DataObject(
        new VariableName("value"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("description"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject quantity(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withDecimalProperty("value").build().build();

    return new DataObject(
        new VariableName("value"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("quantity"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject unitCost(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withDecimalProperty("value").build().build();

    return new DataObject(
        new VariableName("value"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("unitCost"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject lineTotal(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withDecimalProperty("value").build().build();

    return new DataObject(
        new VariableName("value"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("lineTotal"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject discount(PackageName rootPackageName) {
    Set<Data> data =
        DataBuilder.create()
            .withDecimalProperty("amount")
            .build()
            .withBooleanProperty("percentage")
            .build()
            .build();

    return new DataObject(
        new VariableName("discount"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("discount"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject name(String rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("text").build().build();

    return new DataObject(
        new VariableName("name"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("name"),
        new PackageName(rootPackageName).withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject language(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("iso2code").build().build();

    return new DataObject(
        new VariableName("language"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("language"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject domain(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("value").build().build();

    return new DataObject(
        new VariableName("domain"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("domain"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject domain(PackageName rootPackageName, String variableName) {
    Set<Data> data = DataBuilder.create().withTextProperty("value").build().build();

    return new DataObject(
        new VariableName(variableName),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("domain"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject staticfyRequest(PackageName rootPackageName) {
    Set<Data> data =
        DataBuilder.create()
            .withGroupData(Shared.email(rootPackageName))
            .withTextPropertyToValueObject("originDomain", Shared
                .domain(rootPackageName))
            .build()
            .withTextPropertyToValueObject("destinationDomain", Shared
                .domain(rootPackageName))
            .build()
            .build();

    return new DataObject(
        new VariableName("staticfyRequest"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("staticfyRequest"),
        rootPackageName.withSubPackage("shared.request"),
        data,
        DataSourceType.DEFAULT);
  }
}
