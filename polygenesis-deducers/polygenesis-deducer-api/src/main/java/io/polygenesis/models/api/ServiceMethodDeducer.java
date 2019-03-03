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

package io.polygenesis.models.api;

import io.polygenesis.core.Thing;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Service method deducer.
 *
 * @author Christos Tsakostas
 */
public class ServiceMethodDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final DtoDeducer dtoDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service method deducer.
   *
   * @param dtoDeducer the dto deducer
   */
  public ServiceMethodDeducer(DtoDeducer dtoDeducer) {
    this.dtoDeducer = dtoDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce command methods set.
   *
   * @param thing the thing
   * @return the set
   */
  public Set<Method> deduceCommandMethods(Thing thing) {
    Set<Method> methods = new LinkedHashSet<>();

    thing
        .getFunctions()
        .stream()
        .filter(function -> function.getGoal().isCommand())
        .forEach(
            function ->
                methods.add(
                    new Method(
                        function,
                        dtoDeducer.deduceRequestDto(function),
                        dtoDeducer.deduceResponseDto(function))));

    return methods;
  }

  /**
   * Deduce query methods set.
   *
   * @param thing the thing
   * @return the set
   */
  public Set<Method> deduceQueryMethods(Thing thing) {
    Set<Method> methods = new LinkedHashSet<>();

    thing
        .getFunctions()
        .stream()
        .filter(function -> !function.getGoal().isCommand())
        .forEach(
            function ->
                methods.add(
                    new Method(
                        function,
                        dtoDeducer.deduceRequestDto(function),
                        dtoDeducer.deduceResponseDto(function))));

    return methods;
  }
}
