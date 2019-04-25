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

package io.polygenesis.models.rest;

import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import java.util.Set;

/**
 * The type Domain deducer.
 *
 * @author Christos Tsakostas
 */
public class RestDeducerImpl implements RestDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final ResourceDeducer resourceDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Rest deducer.
   *
   * @param rootPackageName the root package name
   * @param resourceDeducer the resource deducer
   */
  public RestDeducerImpl(PackageName rootPackageName, ResourceDeducer resourceDeducer) {
    this.rootPackageName = rootPackageName;
    this.resourceDeducer = resourceDeducer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public RestMetamodelRepository deduce(
      ThingRepository thingRepository, Set<MetamodelRepository> modelRepositories) {

    return new RestMetamodelRepository(
        resourceDeducer.deduceFrom(
            thingRepository,
            CoreRegistry.getMetamodelRepositoryResolver()
                .resolve(modelRepositories, ServiceMetamodelRepository.class),
            rootPackageName));
  }
}
