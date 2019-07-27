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

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
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
import io.polygenesis.models.messaging.subscriber.SubscriberMetamodel;
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

  @Override
  public SubscriberMetamodelRepository deduce(
      Set<AbstractionRepository<?>> abstractionRepositories,
      Set<MetamodelRepository<?>> metamodelRepositories) {
    Set<SubscriberMetamodel> subscriberMetamodels = new LinkedHashSet<>();

    CoreRegistry.getAbstractionRepositoryResolver()
        .resolve(abstractionRepositories, ThingRepository.class)
        .getAbstractionItemsByScope(AbstractionScope.apiClientMessaging())
        .forEach(
            thing ->
                subscriberMetamodels.add(deduceSubscriberFromThing(thing, metamodelRepositories)));

    return new SubscriberMetamodelRepository(subscriberMetamodels);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private SubscriberMetamodel deduceSubscriberFromThing(
      Thing thing, Set<MetamodelRepository<?>> modelRepositories) {

    Thing relatedThing = getRelatedThing(thing);

    SubscriberMetamodel subscriberMetamodel =
        new SubscriberMetamodel(
            getRootPackageName().withSubPackage("subscribers"),
            new Name(thing.getThingName().getText()),
            getMessageData(thing),
            getSupportedMessageTypes(thing),
            getRelatedThing(thing),
            findServiceMethodFromFunction(
                modelRepositories, getHandlerEnsureExistenceFunction(relatedThing)),
            findServiceMethodFromFunction(modelRepositories, getHandlerCommandFunction(thing)));

    return subscriberMetamodel;
  }

  /**
   * Gets related thing.
   *
   * @param thing the thing
   * @return the related thing
   */
  private Thing getRelatedThing(Thing thing) {
    return (Thing) thing.getMetadataValue("relatedThing");
  }

  /**
   * Gets handler ensure existence function.
   *
   * @param relatedThing the related thing
   * @return the handler ensure existence function
   */
  private Function getHandlerEnsureExistenceFunction(Thing relatedThing) {
    return relatedThing
        .getFunctions()
        .stream()
        .filter(function -> function.getName().equals(new FunctionName("ensureExistence")))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

  /**
   * Gets handler command function.
   *
   * @param thing the thing
   * @return the handler command function
   */
  private Function getHandlerCommandFunction(Thing thing) {
    return (Function) thing.getMetadataValue("process");
  }

  /**
   * Gets message data.
   *
   * @param thing the thing
   * @return the message data
   */
  @SuppressWarnings("unchecked")
  private Set<Data> getMessageData(Thing thing) {
    return (Set<Data>) thing.getMetadataValue("messageData");
  }

  /**
   * Gets supported message types.
   *
   * @param thing the thing
   * @return the supported message types
   */
  @SuppressWarnings("unchecked")
  private Set<String> getSupportedMessageTypes(Thing thing) {
    return (Set<String>) thing.getMetadataValue("supportedMessageTypes");
  }

  /**
   * Find service method from function service method.
   *
   * @param modelRepositories the model repositories
   * @param function the function
   * @return the service method
   */
  private ServiceMethod findServiceMethodFromFunction(
      Set<MetamodelRepository<?>> modelRepositories, Function function) {
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
