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

package io.polygenesis.generators.java.implementations.apiimpl;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.generators.java.skeletons.MethodRepresentation;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import java.util.Map;

/**
 * The type Update aggregate entity.
 *
 * @author Christos Tsakostas
 */
public class UpdateAggregateEntity extends AbstractServiceMethodImplementor
    implements ServiceMethodImplementor {

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public String implementationFor(
      FreemarkerService freemarkerService,
      ServiceImplementation serviceImplementation,
      ServiceMethod serviceMethod,
      MethodRepresentation methodRepresentation) {
    Map<String, Object> dataModel =
        aggregateEntityDataModelWithThingIdentity(
            serviceImplementation, serviceMethod, methodRepresentation);

    return freemarkerService.exportToString(
        dataModel, "polygenesis-implementation-java-apiimpl/update-aggregate-entity.ftl");
  }
}