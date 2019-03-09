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

package io.polygenesis.models.api;

import io.polygenesis.annotations.core.GoalType;
import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.data.ObjectName;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.iomodel.IoModelGroup;
import java.util.LinkedHashSet;

/** @author Christos Tsakostas */
public class MethodTest extends AbstractEqualityTest<Method> {

  @Override
  public Method createObject1() {
    Dto requestDto =
        new Dto(
            DtoType.API_REQUEST,
            new IoModelGroup(new ObjectName("asd"), new PackageName("com.oregor")));
    Dto responseDto =
        new Dto(
            DtoType.API_REQUEST,
            new IoModelGroup(new ObjectName("xyz"), new PackageName("com.oregor")));

    Thing thing = new Thing(new ThingName("customer"));
    return new Method(
        new Function(
            thing, new Goal(GoalType.CREATE), new FunctionName("create"), new LinkedHashSet<>()),
        requestDto,
        responseDto);
  }

  @Override
  public Method createObject2() {
    Dto requestDto =
        new Dto(
            DtoType.API_REQUEST,
            new IoModelGroup(new ObjectName("asd"), new PackageName("com.oregor")));
    Dto responseDto =
        new Dto(
            DtoType.API_REQUEST,
            new IoModelGroup(new ObjectName("xyz"), new PackageName("com.oregor")));

    Thing thing = new Thing(new ThingName("user"));
    return new Method(
        new Function(
            thing, new Goal(GoalType.CREATE), new FunctionName("create"), new LinkedHashSet<>()),
        requestDto,
        responseDto);
  }
}
