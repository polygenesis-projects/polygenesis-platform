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
import io.polygenesis.abstraction.thing.Purpose;
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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    Subscriber subscriber =
        new Subscriber(
            getRootPackageName().withSubPackage("subscribers"),
            new Name(thing.getThingName().getText()),
            getMessageData(thing),
            getRelatedThing(thing),
            findServiceMethodFromFunction(modelRepositories, getHandlerCommandFunction(thing)),
            findServiceMethodFromFunction(modelRepositories, getHandlerQueryFunction(thing)));

    thing.getFunctions().forEach(subscriber::appendSubscriberMethod);

    return subscriber;
  }

  /**
   * Gets related thing.
   *
   * @param thing the thing
   * @return the related thing
   */
  private Thing getRelatedThing(Thing thing) {
    Set<Thing> relatedThings =
        thing
            .getFunctions()
            .stream()
            .map(function -> function.getActivity())
            .filter(Objects::nonNull)
            .flatMap(activity -> activity.getExternalFunctions().stream())
            .map(function -> function.getThing())
            .collect(Collectors.toSet());

    if (relatedThings.size() == 0) {
      throw new IllegalStateException("No related thing found");
    } else if (relatedThings.size() > 1) {
      throw new IllegalStateException("More than one related things found");
    }

    return relatedThings.stream().findFirst().orElseThrow(IllegalStateException::new);
  }

  /**
   * Gets handler command function.
   *
   * @param thing the thing
   * @return the handler command function
   */
  private Function getHandlerCommandFunction(Thing thing) {
    return thing
        .getFunctions()
        .stream()
        .map(function -> function.getActivity())
        .filter(Objects::nonNull)
        .flatMap(activity -> activity.getExternalFunctions().stream())
        .filter(function -> function.getPurpose().isCommand())
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

  /**
   * Gets handler query function.
   *
   * @param thing the thing
   * @return the handler query function
   */
  private Function getHandlerQueryFunction(Thing thing) {
    return thing
        .getFunctions()
        .stream()
        .map(function -> function.getActivity())
        .filter(Objects::nonNull)
        .flatMap(activity -> activity.getExternalFunctions().stream())
        .filter(function -> function.getPurpose().isQuery())
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

  /**
   * Gets message data.
   *
   * @param thing the thing
   * @return the message data
   */
  private Set<Data> getMessageData(Thing thing) {
    return thing
        .getFunctions()
        .stream()
        .filter(function -> function.getPurpose().equals(Purpose.process()))
        .flatMap(function -> function.getActivity().getExternalData().stream())
        .collect(Collectors.toCollection(LinkedHashSet::new));
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
