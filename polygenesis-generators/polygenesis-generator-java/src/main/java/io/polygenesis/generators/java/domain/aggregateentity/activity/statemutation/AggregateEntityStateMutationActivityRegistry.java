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

package io.polygenesis.generators.java.domain.aggregateentity.activity.statemutation;

import io.polygenesis.abstraction.thing.AbstractActivityRegistry;
import io.polygenesis.abstraction.thing.AbstractActivityTemplateGenerator;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.ScopePurposeTuple;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.models.domain.StateMutationMethod;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Aggregate entity state mutation activity registry.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntityStateMutationActivityRegistry
    extends AbstractActivityRegistry<StateMutationMethod> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static Map<ScopePurposeTuple, AbstractActivityTemplateGenerator<?>> scopeAndPurposeMap =
      new HashMap<>();

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateEntity(), Purpose.create()),
        new EntityConstructorActivityGenerator(
            new EntityConstructorActivityTransformer(), templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateEntity(), Purpose.modify()),
        new EntityModifyActivityGenerator(new EntityModifyActivityTransformer(), templateEngine));
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Aggregate entity state mutation activity registry. */
  public AggregateEntityStateMutationActivityRegistry() {
    super(scopeAndPurposeMap);
  }
}
