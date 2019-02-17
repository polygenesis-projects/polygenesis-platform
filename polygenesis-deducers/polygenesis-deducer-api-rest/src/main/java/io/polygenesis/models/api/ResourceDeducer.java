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

import io.polygenesis.commons.text.Name;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.models.apirest.Resource;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Resource deducer.
 *
 * @author Christos Tsakostas
 */
public class ResourceDeducer {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from set.
   *
   * @param thingRepository the thing repository
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<Resource> deduceFrom(ThingRepository thingRepository, PackageName rootPackageName) {
    Set<Resource> resources = new LinkedHashSet<>();

    thingRepository
        .getThings()
        .forEach(
            thing ->
                resources.add(
                    new Resource(
                        makeResourcePackageName(rootPackageName, thing), makeResourceName(thing))));

    return resources;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  private PackageName makeResourcePackageName(PackageName rootPackageName, Thing thing) {
    return new PackageName(
        rootPackageName.getText() + "." + thing.getName().getText().toLowerCase());
  }

  private Name makeResourceName(Thing thing) {
    return new Name(thing.getName().getText());
  }
}
