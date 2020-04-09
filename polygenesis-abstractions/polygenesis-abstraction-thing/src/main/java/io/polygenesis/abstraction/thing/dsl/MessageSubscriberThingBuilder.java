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

package io.polygenesis.abstraction.thing.dsl;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.core.AbstractionScope;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class MessageSubscriberThingBuilder
    extends AbstractThingBuilder<MessageSubscriberThingBuilder> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Message subscriber thing builder.
   *
   * @param abstractionScopes the abstraction scopes
   */
  private MessageSubscriberThingBuilder(String thingName, Set<AbstractionScope> abstractionScopes) {
    super(MessageSubscriberThingBuilder.class, thingName, abstractionScopes);
  }

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Of message subscriber thing builder.
   *
   * @param thingName the thing name
   * @return the message subscriber thing builder
   */
  public static MessageSubscriberThingBuilder of(String thingName) {
    return new MessageSubscriberThingBuilder(
        thingName, new LinkedHashSet<>(Arrays.asList(AbstractionScope.apiClientMessaging())));
  }

  // ===============================================================================================
  // BUILD FUNCTIONS
  // ===============================================================================================

  /**
   * Sets related thing.
   *
   * @param relatedThing the related thing
   * @return the related thing
   */
  public MessageSubscriberThingBuilder setRelatedThing(Thing relatedThing) {
    addMetadata(new KeyValue("relatedThing", relatedThing));
    return this;
  }

  /**
   * Sets supported message types.
   *
   * @param supportedMessageTypes the supported message types
   * @return the supported message types
   */
  public MessageSubscriberThingBuilder setSupportedMessageTypes(Set<String> supportedMessageTypes) {
    addMetadata(new KeyValue("supportedMessageTypes", supportedMessageTypes));
    return this;
  }

  /**
   * Sets message data.
   *
   * @param messageData the message data
   * @return the message data
   */
  public MessageSubscriberThingBuilder setMessageData(Set<Data> messageData) {
    addMetadata(new KeyValue("messageData", messageData));
    return this;
  }

  /**
   * Sets ensure existence.
   *
   * @param ensureExistence the ensure existence
   * @return the ensure existence
   */
  public MessageSubscriberThingBuilder setEnsureExistence(Function ensureExistence) {
    addMetadata(new KeyValue("ensureExistence", ensureExistence));
    return this;
  }

  /**
   * Sets process.
   *
   * @param process the process
   * @return the process
   */
  public MessageSubscriberThingBuilder setProcess(Function process) {
    addMetadata(new KeyValue("process", process));
    return this;
  }
}
