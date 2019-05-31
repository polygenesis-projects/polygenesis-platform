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

package io.polygenesis.generators.java.transformers.apidetail.activities;

import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.generators.java.implementations.ScopePurposeTuple;
import io.polygenesis.models.apiimpl.DomainEntityConverterMethod;
import io.polygenesis.representations.code.MethodRepresentation;
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

  private static Map<ScopePurposeTuple, DomainObjectConverterMethodImplementor> scopeAndPurposeMap =
      new HashMap<>();

  static {
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.convertDtoToVo()),
        new ConvertDtoToVo());

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.convertVoToDto()),
        new ConvertVoToDto());

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(
            AbstractionScope.domainAggregateRoot(),
            Purpose.convertDomainObjectToCollectionRecord()),
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
          domainObjectConverterMethodImplementorFor(
                  getAbstractionScopeAsOptional(domainEntityConverterMethod)
                      .orElseThrow(IllegalArgumentException::new),
                  domainEntityConverterMethod)
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
    return getAbstractionScopeAsOptional(domainEntityConverterMethod).isPresent();
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Optional<AbstractionScope> getAbstractionScopeAsOptional(
      DomainEntityConverterMethod domainEntityConverterMethod) {
    return domainEntityConverterMethod
        .getFunction()
        .getThing()
        .getAbstractionsScopes()
        .stream()
        .filter(
            abstractionScope ->
                scopeAndPurposeMap.containsKey(
                    new ScopePurposeTuple(
                        abstractionScope, domainEntityConverterMethod.getFunction().getPurpose())))
        .findFirst();
  }

  private DomainObjectConverterMethodImplementor domainObjectConverterMethodImplementorFor(
      AbstractionScope abstractionScope, DomainEntityConverterMethod domainEntityConverterMethod) {
    return scopeAndPurposeMap.get(
        new ScopePurposeTuple(
            abstractionScope, domainEntityConverterMethod.getFunction().getPurpose()));
  }
}
