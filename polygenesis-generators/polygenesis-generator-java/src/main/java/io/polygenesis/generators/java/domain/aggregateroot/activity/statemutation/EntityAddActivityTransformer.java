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

package io.polygenesis.generators.java.domain.aggregateroot.activity.statemutation;

import io.polygenesis.abstraction.thing.ActivityTemplateTransformer;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.common.AggregateEntityDataService;
import io.polygenesis.models.domain.StateMutationMethod;
import java.util.HashMap;
import java.util.Map;

public class EntityAddActivityTransformer
    implements ActivityTemplateTransformer<StateMutationMethod> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final AggregateEntityDataService aggregateEntityDataService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Entity add activity transformer.
   *
   * @param aggregateEntityDataService the aggregate entity data service
   */
  public EntityAddActivityTransformer(AggregateEntityDataService aggregateEntityDataService) {
    this.aggregateEntityDataService = aggregateEntityDataService;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings({"unchecked"})
  @Override
  public TemplateData transform(StateMutationMethod source, Object... args) {
    Assertion.isNotNull(source, "source is required");
    Assertion.isNotNull(source.getMutatedObject(), "source.getMutatedObject() is required");

    Map<String, Object> dataModel = new HashMap<>();

    dataModel.put(
        "data",
        new EntityAddActivityTemplateData(
            aggregateEntityDataService.get(
                source.getFunction().getDelegatesToFunction().getThing()),
            source.getProperties()));

    return new TemplateData(
        dataModel, "polygenesis-trinity-java/domain/" + "aggregate-root/entity-add.java.ftl");
  }
}
