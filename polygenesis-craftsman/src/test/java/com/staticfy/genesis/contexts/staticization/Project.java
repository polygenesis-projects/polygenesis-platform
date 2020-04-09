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

package com.staticfy.genesis.contexts.staticization;

import com.oregor.trinity4j.Trinity4jShared;
import com.oregor.trinity4j.Trinity4jTenantAggregateRoot;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Set;

/** @author Christos Tsakostas */
public class Project {

  public static Thing create(Thing workspace, PackageName rootPackageName) {
    Thing project =
        ThingBuilder.endToEnd("project")
            .setSuperClass(Trinity4jTenantAggregateRoot.create(rootPackageName))
            .setMultiTenant(Trinity4jShared.tenantId())
            .createThing(rootPackageName);

    project.addFunctions(
        PurposeFunctionBuilder.forThing(project, rootPackageName)
            .withCrudFunction(createData(workspace, rootPackageName))
            .build());

    //    project.addChild(InvoiceItem.create(project, rootPackageName));

    return project;
  }

  private static Set<Data> createData(Thing workspace, PackageName rootPackageName) {
    return DataBuilder.create()
        .withReferenceToThingById(rootPackageName, workspace, "workspaceId")
        .withGroupData(Shared.domain(rootPackageName, "domain"))
        .build();
  }

  private static Set<Data> fetchOneOrPagedCollectionData(PackageName rootPackageName) {
    return DataBuilder.create().withGroupData(Shared.domain(rootPackageName, "domain")).build();
  }
}
