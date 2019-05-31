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

package io.polygenesis.abstraction.thing;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.core.AbstractionScope;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Thing builder.
 *
 * @author Christos Tsakostas
 */
public class ThingBuilder {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

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
   * Domain service thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder domainService() {
    return new ThingBuilder(new LinkedHashSet<>(Arrays.asList(AbstractionScope.domainService())));
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
   * Subscriber thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder subscriber() {
    return new ThingBuilder(
        new LinkedHashSet<>(
            Arrays.asList(AbstractionScope.api(), AbstractionScope.apiClientMessaging())));
  }

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String contextName;
  private ThingName thingName;
  private Set<AbstractionScope> abstractionScopes;
  private Set<ThingProperty> thingProperties = new LinkedHashSet<>();
  private Boolean multiTenant = false;
  private Thing parentThing;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private ThingBuilder(Set<AbstractionScope> abstractionScopes) {
    this.abstractionScopes = abstractionScopes;
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets context name.
   *
   * @param contextName the context name
   */
  public void setContextName(String contextName) {
    this.contextName = contextName;
  }

  /**
   * Sets thing name.
   *
   * @param thingName the thing name
   * @return the thing name
   */
  public ThingBuilder setThingName(ThingName thingName) {
    this.thingName = thingName;
    return this;
  }

  /**
   * Sets abstraction scopes.
   *
   * @param abstractionScopes the abstraction scopes
   * @return the abstraction scopes
   */
  public ThingBuilder setAbstractionScopes(Set<AbstractionScope> abstractionScopes) {
    this.abstractionScopes = abstractionScopes;
    return this;
  }

  /**
   * Sets thing properties.
   *
   * @param thingProperties the thing properties
   * @return the thing properties
   */
  public ThingBuilder setThingProperties(Set<ThingProperty> thingProperties) {
    this.thingProperties = thingProperties;
    return this;
  }

  /**
   * Sets multi tenant.
   *
   * @param multiTenant the multi tenant
   * @return the multi tenant
   */
  public ThingBuilder setMultiTenant(Boolean multiTenant) {
    this.multiTenant = multiTenant;
    return this;
  }

  /**
   * Sets parent thing.
   *
   * @param parentThing the parent thing
   * @return the parent thing
   */
  public ThingBuilder setParentThing(Thing parentThing) {
    this.parentThing = parentThing;
    return this;
  }

  // ===============================================================================================
  // CREATION
  // ===============================================================================================

  /**
   * Create thing thing.
   *
   * @return the thing
   */
  public Thing createThing() {
    return new Thing(
        abstractionScopes,
        contextName != null ? new ContextName(contextName) : ContextName.defaultContext(),
        thingName,
        thingProperties,
        multiTenant,
        parentThing);
  }
}