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

package io.polygenesis.codegen;

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.Generator;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.core.deducer.InclusionOrExclusionType;
import io.polygenesis.core.deducer.ThingDeducerRequest;
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
   * @param genesisRequest the genesis request
   * @param deducers the deducers
   * @param generators the generators
   */
  public void generate(
      GenesisRequest genesisRequest, Set<Deducer> deducers, Set<Generator> generators) {
    Assertion.isNotNull(deducers, "Deducers is required");
    Assertion.isNotNull(generators, "Generators is required");

    ThingRepository thingRepository;
    if (genesisRequest.getAnnotationsRequest() != null) {
      thingRepository =
          CoreRegistry.getThingDeducer()
              .deduce(makeThingDeducerRequest(genesisRequest.getAnnotationsRequest()));
    } else {
      // TODO
      thingRepository = null;
    }

    generate(thingRepository, deducers, generators);
  }

  /**
   * Generate.
   *
   * @param thingRepository the thing repository
   * @param deducers the deducers
   * @param generators the generators
   */
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
  public void generate(Set<ModelRepository> modelRepositories, Set<Generator> generators) {
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
  public Set<ModelRepository> deduceModelRepositories(
      ThingRepository thingRepository, Set<Deducer> deducers) {
    Set<ModelRepository> modelRepositories = new LinkedHashSet<>();
    deducers.forEach(
        deducer -> modelRepositories.add(deducer.deduce(thingRepository, modelRepositories)));
    return modelRepositories;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================
  private ThingDeducerRequest makeThingDeducerRequest(AnnotationsRequest annotationsRequest) {
    if (!annotationsRequest.getInterfacesIncluded().isEmpty()
        && !annotationsRequest.getInterfacesExcluded().isEmpty()) {
      throw new IllegalArgumentException(
          "Either Included or Excluded interfaces can be defined, but not both");
    }

    if (!annotationsRequest.getInterfacesIncluded().isEmpty()) {
      return new ThingDeducerRequest(
          annotationsRequest.getPackagesToScan(),
          annotationsRequest.getInterfacesIncluded(),
          InclusionOrExclusionType.INCLUDE);
    } else if (!annotationsRequest.getInterfacesExcluded().isEmpty()) {
      return new ThingDeducerRequest(
          annotationsRequest.getPackagesToScan(),
          annotationsRequest.getInterfacesExcluded(),
          InclusionOrExclusionType.EXCLUDE);
    } else {
      return new ThingDeducerRequest(annotationsRequest.getPackagesToScan());
    }
  }
}
