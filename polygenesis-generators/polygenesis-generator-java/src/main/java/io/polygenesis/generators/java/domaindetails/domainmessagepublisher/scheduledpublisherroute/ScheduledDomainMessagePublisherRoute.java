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

package io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisherroute;

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
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisher.ScheduledDomainMessagePublisher;

/**
 * The type Scheduled domain message publisher route.
 *
 * @author Christos Tsakostas
 */
public class ScheduledDomainMessagePublisherRoute extends AbstractNameablePackageable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private ScheduledDomainMessagePublisher scheduledDomainMessagePublisher;
  private Function configure;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Scheduled domain message publisher route.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param scheduledDomainMessagePublisher the scheduled domain message publisher
   */
  public ScheduledDomainMessagePublisherRoute(
      ObjectName objectName,
      PackageName packageName,
      ScheduledDomainMessagePublisher scheduledDomainMessagePublisher) {
    super(objectName, packageName);
    this.scheduledDomainMessagePublisher = scheduledDomainMessagePublisher;
    this.configure = makeConfigureFunction();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets scheduled domain message publisher.
   *
   * @return the scheduled domain message publisher
   */
  public ScheduledDomainMessagePublisher getScheduledDomainMessagePublisher() {
    return scheduledDomainMessagePublisher;
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
        ThingBuilder.domainDetailDomainMessagePublisher("scheduledDomainMessagePublisherRoute")
            .createThing();

    return new Function(
        thing,
        Purpose.reset(),
        new FunctionName("configure"),
        null,
        new DataRepository(),
        Activity.empty(),
        thing.getAbstractionsScopes());
  }
}
