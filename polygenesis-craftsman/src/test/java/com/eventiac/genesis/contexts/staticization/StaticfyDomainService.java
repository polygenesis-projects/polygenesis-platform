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

package com.eventiac.genesis.contexts.staticization;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.FunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.Set;

public class StaticfyDomainService {

  public static Thing create(PackageName rootPackageName) {
    Thing staticfyDomainService =
        ThingBuilder.domainService("staticfyDomainService")
            .setPreferredPackage("com.staticfy.staticization.shared")
            .createThing(rootPackageName);

    staticfyDomainService.addFunction(
        FunctionBuilder.of(staticfyDomainService, "execute", "", Purpose.genericCommand())
            .addArgument(argument(rootPackageName))
            .setReturnValue(returnValue(rootPackageName))
            .build());

    return staticfyDomainService;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private static Data argument(PackageName rootPackageName) {
    Set<Data> data =
        DataBuilder.create()
            .withTextProperty("scheme")
            .build()
            .withTextProperty("server")
            .build()
            .withTextProperty("port")
            .build()
            .build();

    return new DataObject(
        new VariableName("request"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("staticfyRequest"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }

  private static Data returnValue(PackageName rootPackageName) {
    Set<Data> data =
        DataBuilder.create()
            .withTextProperty("scheme")
            .build()
            .withTextProperty("server")
            .build()
            .withTextProperty("port")
            .build()
            .build();

    return new DataObject(
        new VariableName("response"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("staticfyResponse"),
        rootPackageName.withSubPackage("shared"),
        data,
        DataSourceType.DEFAULT);
  }
}
