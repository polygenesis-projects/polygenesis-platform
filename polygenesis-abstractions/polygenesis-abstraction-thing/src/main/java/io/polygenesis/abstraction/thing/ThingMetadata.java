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

package io.polygenesis.abstraction.thing;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.commons.assertion.Assertion;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Container of information needed for various cases of things definitions.
 *
 * @author Christos Tsakostas
 */
public class ThingMetadata {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  /** Used for subscribers to messages. */
  private Set<Data> messageData;

  /** The thing that is affected by an incoming message. */
  private Thing relatedThing;

  private Function queryFunction;

  private Function commandFunction;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Empty thing metadata.
   *
   * @return the thing metadata
   */
  public static ThingMetadata empty() {
    return new ThingMetadata(new LinkedHashSet<>(), null, null, null);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Thing metadata.
   *
   * @param messageData the message data
   * @param relatedThing the related thing
   * @param queryFunction the query function
   * @param commandFunction the command function
   */
  public ThingMetadata(
      Set<Data> messageData, Thing relatedThing, Function queryFunction, Function commandFunction) {
    setMessageData(messageData);

    if (relatedThing != null) {
      setRelatedThing(relatedThing);
    }

    if (queryFunction != null) {
      setQueryFunction(queryFunction);
    }

    if (commandFunction != null) {
      setCommandFunction(commandFunction);
    }
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets message data.
   *
   * @return the message data
   */
  public Set<Data> getMessageData() {
    return messageData;
  }

  /**
   * Gets related thing.
   *
   * @return the related thing
   */
  public Thing getRelatedThing() {
    return relatedThing;
  }

  /**
   * Gets query function.
   *
   * @return the query function
   */
  public Function getQueryFunction() {
    return queryFunction;
  }

  /**
   * Gets command function.
   *
   * @return the command function
   */
  public Function getCommandFunction() {
    return commandFunction;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets message data.
   *
   * @param messageData the message data
   */
  private void setMessageData(Set<Data> messageData) {
    Assertion.isNotNull(messageData, "messageData is required");
    this.messageData = messageData;
  }

  /**
   * Sets related thing.
   *
   * @param relatedThing the related thing
   */
  private void setRelatedThing(Thing relatedThing) {
    Assertion.isNotNull(relatedThing, "relatedThing is required");
    this.relatedThing = relatedThing;
  }

  /**
   * Sets query function.
   *
   * @param queryFunction the query function
   */
  private void setQueryFunction(Function queryFunction) {
    Assertion.isNotNull(queryFunction, "queryFunction is required");
    this.queryFunction = queryFunction;
  }

  /**
   * Sets command function.
   *
   * @param commandFunction the command function
   */
  private void setCommandFunction(Function commandFunction) {
    Assertion.isNotNull(commandFunction, "commandFunction is required");
    this.commandFunction = commandFunction;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ThingMetadata that = (ThingMetadata) o;
    return Objects.equals(messageData, that.messageData)
        && Objects.equals(relatedThing, that.relatedThing)
        && Objects.equals(queryFunction, that.queryFunction)
        && Objects.equals(commandFunction, that.commandFunction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(messageData, relatedThing, queryFunction, commandFunction);
  }
}
