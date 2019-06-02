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

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Metamodel;
import io.polygenesis.models.api.ServiceMethod;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Subscriber.
 *
 * @author Christos Tsakostas
 */
public class Subscriber implements Metamodel {

  private PackageName packageName;
  private Name name;
  private Set<Data> messageData;
  private Thing relatedThing;
  private ServiceMethod commandServiceMethod;
  private ServiceMethod queryServiceMethod;
  private Set<SubscriberMethod> subscriberMethods;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Subscriber.
   *
   * @param packageName the package name
   * @param name the name
   * @param messageData the message data
   * @param relatedThing the related thing
   * @param commandServiceMethod the command service method
   * @param queryServiceMethod the query service method
   */
  public Subscriber(
      PackageName packageName,
      Name name,
      Set<Data> messageData,
      Thing relatedThing,
      ServiceMethod commandServiceMethod,
      ServiceMethod queryServiceMethod) {
    setPackageName(packageName);
    setName(name);
    setMessageData(messageData);
    setRelatedThing(relatedThing);

    if (commandServiceMethod != null) {
      setCommandServiceMethod(commandServiceMethod);
    }

    if (queryServiceMethod != null) {
      setQueryServiceMethod(queryServiceMethod);
    }

    setSubscriberMethods(new LinkedHashSet<>());
  }

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  public void appendSubscriberMethod(Function function) {
    subscriberMethods.add(
        new SubscriberMethod(this, function, commandServiceMethod, queryServiceMethod));
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return new ObjectName(getName().getText());
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets package name.
   *
   * @return the package name
   */
  public PackageName getPackageName() {
    return packageName;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public Name getName() {
    return name;
  }

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

  /**
   * Gets subscriber methods.
   *
   * @return the subscriber methods
   */
  public Set<SubscriberMethod> getSubscriberMethods() {
    return subscriberMethods;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets package name.
   *
   * @param packageName the package name
   */
  private void setPackageName(PackageName packageName) {
    Assertion.isNotNull(packageName, "packageName is required");
    this.packageName = packageName;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(Name name) {
    Assertion.isNotNull(name, "name is required");
    this.name = name;
  }

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

  /**
   * Sets subscriber methods.
   *
   * @param subscriberMethods the subscriber methods
   */
  private void setSubscriberMethods(Set<SubscriberMethod> subscriberMethods) {
    Assertion.isNotNull(subscriberMethods, "subscriberMethods is required");
    this.subscriberMethods = subscriberMethods;
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
    Subscriber that = (Subscriber) o;
    return Objects.equals(packageName, that.packageName)
        && Objects.equals(name, that.name)
        && Objects.equals(messageData, that.messageData)
        && Objects.equals(relatedThing, that.relatedThing)
        && Objects.equals(queryServiceMethod, that.queryServiceMethod)
        && Objects.equals(commandServiceMethod, that.commandServiceMethod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        packageName, name, messageData, relatedThing, queryServiceMethod, commandServiceMethod);
  }
}
