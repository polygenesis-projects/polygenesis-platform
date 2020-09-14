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

package io.polygenesis.generators.java.batchprocessactivemq.publisher;

import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataRepository;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.AbstractNameablePackageable;
import io.polygenesis.core.Generatable;
import java.util.Collections;
import java.util.LinkedHashSet;

public class BatchProcessMessagePublisher extends AbstractNameablePackageable
    implements Generatable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================
  private Function send;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process message publisher.
   *
   * @param objectName the object name
   * @param packageName the package name
   */
  public BatchProcessMessagePublisher(ObjectName objectName, PackageName packageName) {
    super(objectName, packageName);
    this.send = makeSendFunction();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets send.
   *
   * @return the send
   */
  public Function getSend() {
    return send;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Function makeSendFunction() {
    Thing thing = ThingBuilder.apiClientBatchProcess("batchProcessMessagePublisher").createThing();

    return new Function(
        thing,
        Purpose.reset(),
        FunctionName.ofVerbOnly("send"),
        null,
        new DataRepository(DataPrimitive.of(PrimitiveType.STRING, new VariableName("message"))),
        Activity.empty(),
        thing.getAbstractionsScopes(),
        new LinkedHashSet<>(Collections.singleton(FunctionRole.system())));
  }
}
