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

package io.polygenesis.core;

import io.polygenesis.commons.valueobjects.Name;
import java.util.Set;

public interface Context<T extends Abstraction> {

  /**
   * Gets name.
   *
   * @return the name
   */
  Name getName();

  /**
   * Gets abstraction repository.
   *
   * @return the abstraction repository
   */
  AbstractionRepository<T> getAbstractionRepository();

  /**
   * Add abstraction.
   *
   * @param abstraction the abstraction
   */
  void addAbstraction(T abstraction);

  /**
   * Gets architecture generator.
   *
   * @return the architecture generator
   */
  ContextGenerator getContextGenerator();

  /**
   * Gets metamodel repositories.
   *
   * @return the metamodel repositories
   */
  Set<MetamodelRepository<?>> getMetamodelRepositories();

  /**
   * Populate metamodel repositories.
   *
   * @param deducers the deducers
   */
  void populateMetamodelRepositories(Set<Deducer<?>> deducers);
}
