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
import io.polygenesis.abstraction.data.DataRepository;
import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.core.AbstractionScope;
import java.util.Set;

public abstract class AbstractFunctionBuilder<T extends AbstractFunctionBuilder<?>> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Class<T> builderClass;

  private Thing thing;
  private Purpose purpose;
  private FunctionName name;
  private Data returnValue;
  private DataRepository arguments;
  private Activity activity;
  private Set<AbstractionScope> abstractionScopes;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract function builder.
   *
   * @param builderClass the builder class
   * @param thing the thing
   * @param verb the verb
   * @param object the object
   * @param purpose the purpose
   */
  protected AbstractFunctionBuilder(
      Class<T> builderClass, Thing thing, String verb, String object, Purpose purpose) {
    Assertion.isNotNull(builderClass, "builderClass is required");
    Assertion.isNotNull(thing, "thing is required");
    Assertion.isNotNull(verb, "verb is required");
    Assertion.isNotNull(object, "object is required");
    Assertion.isNotNull(purpose, "purpose is required");

    this.builderClass = builderClass;
    this.thing = thing;
    this.name = FunctionName.ofVerbAndObject(verb, object);
    this.purpose = purpose;

    arguments = new DataRepository();
    abstractionScopes = thing.getAbstractionsScopes();
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Add argument.
   *
   * @param data the data
   * @return the t
   */
  public T addArgument(Data data) {
    this.arguments.addData(data);
    return builderClass.cast(this);
  }

  /**
   * Add arguments t.
   *
   * @param dataSet the data set
   * @return the t
   */
  public T addArguments(Set<Data> dataSet) {
    this.arguments.addSetOfData(dataSet);
    return builderClass.cast(this);
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets return value.
   *
   * @param data the data
   * @return the return value
   */
  public T setReturnValue(Data data) {
    this.returnValue = data;
    return builderClass.cast(this);
  }

  /**
   * Sets activity.
   *
   * @param activity the activity
   * @return the activity
   */
  public T setActivity(Activity activity) {
    this.activity = activity;
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

  // ===============================================================================================
  // BUILD
  // ===============================================================================================

  /**
   * Build function.
   *
   * @return the function
   */
  public Function build() {
    return new Function(thing, purpose, name, returnValue, arguments, activity, abstractionScopes);
  }
}
