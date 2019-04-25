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

import io.polygenesis.commons.valueobjects.ObjectName;
import java.util.Optional;
import java.util.Set;

/**
 * In-memory repository for the instances of a Metamodel.
 *
 * <p>The implementation is up to the model.
 *
 * @param <T> the type parameter
 * @author Christos Tsakostas
 */
public interface MetamodelRepository<T extends Metamodel> {

  /**
   * Gets items.
   *
   * @return the items
   */
  Set<T> getItems();

  /**
   * Gets item by object name.
   *
   * @param objectName the object name
   * @return the item by object name
   */
  Optional<T> getItemByObjectName(ObjectName objectName);
}
