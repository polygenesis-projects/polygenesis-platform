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

package io.polygenesis.generators.angular.exporters.ui.container;

import static io.polygenesis.generators.angular.exporters.ui.UiExporterConstants.FTL_APP_SHARED_LAYOUT_SPEC_TS;
import static io.polygenesis.generators.angular.exporters.ui.UiExporterConstants.FTL_CONTAINER_SPEC_TS;
import static io.polygenesis.generators.angular.exporters.ui.UiExporterConstants.POSTFIX_COMPONENT_TYPESCRIPT_SPEC;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.models.ui.container.AbstractContainer;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Ui container exporter.
 *
 * @author Christos Tsakostas
 */
public class UiContainerTypescriptSpecExporter extends AbstractUiContainerPart {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Ui container typescript spec exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public UiContainerTypescriptSpecExporter(FreemarkerService freemarkerService) {
    super(freemarkerService);
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export typescript spec.
   *
   * @param generationPathApp the generation path app
   * @param contextFeaturePath the context feature path
   * @param container the container
   * @param uiContainerFolderType the ui container folder type
   */
  public void exportTypescriptSpec(
      Path generationPathApp,
      Path contextFeaturePath,
      AbstractContainer container,
      UiContainerFolderType uiContainerFolderType) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("container", container);

    exportContainerPart(
        generationPathApp,
        contextFeaturePath,
        container,
        uiContainerFolderType,
        getFtlTemplateBy(uiContainerFolderType),
        POSTFIX_COMPONENT_TYPESCRIPT_SPEC,
        dataModel);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================
  private String getFtlTemplateBy(UiContainerFolderType type) {

    switch (type) {
      case COMPONENT:
        return FTL_CONTAINER_SPEC_TS;
      case LAYOUT:
        return FTL_APP_SHARED_LAYOUT_SPEC_TS;
      case PAGE:
        return FTL_CONTAINER_SPEC_TS;
      default:
        throw new IllegalArgumentException();
    }
  }
}
