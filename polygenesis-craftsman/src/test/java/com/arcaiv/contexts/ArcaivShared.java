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

package com.arcaiv.contexts;

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

public class ArcaivShared {

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

  public static DataObject password(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("value").build().build();

    return new DataObject(
        new VariableName("password"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("password"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject webPageUrl(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("value").build().build();

    return new DataObject(
        new VariableName("webPageUrl"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("webPageUrl"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject storageKey(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("value").build().build();

    return new DataObject(
        new VariableName("storageKey"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("storageKey"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

}
