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

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.ui.Feature;
import io.polygenesis.models.ui.container.AbstractContainer;
import java.nio.file.Path;

/**
 * The type Ui container exporter.
 *
 * @author Christos Tsakostas
 */
public class UiContainerExporter {

  private final UiContainerHtmlExporter uiContainerHtmlExporter;
  private final UiContainerScssExporter uiContainerScssExporter;
  private final UiContainerTypescriptExporter uiContainerTypescriptExporter;
  private final UiContainerTypescriptSpecExporter uiContainerTypescriptSpecExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Ui container exporter.
   *
   * @param uiContainerHtmlExporter the ui container html exporter
   * @param uiContainerScssExporter the ui container scss exporter
   * @param uiContainerTypescriptExporter the ui container typescript exporter
   * @param uiContainerTypescriptSpecExporter the ui container typescript spec exporter
   */
  public UiContainerExporter(
      UiContainerHtmlExporter uiContainerHtmlExporter,
      UiContainerScssExporter uiContainerScssExporter,
      UiContainerTypescriptExporter uiContainerTypescriptExporter,
      UiContainerTypescriptSpecExporter uiContainerTypescriptSpecExporter) {
    this.uiContainerHtmlExporter = uiContainerHtmlExporter;
    this.uiContainerScssExporter = uiContainerScssExporter;
    this.uiContainerTypescriptExporter = uiContainerTypescriptExporter;
    this.uiContainerTypescriptSpecExporter = uiContainerTypescriptSpecExporter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export feature container.
   *
   * @param generationPathApp the generation path app
   * @param feature the feature
   * @param container the container
   */
  public void exportFeatureContainer(
      Path generationPathApp, Feature feature, AbstractContainer container) {

    String folderInsideApp = TextConverter.toLowerHyphen(feature.getFeatureName().getText());

    uiContainerHtmlExporter.exportHtml(
        generationPathApp, folderInsideApp, container, UiContainerFolderType.PAGE);
    uiContainerScssExporter.exportScss(
        generationPathApp, folderInsideApp, container, UiContainerFolderType.PAGE);
    uiContainerTypescriptExporter.exportTypescript(
        generationPathApp, folderInsideApp, container, UiContainerFolderType.PAGE);
    uiContainerTypescriptSpecExporter.exportTypescriptSpec(
        generationPathApp, folderInsideApp, container, UiContainerFolderType.PAGE);
  }

  /**
   * Export component container.
   *
   * @param generationPathApp the generation path app
   * @param folderInsideApp the folder inside app
   * @param container the container
   */
  public void exportComponentContainer(
      Path generationPathApp, String folderInsideApp, AbstractContainer container) {

    uiContainerHtmlExporter.exportHtml(
        generationPathApp, folderInsideApp, container, UiContainerFolderType.COMPONENT);
    uiContainerScssExporter.exportScss(
        generationPathApp, folderInsideApp, container, UiContainerFolderType.COMPONENT);
    uiContainerTypescriptExporter.exportTypescript(
        generationPathApp, folderInsideApp, container, UiContainerFolderType.COMPONENT);
    uiContainerTypescriptSpecExporter.exportTypescriptSpec(
        generationPathApp, folderInsideApp, container, UiContainerFolderType.COMPONENT);
  }

  /**
   * Export layout container.
   *
   * @param generationPathApp the generation path app
   * @param folderInsideApp the folder inside app
   * @param container the container
   */
  public void exportLayoutContainer(
      Path generationPathApp, String folderInsideApp, AbstractContainer container) {

    uiContainerHtmlExporter.exportHtml(
        generationPathApp, folderInsideApp, container, UiContainerFolderType.LAYOUT);
    uiContainerScssExporter.exportScss(
        generationPathApp, folderInsideApp, container, UiContainerFolderType.LAYOUT);
    uiContainerTypescriptExporter.exportTypescript(
        generationPathApp, folderInsideApp, container, UiContainerFolderType.LAYOUT);
    uiContainerTypescriptSpecExporter.exportTypescriptSpec(
        generationPathApp, folderInsideApp, container, UiContainerFolderType.LAYOUT);
  }
}
