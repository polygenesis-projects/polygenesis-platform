/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

package io.polygenesis.deducers.apiimpl;

import io.polygenesis.core.data.VariableName;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.apiimpl.DomainObjectConverter;
import io.polygenesis.models.apiimpl.ServiceDependency;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.domain.BaseDomainObject;
import io.polygenesis.models.domain.Persistence;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Service implementation deducer.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementationDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final ServiceMethodImplementationDeducer serviceMethodImplementationDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service implementation deducer.
   *
   * @param serviceMethodImplementationDeducer the service method implementation deducer
   */
  public ServiceImplementationDeducer(
      ServiceMethodImplementationDeducer serviceMethodImplementationDeducer) {
    this.serviceMethodImplementationDeducer = serviceMethodImplementationDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce service implementation.
   *
   * @param service the service
   * @return the service implementation
   */
  public ServiceImplementation deduce(Service service) {
    return new ServiceImplementation(
        service,
        new LinkedHashSet<>(),
        new LinkedHashSet<>(),
        new LinkedHashSet<>(),
        serviceMethodImplementationDeducer.deduce(service));
  }

  /**
   * Deduce service implementation.
   *
   * @param service the service
   * @param domainObject the aggregate root
   * @param domainObjectConverter the aggregate root converter
   * @return the service implementation
   */
  public ServiceImplementation deduce(
      Service service,
      BaseDomainObject<?> domainObject,
      DomainObjectConverter domainObjectConverter) {
    Set<ServiceDependency> dependencies = new LinkedHashSet<>();

    if (domainObject.getOptionalPersistence().isPresent()) {
      Persistence persistence = domainObject.getOptionalPersistence().get();

      dependencies.add(
          new ServiceDependency(
              persistence.getObjectName(),
              persistence.getPackageName(),
              new VariableName(persistence.getObjectName().getText())));
    }

    dependencies.add(
        new ServiceDependency(
            domainObjectConverter.getObjectName(),
            domainObjectConverter.getPackageName(),
            domainObjectConverter.getVariableName()));

    return new ServiceImplementation(
        service,
        dependencies,
        new LinkedHashSet<>(Arrays.asList(domainObject)),
        new LinkedHashSet<>(Arrays.asList(domainObjectConverter)),
        serviceMethodImplementationDeducer.deduce(service));
  }
}
