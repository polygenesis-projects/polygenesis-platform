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

package com.arheio.contexts.archiving;

import com.oregor.trinity4j.Trinity4jShared;
import com.oregor.trinity4j.Trinity4jTenantAggregateRoot;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.LinkedHashSet;
import java.util.Set;

public class Workspace {

  public static Thing create(PackageName rootPackageName) {
    Thing workspace =
        ThingBuilder.endToEnd("workspace")
            .setSuperClass(Trinity4jTenantAggregateRoot.create(rootPackageName))
            .setMultiTenant(Trinity4jShared.tenantId())
            .createThing(rootPackageName);

    workspace.addFunctions(
        PurposeFunctionBuilder.forThing(workspace, rootPackageName)
            .withCrudFunction(createData(rootPackageName), FunctionRole.userAsSet())
            .withFunctionModifyNoReturnValue(
                "verifyDomain", "", new LinkedHashSet<>(), FunctionRole.userAsSet())
            .withFunctionFetchPagedCollection(
                "fetch",
                "unverifiedDomains",
                fetchOneOrPagedCollectionData(rootPackageName),
                FunctionRole.userAsSet())
            .build());

    //    workspace.addChild(InvoiceItem.create(workspace, rootPackageName));

    return workspace;
  }

  private static Set<Data> createData(PackageName rootPackageName) {
    return DataBuilder.create().withGroupData(Shared.domain(rootPackageName, "domain")).build();
  }

  private static Set<Data> fetchOneOrPagedCollectionData(PackageName rootPackageName) {
    return DataBuilder.create().withGroupData(Shared.domain(rootPackageName, "domain")).build();
  }
}
