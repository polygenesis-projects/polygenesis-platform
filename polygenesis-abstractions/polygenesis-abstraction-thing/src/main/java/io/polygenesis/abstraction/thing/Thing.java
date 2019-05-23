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

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Abstraction;
import io.polygenesis.core.AbstractionScope;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * {@link Thing} is defined as a concept or an entity on or for which a {@link Purpose} is defined.
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
public class Thing implements Abstraction {

  private Set<AbstractionScope> abstractionScopes = new LinkedHashSet<>();
  private ContextName contextName;
  private ThingName thingName;
  private Set<ThingProperty> thingProperties;
  private Set<Function> functions;
  private Boolean multiTenant;
  private Set<Thing> children;
  private Set<Thing> virtualChildren;
  private Thing optionalParent;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Thing.
   *
   * @param abstractionScopes the abstraction scopes
   * @param contextName the context name
   * @param thingName the name
   * @param thingProperties the thing properties
   * @param multiTenant the multi tenant
   * @param optionalParent the optional parent
   */
  public Thing(
      Set<AbstractionScope> abstractionScopes,
      ContextName contextName,
      ThingName thingName,
      Set<ThingProperty> thingProperties,
      Boolean multiTenant,
      Thing optionalParent) {
    setAbstractionScopes(abstractionScopes);
    setContextName(contextName);
    setThingName(thingName);
    setThingProperties(thingProperties);
    setFunctions(new LinkedHashSet<>());
    setMultiTenant(multiTenant);
    setChildren(new LinkedHashSet<>());
    setVirtualChildren(new LinkedHashSet<>());

    if (optionalParent != null) {
      setOptionalParent(optionalParent);
    }
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

    addThingPropertiesFromFunction(function);
  }

  /**
   * Add functions.
   *
   * @param functions the functions
   */
  public void addFunctions(Set<Function> functions) {
    this.functions.addAll(functions);

    functions.forEach(function -> addThingPropertiesFromFunction(function));
  }

  /**
   * Add child.
   *
   * @param thing the thing
   */
  public void addChild(Thing thing) {
    Assertion.isNotNull(
        thing.getOptionalParent(),
        String.format("The parent of %s is not set", thing.getThingName().getText()));

    if (!thing.getOptionalParent().equals(this)) {
      throw new IllegalArgumentException(
          String.format(
              "The parent of %s is not set equal to %s",
              thing.getThingName().getText(), getThingName().getText()));
    }

    getChildren().add(thing);
  }

  /**
   * Add virtual child.
   *
   * @param thing the thing
   */
  public void addVirtualChild(Thing thing) {
    Assertion.isNotNull(
        thing.getOptionalParent(),
        String.format("The parent of %s is not set", thing.getThingName().getText()));

    if (!thing.getOptionalParent().equals(this)) {
      throw new IllegalArgumentException(
          String.format(
              "The parent of %s is not set equal to %s",
              thing.getThingName().getText(), getThingName().getText()));
    }

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
    if (thing.getOptionalParent() != null) {
      return makePackageName(rootPackageName, thing.getOptionalParent());
    }

    return new PackageName(
        String.format(
            "%s.%s", rootPackageName.getText(), thing.getThingName().getText().toLowerCase()));
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return new ObjectName(getThingName().getText());
  }

  @Override
  public Set<AbstractionScope> getAbstractionsScopes() {
    return abstractionScopes;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

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
  public Thing getOptionalParent() {
    return optionalParent;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets abstraction scopes.
   *
   * @param abstractionScopes the abstraction scopes
   */
  public void setAbstractionScopes(Set<AbstractionScope> abstractionScopes) {
    Assertion.isNotNull(abstractionScopes, "abstractionScopes is required");
    this.abstractionScopes = abstractionScopes;
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
  private void setOptionalParent(Thing optionalParent) {
    Assertion.isNotNull(optionalParent, "optionalParent is required");
    this.optionalParent = optionalParent;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void addThingPropertiesFromFunction(Function function) {
    Set<ThingProperty> newThingProperties = new LinkedHashSet<>();

    function
        .getArguments()
        .stream()
        .map(Argument::getData)
        .filter(Data::isDataGroup)
        .map(Data::getAsDataGroup)
        .flatMap(dataGroup -> dataGroup.getModels().stream())
        .forEach(data -> newThingProperties.add(new ThingProperty(data)));

    thingProperties.addAll(newThingProperties);
  }

  //  protected void ensureThingProperties(Thing thing) {
  //    if (thing.getThingProperties().isEmpty()) {
  //      Set<ThingProperty> thingProperties = new LinkedHashSet<>();
  //
  //      thing
  //          .getFunctions()
  //          .stream()
  //          .map(Function::getArguments)
  //          .flatMap(arguments -> arguments.stream().map(Argument::getData))
  //          .filter(Data::isDataGroup)
  //          .map(Data::getAsDataGroup)
  //          .flatMap(dataGroup -> dataGroup.getModels().stream())
  //          // TODO
  //          .filter(
  //              data ->
  //                  data.getDataPurpose().equals(DataPurpose.any())
  //                      || data.getDataPurpose().equals(DataPurpose.referenceToThing()))
  //          .forEach(data -> thingProperties.add(new ThingProperty(data)));
  //
  //      thing.assignThingProperties(thingProperties);
  //    }
  //  }

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
    return Objects.equals(abstractionScopes, thing.abstractionScopes)
        && Objects.equals(contextName, thing.contextName)
        && Objects.equals(thingName, thing.thingName)
        && Objects.equals(thingProperties, thing.thingProperties)
        && Objects.equals(functions, thing.functions)
        && Objects.equals(multiTenant, thing.multiTenant)
        && Objects.equals(children, thing.children)
        && Objects.equals(virtualChildren, thing.virtualChildren);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        abstractionScopes,
        contextName,
        thingName,
        thingProperties,
        functions,
        multiTenant,
        children,
        virtualChildren);
  }
}
