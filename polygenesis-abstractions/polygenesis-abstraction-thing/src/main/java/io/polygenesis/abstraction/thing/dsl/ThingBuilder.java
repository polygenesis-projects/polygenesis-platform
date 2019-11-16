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
   * @return the thing builder
   */
  public static ThingBuilder app() {
    return new ThingBuilder(new LinkedHashSet<>(Arrays.asList(AbstractionScope.app())));
  }

  /**
   * Api thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder api() {
    return new ThingBuilder(new LinkedHashSet<>(Arrays.asList(AbstractionScope.api())));
  }

  /**
   * End to end thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder endToEnd() {
    return new ThingBuilder(
        new LinkedHashSet<>(
            Arrays.asList(
                AbstractionScope.api(),
                AbstractionScope.apiDetail(),
                AbstractionScope.apiClientRest(),
                AbstractionScope.domainAggregateRoot())));
  }

  /**
   * End to end child thing builder.
   *
   * @param parentThing the parent thing
   * @return the thing builder
   */
  public static ThingBuilder endToEndChild(Thing parentThing) {
    return new ThingBuilder(
            new LinkedHashSet<>(
                Arrays.asList(
                    AbstractionScope.api(),
                    AbstractionScope.apiDetail(),
                    AbstractionScope.apiClientRest(),
                    AbstractionScope.domainAggregateEntity())))
        .setParentThing(parentThing);
  }

  /**
   * Api client batch process thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder apiClientBatchProcess() {
    return new ThingBuilder(
        new LinkedHashSet<>(
            Arrays.asList(AbstractionScope.api(), AbstractionScope.apiClientBatchProcess())));
  }

  /**
   * Api client domain message thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder apiClientDomainMessage() {
    return new ThingBuilder(
        new LinkedHashSet<>(Arrays.asList(AbstractionScope.apiClientMessaging())));
  }

  /**
   * Projection thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder projection() {
    return new ThingBuilder(
        new LinkedHashSet<>(Arrays.asList(AbstractionScope.api(), AbstractionScope.projection())));
  }

  /**
   * Supportive entity thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder supportiveEntity() {
    return new ThingBuilder(
        new LinkedHashSet<>(Arrays.asList(AbstractionScope.domainSupportiveEntity())));
  }

  /**
   * Domain abstract aggregate root thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder domainAbstractAggregateRoot() {
    return new ThingBuilder(
        new LinkedHashSet<>(Arrays.asList(AbstractionScope.domainAbstractAggregateRoot())));
  }

  /**
   * Domain service thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder domainService() {
    return new ThingBuilder(new LinkedHashSet<>(Arrays.asList(AbstractionScope.domainService())));
  }

  /**
   * Domain aggregate root thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder domainAggregateRoot() {
    return new ThingBuilder(
        new LinkedHashSet<>(Arrays.asList(AbstractionScope.domainAggregateRoot())));
  }

  /**
   * Domain aggregate entity thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder domainAggregateEntity() {
    return new ThingBuilder(
        new LinkedHashSet<>(Arrays.asList(AbstractionScope.domainAggregateEntity())));
  }

  /**
   * Domain message subscriber thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder domainMessageSubscriber() {
    return new ThingBuilder(
        new LinkedHashSet<>(Arrays.asList(AbstractionScope.apiClientMessaging())));
  }

  /**
   * Domain detail domain message publisher thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder domainDetailDomainMessagePublisher() {
    return new ThingBuilder(
        new LinkedHashSet<>(Arrays.asList(AbstractionScope.domainDetailPublisher())));
  }

  /**
   * Domain detail repository in memory thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder domainDetailRepositoryInMemory() {
    return new ThingBuilder(
        new LinkedHashSet<>(Arrays.asList(AbstractionScope.domainDetailRepositoryInMemory())));
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

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private ThingBuilder(Set<AbstractionScope> abstractionScopes) {
    super(ThingBuilder.class, abstractionScopes);
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

}
