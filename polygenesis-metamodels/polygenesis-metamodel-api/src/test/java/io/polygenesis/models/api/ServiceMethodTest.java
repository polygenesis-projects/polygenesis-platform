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

package io.polygenesis.models.api;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingBuilder;
import io.polygenesis.abstraction.thing.ThingName;
import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.GoalType;
import io.polygenesis.core.data.DataGroup;
import java.util.LinkedHashSet;

/** @author Christos Tsakostas */
public class ServiceMethodTest extends AbstractEqualityTest<ServiceMethod> {

  @Override
  public ServiceMethod createObject1() {
    Dto requestDto =
        new Dto(
            DtoType.API_REQUEST,
            new DataGroup(new ObjectName("asd"), new PackageName("com.oregor")),
            false);
    Dto responseDto =
        new Dto(
            DtoType.API_REQUEST,
            new DataGroup(new ObjectName("xyz"), new PackageName("com.oregor")),
            false);

    Thing thing = ThingBuilder.generic().setThingName(new ThingName("customer")).createThing();
    return new ServiceMethod(
        new Function(
            thing, new Goal(GoalType.CREATE), new FunctionName("create"), new LinkedHashSet<>()),
        requestDto,
        responseDto);
  }

  @Override
  public ServiceMethod createObject2() {
    Dto requestDto =
        new Dto(
            DtoType.API_REQUEST,
            new DataGroup(new ObjectName("asd"), new PackageName("com.oregor")),
            false);
    Dto responseDto =
        new Dto(
            DtoType.API_REQUEST,
            new DataGroup(new ObjectName("xyz"), new PackageName("com.oregor")),
            false);

    Thing thing = ThingBuilder.generic().setThingName(new ThingName("user")).createThing();
    return new ServiceMethod(
        new Function(
            thing, new Goal(GoalType.CREATE), new FunctionName("create"), new LinkedHashSet<>()),
        requestDto,
        responseDto);
  }
}
