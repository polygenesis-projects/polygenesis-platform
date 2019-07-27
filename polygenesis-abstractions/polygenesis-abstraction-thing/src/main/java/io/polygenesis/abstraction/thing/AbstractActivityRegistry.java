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

package io.polygenesis.abstraction.thing;

import io.polygenesis.core.AbstractionScope;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The type Abstract activity registry.
 *
 * @param <S> the type parameter
 * @author Christos Tsakostas
 */
public abstract class AbstractActivityRegistry<S extends FunctionProvider>
    implements ActivityRegistry<S> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /** The constant scopeAndPurposeMap. */
  @SuppressWarnings("CPD-START")
  protected static Map<ScopePurposeTuple, AbstractActivityTemplateGenerator<?>> scopeAndPurposeMap =
      new HashMap<>();

  @SuppressWarnings({"unchecked"})
  @Override
  public String activityFor(S source, Object... args) {
    return activityGenerator(
            getAbstractionScopeAsOptional(source).orElseThrow(IllegalArgumentException::new),
            source)
        .generate(source, args);
  }

  @Override
  public Boolean isActivitySupportedFor(S source) {
    return getAbstractionScopeAsOptional(source).isPresent();
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Optional<AbstractionScope> getAbstractionScopeAsOptional(S source) {
    return source
        .getFunction()
        .getThing()
        .getAbstractionsScopes()
        .stream()
        .filter(
            abstractionScope ->
                scopeAndPurposeMap.containsKey(
                    new ScopePurposeTuple(abstractionScope, source.getFunction().getPurpose())))
        .findFirst();
  }

  @SuppressWarnings({"rawtypes", "unchecked", "CPD-END"})
  private AbstractActivityTemplateGenerator activityGenerator(
      AbstractionScope abstractionScope, S source) {
    return scopeAndPurposeMap.get(
        new ScopePurposeTuple(abstractionScope, source.getFunction().getPurpose()));
  }
}
