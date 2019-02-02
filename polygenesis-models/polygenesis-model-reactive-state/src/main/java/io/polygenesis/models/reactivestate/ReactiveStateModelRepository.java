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

package io.polygenesis.models.reactivestate;

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.core.ModelRepository;
import java.util.Set;

/**
 * In-memory repository for Stores.
 *
 * @author Christos Tsakostas
 */
public class ReactiveStateModelRepository implements ModelRepository {

  private Set<Store> stores;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Store repository.
   *
   * @param stores the stores
   */
  public ReactiveStateModelRepository(Set<Store> stores) {
    setStores(stores);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets stores.
   *
   * @return the stores
   */
  public Set<Store> getStores() {
    return stores;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setStores(Set<Store> stores) {
    Assertion.isNotNull(stores, "stores is required");

    this.stores = stores;
  }
}
