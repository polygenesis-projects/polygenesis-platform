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

package io.polygenesis.generators.java.implementations;

import io.polygenesis.abstraction.thing.FunctionProvider;
import io.polygenesis.abstraction.thing.ScopePurposeTuple;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractMethodImplementorRegistry<T extends FunctionProvider> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  /** The Scope and Purpose map. */
  protected Map<ScopePurposeTuple, MethodImplementor<T>> scopeAndPurposeMap = new HashMap<>();

  // ===============================================================================================
  // CONSTRUCTORS
  // ===============================================================================================

  /**
   * Instantiates a new Abstract method implementor registry.
   *
   * @param freemarkerService the freemarker service
   */
  public AbstractMethodImplementorRegistry(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
    initializeScopeAndPurposeMap();
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /** Initialize scope and purpose map. */
  public abstract void initializeScopeAndPurposeMap();

  /**
   * Is supported boolean.
   *
   * @param methodProvider the method provider
   * @return the boolean
   */
  public boolean isSupported(T methodProvider) {
    return getAbstractionScopeAsOptional(methodProvider).isPresent();
  }

  /**
   * Implementation string.
   *
   * @param methodProvider the method provider
   * @param methodRepresentation the method representation
   * @return the string
   */
  public String implementation(T methodProvider, MethodRepresentation methodRepresentation) {
    if (isSupported(methodProvider)) {
      return scopeAndPurposeMap
          .get(
              new ScopePurposeTuple(
                  getAbstractionScopeAsOptional(methodProvider)
                      .orElseThrow(IllegalArgumentException::new),
                  methodProvider.getFunction().getPurpose()))
          .implementationFor(freemarkerService, methodProvider, methodRepresentation);
    } else {
      throw new UnsupportedOperationException(
          String.format(
              "No method implementation found for function with name=%s",
              methodProvider.getFunction().getName().getFullName()));
    }
  }

  /**
   * Gets abstraction scope as optional.
   *
   * @param methodProvider the method provider
   * @return the abstraction scope as optional
   */
  protected Optional<AbstractionScope> getAbstractionScopeAsOptional(T methodProvider) {
    return methodProvider.getFunction().getThing().getAbstractionsScopes().stream()
        .filter(
            abstractionScope ->
                scopeAndPurposeMap.containsKey(
                    new ScopePurposeTuple(
                        abstractionScope, methodProvider.getFunction().getPurpose())))
        .findFirst();
  }
}
