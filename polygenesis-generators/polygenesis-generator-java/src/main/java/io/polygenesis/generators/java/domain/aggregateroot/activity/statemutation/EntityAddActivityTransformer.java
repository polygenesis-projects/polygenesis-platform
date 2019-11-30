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

import io.polygenesis.abstraction.thing.ActivityTemplateTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.apidetail.service.activity.AbstractServiceMethodImplementationTransformer;
import io.polygenesis.models.domain.StateMutationMethod;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Entity add activity transformer.
 *
 * @author Christos Tsakostas
 */
public class EntityAddActivityTransformer extends AbstractServiceMethodImplementationTransformer
    implements ActivityTemplateTransformer<StateMutationMethod> {

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings({"unchecked"})
  @Override
  public TemplateData transform(StateMutationMethod source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("data", new EntityAddActivityTemplateData());

    return new TemplateData(
        dataModel, "polygenesis-trinity-java/domain/" + "aggregate-root/entity-add.java.ftl");
  }
}
