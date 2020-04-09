/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataRepository;
import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingName;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.AbstractionScope;
import java.util.LinkedHashSet;
import java.util.Set;

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

  private Data tenantId;
  private boolean supportsThingIdentity = false;
  private boolean supportsParentThingIdentity = false;
  private boolean supportsTenantIdentity = false;

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
   * Adds abstraction scope t.
   *
   * @param abstractionScope the abstraction scope
   * @return the t
   */
  public T addAbstractionScope(AbstractionScope abstractionScope) {
    this.abstractionScopes.add(abstractionScope);
    return builderClass.cast(this);
  }

  /**
   * Add thing property t.
   *
   * @param thingProperty the thing property
   * @return the t
   */
  public T addThingProperty(Data thingProperty) {
    this.thingProperties.addData(thingProperty);
    return builderClass.cast(this);
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
   * @param tenantId the tenant id
   * @return the multi tenant
   */
  public T setMultiTenant(Data tenantId) {
    this.multiTenant = true;
    this.tenantId = tenantId;
    supportsTenantIdentity = true;
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
    supportsThingIdentity = true;
    return builderClass.cast(this);
  }

  /**
   * With parent thing identity t.
   *
   * @return the t
   */
  public T withParentThingIdentity() {
    Assertion.isNotNull(parentThing, "parentThing should not be NULL");
    supportsParentThingIdentity = true;
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
   * Create thing thing.
   *
   * @return the thing
   */
  public Thing createThing() {
    if (supportsThingIdentity || supportsParentThingIdentity || supportsTenantIdentity) {
      throw new IllegalStateException(
          String.format(
              "createThing must be called with Root PackageName for thing with name=%s",
              thingName.getText()));
    }

    return createThing(new PackageName("com.any"));
  }

  /**
   * Create thing.
   *
   * @param rootPackageName the root package name
   * @return the thing
   */
  public Thing createThing(PackageName rootPackageName) {
    Thing thing =
        new Thing(
            abstractionScopes,
            contextName != null ? new ContextName(contextName) : ContextName.defaultContext(),
            thingName,
            thingProperties,
            multiTenant,
            parentThing,
            metadata);

    DataRepository finalThingProperties = new DataRepository();

    if (supportsThingIdentity) {
      finalThingProperties.addData(makeThingIdentity(rootPackageName, thing));
    }

    if (supportsParentThingIdentity) {
      finalThingProperties.addData(makeParentThingIdentity(rootPackageName, parentThing));
    }

    if (supportsTenantIdentity) {
      finalThingProperties.addData(tenantId);
    }

    finalThingProperties.addSetOfData(thingProperties.getData());

    thing.assignThingProperties(finalThingProperties);

    return thing;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Data makeThingIdentity(PackageName rootPackageName, Thing thing) {
    DataObject dataObject =
        new DataObject(
            new VariableName(
                String.format("%sId", TextConverter.toLowerCamel(thingName.getText()))),
            DataPurpose.thingIdentity(),
            DataValidator.empty(),
            new ObjectName(String.format("%sId", TextConverter.toLowerCamel(thingName.getText()))),
            thing.makePackageName(rootPackageName, thing),
            new LinkedHashSet<>(),
            DataSourceType.DEFAULT);

    return DataPrimitive.ofDataBusinessTypeWithDataObject(
        DataPurpose.thingIdentity(),
        PrimitiveType.STRING,
        new VariableName(String.format("%sId", TextConverter.toLowerCamel(thingName.getText()))),
        dataObject);
  }

  private Data makeParentThingIdentity(PackageName rootPackageName, Thing parentThing) {

    return DataPrimitive.ofDataBusinessTypeWithDataObject(
        DataPurpose.parentThingIdentity(),
        PrimitiveType.STRING,
        new VariableName(
            String.format(
                "%sId", TextConverter.toLowerCamel(parentThing.getThingName().getText()))),
        makeDataObjectForReferenceToThing(parentThing, rootPackageName));
  }

  private DataObject makeDataObjectForReferenceToThing(
      Thing thingParent, PackageName rootPackageName) {
    return new DataObject(
        new VariableName(thingParent.getThingName().getText()),
        DataPurpose.referenceToThingByValue(),
        DataValidator.empty(),
        new ObjectName(thingParent.getThingName().getText()),
        thingParent.makePackageName(rootPackageName, thingParent),
        new LinkedHashSet<>(),
        DataSourceType.DEFAULT);
  }
}
