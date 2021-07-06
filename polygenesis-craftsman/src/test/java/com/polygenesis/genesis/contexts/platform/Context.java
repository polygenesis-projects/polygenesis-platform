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

package com.polygenesis.genesis.contexts.platform;

import com.oregor.trinity4j.Trinity4jAggregateRoot;
import com.oregor.trinity4j.Trinity4jShared;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.Set;

public class Context {

  public static Thing create(Thing project, PackageName rootPackageName) {
    Thing context =
        ThingBuilder.endToEnd("context")
            .setMultiTenant(Trinity4jShared.tenantId())
            .setSuperClass(Trinity4jAggregateRoot.create(rootPackageName))
            .createThing(rootPackageName);

    context.addFunctions(
        PurposeFunctionBuilder.forThing(context, rootPackageName)
            .withCrudFunction(createRequestData(project, rootPackageName), FunctionRole.userAsSet())
            .build());

    context.addChild(AggregateRoot.create(context, rootPackageName));

    return context;
  }

  private static Set<Data> createRequestData(Thing project, PackageName rootPackageName) {
    return DataBuilder.create()
        .withReferenceToThingById(rootPackageName, project, "projectId")
        .withTextPropertyToValueObject("contextName", contextName(rootPackageName))
        .build()
        .build();
  }

  public static DataObject contextName(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("text").build().build();

    return new DataObject(
        new VariableName("contextName"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("contextName"),
        rootPackageName.withSubPackage("context"),
        data,
        DataSourceType.DEFAULT);
  }
}
