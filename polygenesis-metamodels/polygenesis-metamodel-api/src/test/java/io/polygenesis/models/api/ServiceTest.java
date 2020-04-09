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

import io.polygenesis.abstraction.thing.CqsType;
import io.polygenesis.abstraction.thing.ThingName;
import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.commons.valueobjects.PackageName;

public class ServiceTest extends AbstractEqualityTest<Service> {

  @Override
  public Service createObject1() {
    return new Service(
        new PackageName("com.oregor"),
        new ServiceName("create"),
        CqsType.COMMAND,
        new ThingName("aaa"));
  }

  @Override
  public Service createObject2() {
    return new Service(
        new PackageName("com.oregor"),
        new ServiceName("create"),
        CqsType.SUPPORTIVE,
        new ThingName("aaa"));
  }
}
