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

package io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataRepository;
import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.AbstractNameablePackageable;

/**
 * The type Domain message publisher.
 *
 * @author Christos Tsakostas
 */
public class DomainMessagePublisher extends AbstractNameablePackageable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================
  private ContextName contextName;
  private Function send;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message publisher.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param contextName the context name
   */
  public DomainMessagePublisher(
      ObjectName objectName, PackageName packageName, ContextName contextName) {
    super(objectName, packageName);
    this.contextName = contextName;
    this.send = makeSendFunction();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets send.
   *
   * @return the send
   */
  public Function getSend() {
    return send;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Function makeSendFunction() {
    Thing thing =
        ThingBuilder.domainDetailDomainMessagePublisher("DomainMessagePublisher").createThing();

    DataRepository arguments = new DataRepository();
    arguments.addData(
        new DataObject(
            new ObjectName(
                String.format(
                    "%sDomainMessagePublishDto",
                    TextConverter.toUpperCamel(contextName.getText()))),
            getPackageName(),
            new VariableName("domainMessagePublishDto")));

    return new Function(
        thing,
        Purpose.reset(),
        new FunctionName("send"),
        null,
        arguments,
        Activity.empty(),
        thing.getAbstractionsScopes());
  }
}
