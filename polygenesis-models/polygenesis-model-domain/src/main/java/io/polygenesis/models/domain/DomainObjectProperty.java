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

package io.polygenesis.models.domain;

import io.polygenesis.core.data.Data;

/**
 * The interface DomainObjectProperty.
 *
 * @param <D> the type parameter
 * @author Christos Tsakostas
 */
public interface DomainObjectProperty<D extends Data> {

  /**
   * Gets property type.
   *
   * @return the property type
   */
  PropertyType getPropertyType();

  /**
   * Gets data.
   *
   * @return the data
   */
  D getData();

  /**
   * Gets data related to type parameter.
   *
   * <p>Applies only to collections.
   *
   * @return the type parameter data
   */
  Data getTypeParameterData();
}
