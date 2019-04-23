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

package io.polygenesis.implementations.java;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.FunctionProvider;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Abstract method registry.
 *
 * @param <T> the type parameter
 * @author Christos Tsakostas
 */
public abstract class AbstractMethodImplementorRegistry<T extends FunctionProvider> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  /** The Scope and goal map. */
  protected Map<ScopeGoalTuple, MethodImplementor<T>> scopeAndGoalMap = new HashMap<>();

  // ===============================================================================================
  // CONSTRUCTORS
  // ===============================================================================================

  public AbstractMethodImplementorRegistry(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
    initializeScopeAndGoalMap();
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /** Initialize scope and goal map. */
  public abstract void initializeScopeAndGoalMap();

  /**
   * Is supported boolean.
   *
   * @param methodProvider the method provider
   * @return the boolean
   */
  public boolean isSupported(T methodProvider) {
    return scopeAndGoalMap.containsKey(
        new ScopeGoalTuple(
            methodProvider.getFunction().getThing().getThingScopeType(),
            TextConverter.toUpperUnderscore(methodProvider.getFunction().getGoal().getText())));
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
      return scopeAndGoalMap
          .get(
              new ScopeGoalTuple(
                  methodProvider.getFunction().getThing().getThingScopeType(),
                  TextConverter.toUpperUnderscore(
                      methodProvider.getFunction().getGoal().getText())))
          .implementationFor(freemarkerService, methodProvider, methodRepresentation);
    } else {
      throw new UnsupportedOperationException(
          String.format(
              "No method implementation found for function with name=%s",
              methodProvider.getFunction().getName().getText()));
    }
  }
}
