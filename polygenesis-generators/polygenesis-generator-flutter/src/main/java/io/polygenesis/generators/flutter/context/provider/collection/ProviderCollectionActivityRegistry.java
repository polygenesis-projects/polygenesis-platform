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

package io.polygenesis.generators.flutter.context.provider.collection;

import io.polygenesis.abstraction.thing.AbstractActivityTemplateGenerator;
import io.polygenesis.abstraction.thing.ActivityRegistry;
import io.polygenesis.abstraction.thing.ScopePurposeTuple;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.metamodels.stateprovider.ProviderMethod;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The type Provider collection activity registry.
 *
 * @author Christos Tsakostas
 */
public class ProviderCollectionActivityRegistry implements ActivityRegistry<ProviderMethod> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  private static Map<ScopePurposeTuple, AbstractActivityTemplateGenerator<?>> scopeAndPurposeMap =
      new HashMap<>();

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public String activityFor(ProviderMethod source, Object... args) {
    return activityGenerator(
            getAbstractionScopeAsOptional(source).orElseThrow(IllegalArgumentException::new),
            source)
        .generate(source, args);
  }

  @Override
  public Boolean isActivitySupportedFor(ProviderMethod source) {
    return getAbstractionScopeAsOptional(source).isPresent();
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Optional<AbstractionScope> getAbstractionScopeAsOptional(
      ProviderMethod serviceMethodImplementation) {
    return serviceMethodImplementation
        .getFunction()
        .getThing()
        .getAbstractionsScopes()
        .stream()
        .filter(
            abstractionScope ->
                scopeAndPurposeMap.containsKey(
                    new ScopePurposeTuple(
                        abstractionScope, serviceMethodImplementation.getFunction().getPurpose())))
        .findFirst();
  }

  @SuppressWarnings({"rawtypes", "unchecked", "CPD-END"})
  private AbstractActivityTemplateGenerator activityGenerator(
      AbstractionScope abstractionScope, ProviderMethod serviceMethodImplementation) {
    return scopeAndPurposeMap.get(
        new ScopePurposeTuple(
            abstractionScope, serviceMethodImplementation.getFunction().getPurpose()));
  }
}
