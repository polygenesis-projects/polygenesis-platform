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

package io.polygenesis.generators.java.apidetail.service.activity.entity;

import io.polygenesis.abstraction.thing.ActivityTemplateTransformer;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.apidetail.service.activity.AbstractServiceMethodImplementationTransformer;
import io.polygenesis.generators.java.common.ParentCallingChildDataService;
import io.polygenesis.models.apiimpl.ServiceMethodImplementation;
import io.polygenesis.models.domain.AggregateEntity;
import io.polygenesis.models.domain.AggregateRootMetamodelRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The type Create aggregate root transformer.
 *
 * @author Christos Tsakostas
 */
public class CreateAggregateEntityTransformer extends AbstractServiceMethodImplementationTransformer
    implements ActivityTemplateTransformer<ServiceMethodImplementation> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Create aggregate entity transformer.
   *
   * @param parentCallingChildDataService the parent calling child data service
   */
  @SuppressWarnings("CPD-START")
  public CreateAggregateEntityTransformer(
      ParentCallingChildDataService parentCallingChildDataService) {
    super(parentCallingChildDataService);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public TemplateData transform(ServiceMethodImplementation source, Object... args) {
    Set<MetamodelRepository<?>> metamodelRepositories = (Set<MetamodelRepository<?>>) args[0];
    Thing currentThing = source.getFunction().getThing();

    AggregateEntity aggregateEntity =
        AggregateEntity.class.cast(
            CoreRegistry.getMetamodelRepositoryResolver()
                .resolve(metamodelRepositories, AggregateRootMetamodelRepository.class)
                .findEntityByThingName(currentThing.getThingName()));

    @SuppressWarnings("CPD-START")
    CreateAggregateEntityTemplateData data =
        new CreateAggregateEntityTemplateData(
            getAggregateRootData(source),
            getAggregateEntityData(source),
            parentCallingChildDataService.get(source, currentThing),
            getAggregateRootIdDataType(source),
            getThingIdentity(source),
            getParameterRepresentations(source),
            getAggregateRootDataType(source),
            getAggregateRootVariable(source),
            getProperties(source, aggregateEntity),
            getRepositoryVariable(source),
            source.getServiceMethod().getRequestDto(),
            source.getServiceMethod().getResponseDto(),
            getConverterVariable(source, metamodelRepositories),
            source.getFunction().getThing().getMultiTenant(),
            getReturnValue(source));

    @SuppressWarnings("CPD-END")
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("data", data);

    return new TemplateData(
        dataModel,
        "polygenesis-trinity-java/api-detail/"
            + "aggregate-entity/create-aggregate-entity.java.ftl");
  }
}
