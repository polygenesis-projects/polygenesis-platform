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
import io.polygenesis.abstraction.thing.FunctionProvider;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.models.api.ServiceMethod;
import java.util.Objects;

/**
 * The type Subscriber method.
 *
 * @author Christos Tsakostas
 */
public class SubscriberMethod implements FunctionProvider {

  private SubscriberMetamodel subscriberMetamodel;
  private Function function;
  private ServiceMethod commandServiceMethod;
  private ServiceMethod queryServiceMethod;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Subscriber method.
   *
   * @param subscriberMetamodel the subscriber
   * @param function the function
   * @param commandServiceMethod the command service method
   * @param queryServiceMethod the query service method
   */
  public SubscriberMethod(
      SubscriberMetamodel subscriberMetamodel,
      Function function,
      ServiceMethod commandServiceMethod,
      ServiceMethod queryServiceMethod) {
    setSubscriberMetamodel(subscriberMetamodel);
    setFunction(function);
    setCommandServiceMethod(commandServiceMethod);
    setQueryServiceMethod(queryServiceMethod);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets subscriber.
   *
   * @return the subscriber
   */
  public SubscriberMetamodel getSubscriberMetamodel() {
    return subscriberMetamodel;
  }

  /**
   * Gets function.
   *
   * @return the function
   */
  public Function getFunction() {
    return function;
  }

  /**
   * Gets command service method.
   *
   * @return the command service method
   */
  public ServiceMethod getCommandServiceMethod() {
    return commandServiceMethod;
  }

  /**
   * Gets query service method.
   *
   * @return the query service method
   */
  public ServiceMethod getQueryServiceMethod() {
    return queryServiceMethod;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets subscriber.
   *
   * @param subscriberMetamodel the subscriber
   */
  private void setSubscriberMetamodel(SubscriberMetamodel subscriberMetamodel) {
    Assertion.isNotNull(subscriberMetamodel, "subscriber is required");
    this.subscriberMetamodel = subscriberMetamodel;
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

  /**
   * Sets command service method.
   *
   * @param commandServiceMethod the command service method
   */
  private void setCommandServiceMethod(ServiceMethod commandServiceMethod) {
    Assertion.isNotNull(commandServiceMethod, "commandServiceMethod is required");
    this.commandServiceMethod = commandServiceMethod;
  }

  /**
   * Sets query service method.
   *
   * @param queryServiceMethod the query service method
   */
  private void setQueryServiceMethod(ServiceMethod queryServiceMethod) {
    Assertion.isNotNull(queryServiceMethod, "queryServiceMethod is required");
    this.queryServiceMethod = queryServiceMethod;
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
    return Objects.equals(subscriberMetamodel, that.subscriberMetamodel)
        && Objects.equals(function, that.function)
        && Objects.equals(commandServiceMethod, that.commandServiceMethod)
        && Objects.equals(queryServiceMethod, that.queryServiceMethod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriberMetamodel, function, commandServiceMethod, queryServiceMethod);
  }
}
