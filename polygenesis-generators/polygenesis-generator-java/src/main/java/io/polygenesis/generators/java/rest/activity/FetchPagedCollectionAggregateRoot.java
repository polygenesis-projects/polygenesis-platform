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

package io.polygenesis.generators.java.rest.activity;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.models.rest.Endpoint;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.Map;

/**
 * The type Fetch paged collection aggregate root.
 *
 * @author Christos Tsakostas
 */
public class FetchPagedCollectionAggregateRoot extends AbstractEndpointImplementor
    implements EndpointImplementor {

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public String implementationFor(
      FreemarkerService freemarkerService,
      Endpoint endpoint,
      MethodRepresentation methodRepresentation) {
    Map<String, Object> dataModel = aggregateRootDataModel(endpoint, methodRepresentation);

    return freemarkerService.exportToString(
        dataModel,
        "polygenesis-implementation-java-rest/fetch-paged-collection-aggregate-root.ftl");
  }
}