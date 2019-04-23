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

import java.util.Set;

/**
 * Contract for PolyGenesis Deducers.
 *
 * @param <R> the type parameter ModelRepository
 * @author Christos Tsakostas
 */
public interface Deducer<R extends ModelRepository<? extends Model>> {

  /**
   * Deduces a model's repository.
   *
   * @param thingRepository the thing repository
   * @param modelRepositories the model repositories
   * @return the ModelRepository
   */
  R deduce(
      ThingRepository thingRepository, Set<ModelRepository<? extends Model>> modelRepositories);
}
