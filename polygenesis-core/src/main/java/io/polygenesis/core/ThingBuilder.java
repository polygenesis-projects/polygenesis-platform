/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

package io.polygenesis.core;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Thing builder.
 *
 * @author Christos Tsakostas
 */
public class ThingBuilder {

  private ThingName thingName;
  private ThingScopeType thingScopeType = ThingScopeType.ACROSS_LAYERS;
  private ThingBusinessType thingBusinessType = ThingBusinessType.ANY;
  private Set<ThingProperty> thingProperties = new LinkedHashSet<>();
  private Boolean multiTenant = false;

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
   * @param thingScopeType the thing scope type
   * @return the thing scope type
   */
  public ThingBuilder setThingScopeType(ThingScopeType thingScopeType) {
    this.thingScopeType = thingScopeType;
    return this;
  }

  /**
   * Sets thing business type.
   *
   * @param thingBusinessType the thing business type
   * @return the thing business type
   */
  public ThingBuilder setThingBusinessType(ThingBusinessType thingBusinessType) {
    this.thingBusinessType = thingBusinessType;
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
   * Create thing thing.
   *
   * @return the thing
   */
  public Thing createThing() {
    return new Thing(thingScopeType, thingBusinessType, thingName, thingProperties, multiTenant);
  }
}
