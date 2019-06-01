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

package io.polygenesis.abstraction.thing.dsl;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingMetadata;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.core.AbstractionScope;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Message thing builder.
 *
 * @author Christos Tsakostas
 */
public class MessageThingBuilder extends AbstractThingBuilder<MessageThingBuilder> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<Data> messageData;
  private Thing relatedThing;
  private Function commandFunction;
  private Function queryFunction;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Subscriber message thing builder.
   *
   * @return the message thing builder
   */
  public static MessageThingBuilder subscriber() {
    return new MessageThingBuilder(
        new LinkedHashSet<>(
            Arrays.asList(AbstractionScope.api(), AbstractionScope.apiClientMessaging())));
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private MessageThingBuilder(Set<AbstractionScope> abstractionScopes) {
    super(MessageThingBuilder.class, abstractionScopes);
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets message data.
   *
   * @param messageData the message data
   * @return the message data
   */
  public MessageThingBuilder setMessageData(Set<Data> messageData) {
    Assertion.isNotNull(messageData, "messageData is required");
    this.messageData = messageData;
    return this;
  }

  /**
   * Sets related thing.
   *
   * @param relatedThing the related thing
   * @return the related thing
   */
  public MessageThingBuilder setRelatedThing(Thing relatedThing) {
    Assertion.isNotNull(relatedThing, "relatedThing is required");
    this.relatedThing = relatedThing;
    return this;
  }

  /**
   * Sets command function.
   *
   * @param commandFunction the command function
   * @return the command function
   */
  public MessageThingBuilder setCommandFunction(Function commandFunction) {
    Assertion.isNotNull(commandFunction, "commandFunction is required");
    this.commandFunction = commandFunction;
    return this;
  }

  /**
   * Sets query function.
   *
   * @param queryFunction the query function
   * @return the query function
   */
  public MessageThingBuilder setQueryFunction(Function queryFunction) {
    Assertion.isNotNull(queryFunction, "queryFunction is required");
    this.queryFunction = queryFunction;
    return this;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Thing createThing() {
    ThingMetadata thingMetadata =
        ThingMetadataBuilder.start()
            .setMessageData(messageData)
            .setRelatedThing(relatedThing)
            .setCommandFunction(commandFunction)
            .setQueryFunction(queryFunction)
            .build();

    setThingMetadata(thingMetadata);
    return super.createThing();
  }
}
