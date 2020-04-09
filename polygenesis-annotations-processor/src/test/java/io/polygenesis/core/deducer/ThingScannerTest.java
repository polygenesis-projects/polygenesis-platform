/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.core.deducer;

import io.polygenesis.core.Thing;
import java.util.LinkedHashSet;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ThingScannerTest {

  @Test
  public void identifyThingsInInterfaces() {
    ThingScanner thingScanner = new ThingScanner();

    Set<Class<?>> classes = getClasses();
    Set<Thing> things = thingScanner.identifyThingsInInterfaces(classes);

    Assertions.assertThat(things).isNotNull();
    Assertions.assertThat(things.size()).isEqualTo(4);
  }

  private Set<Class<?>> getClasses() {
    ClassScanner classScanner = new ClassScanner();

    Set<String> packages = new LinkedHashSet<>();
    packages.add("io.polygenesis.core.sample");

    return classScanner.identify(packages, new LinkedHashSet<>(), InclusionOrExclusionType.NONE);
  }
}
