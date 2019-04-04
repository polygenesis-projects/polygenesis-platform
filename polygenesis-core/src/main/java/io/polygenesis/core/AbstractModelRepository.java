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

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import java.util.Optional;
import java.util.Set;

/**
 * The type Abstract model repository.
 *
 * @param <T> the type parameter
 * @author Christos Tsakostas
 */
public class AbstractModelRepository<T extends Model> implements ModelRepository<T> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<T> items;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract model repository.
   *
   * @param items the items
   */
  public AbstractModelRepository(Set<T> items) {
    setItems(items);
  }

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  @Override
  public Set<T> getItems() {
    return items;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================
  @Override
  public Optional<T> getItemByObjectName(ObjectName objectName) {
    return items.stream().filter(item -> item.getObjectName().equals(objectName)).findFirst();
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets items.
   *
   * @param items the items
   */
  private void setItems(Set<T> items) {
    Assertion.isNotNull(items, "items is required");
    this.items = items;
  }
}
