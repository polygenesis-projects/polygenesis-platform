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
import io.polygenesis.models.domain.aggregateroot.AggregateRootDeducer;
import io.polygenesis.models.domain.agrregateentity.AggregateEntityDeducer;
import io.polygenesis.models.domain.common.ConstructorsDeducer;
import io.polygenesis.models.domain.common.DataToDomainObjectPropertyConverter;
import io.polygenesis.models.domain.common.DomainObjectPropertiesDeducer;
import io.polygenesis.models.domain.common.IdentityDomainObjectPropertiesDeducer;
import io.polygenesis.models.domain.common.StateMutationMethodDeducer;
import io.polygenesis.models.domain.domainmessage.DomainEventConstructorDeducer;
import io.polygenesis.models.domain.domainmessage.DomainEventMutationDeducer;

/**
 * The type Domain deducer factory.
 *
 * @author Christos Tsakostas
 */
public final class DomainDeducerFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static DomainObjectPropertiesDeducer domainObjectPropertiesDeducer;
  private static AggregateRootDeducer aggregateRootDeducer;
  private static AggregateEntityDeducer aggregateEntityDeducer;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    final DataToDomainObjectPropertyConverter dataToDomainObjectPropertyConverter =
        new DataToDomainObjectPropertyConverter();

    final ConstructorsDeducer constructorsDeducer =
        new ConstructorsDeducer(dataToDomainObjectPropertyConverter);

    final DomainEventMutationDeducer domainEventMutationDeducer = new DomainEventMutationDeducer();

    final IdentityDomainObjectPropertiesDeducer identityDomainObjectPropertiesDeducer =
        new IdentityDomainObjectPropertiesDeducer();

    domainObjectPropertiesDeducer =
        new DomainObjectPropertiesDeducer(dataToDomainObjectPropertyConverter);

    aggregateEntityDeducer =
        new AggregateEntityDeducer(
            domainObjectPropertiesDeducer,
            constructorsDeducer,
            new StateMutationMethodDeducer(
                dataToDomainObjectPropertyConverter,
                domainEventMutationDeducer,
                identityDomainObjectPropertiesDeducer),
            new DomainEventConstructorDeducer());

    aggregateRootDeducer =
        new AggregateRootDeducer(
            domainObjectPropertiesDeducer,
            constructorsDeducer,
            new StateMutationMethodDeducer(
                dataToDomainObjectPropertyConverter,
                domainEventMutationDeducer,
                identityDomainObjectPropertiesDeducer),
            new DomainEventConstructorDeducer());
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
    return new DomainDeducer(packageName, aggregateRootDeducer, aggregateEntityDeducer);
  }
}
