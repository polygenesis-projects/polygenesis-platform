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

package io.polygenesis.models.rest;

import io.polygenesis.abstraction.thing.CqsType;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingBuilder;
import io.polygenesis.abstraction.thing.ThingName;
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
    Thing thing = ThingBuilder.endToEnd().setThingName(new ThingName("customer")).createThing();
    return new Function(thing, Purpose.create(), new FunctionName("create"), new LinkedHashSet<>());
  }

  /**
   * Make function modify.
   *
   * @return the function
   */
  protected Function makeFunctionModify() {
    Thing thing = ThingBuilder.endToEnd().setThingName(new ThingName("customer")).createThing();
    return new Function(thing, Purpose.modify(), new FunctionName("modify"), new LinkedHashSet<>());
  }

  /**
   * Make function delete.
   *
   * @return the function
   */
  protected Function makeFunctionDelete() {
    Thing thing = ThingBuilder.endToEnd().setThingName(new ThingName("customer")).createThing();
    return new Function(thing, Purpose.modify(), new FunctionName("delete"), new LinkedHashSet<>());
  }

  /**
   * Make function fetch one.
   *
   * @return the function
   */
  protected Function makeFunctionFetchOne() {
    Thing thing = ThingBuilder.endToEnd().setThingName(new ThingName("customer")).createThing();
    return new Function(
        thing, Purpose.fetchOne(), new FunctionName("fetchOne"), new LinkedHashSet<>());
  }

  /**
   * Make function fetch collection.
   *
   * @return the function
   */
  protected Function makeFunctionFetchCollection() {
    Thing thing = ThingBuilder.endToEnd().setThingName(new ThingName("customer")).createThing();
    return new Function(
        thing,
        Purpose.fetchCollection(),
        new FunctionName("fetchCollection"),
        new LinkedHashSet<>());
  }

  /**
   * Make function fetch paged collection.
   *
   * @return the function
   */
  protected Function makeFunctionFetchPagedCollection() {
    Thing thing = ThingBuilder.endToEnd().setThingName(new ThingName("customer")).createThing();
    return new Function(
        thing,
        Purpose.fetchPagedCollection(),
        new FunctionName("fetchCollection"),
        new LinkedHashSet<>());
  }

  /**
   * Make invalid get function.
   *
   * @return the function
   */
  protected Function makeInvalidGetFunction() {
    Thing thing = ThingBuilder.endToEnd().setThingName(new ThingName("customer")).createThing();
    return new Function(
        thing,
        Purpose.custom("validate", CqsType.COMMAND),
        new FunctionName("fetchCollection"),
        new LinkedHashSet<>());
  }
}
