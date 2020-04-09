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

import io.polygenesis.abstraction.thing.AbstractActivityRegistry;
import io.polygenesis.abstraction.thing.AbstractActivityTemplateGenerator;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.ScopePurposeTuple;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.common.AggregateEntityDataService;
import io.polygenesis.models.domain.StateMutationMethod;
import java.util.HashMap;
import java.util.Map;

public class AggregateRootStateMutationActivityRegistry
    extends AbstractActivityRegistry<StateMutationMethod> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static Map<ScopePurposeTuple, AbstractActivityTemplateGenerator<?>> scopeAndPurposeMap =
      new HashMap<>();

  static {
    final TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    final AggregateEntityDataService aggregateEntityDataService = new AggregateEntityDataService();

    // ABSTRACT AGGREGATE ROOT
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAbstractAggregateRoot(), Purpose.create()),
        new ConstructorActivityGenerator(new ConstructorActivityTransformer(), templateEngine));

    // AGGREGATE ROOT
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.create()),
        new ConstructorActivityGenerator(new ConstructorActivityTransformer(), templateEngine));

    // ALL AGGREGATE ROOTS
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.modify()),
        new ModifyActivityGenerator(new ModifyActivityTransformer(), templateEngine));

    // AGGREGATE ENTITIES
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.entityCreate()),
        new EntityAddActivityGenerator(
            new EntityAddActivityTransformer(aggregateEntityDataService), templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.entityModify()),
        new EntityModifyActivityGenerator(
            new EntityModifyActivityTransformer(aggregateEntityDataService), templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.entityRemove()),
        new EntityRemoveActivityGenerator(
            new EntityRemoveActivityTransformer(aggregateEntityDataService), templateEngine));
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Aggregate root state mutation activity registry. */
  public AggregateRootStateMutationActivityRegistry() {
    super(scopeAndPurposeMap);
  }
}
