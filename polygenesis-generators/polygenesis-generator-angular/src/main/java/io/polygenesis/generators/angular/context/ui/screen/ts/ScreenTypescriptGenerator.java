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

package io.polygenesis.generators.angular.context.ui.screen.ts;

import io.polygenesis.core.AbstractUnitTemplateGenerator;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.metamodels.ui.screen.Screen;

/**
 * The type Screen typescript generator.
 *
 * @author Christos Tsakostas
 */
public class ScreenTypescriptGenerator extends AbstractUnitTemplateGenerator<Screen> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Screen typescript generator.
   *
   * @param screenTypescriptTransformer the screen typescript transformer
   * @param templateEngine the template engine
   * @param exporter the exporter
   */
  public ScreenTypescriptGenerator(
      ScreenTypescriptTransformer screenTypescriptTransformer,
      TemplateEngine templateEngine,
      Exporter exporter) {
    super(screenTypescriptTransformer, templateEngine, exporter);
  }
}
