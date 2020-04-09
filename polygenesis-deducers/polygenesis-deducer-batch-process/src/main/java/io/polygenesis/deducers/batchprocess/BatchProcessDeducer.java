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

package io.polygenesis.deducers.batchprocess;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.DtoType;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.periodicprocess.BatchProcessMetamodel;
import io.polygenesis.models.periodicprocess.BatchProcessMetamodelRepository;
import java.util.LinkedHashSet;
import java.util.Set;

public class BatchProcessDeducer implements Deducer<BatchProcessMetamodelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process deducer.
   *
   * @param rootPackageName the root package name
   */
  public BatchProcessDeducer(PackageName rootPackageName) {
    this.rootPackageName = rootPackageName;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets root package name.
   *
   * @return the root package name
   */
  public PackageName getRootPackageName() {
    return rootPackageName;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public BatchProcessMetamodelRepository deduce(
      Set<AbstractionRepository<?>> abstractionRepositories,
      Set<MetamodelRepository<?>> metamodelRepositories) {
    Set<BatchProcessMetamodel> batchProcessMetamodels = new LinkedHashSet<>();

    CoreRegistry.getAbstractionRepositoryResolver()
        .resolve(abstractionRepositories, ThingRepository.class)
        .getAbstractionItemsByScope(AbstractionScope.apiClientBatchProcess())
        .forEach(
            thing -> {
              ServiceMethod commandServiceMethod =
                  commandServiceMethod(metamodelRepositories, thing);
              ServiceMethod queryServiceMethod = queryServiceMethod(metamodelRepositories, thing);

              batchProcessMetamodels.add(
                  new BatchProcessMetamodel(
                      new ObjectName(thing.getThingName().getText()),
                      extractPackageName(commandServiceMethod),
                      commandServiceMethod,
                      queryServiceMethod,
                      getQueryCollectionItem(queryServiceMethod)));
            });

    return new BatchProcessMetamodelRepository(batchProcessMetamodels);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ServiceMethod commandServiceMethod(
      Set<MetamodelRepository<?>> metamodelRepositories, Thing thing) {
    Function commandFunction = Function.class.cast(thing.getMetadataValue("commandFunction"));

    return CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(metamodelRepositories, ServiceMetamodelRepository.class)
        .getServiceMethodByFunction(commandFunction);
  }

  private ServiceMethod queryServiceMethod(
      Set<MetamodelRepository<?>> metamodelRepositories, Thing thing) {
    Function queryFunction = Function.class.cast(thing.getMetadataValue("queryFunction"));

    return CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(metamodelRepositories, ServiceMetamodelRepository.class)
        .getServiceMethodByFunction(queryFunction);
  }

  /**
   * Gets query collection item.
   *
   * @param queryServiceMethod the query service method
   * @return the query collection item
   */
  private Dto getQueryCollectionItem(ServiceMethod queryServiceMethod) {
    return queryServiceMethod.getService().getDtos().stream()
        .filter(
            dto ->
                dto.getDtoType().equals(DtoType.COLLECTION_RECORD)
                    && dto.getParent() != null
                    && dto.getParent().equals(queryServiceMethod.getResponseDto()))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

  /**
   * Extract package name package name.
   *
   * @param commandServiceMethod the command service method
   * @return the package name
   */
  private PackageName extractPackageName(ServiceMethod commandServiceMethod) {
    return commandServiceMethod.getService().getPackageName();
  }
}
