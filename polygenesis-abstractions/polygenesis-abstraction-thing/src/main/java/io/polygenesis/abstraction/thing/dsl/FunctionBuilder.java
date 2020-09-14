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

package io.polygenesis.abstraction.thing.dsl;

import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import java.util.Set;

public class FunctionBuilder extends AbstractFunctionBuilder<FunctionBuilder> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Of new function builder.
   *
   * @param thing the thing
   * @param verb the verb
   * @param object the object
   * @param purpose the purpose
   * @return the new function builder
   */
  public static FunctionBuilder of(
      Thing thing, String verb, String object, Purpose purpose, Set<FunctionRole> roles) {
    return new FunctionBuilder(thing, verb, object, purpose, roles);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private FunctionBuilder(
      Thing thing, String verb, String object, Purpose purpose, Set<FunctionRole> roles) {
    super(FunctionBuilder.class, thing, verb, object, purpose, roles);
  }
}
