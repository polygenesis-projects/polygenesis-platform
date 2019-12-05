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
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.core.AbstractionScope;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * In the context of automatic programming, Function is defined as an activity expressing what has
 * to be done, with a {@link Purpose}, written by a programmer in a specific programming language to
 * or the purpose of a {@link Thing}, which is characterized by a Name and Properties provided as
 * activity's optional arguments and return value.
 *
 * @author Christos Tsakostas
 */
public class Function implements FunctionProvider {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Thing thing;
  private Purpose purpose;
  private FunctionName name;
  private Data returnValue;
  private Set<Data> arguments;
  private Activity activity;
  private Set<AbstractionScope> abstractionScopes;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Function.
   *
   * @param thing the thing
   * @param purpose the purpose
   * @param name the name
   * @param returnValue the return value
   * @param arguments the arguments
   * @param activity the activity
   * @param abstractionScopes the abstraction scopes
   */
  public Function(
      Thing thing,
      Purpose purpose,
      FunctionName name,
      Data returnValue,
      Set<Data> arguments,
      Activity activity,
      Set<AbstractionScope> abstractionScopes) {
    setThing(thing);
    setPurpose(purpose);
    setName(name);

    if (returnValue != null) {
      setReturnValue(returnValue);
    }

    if (arguments != null) {
      setArguments(arguments);
    }

    if (activity != null) {
      setActivity(activity);
    }

    setAbstractionScopes(abstractionScopes);
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Supports abstraction scope boolean.
   *
   * @param abstractionScope the abstraction scope
   * @return the boolean
   */
  public Boolean supportsAbstractionScope(AbstractionScope abstractionScope) {
    return getAbstractionScopes()
        .stream()
        .anyMatch(abstractionScope1 -> abstractionScope1.equals(abstractionScope));
  }

  /**
   * Supports ui boolean.
   *
   * @return the boolean
   */
  public Boolean supportsUi() {
    if (purpose.isFetchOne()
        || purpose.isFetchPagedCollection()
        || purpose.isCreate()
        || purpose.isModify()) {
      return true;
    }

    return false;
  }

  /**
   * Gets all arguments data objects.
   *
   * @return the all arguments data objects
   */
  public Set<DataObject> getAllArgumentsDataObjects() {
    Set<DataObject> dataObjects = new LinkedHashSet<>();

    if (getArguments() != null) {
      // Ge Data Objects
      getArguments()
          .stream()
          .filter(argument -> argument.isDataGroup())
          .map(DataObject.class::cast)
          .forEach(dataObject -> fillDataObjects(dataObjects, dataObject));

      // Ge Data Objects inside Data Primitives
      getArguments()
          .stream()
          .filter(argument -> argument.isDataPrimitive())
          .map(Data::getAsDataPrimitive)
          .filter(dataPrimitive -> dataPrimitive.getDataObject() != null)
          .map(dataPrimitive -> dataPrimitive.getDataObject())
          .forEach(dataObject -> fillDataObjects(dataObjects, dataObject));
    }

    return dataObjects;
  }

  // ===============================================================================================
  // QUERIES - PRIVATE
  // ===============================================================================================

  private void fillDataObjects(Set<DataObject> dataObjects, DataObject dataObject) {
    dataObjects.add(dataObject);

    dataObject
        .getModels()
        .stream()
        .filter(data -> data.isDataGroup())
        .map(DataObject.class::cast)
        .forEach(dataObjectInternal -> fillDataObjects(dataObjects, dataObjectInternal));

    dataObject
        .getModels()
        .stream()
        .filter(data -> data.isDataPrimitive())
        .map(Data::getAsDataPrimitive)
        .filter(dataPrimitive -> dataPrimitive.getDataObject() != null)
        .map(dataPrimitive -> dataPrimitive.getDataObject())
        .forEach(dataObjectInternal -> fillDataObjects(dataObjects, dataObjectInternal));
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets thing.
   *
   * @return the thing
   */
  public Thing getThing() {
    return thing;
  }

  /**
   * Gets purpose.
   *
   * @return the purpose
   */
  public Purpose getPurpose() {
    return purpose;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public FunctionName getName() {
    return name;
  }

  /**
   * Gets return value.
   *
   * @return the return value
   */
  public Data getReturnValue() {
    return returnValue;
  }

  /**
   * Gets arguments.
   *
   * @return the arguments
   */
  public Set<Data> getArguments() {
    return arguments;
  }

  /**
   * Gets activity.
   *
   * @return the activity
   */
  public Activity getActivity() {
    return activity;
  }

  /**
   * Gets abstraction scopes.
   *
   * @return the abstraction scopes
   */
  public Set<AbstractionScope> getAbstractionScopes() {
    return abstractionScopes;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets thing.
   *
   * @param thing the thing
   */
  private void setThing(Thing thing) {
    Assertion.isNotNull(thing, "thing is required");
    this.thing = thing;
  }

  /**
   * Sets purpose.
   *
   * @param purpose the purpose
   */
  private void setPurpose(Purpose purpose) {
    Assertion.isNotNull(purpose, "purpose is required");
    this.purpose = purpose;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(FunctionName name) {
    Assertion.isNotNull(name, "name is required");
    this.name = name;
  }

  /**
   * Sets return value.
   *
   * @param returnValue the return value
   */
  private void setReturnValue(Data returnValue) {
    Assertion.isNotNull(returnValue, "returnValue is required");
    this.returnValue = returnValue;
  }

  /**
   * Sets arguments.
   *
   * @param arguments the arguments
   */
  private void setArguments(Set<Data> arguments) {
    Assertion.isNotNull(arguments, "arguments is required");
    this.arguments = arguments;
  }

  /**
   * Sets activity.
   *
   * @param activity the activity
   */
  public void setActivity(Activity activity) {
    Assertion.isNotNull(activity, "activity is required");
    this.activity = activity;
  }

  /**
   * Sets abstraction scopes.
   *
   * @param abstractionScopes the abstraction scopes
   */
  private void setAbstractionScopes(Set<AbstractionScope> abstractionScopes) {
    Assertion.isNotNull(abstractionScopes, "abstractionScopes is required");
    this.abstractionScopes = abstractionScopes;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Function getFunction() {
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Function function = (Function) o;
    return Objects.equals(purpose, function.purpose)
        && Objects.equals(name, function.name)
        && Objects.equals(abstractionScopes, function.abstractionScopes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(purpose, name, abstractionScopes);
  }
}
