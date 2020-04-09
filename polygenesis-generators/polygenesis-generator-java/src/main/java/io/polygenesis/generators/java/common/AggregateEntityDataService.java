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

package io.polygenesis.generators.java.common;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.domain.DomainObject;

/**
 * The type Aggregate entity data service.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntityDataService {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Get aggregate entity data.
   *
   * @param domainObject the domain object
   * @return the aggregate entity data
   */
  public AggregateEntityData get(DomainObject domainObject) {

    return new AggregateEntityData(
        TextConverter.toUpperCamel(domainObject.getObjectName().getText()),
        TextConverter.toLowerCamel(domainObject.getObjectName().getText()),
        TextConverter.toPlural(TextConverter.toLowerCamel(domainObject.getObjectName().getText())),
        TextConverter.toUpperCamel(String.format("%sId", domainObject.getObjectName().getText())),
        TextConverter.toLowerCamel(String.format("%sId", domainObject.getObjectName().getText())));
  }

  /**
   * Get aggregate entity data.
   *
   * @param currentThing the current thing
   * @return the aggregate entity data
   */
  public AggregateEntityData get(Thing currentThing) {

    return new AggregateEntityData(
        getAggregateEntityDataTypeByThing(currentThing),
        getAggregateEntityVariableByThing(currentThing),
        TextConverter.toPlural(TextConverter.toLowerCamel(currentThing.getThingName().getText())),
        getAggregateEntityIdDataTypeByThing(currentThing),
        getAggregateEntityIdVariableByThing(currentThing));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private String getAggregateEntityDataTypeByThing(Thing aggregateEntityThing) {
    return TextConverter.toUpperCamel(aggregateEntityThing.getThingName().getText());
  }

  private String getAggregateEntityVariableByThing(Thing aggregateEntityThing) {
    return TextConverter.toLowerCamel(aggregateEntityThing.getThingName().getText());
  }

  private String getAggregateEntityIdDataTypeByThing(Thing aggregateEntityThing) {
    return TextConverter.toUpperCamel(
        String.format("%sId", aggregateEntityThing.getThingName().getText()));
  }

  private String getAggregateEntityIdVariableByThing(Thing aggregateEntityThing) {
    return TextConverter.toLowerCamel(
        String.format("%sId", aggregateEntityThing.getThingName().getText()));
  }
}
