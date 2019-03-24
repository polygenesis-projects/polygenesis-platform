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

package io.polygenesis.implementations.java.apiimpl;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.models.apiimpl.DomainEntityConverterMethod;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Convert dto to vo.
 *
 * @author Christos Tsakostas
 */
public class ConvertDtoToVo extends AbstractServiceMethodImplementor
    implements DomainObjectConverterMethodImplementor {

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public String implementationFor(
      FreemarkerService freemarkerService,
      DomainEntityConverterMethod domainEntityConverterMethod,
      MethodRepresentation methodRepresentation) {
    Map<String, Object> dataModel = new HashMap<>();

    dataModel.put("representation", methodRepresentation);
    dataModel.put("from", domainEntityConverterMethod.getFrom());
    dataModel.put("to", domainEntityConverterMethod.getTo());

    return freemarkerService.exportToString(
        dataModel, "polygenesis-implementation-java-apiimpl/convert-dto-to-vo.ftl");
  }
}
