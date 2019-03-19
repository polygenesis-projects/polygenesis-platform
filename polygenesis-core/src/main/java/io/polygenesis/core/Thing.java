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

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * {@link Thing} is defined as a concept or an entity on or for which a {@link Goal} is defined.
 * Most commonly, the names of the domain business concepts will be defined as things, being
 * concrete entities or more abstract concepts.
 *
 * <p>Therefore, a {@link Thing} can be anything that makes sense to code generation.
 *
 * <p>Example of concrete business concepts: Customer, User, LoginContext etc.
 *
 * <p>Example of more abstract concepts: Sum calculation etc.
 *
 * @author Christos Tsakostas
 */
public class Thing {

  private ThingScopeType thingScopeType;
  private ThingBusinessType thingBusinessType;
  private ContextName contextName;
  private ThingName thingName;
  private Set<ThingProperty> thingProperties;
  private Set<Function> functions;
  private Boolean multiTenant;
  private Set<Thing> children;
  private Set<Thing> virtualChildren;
  private Optional<Thing> optionalParent;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Thing.
   *
   * @param thingName the thing name
   */
  public Thing(ThingName thingName) {
    this(
        ThingScopeType.LAYERS_ALL,
        ThingBusinessType.ANY,
        ContextName.defaultContext(),
        thingName,
        new LinkedHashSet<>(),
        false,
        Optional.empty());
  }

  /**
   * Instantiates a new Thing.
   *
   * @param thingName the thing name
   * @param multiTenant the multi tenant
   */
  public Thing(ThingName thingName, Boolean multiTenant) {
    this(
        ThingScopeType.LAYERS_ALL,
        ThingBusinessType.ANY,
        ContextName.defaultContext(),
        thingName,
        new LinkedHashSet<>(),
        multiTenant,
        Optional.empty());
  }

  /**
   * Instantiates a new Thing with parent.
   *
   * @param thingName the thing name
   * @param parentThing the parent thing
   */
  public Thing(ThingName thingName, Thing parentThing) {
    this(
        ThingScopeType.LAYERS_ALL,
        ThingBusinessType.ANY,
        ContextName.defaultContext(),
        thingName,
        new LinkedHashSet<>(),
        parentThing.getMultiTenant(),
        Optional.empty());
  }

  /**
   * Instantiates a new Thing.
   *
   * @param thingScopeType the thing scope type
   * @param thingBusinessType the thing business type
   * @param contextName the context name
   * @param thingName the name
   * @param thingProperties the thing properties
   * @param multiTenant the multi tenant
   */
  public Thing(
      ThingScopeType thingScopeType,
      ThingBusinessType thingBusinessType,
      ContextName contextName,
      ThingName thingName,
      Set<ThingProperty> thingProperties,
      Boolean multiTenant,
      Optional<Thing> optionalParent) {
    setThingScopeType(thingScopeType);
    setThingBusinessType(thingBusinessType);
    setContextName(contextName);
    setThingName(thingName);
    setThingProperties(thingProperties);
    setFunctions(new LinkedHashSet<>());
    setMultiTenant(multiTenant);
    setChildren(new LinkedHashSet<>());
    setVirtualChildren(new LinkedHashSet<>());
    setOptionalParent(optionalParent);
  }

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  /**
   * Assign thing properties.
   *
   * @param thingProperties the thing properties
   */
  public void assignThingProperties(Set<ThingProperty> thingProperties) {
    setThingProperties(thingProperties);
  }

  /**
   * Add function.
   *
   * @param function the function
   */
  public void addFunction(Function function) {
    this.functions.add(function);
  }

  /**
   * Add functions.
   *
   * @param functions the functions
   */
  public void addFunctions(Set<Function> functions) {
    this.functions.addAll(functions);
  }

  /**
   * Add child.
   *
   * @param thing the thing
   */
  public void addChild(Thing thing) {
    Assertion.isTrue(
        thing
            .getOptionalParent()
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        String.format(
                            "The parent of %s is not set", thing.getThingName().getText())))
            .equals(this),
        String.format(
            "The parent of %s is not set equal to %s",
            thing.getThingName().getText(), getThingName().getText()));

    getChildren().add(thing);
  }

  /**
   * Add virtual child.
   *
   * @param thing the thing
   */
  public void addVirtualChild(Thing thing) {
    Assertion.isTrue(
        thing
            .getOptionalParent()
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        String.format(
                            "The parent of %s is not set", thing.getThingName().getText())))
            .equals(this),
        String.format(
            "The parent of %s is not set equal to %s",
            thing.getThingName().getText(), getThingName().getText()));

    getVirtualChildren().add(thing);
  }

  // ===============================================================================================
  // FACTORY
  // ===============================================================================================

  /**
   * Make package name package name.
   *
   * @param rootPackageName the root package name
   * @param thing the thing
   * @return the package name
   */
  public PackageName makePackageName(PackageName rootPackageName, Thing thing) {
    if (thing.getOptionalParent().isPresent()) {
      return makePackageName(rootPackageName, thing.getOptionalParent().get());
    }

    return new PackageName(
        String.format(
            "%s.%s", rootPackageName.getText(), thing.getThingName().getText().toLowerCase()));
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets thing type.
   *
   * @return the thing type
   */
  public ThingScopeType getThingScopeType() {
    return thingScopeType;
  }

  /**
   * Gets thing business type.
   *
   * @return the thing business type
   */
  public ThingBusinessType getThingBusinessType() {
    return thingBusinessType;
  }

  /**
   * Gets context name.
   *
   * @return the context name
   */
  public ContextName getContextName() {
    return contextName;
  }

  /**
   * Gets thing name.
   *
   * @return the thing name
   */
  public ThingName getThingName() {
    return thingName;
  }

  /**
   * Gets thing properties.
   *
   * @return the thing properties
   */
  public Set<ThingProperty> getThingProperties() {
    return thingProperties;
  }

  /**
   * Gets functions.
   *
   * @return the functions
   */
  public Set<Function> getFunctions() {
    return functions;
  }

  /**
   * Gets multi tenant.
   *
   * @return the multi tenant
   */
  public Boolean getMultiTenant() {
    return multiTenant;
  }

  /**
   * Gets children.
   *
   * @return the children
   */
  public Set<Thing> getChildren() {
    return children;
  }

  /**
   * Gets virtual children.
   *
   * @return the virtual children
   */
  public Set<Thing> getVirtualChildren() {
    return virtualChildren;
  }

  /**
   * Gets optional parent.
   *
   * @return the optional parent
   */
  public Optional<Thing> getOptionalParent() {
    return optionalParent;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets thing type.
   *
   * @param thingScopeType the thing type
   */
  private void setThingScopeType(ThingScopeType thingScopeType) {
    Assertion.isNotNull(thingScopeType, "thingScopeType is required");
    this.thingScopeType = thingScopeType;
  }

  /**
   * Sets thing business type.
   *
   * @param thingBusinessType the thing business type
   */
  private void setThingBusinessType(ThingBusinessType thingBusinessType) {
    Assertion.isNotNull(thingBusinessType, "thingBusinessType is required");
    this.thingBusinessType = thingBusinessType;
  }

  /**
   * Sets context name.
   *
   * @param contextName the context name
   */
  private void setContextName(ContextName contextName) {
    this.contextName = contextName;
  }

  /**
   * Sets name.
   *
   * @param thingName the name
   */
  private void setThingName(ThingName thingName) {
    Assertion.isNotNull(thingName, "thingName is required");
    this.thingName = thingName;
  }

  /**
   * Sets thing properties.
   *
   * @param thingProperties the thing properties
   */
  private void setThingProperties(Set<ThingProperty> thingProperties) {
    Assertion.isNotNull(thingProperties, "thingProperties is required");
    this.thingProperties = thingProperties;
  }

  /**
   * Sets functions.
   *
   * @param functions the functions
   */
  private void setFunctions(Set<Function> functions) {
    Assertion.isNotNull(functions, "functions is required");
    this.functions = functions;
  }

  /**
   * Sets multi tenant.
   *
   * @param multiTenant the multi tenant
   */
  private void setMultiTenant(Boolean multiTenant) {
    Assertion.isNotNull(multiTenant, "multiTenant is required");
    this.multiTenant = multiTenant;
  }

  /**
   * Sets children.
   *
   * @param children the children
   */
  private void setChildren(Set<Thing> children) {
    Assertion.isNotNull(children, "children is required");
    this.children = children;
  }

  /**
   * Sets virtual children.
   *
   * @param virtualChildren the virtual children
   */
  private void setVirtualChildren(Set<Thing> virtualChildren) {
    Assertion.isNotNull(virtualChildren, "virtualChildren is required");
    this.virtualChildren = virtualChildren;
  }

  /**
   * Sets optional parent.
   *
   * @param optionalParent the optional parent
   */
  private void setOptionalParent(Optional<Thing> optionalParent) {
    Assertion.isNotNull(optionalParent, "optionalParent is required");
    this.optionalParent = optionalParent;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Thing thing = (Thing) o;
    return thingScopeType == thing.thingScopeType
        && thingBusinessType == thing.thingBusinessType
        && Objects.equals(contextName, thing.contextName)
        && Objects.equals(thingName, thing.thingName)
        && Objects.equals(thingProperties, thing.thingProperties)
        && Objects.equals(multiTenant, thing.multiTenant)
        && Objects.equals(children, thing.children)
        && Objects.equals(virtualChildren, thing.virtualChildren);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        thingScopeType,
        thingBusinessType,
        contextName,
        thingName,
        thingProperties,
        multiTenant,
        children,
        virtualChildren);
  }
}
