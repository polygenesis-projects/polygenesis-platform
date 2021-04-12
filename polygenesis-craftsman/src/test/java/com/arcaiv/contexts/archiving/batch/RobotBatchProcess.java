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

package com.arcaiv.contexts.archiving.batch;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Collections;
import java.util.LinkedHashSet;

public class RobotBatchProcess {

  /** @return the thing */
  public static Thing create(Thing demo) {
    Thing RobotBatchProcess =
        ThingBuilder.apiClientBatchProcess("checkIfShouldArchive").createThing();

    RobotBatchProcess.addMetadata(
        new KeyValue("queryFunction", demo.getFunctionByName("fetchVerifiedEnabledDomains")));

    RobotBatchProcess.addMetadata(
        new KeyValue("commandFunction", demo.getFunctionByName("claimArchive")));

    return RobotBatchProcess;
  }
}
