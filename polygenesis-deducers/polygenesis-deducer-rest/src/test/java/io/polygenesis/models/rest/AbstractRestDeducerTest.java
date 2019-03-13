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

package io.polygenesis.models.rest;

import io.polygenesis.annotations.core.GoalType;
import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingBuilder;
import io.polygenesis.core.ThingName;
import java.util.LinkedHashSet;

/**
 * The type Mapping deducer test.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractRestDeducerTest {

  /**
   * Make function create.
   *
   * @return the function
   */
  protected Function makeFunctionCreate() {
    Thing thing = new ThingBuilder().setThingName(new ThingName("customer")).createThing();
    return new Function(
        thing, new Goal(GoalType.CREATE), new FunctionName("create"), new LinkedHashSet<>());
  }

  /**
   * Make function modify.
   *
   * @return the function
   */
  protected Function makeFunctionModify() {
    Thing thing = new ThingBuilder().setThingName(new ThingName("customer")).createThing();
    return new Function(
        thing, new Goal(GoalType.MODIFY), new FunctionName("modify"), new LinkedHashSet<>());
  }

  /**
   * Make function delete.
   *
   * @return the function
   */
  protected Function makeFunctionDelete() {
    Thing thing = new ThingBuilder().setThingName(new ThingName("customer")).createThing();
    return new Function(
        thing, new Goal(GoalType.MODIFY), new FunctionName("delete"), new LinkedHashSet<>());
  }

  /**
   * Make function fetch one.
   *
   * @return the function
   */
  protected Function makeFunctionFetchOne() {
    Thing thing = new ThingBuilder().setThingName(new ThingName("customer")).createThing();
    return new Function(
        thing, new Goal(GoalType.FETCH_ONE), new FunctionName("fetchOne"), new LinkedHashSet<>());
  }

  /**
   * Make function fetch collection.
   *
   * @return the function
   */
  protected Function makeFunctionFetchCollection() {
    Thing thing = new ThingBuilder().setThingName(new ThingName("customer")).createThing();
    return new Function(
        thing,
        new Goal(GoalType.FETCH_COLLECTION),
        new FunctionName("fetchCollection"),
        new LinkedHashSet<>());
  }

  /**
   * Make function fetch paged collection.
   *
   * @return the function
   */
  protected Function makeFunctionFetchPagedCollection() {
    Thing thing = new ThingBuilder().setThingName(new ThingName("customer")).createThing();
    return new Function(
        thing,
        new Goal(GoalType.FETCH_PAGED_COLLECTION),
        new FunctionName("fetchCollection"),
        new LinkedHashSet<>());
  }

  /**
   * Make invalid get function.
   *
   * @return the function
   */
  protected Function makeInvalidGetFunction() {
    Thing thing = new ThingBuilder().setThingName(new ThingName("customer")).createThing();
    return new Function(
        thing,
        new Goal("UnidentifiedGet"),
        new FunctionName("fetchCollection"),
        new LinkedHashSet<>());
  }
}
