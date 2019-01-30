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

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.path.PathService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.ui.container.AbstractContainer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The type Abstract ui container part.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractUiContainerPart {

  /** The Freemarker service. */
  protected final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract ui container part.
   *
   * @param freemarkerService the freemarker service
   */
  public AbstractUiContainerPart(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Export container part.
   *
   * @param generationPathApp the generation path app
   * @param folderInsideApp the folder inside app
   * @param container the container
   * @param uiContainerFolderType the ui container folder type
   * @param freemarkerTemplate the freemarker template
   * @param containerPartFilePostFix the container part file post fix
   */
  protected void exportContainerPart(
      Path generationPathApp,
      String folderInsideApp,
      AbstractContainer container,
      UiContainerFolderType uiContainerFolderType,
      String freemarkerTemplate,
      String containerPartFilePostFix,
      Map<String, Object> dataModel) {
    dataModel.put("container", container);

    Path componentPath =
        Paths.get(
            generationPathApp.toString(),
            TextConverter.toLowerHyphen(folderInsideApp),
            uiContainerFolderType.toString(),
            TextConverter.toLowerHyphen(container.getContainerName().getText()));

    PathService.ensurePath(componentPath);

    freemarkerService.export(
        dataModel,
        freemarkerTemplate,
        Paths.get(
            componentPath.toString(),
            TextConverter.toLowerHyphen(container.getContainerName().getText())
                + containerPartFilePostFix));
  }
}
