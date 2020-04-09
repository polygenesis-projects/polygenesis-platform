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

package io.polygenesis.generators.angular.project.appmodule;

import io.polygenesis.core.AbstractUnitTemplateGenerator;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.angular.project.appmodule.source.AppModule;

public class AppModuleGenerator extends AbstractUnitTemplateGenerator<AppModule> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new App module generator.
   *
   * @param appModuleTransformer the app module transformer
   * @param templateEngine the template engine
   * @param exporter the exporter
   */
  public AppModuleGenerator(
      AppModuleTransformer appModuleTransformer, TemplateEngine templateEngine, Exporter exporter) {
    super(appModuleTransformer, templateEngine, exporter);
  }
}
