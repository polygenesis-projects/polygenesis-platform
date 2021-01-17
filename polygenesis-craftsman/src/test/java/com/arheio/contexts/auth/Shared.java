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

package com.arheio.contexts.auth;

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

  public static DataObject name(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("value").build().build();

    return new DataObject(
        new VariableName("name"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("name"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject emailAddress(String rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("value").build().build();

    return new DataObject(
        new VariableName("emailAddress"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("emailAddress"),
        new PackageName(rootPackageName).withSubPackage("identity"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject plainPassword(String rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("value").build().build();

    return new DataObject(
        new VariableName("plainPassword"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("plainPassword"),
        new PackageName(rootPackageName).withSubPackage("identity"),
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
        rootPackageName.withSubPackage("identity"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject confirmationCode(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("value").build().build();

    return new DataObject(
        new VariableName("confirmationCode"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("confirmationCode"),
        rootPackageName.withSubPackage("identity"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject expiresOn(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withDateTimeProperty("value").build().build();

    return new DataObject(
        new VariableName("expiresOn"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("expiresOn"),
        rootPackageName.withSubPackage("identity"),
        data,
        DataSourceType.DEFAULT);
  }
}
