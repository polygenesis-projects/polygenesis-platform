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

package com.eventiac.genesis.contexts.staticization.domainservice;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.FunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.LinkedHashSet;

public class WebHostingDomainService {

  public static Thing create(PackageName rootPackageName) {
    Thing webHostingDomainService =
        ThingBuilder.domainService("webHostingDomainService")
            .setPreferredPackage(rootPackageName.withSubPackage("shared").getText())
            .createThing(rootPackageName);

    webHostingDomainService.addFunction(
        FunctionBuilder.of(webHostingDomainService, "prepare", "", Purpose.genericCommand())
            .addArgument(argumentFilePath())
            .addArgument(argumentByteArrayOutputStream())
            .build());

    return webHostingDomainService;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private static Data argumentFilePath() {
    return DataPrimitive.of(PrimitiveType.STRING, new VariableName("filePath"));
  }

  private static Data argumentByteArrayOutputStream() {
    return new DataObject(
        new VariableName("outputStream"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("ByteArrayOutputStream"),
        new PackageName("java.io"),
        new LinkedHashSet<>(),
        DataSourceType.EXTERNALLY_PROVIDED);
  }
}
