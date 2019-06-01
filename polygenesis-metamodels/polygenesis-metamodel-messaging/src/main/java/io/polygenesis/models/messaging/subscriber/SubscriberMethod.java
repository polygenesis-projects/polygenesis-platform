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

package io.polygenesis.models.messaging.subscriber;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.assertion.Assertion;
import java.util.Objects;

/**
 * The type Subscriber method.
 *
 * @author Christos Tsakostas
 */
public class SubscriberMethod {

  private Subscriber subscriber;
  private Function function;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Subscriber method.
   *
   * @param subscriber the subscriber
   * @param function the function
   */
  public SubscriberMethod(Subscriber subscriber, Function function) {
    setSubscriber(subscriber);
    setFunction(function);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets subscriber.
   *
   * @return the subscriber
   */
  public Subscriber getSubscriber() {
    return subscriber;
  }

  /**
   * Gets function.
   *
   * @return the function
   */
  public Function getFunction() {
    return function;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets subscriber.
   *
   * @param subscriber the subscriber
   */
  private void setSubscriber(Subscriber subscriber) {
    Assertion.isNotNull(subscriber, "subscriber is required");
    this.subscriber = subscriber;
  }

  /**
   * Sets function.
   *
   * @param function the function
   */
  private void setFunction(Function function) {
    Assertion.isNotNull(function, "function is required");
    this.function = function;
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
    SubscriberMethod that = (SubscriberMethod) o;
    return Objects.equals(subscriber, that.subscriber) && Objects.equals(function, that.function);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriber, function);
  }
}
