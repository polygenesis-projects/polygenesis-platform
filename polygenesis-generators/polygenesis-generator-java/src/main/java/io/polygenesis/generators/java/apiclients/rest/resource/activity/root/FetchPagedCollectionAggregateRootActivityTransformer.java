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

package io.polygenesis.generators.java.apiclients.rest.resource.activity.root;

import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.apiclients.rest.resource.ResourceMethodParameterRepresentationService;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.common.ResourceActivityTransformer;
import io.polygenesis.models.rest.Endpoint;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Fetch paged collection aggregate root activity transformer.
 *
 * @author Christos Tsakostas
 */
public class FetchPagedCollectionAggregateRootActivityTransformer
    extends ResourceActivityTransformer {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Fetch paged collection aggregate root activity transformer.
   *
   * @param resourceMethodParameterRepresentationService the resource method parameter
   *     representation service
   */
  public FetchPagedCollectionAggregateRootActivityTransformer(
      ResourceMethodParameterRepresentationService resourceMethodParameterRepresentationService) {
    super(resourceMethodParameterRepresentationService);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(Endpoint source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put(
        "data",
        new FetchPagedCollectionAggregateRootActivityTemplateData(
            resourceMethodParameterRepresentationService.parameterRepresentations(source, args),
            source.getService().getServiceName().getText(),
            source.getServiceMethod().getFunction().getName().getText(),
            source.getServiceMethod().getRequestDto()));

    return new TemplateData(
        dataModel,
        "polygenesis-trinity-java/api-clients/"
            + "api-client-rest/fetch-paged-collection-aggregate-root.ftl");
  }
}
