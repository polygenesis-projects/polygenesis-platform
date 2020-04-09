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

package io.polygenesis.generators.java.apidetail.service.activity.root;

import io.polygenesis.abstraction.thing.ActivityTemplateTransformer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.apidetail.service.activity.AbstractServiceMethodImplementationTransformer;
import io.polygenesis.generators.java.common.AggregateEntityDataService;
import io.polygenesis.generators.java.common.ParentCallingChildDataService;
import io.polygenesis.models.apiimpl.ServiceMethodImplementation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FetchPagedCollectionAggregateRootTransformer
    extends AbstractServiceMethodImplementationTransformer
    implements ActivityTemplateTransformer<ServiceMethodImplementation> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Fetch paged collection aggregate root transformer.
   *
   * @param aggregateEntityDataService the aggregate entity data service
   * @param parentCallingChildDataService the parent calling child data service
   */
  @SuppressWarnings("CPD-START")
  public FetchPagedCollectionAggregateRootTransformer(
      AggregateEntityDataService aggregateEntityDataService,
      ParentCallingChildDataService parentCallingChildDataService) {
    super(aggregateEntityDataService, parentCallingChildDataService);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public TemplateData transform(ServiceMethodImplementation source, Object... args) {
    final Set<MetamodelRepository<?>> metamodelRepositories = (Set<MetamodelRepository<?>>) args[0];

    FetchPagedCollectionAggregateRootTemplateData data =
        new FetchPagedCollectionAggregateRootTemplateData();

    // data.setProperties(getPropertiesFromConstructor(source, metamodelRepositories));
    data.setAggregateRootDataType(getAggregateRootDataType(source));

    data.setAggregateRootIdDataType(getAggregateRootIdDataType(source));

    data.setAggregateRootVariable(getAggregateRootVariable(source));
    data.setPersistenceVariable(getRepositoryVariable(source));
    data.setReturnValue(getReturnValue(source));
    data.setRequestDto(source.getServiceMethod().getRequestDto());
    data.setResponseDto(source.getServiceMethod().getResponseDto());
    if (getServiceImplementation(source, metamodelRepositories).domainObjectConverter() != null) {
      data.setConverterVariable(
          getServiceImplementation(source, metamodelRepositories)
              .domainObjectConverter()
              .getVariableName()
              .getText());
    }

    data.setMultiTenant(source.getFunction().getThing().getMultiTenant());

    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("data", data);

    return new TemplateData(
        dataModel,
        "polygenesis-implementation-java-apiimpl/fetch-paged-collection-aggregate-root.ftl");
  }
}
