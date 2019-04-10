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
import io.polygenesis.core.FunctionProvider;
import io.polygenesis.representations.java.MethodRepresentation;

/**
 * The interface Method implementor.
 *
 * @param <T> the type parameter
 * @author Christos Tsakostas
 */
public interface MethodImplementor<T extends FunctionProvider> {

  /**
   * Implementation for string.
   *
   * @param freemarkerService the freemarker service
   * @param methodProvider the method provider
   * @param methodRepresentation the method representation
   * @return the string
   */
  String implementationFor(
      FreemarkerService freemarkerService,
      T methodProvider,
      MethodRepresentation methodRepresentation);
}
