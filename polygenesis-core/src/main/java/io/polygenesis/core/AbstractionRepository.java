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

import io.polygenesis.commons.valueobjects.ObjectName;
import java.util.Optional;
import java.util.Set;

public interface AbstractionRepository<T extends Abstraction> {

  /**
   * Add abstraction item.
   *
   * @param abstraction the abstraction
   */
  void addAbstractionItem(T abstraction);

  /**
   * Gets all abstraction items.
   *
   * @return the all abstraction items
   */
  Set<T> getAllAbstractionItems();

  /**
   * Gets abstraction item by object name.
   *
   * @param objectName the object name
   * @return the abstraction item by object name
   */
  Optional<T> getAbstractionItemByObjectName(ObjectName objectName);

  /**
   * Gets abstraction items by scope.
   *
   * @param abstractionScope the abstraction scope
   * @return the abstraction items by scope
   */
  Set<T> getAbstractionItemsByScope(AbstractionScope abstractionScope);

  /**
   * Gets abstraction items by scopes.
   *
   * @param abstractionScopes the abstraction scopes
   * @return the abstraction items by scopes
   */
  Set<T> getAbstractionItemsByScopes(Set<AbstractionScope> abstractionScopes);
}
