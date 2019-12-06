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

package io.polygenesis.models.domain.projection;

import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.common.ConstructorsDeducer;
import io.polygenesis.models.domain.common.DataToDomainObjectPropertyConverter;
import io.polygenesis.models.domain.common.DomainObjectPropertiesDeducer;
import io.polygenesis.models.domain.common.IdentityDomainObjectPropertiesDeducer;

/**
 * The type Projection deducer factory.
 *
 * @author Christos Tsakostas
 */
public final class ProjectionDeducerFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static DomainObjectPropertiesDeducer domainObjectPropertiesDeducer;
  private static ConstructorsDeducer constructorsDeducer;
  private static IdentityDomainObjectPropertiesDeducer identityDomainObjectPropertiesDeducer;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    final DataToDomainObjectPropertyConverter dataToDomainObjectPropertyConverter =
        new DataToDomainObjectPropertyConverter();

    domainObjectPropertiesDeducer =
        new DomainObjectPropertiesDeducer(dataToDomainObjectPropertyConverter);

    constructorsDeducer = new ConstructorsDeducer(dataToDomainObjectPropertyConverter);

    identityDomainObjectPropertiesDeducer = new IdentityDomainObjectPropertiesDeducer();
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================
  private ProjectionDeducerFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * New instance projection deducer.
   *
   * @param packageName the package name
   * @return the projection deducer
   */
  public static ProjectionDeducer newInstance(PackageName packageName) {
    return new ProjectionDeducer(
        packageName,
        constructorsDeducer,
        domainObjectPropertiesDeducer,
        identityDomainObjectPropertiesDeducer);
  }
}
