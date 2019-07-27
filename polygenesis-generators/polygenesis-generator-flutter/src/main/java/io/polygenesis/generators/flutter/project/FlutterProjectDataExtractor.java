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

import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Project;
import io.polygenesis.metamodels.ui.UiContextMetamodelRepository;
import io.polygenesis.metamodels.ui.screen.Screen;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Flutter project data extractor.
 *
 * @author Christos Tsakostas
 */
public class FlutterProjectDataExtractor {

  /**
   * Screens set.
   *
   * @param project the project
   * @return the set
   */
  public Set<Screen> screens(Project project) {
    return CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(project.getAllContextsMetamodelRepositories(), UiContextMetamodelRepository.class)
        .getItems()
        .stream()
        .flatMap(uiContext -> uiContext.getFeatures().stream())
        .flatMap(feature -> feature.getScreens().stream())
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }
}
