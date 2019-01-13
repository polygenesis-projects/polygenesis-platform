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

package io.polygenesis.core;

import io.polygenesis.commons.text.Text;
import java.util.Optional;
import java.util.Set;

/**
 * The interface Thing repository.
 *
 * @author Christos Tsakostas
 */
public interface ThingRepository extends ModelRepository {

  /**
   * Gets all things in the repository.
   *
   * @return the things
   */
  Set<Thing> getThings();

  /**
   * Gets thing by name.
   *
   * @param thingName the thing name
   * @return the thing by name
   */
  Optional<Thing> getThingByName(Text thingName);

  /**
   * Gets a thing's function.
   *
   * @param thingName the thing name
   * @param goalName the function name
   * @return the thing function
   */
  Optional<Function> getThingFunction(Text thingName, Text goalName);
}
