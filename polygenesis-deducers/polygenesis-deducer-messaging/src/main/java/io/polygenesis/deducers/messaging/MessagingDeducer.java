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

package io.polygenesis.deducers.messaging;

import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.deducers.messaging.subscriber.SubscriberDeducer;
import io.polygenesis.models.messaging.subscriber.Subscriber;
import io.polygenesis.models.messaging.subscriber.SubscriberMetamodelRepository;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Messaging deducer.
 *
 * @author Christos Tsakostas
 */
public class MessagingDeducer implements Deducer<SubscriberMetamodelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final SubscriberDeducer subscriberDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Messaging deducer.
   *
   * @param rootPackageName the root package name
   * @param subscriberDeducer the subscriber deducer
   */
  public MessagingDeducer(PackageName rootPackageName, SubscriberDeducer subscriberDeducer) {
    this.rootPackageName = rootPackageName;
    this.subscriberDeducer = subscriberDeducer;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets package name.
   *
   * @return the package name
   */
  public PackageName getRootPackageName() {
    return rootPackageName;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public SubscriberMetamodelRepository deduce(
      Set<AbstractionRepository> abstractionRepositories,
      Set<MetamodelRepository> modelRepositories) {

    Set<Subscriber> subscribers = new LinkedHashSet<>();

    CoreRegistry.getAbstractionRepositoryResolver()
        .resolve(abstractionRepositories, ThingRepository.class)
        .getAbstractionItemsByScope(AbstractionScope.api())
        .forEach(thing -> subscribers.addAll(subscriberDeducer.deduce()));

    return new SubscriberMetamodelRepository(subscribers);
  }
}
