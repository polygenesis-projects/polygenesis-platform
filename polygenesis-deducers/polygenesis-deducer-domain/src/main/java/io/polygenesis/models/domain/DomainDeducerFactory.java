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

package io.polygenesis.models.domain;

import io.polygenesis.commons.valueobjects.PackageName;

/**
 * The type Domain deducer factory.
 *
 * @author Christos Tsakostas
 */
public final class DomainDeducerFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static final AggregateRootDeducer aggregateRootDeducer;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    DomainObjectConstructorDeducer domainObjectConstructorDeducer =
        new DomainObjectConstructorDeducer();

    AggregateEntityPropertyDeducer aggregateEntityPropertyDeducer =
        new AggregateEntityPropertyDeducer();

    AggregateEntityDeducer aggregateEntityDeducer =
        new AggregateEntityDeducer(domainObjectConstructorDeducer, aggregateEntityPropertyDeducer);

    AggregateRootPropertyDeducer aggregateRootPropertyDeducer =
        new AggregateRootPropertyDeducer(aggregateEntityDeducer);

    StateMutationMethodDeducer stateMutationMethodDeducer = new StateMutationMethodDeducer();

    aggregateRootDeducer =
        new AggregateRootDeducer(
            domainObjectConstructorDeducer,
            aggregateRootPropertyDeducer,
            stateMutationMethodDeducer);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================
  private DomainDeducerFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * New instance domain deducer.
   *
   * @param packageName the package name
   * @return the api deducer
   */
  public static DomainDeducer newInstance(PackageName packageName) {
    return new DomainDeducer(packageName, aggregateRootDeducer);
  }
}
