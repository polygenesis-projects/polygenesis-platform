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

package io.polygenesis.models.domain.aggregateroot;

import io.polygenesis.core.AbstractionScope;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.common.ConstructorsDeducer;
import io.polygenesis.models.domain.common.DomainObjectDeducer;
import io.polygenesis.models.domain.common.DomainObjectPropertiesDeducer;
import io.polygenesis.models.domain.common.StateMutationMethodDeducer;
import io.polygenesis.models.domain.domainmessage.DomainEventConstructorDeducer;

/**
 * The type Aggregate root deducer.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootDeducer extends DomainObjectDeducer<AggregateRoot> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root deducer.
   *
   * @param domainObjectPropertiesDeducer the domain object properties deducer
   * @param constructorsDeducer the constructors deducer
   * @param stateMutationMethodDeducer the state mutation method deducer
   * @param domainEventConstructorDeducer the domain event constructor deducer
   */
  public AggregateRootDeducer(
      DomainObjectPropertiesDeducer domainObjectPropertiesDeducer,
      ConstructorsDeducer constructorsDeducer,
      StateMutationMethodDeducer stateMutationMethodDeducer,
      DomainEventConstructorDeducer domainEventConstructorDeducer) {
    super(
        AggregateRoot.class,
        domainObjectPropertiesDeducer,
        constructorsDeducer,
        stateMutationMethodDeducer,
        domainEventConstructorDeducer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  protected AbstractionScope getAbstractDomainObjectAbstractionScope() {
    return AbstractionScope.domainAbstractAggregateRoot();
  }

  @Override
  protected AbstractionScope getDomainObjectAbstractionScope() {
    return AbstractionScope.domainAggregateRoot();
  }
}
