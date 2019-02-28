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

import io.polygenesis.models.api.Service;
import io.polygenesis.models.apiimpl.AggregateRootConverter;
import io.polygenesis.models.apiimpl.Dependency;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.domain.AggregateRoot;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Service implementation deducer.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementationDeducer {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce service implementation.
   *
   * @param service the service
   * @param aggregateRoot the aggregate root
   * @param aggregateRootConverter the aggregate root converter
   * @return the service implementation
   */
  public ServiceImplementation deduce(
      Service service, AggregateRoot aggregateRoot, AggregateRootConverter aggregateRootConverter) {
    Set<Dependency> dependencies = new LinkedHashSet<>();

    dependencies.add(
        new Dependency(
            aggregateRootConverter.getDataType(), aggregateRootConverter.getVariableName()));

    return new ServiceImplementation(service, dependencies, aggregateRoot, aggregateRootConverter);
  }
}
