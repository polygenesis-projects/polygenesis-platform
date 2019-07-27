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

package io.polygenesis.generators.angular.legacy.exporters.ui.container;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.ui.Feature;
import io.polygenesis.models.ui.container.AbstractContainer;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    Path contextFeaturePath =
        Paths.get(
            TextConverter.toLowerHyphen(feature.getContextName().getText()),
            TextConverter.toLowerHyphen(feature.getFeatureName().getText()));

    uiContainerHtmlExporter.exportHtml(
        generationPathApp, contextFeaturePath, container, UiContainerFolderType.PAGE);
    uiContainerScssExporter.exportScss(
        generationPathApp, contextFeaturePath, container, UiContainerFolderType.PAGE);
    uiContainerTypescriptExporter.exportTypescript(
        generationPathApp, contextFeaturePath, container, UiContainerFolderType.PAGE);
    uiContainerTypescriptSpecExporter.exportTypescriptSpec(
        generationPathApp, contextFeaturePath, container, UiContainerFolderType.PAGE);
  }

  /**
   * Export component container.
   *
   * @param generationPathApp the generation path app
   * @param contextFeaturePath the context feature path
   * @param container the container
   */
  public void exportComponentContainer(
      Path generationPathApp, Path contextFeaturePath, AbstractContainer container) {

    uiContainerHtmlExporter.exportHtml(
        generationPathApp, contextFeaturePath, container, UiContainerFolderType.COMPONENT);
    uiContainerScssExporter.exportScss(
        generationPathApp, contextFeaturePath, container, UiContainerFolderType.COMPONENT);
    uiContainerTypescriptExporter.exportTypescript(
        generationPathApp, contextFeaturePath, container, UiContainerFolderType.COMPONENT);
    uiContainerTypescriptSpecExporter.exportTypescriptSpec(
        generationPathApp, contextFeaturePath, container, UiContainerFolderType.COMPONENT);
  }

  /**
   * Export layout container.
   *
   * @param generationPathApp the generation path app
   * @param contextFeaturePath the context feature path
   * @param container the container
   */
  public void exportLayoutContainer(
      Path generationPathApp, Path contextFeaturePath, AbstractContainer container) {

    uiContainerHtmlExporter.exportHtml(
        generationPathApp, contextFeaturePath, container, UiContainerFolderType.LAYOUT);
    uiContainerScssExporter.exportScss(
        generationPathApp, contextFeaturePath, container, UiContainerFolderType.LAYOUT);
    uiContainerTypescriptExporter.exportTypescript(
        generationPathApp, contextFeaturePath, container, UiContainerFolderType.LAYOUT);
    uiContainerTypescriptSpecExporter.exportTypescriptSpec(
        generationPathApp, contextFeaturePath, container, UiContainerFolderType.LAYOUT);
  }
}
