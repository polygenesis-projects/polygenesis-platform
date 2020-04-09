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

package io.polygenesis.generators.java.domain.aggregateroot;

import io.polygenesis.abstraction.thing.AbstractActivityRegistry;
import io.polygenesis.abstraction.thing.AbstractActivityTemplateGenerator;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.ScopePurposeTuple;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.domain.aggregateroot.activity.statemutation.ConstructorActivityGenerator;
import io.polygenesis.generators.java.domain.aggregateroot.activity.statemutation.ConstructorActivityTransformer;
import java.util.HashMap;
import java.util.Map;

public class AggregateRootActivityRegistry extends AbstractActivityRegistry<Function> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static Map<ScopePurposeTuple, AbstractActivityTemplateGenerator<?>> scopeAndPurposeMap =
      new HashMap<>();

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();

    // ABSTRACT AGGREGATE ROOT
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAbstractAggregateRoot(), Purpose.create()),
        new ConstructorActivityGenerator(new ConstructorActivityTransformer(), templateEngine));

    // AGGREGATE ROOT
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.create()),
        new ConstructorActivityGenerator(new ConstructorActivityTransformer(), templateEngine));
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Aggregate root activity registry. */
  public AggregateRootActivityRegistry() {
    super(scopeAndPurposeMap);
  }
}
