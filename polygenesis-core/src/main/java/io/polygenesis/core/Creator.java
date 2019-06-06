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

package io.polygenesis.core;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The entry point to code generation.
 *
 * @author Christos Tsakostas
 */
public class Creator {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Generate by project.
   *
   * @param project the project
   * @param deducers the deducers
   */
  @SuppressWarnings("rawtypes")
  public void generateByProject(Project project, Set<Deducer> deducers) {
    project
        .getContexts()
        .forEach(
            context ->
                generate(
                    deduceModelRepositories(
                        new LinkedHashSet<>(Arrays.asList(context.getAbstractionRepository())),
                        deducers),
                    context.getContextGenerator().getMetamodelGenerators()));
  }

  /**
   * Generate by context.
   *
   * @param context the context
   * @param deducers the deducers
   */
  @SuppressWarnings("rawtypes")
  public void generateByContext(Context context, Set<Deducer> deducers) {
    generate(
        deduceModelRepositories(
            new LinkedHashSet<>(Arrays.asList(context.getAbstractionRepository())), deducers),
        context.getContextGenerator().getMetamodelGenerators());
  }

  /**
   * Generate.
   *
   * @param abstractionRepositories the abstraction repositories
   * @param deducers the deducers
   * @param metamodelGenerators the metamodelGenerators
   */
  @SuppressWarnings("rawtypes")
  public void generate(
      Set<AbstractionRepository> abstractionRepositories,
      Set<Deducer> deducers,
      Set<MetamodelGenerator> metamodelGenerators) {
    generate(deduceModelRepositories(abstractionRepositories, deducers), metamodelGenerators);
  }

  /**
   * Generate.
   *
   * @param metamodelRepositories the model repositories
   * @param metamodelGenerators the metamodelGenerators
   */
  @SuppressWarnings("rawtypes")
  public void generate(
      Set<MetamodelRepository> metamodelRepositories, Set<MetamodelGenerator> metamodelGenerators) {
    if (metamodelGenerators.isEmpty()) {
      throw new IllegalArgumentException("metamodelGenerators cannot be empty");
    }

    metamodelGenerators.forEach(generator -> generator.generate(metamodelRepositories));
  }

  /**
   * Deduce model repositories set.
   *
   * @param abstractionRepositories the abstraction repositories
   * @param deducers the deducers
   * @return the set
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public Set<MetamodelRepository> deduceModelRepositories(
      Set<AbstractionRepository> abstractionRepositories, Set<Deducer> deducers) {
    Set<MetamodelRepository> metamodelRepositories = new LinkedHashSet<>();
    deducers.forEach(
        deducer -> {
          MetamodelRepository metamodelRepository =
              deducer.deduce(abstractionRepositories, metamodelRepositories);
          if (metamodelRepository == null) {
            throw new IllegalArgumentException(
                String.format(
                    "Metamodel repository is null for deducer=%s",
                    deducer.getClass().getCanonicalName()));
          }
          metamodelRepositories.add(metamodelRepository);
        });
    return metamodelRepositories;
  }
}
