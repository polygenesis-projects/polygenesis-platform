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

package io.polygenesis.models.rest;

import io.polygenesis.abstraction.thing.CqsType;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.FunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.PackageName;

public abstract class AbstractRestDeducerTest {

  /**
   * Make function create.
   *
   * @return the function
   */
  protected Function makeFunctionCreate() {
    Thing thing = ThingBuilder.endToEnd("customer").createThing(PackageName.any());

    return FunctionBuilder.of(thing, "create", "", Purpose.create(), FunctionRole.userAsSet())
        .build();
  }

  /**
   * Make function modify.
   *
   * @return the function
   */
  protected Function makeFunctionModify() {
    Thing thing = ThingBuilder.endToEnd("customer").createThing(PackageName.any());

    return FunctionBuilder.of(thing, "modify", "", Purpose.modify(), FunctionRole.userAsSet())
        .build();
  }

  /**
   * Make function delete.
   *
   * @return the function
   */
  protected Function makeFunctionDelete() {
    Thing thing = ThingBuilder.endToEnd("customer").createThing(PackageName.any());

    return FunctionBuilder.of(thing, "delete", "", Purpose.delete(), FunctionRole.userAsSet())
        .build();
  }

  /**
   * Make function fetch one.
   *
   * @return the function
   */
  protected Function makeFunctionFetchOne() {
    Thing thing = ThingBuilder.endToEnd("customer").createThing(PackageName.any());

    return FunctionBuilder.of(thing, "fetchOne", "", Purpose.fetchOne(), FunctionRole.userAsSet())
        .build();
  }

  /**
   * Make function fetch collection.
   *
   * @return the function
   */
  protected Function makeFunctionFetchCollection() {
    Thing thing = ThingBuilder.endToEnd("customer").createThing(PackageName.any());

    return FunctionBuilder.of(
            thing, "fetchCollection", "", Purpose.fetchCollection(), FunctionRole.userAsSet())
        .build();
  }

  /**
   * Make function fetch paged collection.
   *
   * @return the function
   */
  protected Function makeFunctionFetchPagedCollection() {
    Thing thing = ThingBuilder.endToEnd("customer").createThing(PackageName.any());

    return FunctionBuilder.of(
            thing,
            "fetchPagedCollection",
            "",
            Purpose.fetchPagedCollection(),
            FunctionRole.userAsSet())
        .build();
  }

  /**
   * Make invalid get function.
   *
   * @return the function
   */
  protected Function makeInvalidGetFunction() {
    Thing thing = ThingBuilder.endToEnd("customer").createThing(PackageName.any());

    return FunctionBuilder.of(
            thing,
            "validate",
            "",
            Purpose.custom("validate", CqsType.COMMAND),
            FunctionRole.userAsSet())
        .build();
  }
}
