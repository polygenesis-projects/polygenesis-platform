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

package io.polygenesis.generators.angular.ui.container;

import static io.polygenesis.generators.angular.ui.UiExporterConstants.FTL_APP_SHARED_LAYOUT_HTML;
import static io.polygenesis.generators.angular.ui.UiExporterConstants.FTL_CONTAINER_HTML;
import static io.polygenesis.generators.angular.ui.UiExporterConstants.POSTFIX_COMPONENT_HTML;

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
public class UiContainerHtmlExporter extends AbstractUiContainerPart {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Ui container html exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public UiContainerHtmlExporter(FreemarkerService freemarkerService) {
    super(freemarkerService);
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export html.
   *
   * @param generationPathApp the generation path app
   * @param folderInsideApp the folder inside app
   * @param container the container
   * @param uiContainerFolderType the ui container folder type
   */
  public void exportHtml(
      Path generationPathApp,
      String folderInsideApp,
      AbstractContainer container,
      UiContainerFolderType uiContainerFolderType) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("container", container);

    exportContainerPart(
        generationPathApp,
        folderInsideApp,
        container,
        uiContainerFolderType,
        getFtlTemplateBy(uiContainerFolderType),
        POSTFIX_COMPONENT_HTML,
        dataModel);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================
  private String getFtlTemplateBy(UiContainerFolderType type) {

    switch (type) {
      case COMPONENT:
        return FTL_CONTAINER_HTML;
      case LAYOUT:
        return FTL_APP_SHARED_LAYOUT_HTML;
      case PAGE:
        return FTL_CONTAINER_HTML;
      default:
        throw new IllegalArgumentException();
    }
  }
}
