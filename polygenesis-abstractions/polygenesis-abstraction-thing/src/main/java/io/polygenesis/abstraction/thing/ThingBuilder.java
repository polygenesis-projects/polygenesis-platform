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
   * Prepare thing builder.
   *
   * @return the thing builder
   */
  public static ThingBuilder generic() {
    return new ThingBuilder();
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private ThingBuilder() {
    super();
  }

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String contextName;
  private ThingName thingName;
  private ThingType thingType = ThingType.DOMAIN_AGGREGATE_ROOT;
  private Set<ThingProperty> thingProperties = new LinkedHashSet<>();
  private Boolean multiTenant = false;
  private Thing parentThing;

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
   * Sets thing scope type.
   *
   * @param thingType the thing scope type
   * @return the thing scope type
   */
  public ThingBuilder setThingType(ThingType thingType) {
    this.thingType = thingType;
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
        thingType,
        contextName != null ? new ContextName(contextName) : ContextName.defaultContext(),
        thingName,
        thingProperties,
        multiTenant,
        parentThing);
  }
}
