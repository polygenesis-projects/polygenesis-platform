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

package com.toptal.genesis.contexts.team;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.AbstractionScope;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class TeamShared {

  public static DataObject monetaryValue(String variableName, PackageName rootPackageName) {
    Set<Data> data =
        DataBuilder.create()
            .withDecimalProperty("value")
            .build()
            .withTextProperty("currency")
            .build()
            .build();

    return new DataObject(
        new VariableName(variableName),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("monetaryValue"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  public static Set<AbstractionScope> abstractionScopeApi() {
    return new LinkedHashSet<>(
        Arrays.asList(
            AbstractionScope.api(),
            AbstractionScope.apiDetail(),
            AbstractionScope.apiClientRest()));
  }

  public static Set<AbstractionScope> abstractionScopeDomain() {
    return new LinkedHashSet<>(Arrays.asList(AbstractionScope.domainAggregateRoot()));
  }
}
