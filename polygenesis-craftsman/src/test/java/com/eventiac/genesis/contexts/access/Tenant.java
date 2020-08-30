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

package com.eventiac.genesis.contexts.access;

import com.oregor.trinity4j.Trinity4jAggregateRoot;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/** @author Christos Tsakostas */
public class Tenant {

  public static Thing create(PackageName rootPackageName, Thing user) {
    Thing tenant =
        ThingBuilder.endToEnd("tenant")
            .setSuperClass(Trinity4jAggregateRoot.create(rootPackageName))
            .createThing(rootPackageName);

    tenant.addFunctions(
        PurposeFunctionBuilder.forThing(tenant, rootPackageName)
            .withFunctionCreate(createData(rootPackageName, user))
            .withFunctionModify(
                "deactivate",
                "",
                new LinkedHashSet<>(
                    Collections.singletonList(
                        DataPrimitive.of(PrimitiveType.STRING, new VariableName("someVar")))))
            .build());

    return tenant;
  }

  private static Set<Data> createData(PackageName rootPackageName, Thing user) {
    return DataBuilder.create()
        .withReferenceToThingById(rootPackageName, user, "ownerUserId")
        .withTextPropertyToValueObject("name", Shared.name(rootPackageName))
        .build()
        .build();
  }
}
