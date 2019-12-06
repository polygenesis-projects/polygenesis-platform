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

package io.polygenesis.abstraction.thing.dsl;

import static java.util.Collections.singletonList;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingMetadataKey;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionScope;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Thing builder.
 *
 * @author Christos Tsakostas
 */
public class ThingBuilder extends AbstractThingBuilder<ThingBuilder> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * App thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder app(String thingName) {
    return new ThingBuilder(thingName, new LinkedHashSet<>(singletonList(AbstractionScope.app())));
  }

  /**
   * Api thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder api(String thingName) {
    return new ThingBuilder(thingName, new LinkedHashSet<>(singletonList(AbstractionScope.api())));
  }

  /**
   * End to end thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder endToEnd(String thingName) {
    return new ThingBuilder(
            thingName,
            new LinkedHashSet<>(
                Arrays.asList(
                    AbstractionScope.api(),
                    AbstractionScope.apiDetail(),
                    AbstractionScope.apiClientRest(),
                    AbstractionScope.domainAggregateRoot())))
        .withThingIdentity();
  }

  /**
   * End to end child with identity thing builder.
   *
   * @param thingName the thing name
   * @param parentThing the parent thing
   * @return the thing builder
   */
  public static ThingBuilder endToEndChildWithIdentity(String thingName, Thing parentThing) {
    return new ThingBuilder(
            thingName,
            new LinkedHashSet<>(
                Arrays.asList(
                    AbstractionScope.api(),
                    AbstractionScope.apiDetail(),
                    AbstractionScope.apiClientRest(),
                    AbstractionScope.domainAggregateEntity())))
        .withThingIdentity()
        .setParentThing(parentThing)
        .withParentThingIdentity();
  }

  /**
   * End to end child without identity thing builder.
   *
   * @param thingName the thing name
   * @param parentThing the parent thing
   * @return the thing builder
   */
  public static ThingBuilder endToEndChildWithoutIdentity(String thingName, Thing parentThing) {
    return new ThingBuilder(
            thingName,
            new LinkedHashSet<>(
                Arrays.asList(
                    AbstractionScope.api(),
                    AbstractionScope.apiDetail(),
                    AbstractionScope.apiClientRest(),
                    AbstractionScope.domainAggregateEntity())))
        .setParentThing(parentThing)
        .withParentThingIdentity();
  }

  /**
   * Api client batch process thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder apiClientBatchProcess(String thingName) {
    return new ThingBuilder(
        thingName,
        new LinkedHashSet<>(
            Arrays.asList(AbstractionScope.api(), AbstractionScope.apiClientBatchProcess())));
  }

  /**
   * Api client domain message thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder apiClientDomainMessage(String thingName) {
    return new ThingBuilder(
        thingName, new LinkedHashSet<>(singletonList(AbstractionScope.apiClientMessaging())));
  }

  /**
   * Api client rest thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder apiClientRest(String thingName) {
    return new ThingBuilder(
        thingName, new LinkedHashSet<>(singletonList(AbstractionScope.apiClientRest())));
  }

  /**
   * Api detail service aspect thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder apiDetailServiceAspect(String thingName) {
    return new ThingBuilder(
        thingName, new LinkedHashSet<>(singletonList(AbstractionScope.apiDetail())));
  }

  /**
   * Projection thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder projection(String thingName) {
    return new ThingBuilder(
            thingName,
            new LinkedHashSet<>(
                Arrays.asList(AbstractionScope.api(), AbstractionScope.projection())))
        .withThingIdentity();
  }

  /**
   * Supportive entity thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder supportiveEntity(String thingName) {
    return new ThingBuilder(
            thingName,
            new LinkedHashSet<>(singletonList(AbstractionScope.domainSupportiveEntity())))
        .withThingIdentity();
  }

  /**
   * Domain abstract aggregate root thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder domainAbstractAggregateRoot(String thingName) {
    return new ThingBuilder(
            thingName,
            new LinkedHashSet<>(singletonList(AbstractionScope.domainAbstractAggregateRoot())))
        .withThingIdentity();
  }

  /**
   * Domain service thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder domainService(String thingName) {
    return new ThingBuilder(
        thingName, new LinkedHashSet<>(singletonList(AbstractionScope.domainService())));
  }

  /**
   * Domain aggregate root thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder domainAggregateRoot(String thingName) {
    return new ThingBuilder(
        thingName, new LinkedHashSet<>(singletonList(AbstractionScope.domainAggregateRoot())));
  }

  /**
   * Domain aggregate entity thing builder.
   *
   * @param thingName the thing name
   * @param parentThing the parent thing
   * @return the thing builder
   */
  public static ThingBuilder domainAggregateEntity(String thingName, Thing parentThing) {
    return new ThingBuilder(
            thingName, new LinkedHashSet<>(singletonList(AbstractionScope.domainAggregateEntity())))
        .withThingIdentity()
        .setParentThing(parentThing)
        .withParentThingIdentity();
  }

  /**
   * Domain message subscriber thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder domainMessageSubscriber(String thingName) {
    return new ThingBuilder(
        thingName, new LinkedHashSet<>(singletonList(AbstractionScope.apiClientMessaging())));
  }

  /**
   * Domain detail domain message publisher thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder domainDetailDomainMessagePublisher(String thingName) {
    return new ThingBuilder(
        thingName, new LinkedHashSet<>(singletonList(AbstractionScope.domainDetailPublisher())));
  }

  /**
   * Domain detail repository in memory thing builder.
   *
   * @param thingName the thing name
   * @return the thing builder
   */
  public static ThingBuilder domainDetailRepositoryInMemory(String thingName) {
    return new ThingBuilder(
        thingName,
        new LinkedHashSet<>(singletonList(AbstractionScope.domainDetailRepositoryInMemory())));
  }

  // ===============================================================================================
  // METADATA RELATED FUNCTIONS
  // ===============================================================================================

  /**
   * Sets preferred package.
   *
   * @param preferredPackage the preferred package
   * @return the preferred package
   */
  public ThingBuilder setPreferredPackage(String preferredPackage) {
    addMetadata(
        new KeyValue(ThingMetadataKey.PREFERRED_PACKAGE, new PackageName(preferredPackage)));
    return this;
  }

  /**
   * Sets super class.
   *
   * @param superClass the super class
   * @return the super class
   */
  public ThingBuilder setSuperClass(Thing superClass) {
    addMetadata(new KeyValue(ThingMetadataKey.SUPER_CLASS, superClass));
    return this;
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private ThingBuilder(String thingName, Set<AbstractionScope> abstractionScopes) {
    super(ThingBuilder.class, thingName, abstractionScopes);
  }
}
