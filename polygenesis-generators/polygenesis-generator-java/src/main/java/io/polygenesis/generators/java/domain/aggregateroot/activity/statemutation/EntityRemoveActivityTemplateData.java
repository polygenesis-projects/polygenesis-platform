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

package io.polygenesis.generators.java.domain.aggregateroot.activity.statemutation;

import io.polygenesis.generators.java.common.AggregateEntityData;
import io.polygenesis.models.domain.DomainObjectProperty;
import java.util.Set;

/**
 * The type Entity Remove activity template data.
 *
 * @author Christos Tsakostas
 */
public class EntityRemoveActivityTemplateData extends AbstractEntityMutationActivityTemplateData {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Entity remove activity template data.
   *
   * @param aggregateEntityData the aggregate entity data
   * @param properties the properties
   */
  public EntityRemoveActivityTemplateData(
      AggregateEntityData aggregateEntityData, Set<DomainObjectProperty<?>> properties) {
    super(aggregateEntityData, properties);
  }
}
