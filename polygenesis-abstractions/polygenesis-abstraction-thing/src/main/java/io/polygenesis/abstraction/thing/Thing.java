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
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataRepository;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.Abstraction;
import io.polygenesis.core.AbstractionScope;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
  private DataRepository thingProperties;
  private Set<Function> functions;
  private Boolean multiTenant;
  private Set<Thing> children;
  private Thing optionalParent;
  private Set<KeyValue> metadata;

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
   * @param metadata the metadata
   */
  public Thing(
      Set<AbstractionScope> abstractionScopes,
      ContextName contextName,
      ThingName thingName,
      DataRepository thingProperties,
      Boolean multiTenant,
      Thing optionalParent,
      Set<KeyValue> metadata) {
    setAbstractionScopes(abstractionScopes);
    setContextName(contextName);
    setThingName(thingName);
    setThingProperties(thingProperties);
    setFunctions(new LinkedHashSet<>());
    setMultiTenant(multiTenant);
    setChildren(new LinkedHashSet<>());

    if (optionalParent != null) {
      setOptionalParent(optionalParent);
    }

    setMetadata(metadata);
  }

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  /**
   * Add data.
   *
   * @param data the data
   */
  public void addData(Set<Data> data) {
    Assertion.isNotNull(data, "data is required");

    thingProperties.addSetOfData(
        data.stream().collect(Collectors.toCollection(LinkedHashSet::new)));
  }

  /**
   * Assign context name.
   *
   * @param contextName the context name
   */
  public void assignContextName(ContextName contextName) {
    setContextName(contextName);
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
   * @param childThing the child thing
   */
  public void addChild(Thing childThing) {
    Assertion.isNotNull(childThing, "childThing is required");
    //    Assertion.isNotNull(
    //        childThing.getOptionalParent(),
    //        String.format("The parent of %s is not set", childThing.getThingName().getText()));
    //
    //    if (!childThing.getOptionalParent().equals(this)) {
    //      throw new IllegalArgumentException(
    //          String.format(
    //              "The parent of %s is not set equal to %s",
    //              childThing.getThingName().getText(), getThingName().getText()));
    //    }

    childThing.setOptionalParent(this);

    getChildren().add(childThing);
  }

  /**
   * Add metadata.
   *
   * @param metadata the metadata
   */
  public void addMetadata(KeyValue metadata) {
    Assertion.isNotNull(metadata, "metadata is required");

    getMetadata().add(metadata);
  }

  // ===============================================================================================
  // TRANSFORMATIONS
  // ===============================================================================================

  /**
   * Gets as data object.
   *
   * @param rootPackageName the root package name
   * @return the as data object
   */
  public DataObject getAsDataObject(PackageName rootPackageName) {
    return new DataObject(
        new ObjectName(getThingName().getText()), makePackageName(rootPackageName, this));
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
    if (thing.getMetadataValueIfExists(ThingMetadataKey.PREFERRED_PACKAGE) != null) {
      return PackageName.class.cast(thing.getMetadataValue(ThingMetadataKey.PREFERRED_PACKAGE));
    }

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
  public Set<Data> getThingProperties() {
    return thingProperties.getData();
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
   * Gets optional parent.
   *
   * @return the optional parent
   */
  public Thing getOptionalParent() {
    return optionalParent;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets function by name.
   *
   * @param functionName the function name
   * @return the function by name
   */
  public Function getFunctionByName(String functionName) {
    return getFunctions()
        .stream()
        .filter(function -> function.getName().getText().equals(functionName))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

  /**
   * Gets metadata.
   *
   * @return the metadata
   */
  public Set<KeyValue> getMetadata() {
    return metadata;
  }

  /**
   * Gets metadata value.
   *
   * @param key the key
   * @return the metadata value
   */
  public Object getMetadataValue(Object key) {
    Assertion.isNotNull(key, "key is required");

    return metadata
        .stream()
        .filter(keyValue -> keyValue.getKey().equals(key))
        .map(keyValue -> keyValue.getValue())
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    String.format("Cannot find thing metadata for key=%s", key)));
  }

  /**
   * Gets metadata value if exists.
   *
   * @param key the key
   * @return the metadata value if exists
   */
  public Object getMetadataValueIfExists(Object key) {
    Assertion.isNotNull(key, "key is required");

    return metadata
        .stream()
        .filter(keyValue -> keyValue.getKey().equals(key))
        .map(keyValue -> keyValue.getValue())
        .findFirst()
        .orElse(null);
  }

  /**
   * Gets thing identity.
   *
   * @return the thing identity
   */
  public Data getThingIdentity() {
    return getThingProperties()
        .stream()
        .filter(data -> data.isThingIdentity())
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalStateException(
                    String.format("Thing %s does not have identity", getThingName().getText())));
  }

  /**
   * Gets thing identity as data object from data primitive.
   *
   * @param rootPackageName the root package name
   * @param variableName the variable name
   * @param dataPrimitive the data primitive
   * @return the thing identity as data object from data primitive
   */
  public DataObject getThingIdentityAsDataObjectFromDataPrimitive(
      PackageName rootPackageName, VariableName variableName, DataPrimitive dataPrimitive) {
    Data data = getThingIdentity();

    if (data.isDataGroup()) {
      return data.getAsDataObject();
    } else {
      DataObject dataObject =
          new DataObject(
              new ObjectName(data.getVariableName().getText()),
              makePackageName(rootPackageName, this),
              variableName);
      dataObject.addData(dataPrimitive);
      return dataObject;
    }
  }

  public boolean extendsThing() {
    return getMetadataValueIfExists(ThingMetadataKey.SUPER_CLASS) != null;
  }

  public boolean doesNotExtendThing() {
    return getMetadataValueIfExists(ThingMetadataKey.SUPER_CLASS) == null;
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
  private void setThingProperties(DataRepository thingProperties) {
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
   * Sets optional parent.
   *
   * @param optionalParent the optional parent
   */
  private void setOptionalParent(Thing optionalParent) {
    Assertion.isNotNull(optionalParent, "optionalParent is required");
    this.optionalParent = optionalParent;
  }

  /**
   * Sets metadata.
   *
   * @param metadata the metadata
   */
  private void setMetadata(Set<KeyValue> metadata) {
    Assertion.isNotNull(metadata, "metadata is required");
    this.metadata = metadata;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void addThingPropertiesFromFunction(Function function) {
    if (!function.getPurpose().isCommand() && !function.getPurpose().isModify()) {
      return;
    }

    Set<Data> newThingProperties = new LinkedHashSet<>();

    function.getArguments().stream().forEach(data -> newThingProperties.add(data));

    thingProperties.addSetOfData(newThingProperties);
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
    return Objects.equals(abstractionScopes, thing.abstractionScopes)
        && Objects.equals(contextName, thing.contextName)
        && Objects.equals(thingName, thing.thingName)
        && Objects.equals(thingProperties, thing.thingProperties)
        && Objects.equals(functions, thing.functions)
        && Objects.equals(multiTenant, thing.multiTenant)
        && Objects.equals(children, thing.children)
        && Objects.equals(metadata, thing.metadata);
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
        metadata);
  }
}
