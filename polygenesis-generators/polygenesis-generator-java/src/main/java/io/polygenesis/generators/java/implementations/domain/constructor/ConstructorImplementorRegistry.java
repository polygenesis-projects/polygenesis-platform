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

package io.polygenesis.generators.java.implementations.domain.constructor;

import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.ScopePurposeTuple;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.generators.java.implementations.AbstractMethodImplementorRegistry;
import io.polygenesis.models.domain.Constructor;

/**
 * The type Constructor implementor registry.
 *
 * @author Christos Tsakostas
 */
public class ConstructorImplementorRegistry extends AbstractMethodImplementorRegistry<Constructor> {

  // ===============================================================================================
  // CONSTRUCTORS
  // ===============================================================================================

  /**
   * Instantiates a new Constructor implementor registry.
   *
   * @param freemarkerService the freemarker service
   */
  public ConstructorImplementorRegistry(FreemarkerService freemarkerService) {
    super(freemarkerService);
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public void initializeScopeAndPurposeMap() {
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.create()),
        new AggregateRootConstructor());

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateEntity(), Purpose.create()),
        new AggregateEntityConstructor());

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateEntity(), Purpose.create()),
        new SupportiveEntityConstructor());
  }
}
