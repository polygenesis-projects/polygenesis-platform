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
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.ThingScopeType;
import io.polygenesis.implementations.java.ScopeGoalTuple;
import io.polygenesis.models.apiimpl.DomainEntityConverterMethod;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The type Converter method implementation registry.
 *
 * @author Christos Tsakostas
 */
public class ConverterMethodImplementationRegistry {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static Map<ScopeGoalTuple, DomainObjectConverterMethodImplementor> scopeAndGoalMap =
      new HashMap<>();

  static {
    scopeAndGoalMap.put(
        new ScopeGoalTuple(ThingScopeType.DOMAIN_AGGREGATE_ROOT, "CONVERT_DTO_TO_VO"),
        new ConvertDtoToVo());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(ThingScopeType.DOMAIN_AGGREGATE_ROOT, "CONVERT_VO_TO_DTO"),
        new ConvertVoToDto());

    scopeAndGoalMap.put(
        new ScopeGoalTuple(
            ThingScopeType.DOMAIN_AGGREGATE_ROOT, "CONVERT_DOMAIN_OBJECT_TO_COLLECTION_RECORD"),
        new ConvertDomainObjectToCollectionRecord());
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Implementation optional.
   *
   * @param freemarkerService the freemarker service
   * @param domainEntityConverterMethod the domain object converter method
   * @param methodRepresentation the method representation
   * @return the optional
   */
  public Optional<String> implementation(
      FreemarkerService freemarkerService,
      DomainEntityConverterMethod domainEntityConverterMethod,
      MethodRepresentation methodRepresentation) {
    if (isConverterMethodSupported(domainEntityConverterMethod)) {
      return Optional.of(
          domainObjectConverterMethodImplementorFor(domainEntityConverterMethod)
              .implementationFor(
                  freemarkerService, domainEntityConverterMethod, methodRepresentation));
    } else {
      return Optional.empty();
    }
  }

  /**
   * Is converter method supported boolean.
   *
   * @param domainEntityConverterMethod the domain object converter method
   * @return the boolean
   */
  public boolean isConverterMethodSupported(
      DomainEntityConverterMethod domainEntityConverterMethod) {
    return scopeAndGoalMap.containsKey(
        new ScopeGoalTuple(
            domainEntityConverterMethod.getFunction().getThing().getThingScopeType(),
            TextConverter.toUpperUnderscore(
                domainEntityConverterMethod.getFunction().getGoal().getText())));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private DomainObjectConverterMethodImplementor domainObjectConverterMethodImplementorFor(
      DomainEntityConverterMethod domainEntityConverterMethod) {
    return scopeAndGoalMap.get(
        new ScopeGoalTuple(
            domainEntityConverterMethod.getFunction().getThing().getThingScopeType(),
            TextConverter.toUpperUnderscore(
                domainEntityConverterMethod.getFunction().getGoal().getText())));
  }
}
