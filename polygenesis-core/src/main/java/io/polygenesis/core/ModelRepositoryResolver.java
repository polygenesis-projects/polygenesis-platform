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

import java.util.Optional;
import java.util.Set;

/**
 * Resolves a concrete implementation of Model Repository, provided a set of Model Repositories.
 *
 * @author Christos Tsakostas
 */
public class ModelRepositoryResolver {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Resolve t.
   *
   * @param <T> the type parameter
   * @param modelRepositories the model repositories
   * @param clazz the clazz
   * @return the t
   */
  @SuppressWarnings("unchecked")
  public <T extends ModelRepository> T resolve(
      Set<ModelRepository> modelRepositories, Class<T> clazz) {

    Optional<T> optionalClazz =
        modelRepositories
            .stream()
            .filter(
                modelRepository ->
                    modelRepository.getClass().getCanonicalName().equals(clazz.getCanonicalName()))
            .map(modelRepository -> (T) modelRepository)
            .findFirst();

    if (optionalClazz.isPresent()) {
      return optionalClazz.get();
    } else {
      throw new IllegalArgumentException(
          String.format(
              "No Model Repository found for Class=%s in provided modelRepositories",
              clazz.getCanonicalName()));
    }
  }
}
