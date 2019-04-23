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

package io.polygenesis.craftsman;

import io.polygenesis.core.Deducer;
import io.polygenesis.core.Generator;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.core.ThingRepository;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The entry point to code generation.
 *
 * @author Christos Tsakostas
 */
public class Genesis {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Generate.
   *
   * @param thingRepository the thing repository
   * @param deducers the deducers
   * @param generators the generators
   */
  @SuppressWarnings("rawtypes")
  public void generate(
      ThingRepository thingRepository, Set<Deducer> deducers, Set<Generator> generators) {
    generate(deduceModelRepositories(thingRepository, deducers), generators);
  }

  /**
   * Generate.
   *
   * @param modelRepositories the model repositories
   * @param generators the generators
   */
  @SuppressWarnings("rawtypes")
  public void generate(Set<MetamodelRepository> modelRepositories, Set<Generator> generators) {
    if (generators.isEmpty()) {
      throw new IllegalArgumentException("generators cannot be empty");
    }

    generators.forEach(generator -> generator.generate(modelRepositories));
  }

  /**
   * Deduce model repositories set.
   *
   * @param thingRepository the thing repository
   * @param deducers the deducers
   * @return the set
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public Set<MetamodelRepository> deduceModelRepositories(
      ThingRepository thingRepository, Set<Deducer> deducers) {
    Set<MetamodelRepository> modelRepositories = new LinkedHashSet<>();
    deducers.forEach(
        deducer -> {
          MetamodelRepository metamodelRepository =
              deducer.deduce(thingRepository, modelRepositories);
          if (metamodelRepository == null) {
            throw new IllegalArgumentException(
                String.format(
                    "Metamodel repository is null for deducer=%s",
                    deducer.getClass().getCanonicalName()));
          }
          modelRepositories.add(metamodelRepository);
        });
    return modelRepositories;
  }
}
