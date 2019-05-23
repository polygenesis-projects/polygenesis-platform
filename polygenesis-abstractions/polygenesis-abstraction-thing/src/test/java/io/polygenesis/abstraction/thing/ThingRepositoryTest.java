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

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.AbstractionScope;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ThingRepositoryTest {

  @Test
  public void shouldSucceedToMakeDeducer() {
    ThingRepository repository = new ThingRepositoryImpl(new LinkedHashSet<>());

    assertThat(repository).isNotNull();
    assertThat(repository.getAbstractionItemsByScope(AbstractionScope.api()).size()).isEqualTo(0);
  }

  @Test
  public void shouldSucceedToGetThingByName() {
    ThingRepository repository = new ThingRepositoryImpl(createThings());

    assertThat(repository.getAbstractionItemByObjectName(new ObjectName("someThing"))).isPresent();
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<Thing> createThings() {
    Set<Thing> things = new LinkedHashSet<>();

    Thing someThing =
        ThingBuilder.endToEnd().setThingName(new ThingName("someThing")).createThing();

    Function someFunction =
        new Function(
            someThing, Purpose.create(), new FunctionName("someFunction"), new LinkedHashSet<>());

    someThing.addFunction(someFunction);

    things.add(someThing);
    return things;
  }
}
