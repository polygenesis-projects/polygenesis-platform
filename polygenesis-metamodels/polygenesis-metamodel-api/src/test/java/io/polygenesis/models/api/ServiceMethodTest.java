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

package io.polygenesis.models.api;

import static org.mockito.Mockito.mock;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.FunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;

public class ServiceMethodTest extends AbstractEqualityTest<ServiceMethod> {

  private Thing relatedThing = mock(Thing.class);

  @Override
  public ServiceMethod createObject1() {
    Dto requestDto =
        new Dto(
            relatedThing,
            DtoType.API_REQUEST,
            new DataObject(new ObjectName("asd"), new PackageName("com.oregor")),
            false);

    Dto responseDto =
        new Dto(
            relatedThing,
            DtoType.API_REQUEST,
            new DataObject(new ObjectName("xyz"), new PackageName("com.oregor")),
            false);

    Thing thing = ThingBuilder.endToEnd("customer").createThing(PackageName.any());
    return new ServiceMethod(
        mock(Service.class),
        FunctionBuilder.of(thing, "create", "", Purpose.create(), FunctionRole.userAsSet()).build(),
        requestDto,
        responseDto);
  }

  @Override
  public ServiceMethod createObject2() {
    Dto requestDto =
        new Dto(
            relatedThing,
            DtoType.API_REQUEST,
            new DataObject(new ObjectName("qwe"), new PackageName("com.oregor")),
            false);

    Dto responseDto =
        new Dto(
            relatedThing,
            DtoType.API_REQUEST,
            new DataObject(new ObjectName("iop"), new PackageName("com.oregor")),
            false);

    Thing thing = ThingBuilder.endToEnd("user").createThing(PackageName.any());

    return new ServiceMethod(
        mock(Service.class),
        FunctionBuilder.of(thing, "create", "", Purpose.create(), FunctionRole.userAsSet()).build(),
        requestDto,
        responseDto);
  }
}
