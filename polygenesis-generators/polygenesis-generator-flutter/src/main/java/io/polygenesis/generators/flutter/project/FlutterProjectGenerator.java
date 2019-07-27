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

package io.polygenesis.generators.flutter.project;

import io.polygenesis.core.AbstractProjectGenerator;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.generators.flutter.DartFolderFileConstants;
import io.polygenesis.generators.flutter.project.app.FlutterAppGenerator;
import io.polygenesis.metamodels.appflutter.FlutterApp;
import io.polygenesis.metamodels.appflutter.FlutterProject;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The type Flutter project generator.
 *
 * @author Christos Tsakostas
 */
public class FlutterProjectGenerator extends AbstractProjectGenerator<FlutterProject> {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private final FlutterAppGenerator flutterAppGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Flutter project generator.
   *
   * @param generationPath the generation path
   * @param flutterAppGenerator the flutter app generator
   */
  public FlutterProjectGenerator(Path generationPath, FlutterAppGenerator flutterAppGenerator) {
    super(generationPath);
    this.flutterAppGenerator = flutterAppGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  protected void generateBootstrap(FlutterProject project) {
    flutterAppGenerator.generate(
        FlutterApp.of(),
        ExportInfo.file(
            Paths.get(getGenerationPath().toString(), DartFolderFileConstants.LIB),
            String.format("myapp%s", DartFolderFileConstants.DART_POSTFIX)),
        project);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

}
