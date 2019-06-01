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
import io.polygenesis.abstraction.thing.ThingMetadata;
import io.polygenesis.abstraction.thing.ThingName;
import io.polygenesis.abstraction.thing.ThingProperty;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.core.AbstractionScope;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Thing builder.
 *
 * @param <T> the type parameter
 * @author Christos Tsakostas
 */
public abstract class AbstractThingBuilder<T extends AbstractThingBuilder<?>> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Class<T> builderClass;

  private String contextName;
  private ThingName thingName;
  private Set<AbstractionScope> abstractionScopes;
  private Set<ThingProperty> thingProperties = new LinkedHashSet<>();
  private Boolean multiTenant = false;
  private Thing parentThing;
  private ThingMetadata thingMetadata = ThingMetadata.empty();

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract thing builder.
   *
   * @param builderClass the builder class
   * @param abstractionScopes the abstraction scopes
   */
  public AbstractThingBuilder(Class<T> builderClass, Set<AbstractionScope> abstractionScopes) {
    this.builderClass = builderClass;
    setAbstractionScopes(abstractionScopes);
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
  public T setThingName(String thingName) {
    this.thingName = new ThingName(thingName);
    return builderClass.cast(this);
  }

  /**
   * Sets abstraction scopes.
   *
   * @param abstractionScopes the abstraction scopes
   * @return the abstraction scopes
   */
  public T setAbstractionScopes(Set<AbstractionScope> abstractionScopes) {
    this.abstractionScopes = abstractionScopes;
    return builderClass.cast(this);
  }

  /**
   * Sets thing properties.
   *
   * @param thingProperties the thing properties
   * @return the thing properties
   */
  public T setThingProperties(Set<ThingProperty> thingProperties) {
    this.thingProperties = thingProperties;
    return builderClass.cast(this);
  }

  /**
   * Sets multi tenant.
   *
   * @param multiTenant the multi tenant
   * @return the multi tenant
   */
  public T setMultiTenant(Boolean multiTenant) {
    this.multiTenant = multiTenant;
    return builderClass.cast(this);
  }

  /**
   * Sets parent thing.
   *
   * @param parentThing the parent thing
   * @return the parent thing
   */
  public T setParentThing(Thing parentThing) {
    this.parentThing = parentThing;
    return builderClass.cast(this);
  }

  /**
   * Sets thing metadata.
   *
   * @param thingMetadata the thing metadata
   * @return the thing metadata
   */
  public T setThingMetadata(ThingMetadata thingMetadata) {
    this.thingMetadata = thingMetadata;
    return builderClass.cast(this);
  }

  // ===============================================================================================
  // CREATION
  // ===============================================================================================

  /**
   * Create thing.
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
        parentThing,
        thingMetadata);
  }
}
