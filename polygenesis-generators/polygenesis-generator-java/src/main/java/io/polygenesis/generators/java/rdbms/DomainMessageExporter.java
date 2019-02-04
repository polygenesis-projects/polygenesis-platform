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

package io.polygenesis.generators.java.rdbms;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.Name;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.generators.java.shared.ObjectProjection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeSet;

/**
 * The type Domain message exporter.
 *
 * @author Christos Tsakostas
 */
public class DomainMessageExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message exporter.
   *
   * @param freemarkerService the freemarker service
   */
  public DomainMessageExporter(FreemarkerService freemarkerService) {
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param context the context
   */
  public void export(Path generationPath, PackageName rootPackageName, Name context) {
    exportDomainMessageData(generationPath, rootPackageName, context);
    exportDomainMessageDataConverter(generationPath, rootPackageName, context);
    exportDomainMessageDataRepository(generationPath, rootPackageName, context);
  }

  // ===============================================================================================
  // PRIVATE - DOMAIN MESSAGE DATA
  // ===============================================================================================
  private void exportDomainMessageData(
      Path generationPath, PackageName rootPackageName, Name context) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("projection", makeDomainMessageDataProjection(rootPackageName, context));
    dataModel.put("context", context.getText());

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-persistence-rdbms/"
            + "message/ContextDomainMessageData.java.ftl",
        makeDomainMessageDataFileName(generationPath, rootPackageName, context));
  }

  private ObjectProjection makeDomainMessageDataProjection(
      PackageName rootPackageName, Name context) {
    return new ObjectProjection(
        rootPackageName.getText(),
        new TreeSet<>(),
        "The " + TextConverter.toUpperCamel(context.getText()) + "Domain Message Data.",
        TextConverter.toUpperCamel(context.getText() + "DomainMessageData"),
        TextConverter.toUpperCamel(context.getText() + "DomainMessageData"),
        new LinkedHashSet<>(),
        new LinkedHashSet<>(),
        new LinkedHashSet<>());
  }

  private Path makeDomainMessageDataFileName(
      Path generationPath, PackageName rootPackageName, Name context) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        rootPackageName.toPath().toString(),
        TextConverter.toUpperCamel(context.getText()) + "DomainMessageData.java");
  }

  // ===============================================================================================
  // PRIVATE - DOMAIN MESSAGE DATA CONVERTER
  // ===============================================================================================

  private void exportDomainMessageDataConverter(
      Path generationPath, PackageName rootPackageName, Name context) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("projection", makeDomainMessageDataConverterProjection(rootPackageName, context));
    dataModel.put("context", context.getText());

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-persistence-rdbms/"
            + "message/ContextDomainMessageDataConverter.java.ftl",
        makeDomainMessageDataConverterFileName(generationPath, rootPackageName, context));
  }

  private ObjectProjection makeDomainMessageDataConverterProjection(
      PackageName rootPackageName, Name context) {
    return new ObjectProjection(
        rootPackageName.getText(),
        new TreeSet<>(),
        "The " + TextConverter.toUpperCamel(context.getText()) + "Domain Message Data Converter.",
        TextConverter.toUpperCamel(context.getText() + "DomainMessageDataConverter"),
        TextConverter.toUpperCamel(context.getText() + "DomainMessageDataConverter")
            + " extends \n\t\tAbstractDomainMessageDataConverter<"
            + TextConverter.toUpperCamel(context.getText())
            + "DomainMessageData>",
        new LinkedHashSet<>(),
        new LinkedHashSet<>(),
        new LinkedHashSet<>());
  }

  private Path makeDomainMessageDataConverterFileName(
      Path generationPath, PackageName rootPackageName, Name context) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        rootPackageName.toPath().toString(),
        TextConverter.toUpperCamel(context.getText()) + "DomainMessageDataConverter.java");
  }

  // ===============================================================================================
  // PRIVATE - DOMAIN MESSAGE DATA REPOSITORY
  // ===============================================================================================

  private void exportDomainMessageDataRepository(
      Path generationPath, PackageName rootPackageName, Name context) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put(
        "projection", makeDomainMessageDataRepositoryProjection(rootPackageName, context));
    dataModel.put("context", context.getText());

    freemarkerService.export(
        dataModel,
        "polygenesis-generator-java-persistence-rdbms/"
            + "message/ContextDomainMessageDataRepository.java.ftl",
        makeDomainMessageDataRepositoryFileName(generationPath, rootPackageName, context));
  }

  private ObjectProjection makeDomainMessageDataRepositoryProjection(
      PackageName rootPackageName, Name context) {
    return new ObjectProjection(
        rootPackageName.getText(),
        new TreeSet<>(),
        "The " + TextConverter.toUpperCamel(context.getText()) + "Domain Message Data Repository.",
        TextConverter.toUpperCamel(context.getText() + "DomainMessageDataRepository"),
        TextConverter.toUpperCamel(context.getText() + "DomainMessageDataRepository")
            + " extends \n\t\tSpringDomainMessageDataRepository<"
            + TextConverter.toUpperCamel(context.getText())
            + "DomainMessageData>",
        new LinkedHashSet<>(),
        new LinkedHashSet<>(),
        new LinkedHashSet<>());
  }

  private Path makeDomainMessageDataRepositoryFileName(
      Path generationPath, PackageName rootPackageName, Name context) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        rootPackageName.toPath().toString(),
        TextConverter.toUpperCamel(context.getText()) + "DomainMessageDataRepository.java");
  }
}
