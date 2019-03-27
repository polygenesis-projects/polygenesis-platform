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

package io.polygenesis.implementations.java.domain.constructor;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.implementations.java.MethodImplementor;
import io.polygenesis.models.domain.Constructor;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Supportive entity constructor.
 *
 * @author Christos Tsakostas
 */
public class SupportiveEntityConstructor implements MethodImplementor<Constructor> {

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public String implementationFor(
      FreemarkerService freemarkerService,
      Constructor methodProvider,
      MethodRepresentation methodRepresentation) {
    Map<String, Object> dataModel = new HashMap<>();

    dataModel.put("representation", methodRepresentation);

    return freemarkerService.exportToString(
        dataModel,
        "polygenesis-implementation-java-domain/constructor/create-supportive-entity.ftl");
  }
}
