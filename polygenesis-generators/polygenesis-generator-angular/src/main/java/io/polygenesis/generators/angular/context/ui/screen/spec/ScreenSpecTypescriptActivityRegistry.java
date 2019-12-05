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

package io.polygenesis.generators.angular.context.ui.screen.spec;

import io.polygenesis.abstraction.thing.AbstractActivityRegistry;
import io.polygenesis.abstraction.thing.AbstractActivityTemplateGenerator;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.ScopePurposeTuple;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Screen spec typescript activity registry.
 *
 * @author Christos Tsakostas
 */
public class ScreenSpecTypescriptActivityRegistry extends AbstractActivityRegistry<Function> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  private static Map<ScopePurposeTuple, AbstractActivityTemplateGenerator<?>> scopeAndPurposeMap =
      new HashMap<>();

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Screen spec typescript activity registry. */
  public ScreenSpecTypescriptActivityRegistry() {
    super(scopeAndPurposeMap);
  }
}
