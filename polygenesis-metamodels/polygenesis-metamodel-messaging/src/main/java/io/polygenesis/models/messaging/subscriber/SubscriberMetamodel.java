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
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Metamodel;
import io.polygenesis.models.api.ServiceMethod;
import java.util.Objects;
import java.util.Set;

/**
 * The type Subscriber.
 *
 * @author Christos Tsakostas
 */
public class SubscriberMetamodel implements Metamodel {

  private PackageName packageName;
  private Name name;
  private Set<Data> messageData;
  private Set<String> supportedMessageTypes;
  private Thing relatedThing;
  private ServiceMethod ensureExistenceServiceMethod;
  private ServiceMethod commandServiceMethod;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Subscriber metamodel.
   *
   * @param packageName the package name
   * @param name the name
   * @param messageData the message data
   * @param supportedMessageTypes the supported message types
   * @param relatedThing the related thing
   * @param ensureExistenceServiceMethod the ensure existence service method
   * @param commandServiceMethod the command service method
   */
  public SubscriberMetamodel(
      PackageName packageName,
      Name name,
      Set<Data> messageData,
      Set<String> supportedMessageTypes,
      Thing relatedThing,
      ServiceMethod ensureExistenceServiceMethod,
      ServiceMethod commandServiceMethod) {
    setPackageName(packageName);
    setName(name);
    setMessageData(messageData);
    setSupportedMessageTypes(supportedMessageTypes);
    setRelatedThing(relatedThing);

    if(ensureExistenceServiceMethod != null) {
      setEnsureExistenceServiceMethod(ensureExistenceServiceMethod);
    }

    if(commandServiceMethod != null) {
      setCommandServiceMethod(commandServiceMethod);
    }
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
   * Gets supported message types.
   *
   * @return the supported message types
   */
  public Set<String> getSupportedMessageTypes() {
    return supportedMessageTypes;
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
   * Gets ensure existence service method.
   *
   * @return the ensure existence service method
   */
  public ServiceMethod getEnsureExistenceServiceMethod() {
    return ensureExistenceServiceMethod;
  }

  /**
   * Gets command service method.
   *
   * @return the command service method
   */
  public ServiceMethod getCommandServiceMethod() {
    return commandServiceMethod;
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
   * Sets supported message types.
   *
   * @param supportedMessageTypes the supported message types
   */
  private void setSupportedMessageTypes(Set<String> supportedMessageTypes) {
    Assertion.isNotNull(supportedMessageTypes, "supportedMessageTypes is required");
    this.supportedMessageTypes = supportedMessageTypes;
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
   * Sets ensure existence service method.
   *
   * @param ensureExistenceServiceMethod the query service method
   */
  private void setEnsureExistenceServiceMethod(ServiceMethod ensureExistenceServiceMethod) {
    Assertion.isNotNull(ensureExistenceServiceMethod, "ensureExistenceServiceMethod is required");
    this.ensureExistenceServiceMethod = ensureExistenceServiceMethod;
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
    SubscriberMetamodel that = (SubscriberMetamodel) o;
    return Objects.equals(packageName, that.packageName)
        && Objects.equals(name, that.name)
        && Objects.equals(messageData, that.messageData)
        && Objects.equals(supportedMessageTypes, that.supportedMessageTypes)
        && Objects.equals(relatedThing, that.relatedThing)
        && Objects.equals(ensureExistenceServiceMethod, that.ensureExistenceServiceMethod)
        && Objects.equals(commandServiceMethod, that.commandServiceMethod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        packageName,
        name,
        messageData,
        supportedMessageTypes,
        relatedThing,
        ensureExistenceServiceMethod,
        commandServiceMethod);
  }
}
