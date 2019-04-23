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

package io.polygenesis.codegen;

import io.polygenesis.core.Thing;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/** The type Oregor ddd 4 j things. */
public class OregorDdd4jThings {

  /**
   * Get set.
   *
   * @param rootPackageName the root package name
   * @return the set
   */
  public static Set<Thing> get(String rootPackageName) {
    return new LinkedHashSet<>(Arrays.asList(ThingTodo.create(rootPackageName)));
  }
}
