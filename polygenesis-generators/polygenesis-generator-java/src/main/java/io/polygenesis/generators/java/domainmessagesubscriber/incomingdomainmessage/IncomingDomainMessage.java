/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LTD
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

package io.polygenesis.generators.java.domainmessagesubscriber.incomingdomainmessage;

import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.AbstractNameablePackageable;
import java.util.LinkedHashSet;

/**
 * The type Incoming domain message.
 *
 * @author Christos Tsakostas
 */
public class IncomingDomainMessage extends AbstractNameablePackageable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Function getMessageId;
  private Function getRootId;
  private Function getMessageBody;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Incoming domain message.
   *
   * @param objectName the object name
   * @param packageName the package name
   */
  public IncomingDomainMessage(ObjectName objectName, PackageName packageName) {
    super(objectName, packageName);
    this.getMessageId = makeGetMessageId();
    this.getRootId = makeGetRootId();
    this.getMessageBody = makeGetMessageBody();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets get message id.
   *
   * @return the get message id
   */
  public Function getGetMessageId() {
    return getMessageId;
  }

  /**
   * Gets get root id.
   *
   * @return the get root id
   */
  public Function getGetRootId() {
    return getRootId;
  }

  /**
   * Gets get message body.
   *
   * @return the get message body
   */
  public Function getGetMessageBody() {
    return getMessageBody;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Function makeGetMessageId() {
    Thing thing = ThingBuilder.apiClientDomainMessage("incomingDomainMessage").createThing();

    return new Function(
        thing,
        Purpose.incomingDomainMessageGetMessageId(),
        new FunctionName("getMessageId"),
        DataPrimitive.of(PrimitiveType.STRING, VariableName.response()),
        new LinkedHashSet<>(),
        Activity.empty(),
        thing.getAbstractionsScopes());
  }

  private Function makeGetRootId() {
    Thing thing = ThingBuilder.apiClientDomainMessage("incomingDomainMessage").createThing();

    return new Function(
        thing,
        Purpose.incomingDomainMessageGetMessageId(),
        new FunctionName("getRootId"),
        DataPrimitive.of(PrimitiveType.STRING, VariableName.response()),
        new LinkedHashSet<>(),
        Activity.empty(),
        thing.getAbstractionsScopes());
  }

  private Function makeGetMessageBody() {
    Thing thing = ThingBuilder.apiClientDomainMessage("incomingDomainMessage").createThing();

    return new Function(
        thing,
        Purpose.incomingDomainMessageGetMessageId(),
        new FunctionName("getMessageBody"),
        DataPrimitive.of(PrimitiveType.STRING, VariableName.response()),
        new LinkedHashSet<>(),
        Activity.empty(),
        thing.getAbstractionsScopes());
  }
}
