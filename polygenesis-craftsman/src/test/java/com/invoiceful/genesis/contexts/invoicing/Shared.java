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

package com.invoiceful.genesis.contexts.invoicing;

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

public class Shared {

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

  /**
   * Tax details data group.
   *
   * @param rootPackageName the root package name
   * @return the data group
   */
  public static DataObject taxDetails(String rootPackageName) {
    Set<Data> data =
        DataBuilder.create()
            .withTextProperty("taxId")
            .build()
            .withTextProperty("taxOffice")
            .build()
            .withTextProperty("taxCountryCode")
            .build()
            .build();

    return new DataObject(
        new VariableName("taxDetails"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("taxDetails"),
        new PackageName(rootPackageName).withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }
}
