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
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Metamodel;
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

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Subscriber.
   *
   * @param packageName the package name
   * @param name the name
   * @param messageData the message data
   */
  public Subscriber(PackageName packageName, Name name, Set<Data> messageData) {
    setPackageName(packageName);
    setName(name);
    setMessageData(messageData);
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
  public void setName(Name name) {
    Assertion.isNotNull(name, "name is required");
    this.name = name;
  }

  /**
   * Sets message data.
   *
   * @param messageData the message data
   */
  public void setMessageData(Set<Data> messageData) {
    Assertion.isNotNull(messageData, "messageData is required");
    this.messageData = messageData;
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
        && Objects.equals(messageData, that.messageData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(packageName, name, messageData);
  }
}
