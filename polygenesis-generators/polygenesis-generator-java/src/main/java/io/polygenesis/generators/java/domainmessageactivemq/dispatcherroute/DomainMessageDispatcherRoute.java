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

package io.polygenesis.generators.java.domainmessageactivemq.dispatcherroute;

import io.polygenesis.abstraction.data.DataRepository;
import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractNameablePackageable;
import io.polygenesis.core.Generatable;
import io.polygenesis.generators.java.domainmessagesubscriber.dispatcher.DomainMessageDispatcher;

public class DomainMessageDispatcherRoute extends AbstractNameablePackageable
    implements Generatable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private DomainMessageDispatcher domainMessageDispatcher;
  private Function configure;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message dispatcher route.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param domainMessageDispatcher the domain message dispatcher
   */
  public DomainMessageDispatcherRoute(
      ObjectName objectName,
      PackageName packageName,
      DomainMessageDispatcher domainMessageDispatcher) {
    super(objectName, packageName);
    this.domainMessageDispatcher = domainMessageDispatcher;
    this.configure = makeConfigureFunction();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets domain message dispatcher.
   *
   * @return the domain message dispatcher
   */
  public DomainMessageDispatcher getDomainMessageDispatcher() {
    return domainMessageDispatcher;
  }

  /**
   * Gets configure.
   *
   * @return the configure
   */
  public Function getConfigure() {
    return configure;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Function makeConfigureFunction() {
    Thing thing =
        ThingBuilder.domainMessageSubscriber("domainMessageDispatcherRoute").createThing();

    return new Function(
        thing,
        Purpose.reset(),
        FunctionName.ofVerbOnly("configure"),
        null,
        new DataRepository(),
        Activity.empty(),
        thing.getAbstractionsScopes());
  }
}
