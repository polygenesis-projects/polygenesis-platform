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

package io.polygenesis.generators.flutter.context.ui.widget.activity;

import io.polygenesis.abstraction.thing.ActivityTemplateTransformer;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.Project;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.flutter.project.FlutterProjectDataExtractor;
import java.util.HashMap;
import java.util.Map;

public class BuildActivityTransformer implements ActivityTemplateTransformer<Function> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================
  private final FlutterProjectDataExtractor flutterProjectDataExtractor;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Build activity transformer.
   *
   * @param flutterProjectDataExtractor the flutter project data extractor
   */
  @SuppressWarnings("CPD-START")
  public BuildActivityTransformer(FlutterProjectDataExtractor flutterProjectDataExtractor) {
    this.flutterProjectDataExtractor = flutterProjectDataExtractor;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings({"unchecked", "rawtypes", "CPD-END"})
  @Override
  public TemplateData transform(Function source, Object... args) {
    Project project = (Project) args[0];

    BuildTemplateData data = new BuildTemplateData(flutterProjectDataExtractor.screens(project));

    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("data", data);

    return new TemplateData(dataModel, "polygenesis-dart/lib/app/activity-build.dart.ftl");
  }
}
