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

package io.polygenesis.deducers.messaging.subscriber;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.messaging.subscriber.Subscriber;
import io.polygenesis.models.messaging.subscriber.SubscriberMetamodelRepository;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Subscriber deducer.
 *
 * @author Christos Tsakostas
 */
public class SubscriberDeducer implements Deducer<SubscriberMetamodelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Subscriber deducer.
   *
   * @param rootPackageName the root package name
   */
  public SubscriberDeducer(PackageName rootPackageName) {
    this.rootPackageName = rootPackageName;
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
        .getAbstractionItemsByScope(AbstractionScope.apiClientMessaging())
        .forEach(thing -> subscribers.add(deduceSubscriberFromThing(thing, modelRepositories)));

    return new SubscriberMetamodelRepository(subscribers);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Deduce subscriber from thing subscriber.
   *
   * @param thing the thing
   * @return the subscriber
   */
  @SuppressWarnings("rawtypes")
  private Subscriber deduceSubscriberFromThing(
      Thing thing, Set<MetamodelRepository> modelRepositories) {
    return new Subscriber(
        getRootPackageName().withSubPackage("subscribers"),
        new Name(thing.getThingName().getText()),
        thing.getThingMetadata().getMessageData(),
        thing.getThingMetadata().getRelatedThing(),
        findServiceMethodFromFunction(
            modelRepositories, thing.getThingMetadata().getQueryFunction()),
        findServiceMethodFromFunction(
            modelRepositories, thing.getThingMetadata().getCommandFunction()));
  }

  /**
   * Find service method from function service method.
   *
   * @param modelRepositories the model repositories
   * @param function the function
   * @return the service method
   */
  @SuppressWarnings("rawtypes")
  private ServiceMethod findServiceMethodFromFunction(
      Set<MetamodelRepository> modelRepositories, Function function) {
    return CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, ServiceMetamodelRepository.class)
        .getServicesBy(function.getThing().getThingName())
        .stream()
        .flatMap(service -> service.getServiceMethods().stream())
        .filter(serviceMethod -> serviceMethod.getFunction().equals(function))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
