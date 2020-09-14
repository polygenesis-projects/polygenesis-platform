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

package io.polygenesis.abstraction.thing;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataRepository;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.core.AbstractionScope;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Function implements FunctionProvider {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Thing thing;
  private Purpose purpose;
  private FunctionName name;
  private Data returnValue;
  private DataRepository arguments;
  private Activity activity;
  private Set<AbstractionScope> abstractionScopes;
  private Function delegatesToFunction;
  private Set<FunctionRole> roles;

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
      DataRepository arguments,
      Activity activity,
      Set<AbstractionScope> abstractionScopes,
      Set<FunctionRole> roles) {
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

    setRoles(roles);
  }

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
   * @param delegatesToFunction the delegates to function
   */
  public Function(
      Thing thing,
      Purpose purpose,
      FunctionName name,
      Data returnValue,
      DataRepository arguments,
      Activity activity,
      Set<AbstractionScope> abstractionScopes,
      Function delegatesToFunction,
      Set<FunctionRole> roles) {
    this(thing, purpose, name, returnValue, arguments, activity, abstractionScopes, roles);
    setDelegatesToFunction(delegatesToFunction);
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
    return getAbstractionScopes().stream()
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
      getArguments().getData().stream()
          .filter(argument -> argument.isDataGroup())
          .map(DataObject.class::cast)
          .forEach(dataObject -> fillDataObjects(dataObjects, dataObject));

      // Ge Data Objects inside Data Primitives
      getArguments().getData().stream()
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

    dataObject.getModels().stream()
        .filter(data -> data.isDataGroup())
        .map(DataObject.class::cast)
        .forEach(dataObjectInternal -> fillDataObjects(dataObjects, dataObjectInternal));

    dataObject.getModels().stream()
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
  public DataRepository getArguments() {
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

  /**
   * Gets delegates to function.
   *
   * @return the delegates to function
   */
  public Function getDelegatesToFunction() {
    return delegatesToFunction;
  }

  public Set<FunctionRole> getRoles() {
    return roles;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setThing(Thing thing) {
    Assertion.isNotNull(thing, "thing is required");
    this.thing = thing;
  }

  private void setPurpose(Purpose purpose) {
    Assertion.isNotNull(purpose, "purpose is required");
    this.purpose = purpose;
  }

  private void setName(FunctionName name) {
    Assertion.isNotNull(name, "name is required");
    this.name = name;
  }

  private void setReturnValue(Data returnValue) {
    Assertion.isNotNull(returnValue, "returnValue is required");
    this.returnValue = returnValue;
  }

  private void setArguments(DataRepository arguments) {
    Assertion.isNotNull(arguments, "arguments is required");
    this.arguments = arguments;
  }

  private void setActivity(Activity activity) {
    Assertion.isNotNull(activity, "activity is required");
    this.activity = activity;
  }

  private void setAbstractionScopes(Set<AbstractionScope> abstractionScopes) {
    Assertion.isNotNull(abstractionScopes, "abstractionScopes is required");
    this.abstractionScopes = abstractionScopes;
  }

  private void setDelegatesToFunction(Function delegatesToFunction) {
    Assertion.isNotNull(delegatesToFunction, "delegatesToFunction is required");
    this.delegatesToFunction = delegatesToFunction;
  }

  private void setRoles(Set<FunctionRole> roles) {
    Assertion.isNotNull(roles, "roles is required");
    this.roles = roles;
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
        && Objects.equals(thing, function.thing)
        && Objects.equals(abstractionScopes, function.abstractionScopes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(purpose, name, abstractionScopes);
  }
}
