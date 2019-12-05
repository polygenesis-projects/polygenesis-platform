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

package io.polygenesis.generators.java.domainmessagesubscriber.subscriber;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataArray;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.messaging.subscriber.SubscriberMetamodel;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Domain message subscriber.
 *
 * @author Christos Tsakostas
 */
public class DomainMessageSubscriber extends SubscriberMetamodel {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private ContextName contextName;
  private PackageName rootPackageName;
  private Function process;
  private Function getSupportedMessageTypes;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message subscriber.
   *
   * @param packageName the package name
   * @param name the name
   * @param messageData the message data
   * @param supportedMessageTypes the supported message types
   * @param relatedThing the related thing
   * @param ensureExistenceServiceMethod the ensure existence service method
   * @param commandServiceMethod the command service method
   * @param contextName the context name
   * @param rootPackageName the root package name
   */
  public DomainMessageSubscriber(
      PackageName packageName,
      Name name,
      Set<Data> messageData,
      Set<String> supportedMessageTypes,
      Thing relatedThing,
      ServiceMethod ensureExistenceServiceMethod,
      ServiceMethod commandServiceMethod,
      ContextName contextName,
      PackageName rootPackageName) {
    super(
        packageName,
        name,
        messageData,
        supportedMessageTypes,
        relatedThing,
        ensureExistenceServiceMethod,
        commandServiceMethod);
    this.contextName = contextName;
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.process = makeProcess();
    this.getSupportedMessageTypes = makeGetSupportedMessageTypes();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets context name.
   *
   * @return the context name
   */
  public ContextName getContextName() {
    return contextName;
  }

  /**
   * Gets root package name.
   *
   * @return the root package name
   */
  public PackageName getRootPackageName() {
    return rootPackageName;
  }

  /**
   * Gets process.
   *
   * @return the process
   */
  public Function getProcess() {
    return process;
  }

  /**
   * Gets get supported message types.
   *
   * @return the get supported message types
   */
  public Function getGetSupportedMessageTypes() {
    return getSupportedMessageTypes;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Function makeProcess() {
    Set<KeyValue> keyValues = new LinkedHashSet<>();

    if (getMessageData() != null) {
      keyValues.add(new KeyValue("messageData", getMessageData()));
    }

    if (getEnsureExistenceServiceMethod() != null) {
      keyValues.add(
          new KeyValue("ensureExistenceServiceMethod", getEnsureExistenceServiceMethod()));
    }

    if (getCommandServiceMethod() != null) {
      keyValues.add(new KeyValue("commandServiceMethod", getCommandServiceMethod()));
    }

    Thing thing = ThingBuilder.apiClientDomainMessage("domainMessageSubscriber").createThing();

    return new Function(
        thing,
        Purpose.domainMessageSubscriberProcess(),
        new FunctionName("process"),
        null,
        new LinkedHashSet<>(
            Arrays.asList(
                new DataObject(
                    new ObjectName(String.format("%sIncomingDomainMessage", contextName.getText())),
                    getRootPackageName(),
                    new VariableName("incomingDomainMessage")),
                new DataObject(
                    new ObjectName("JsonNode"),
                    new PackageName("com.fasterxml.jackson.databind"),
                    new VariableName("jsonNodeBody")))),
        Activity.keyValues(keyValues),
        thing.getAbstractionsScopes());
  }

  private Function makeGetSupportedMessageTypes() {
    Thing thing = ThingBuilder.apiClientDomainMessage("domainMessageSubscriber").createThing();

    Set<KeyValue> keyValues = new LinkedHashSet<>();

    keyValues.add(new KeyValue("supportedMessageTypes", getSupportedMessageTypes()));

    return new Function(
        thing,
        Purpose.domainMessageSubscriberSupportedTypes(),
        new FunctionName("getSupportedMessageTypes"),
        DataArray.of(DataPrimitive.of(PrimitiveType.STRING, VariableName.response())),
        new LinkedHashSet<>(),
        Activity.keyValues(keyValues),
        thing.getAbstractionsScopes());
  }
}
