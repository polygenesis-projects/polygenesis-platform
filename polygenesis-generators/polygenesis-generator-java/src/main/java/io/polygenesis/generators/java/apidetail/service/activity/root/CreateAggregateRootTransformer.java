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

package io.polygenesis.generators.java.apidetail.service.activity.root;

import io.polygenesis.abstraction.thing.ActivityTemplateTransformer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.apidetail.service.activity.AbstractServiceMethodImplementationTransformer;
import io.polygenesis.generators.java.common.ParentCallingChildDataService;
import io.polygenesis.models.apiimpl.ServiceMethodImplementation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The type Create aggregate root transformer.
 *
 * @author Christos Tsakostas
 */
public class CreateAggregateRootTransformer extends AbstractServiceMethodImplementationTransformer
    implements ActivityTemplateTransformer<ServiceMethodImplementation> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Create aggregate root transformer.
   *
   * @param parentCallingChildDataService the parent calling child data service
   */
  public CreateAggregateRootTransformer(
      ParentCallingChildDataService parentCallingChildDataService) {
    super(parentCallingChildDataService);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings({"unchecked", "rawtypes", "CPD-START"})
  @Override
  public TemplateData transform(ServiceMethodImplementation source, Object... args) {
    Set<MetamodelRepository<?>> metamodelRepositories = (Set<MetamodelRepository<?>>) args[0];

    CreateAggregateRootTemplateData data =
        new CreateAggregateRootTemplateData(
            getAggregateRootData(source),
            getParameterRepresentations(source),
            getAggregateRootDataType(source),
            getAggregateRootVariable(source),
            getPropertiesFromConstructor(source, metamodelRepositories),
            getRepositoryVariable(source),
            source.getServiceMethod().getRequestDto(),
            source.getServiceMethod().getResponseDto(),
            getConverterVariable(source, metamodelRepositories),
            source.getFunction().getThing().getMultiTenant(),
            getReturnValue(source),
            getAggregateRootIdDataType(source),
            getThingIdentity(source));

    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("data", data);

    return new TemplateData(
        dataModel,
        "polygenesis-trinity-java/api-detail/" + "aggregate-root/create-aggregate-root.java.ftl");
  }
}
