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

package com.arcaiv.contexts.archiving.domainservice;

import com.arcaiv.contexts.ArcaivShared;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.FunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.LinkedHashSet;

public class StorageDomainService {

  public static Thing create(Thing archive, PackageName rootPackageName) {
    Thing storageDomainService =
        ThingBuilder.domainService("storageDomainService")
            .setPreferredPackage(rootPackageName.withSubPackage("archive").getText())
            .createThing(rootPackageName);

    storageDomainService.addFunction(
        FunctionBuilder.of(
            storageDomainService,
            "storeRawHtmlContent",
            "",
            Purpose.genericCommand(),
            FunctionRole.userAsSet())
            .addArgument(archive.getAsDataObject(rootPackageName))
            .addArgument(DataPrimitive.of(PrimitiveType.STRING, new VariableName("content")))
            .setReturnValue(ArcaivShared.storageKey(rootPackageName))
            .build());

    storageDomainService.addFunction(
        FunctionBuilder.of(
            storageDomainService,
            "storeDomHtmlContent",
            "",
            Purpose.genericCommand(),
            FunctionRole.userAsSet())
            .addArgument(archive.getAsDataObject(rootPackageName))
            .addArgument(DataPrimitive.of(PrimitiveType.STRING, new VariableName("content")))
            .setReturnValue(ArcaivShared.storageKey(rootPackageName))
            .build());

    storageDomainService.addFunction(
        FunctionBuilder.of(
            storageDomainService,
            "store",
            "JsonContent",
            Purpose.genericCommand(),
            FunctionRole.userAsSet())
            .addArgument(DataPrimitive.of(PrimitiveType.STRING, new VariableName("tenantId")))
            .addArgument(DataPrimitive.of(PrimitiveType.STRING, new VariableName("archiveId")))
            .addArgument(DataPrimitive.of(PrimitiveType.STRING, new VariableName("json")))
            .addArgument(DataPrimitive.of(PrimitiveType.STRING, new VariableName("filename")))
            .setReturnValue(ArcaivShared.storageKey(rootPackageName))
            .build());

    storageDomainService.addFunction(
        FunctionBuilder.of(
            storageDomainService,
            "restoreContent",
            "",
            Purpose.genericCommand(),
            FunctionRole.userAsSet())
            .addArgument(archive.getAsDataObject(rootPackageName))
            .setReturnValue(DataPrimitive.of(PrimitiveType.STRING, new VariableName("content")))
            .build());

    return storageDomainService;
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
