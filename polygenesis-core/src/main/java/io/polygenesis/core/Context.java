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

import io.polygenesis.commons.valueobjects.Name;

/**
 * The interface Context.
 *
 * @param <T> the type parameter
 * @author Christos Tsakostas
 */
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
}
