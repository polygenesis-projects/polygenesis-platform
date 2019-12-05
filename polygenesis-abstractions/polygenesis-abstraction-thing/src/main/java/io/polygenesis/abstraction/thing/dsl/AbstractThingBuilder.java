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

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataRepository;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingName;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.VariableName;
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
  private DataRepository thingProperties = new DataRepository();
  private Boolean multiTenant = false;
  private Thing parentThing;
  private Set<KeyValue> metadata = new LinkedHashSet<>();

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract thing builder.
   *
   * @param builderClass the builder class
   * @param thingName the thing name
   * @param abstractionScopes the abstraction scopes
   */
  public AbstractThingBuilder(
      Class<T> builderClass, String thingName, Set<AbstractionScope> abstractionScopes) {
    this.builderClass = builderClass;
    setThingName(thingName);
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
   * Adds thing properties.
   *
   * @param thingProperties the thing properties
   * @return the thing properties
   */
  public T addThingProperties(Set<Data> thingProperties) {
    this.thingProperties.addSetOfData(thingProperties);
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
   * Add metadata t.
   *
   * @param metadata the metadata
   * @return the t
   */
  public T addMetadata(KeyValue metadata) {
    this.metadata.add(metadata);
    return builderClass.cast(this);
  }

  /**
   * With thing identity t.
   *
   * @return the t
   */
  public T withThingIdentity() {
    this.thingProperties.addData(
        DataPrimitive.ofDataBusinessType(
            DataPurpose.thingIdentity(),
            PrimitiveType.STRING,
            new VariableName(
                String.format("%sId", TextConverter.toLowerCamel(thingName.getText())))));
    return builderClass.cast(this);
  }

  /**
   * With parent thing identity t.
   *
   * @return the t
   */
  public T withParentThingIdentity() {
    Assertion.isNotNull(parentThing, "parentThing should not be NULL");

    this.thingProperties.addData(
        DataPrimitive.ofDataBusinessType(
            DataPurpose.parentThingIdentity(),
            PrimitiveType.STRING,
            new VariableName(
                String.format(
                    "%sId", TextConverter.toLowerCamel(parentThing.getThingName().getText())))));
    return builderClass.cast(this);
  }

  // ===============================================================================================
  // PROTECTED SETTERS
  // ===============================================================================================

  /**
   * Sets thing name.
   *
   * @param thingName the thing name
   * @return the thing name
   */
  protected T setThingName(String thingName) {
    this.thingName = new ThingName(thingName);
    return builderClass.cast(this);
  }

  /**
   * Sets abstraction scopes.
   *
   * @param abstractionScopes the abstraction scopes
   * @return the abstraction scopes
   */
  protected T setAbstractionScopes(Set<AbstractionScope> abstractionScopes) {
    this.abstractionScopes = abstractionScopes;
    return builderClass.cast(this);
  }

  /**
   * Sets parent thing.
   *
   * @param parentThing the parent thing
   * @return the parent thing
   */
  protected T setParentThing(Thing parentThing) {
    this.parentThing = parentThing;
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
        metadata);
  }
}
