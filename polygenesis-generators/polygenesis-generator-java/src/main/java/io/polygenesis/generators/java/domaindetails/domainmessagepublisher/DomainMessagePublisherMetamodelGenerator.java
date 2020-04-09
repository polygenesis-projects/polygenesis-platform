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

package io.polygenesis.generators.java.domaindetails.domainmessagepublisher;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.dataconverter.DomainMessagePublishedDataConverter;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.dataconverter.DomainMessagePublishedDataConverterGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publishdto.DomainMessagePublishDto;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publishdto.DomainMessagePublishDtoGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publishdtoconverter.DomainMessagePublishDtoConverter;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publishdtoconverter.DomainMessagePublishDtoConverterGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.DomainMessagePublisher;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.DomainMessagePublisherGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisher.ScheduledDomainMessagePublisher;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisher.ScheduledDomainMessagePublisherGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisherroute.ScheduledDomainMessagePublisherRoute;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisherroute.ScheduledDomainMessagePublisherRouteGenerator;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class DomainMessagePublisherMetamodelGenerator extends AbstractMetamodelGenerator {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final ContextName contextName;
  private final DomainMessagePublisherGenerator domainMessagePublisherGenerator;
  private final ScheduledDomainMessagePublisherRouteGenerator
      scheduledDomainMessagePublisherRouteGenerator;
  private final ScheduledDomainMessagePublisherGenerator scheduledDomainMessagePublisherGenerator;

  private final DomainMessagePublishedDataConverterGenerator
      domainMessagePublishedDataConverterGenerator;

  private final DomainMessagePublishDtoGenerator domainMessagePublishDtoGenerator;
  private final DomainMessagePublishDtoConverterGenerator domainMessagePublishDtoConverterGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message publisher metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @param domainMessagePublisherGenerator the domain message publisher generator
   * @param scheduledDomainMessagePublisherRouteGenerator the scheduled domain message publisher
   *     route generator
   * @param scheduledDomainMessagePublisherGenerator the scheduled domain message publisher
   *     generator
   * @param domainMessagePublishedDataConverterGenerator the domain message published data converter
   *     generator
   * @param domainMessagePublishDtoGenerator the domain message publish dto generator
   * @param domainMessagePublishDtoConverterGenerator the domain message publish dto converter
   *     generator
   */
  public DomainMessagePublisherMetamodelGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ContextName contextName,
      DomainMessagePublisherGenerator domainMessagePublisherGenerator,
      ScheduledDomainMessagePublisherRouteGenerator scheduledDomainMessagePublisherRouteGenerator,
      ScheduledDomainMessagePublisherGenerator scheduledDomainMessagePublisherGenerator,
      DomainMessagePublishedDataConverterGenerator domainMessagePublishedDataConverterGenerator,
      DomainMessagePublishDtoGenerator domainMessagePublishDtoGenerator,
      DomainMessagePublishDtoConverterGenerator domainMessagePublishDtoConverterGenerator) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.domainMessagePublisherGenerator = domainMessagePublisherGenerator;
    this.scheduledDomainMessagePublisherRouteGenerator =
        scheduledDomainMessagePublisherRouteGenerator;
    this.scheduledDomainMessagePublisherGenerator = scheduledDomainMessagePublisherGenerator;
    this.domainMessagePublishedDataConverterGenerator =
        domainMessagePublishedDataConverterGenerator;
    this.domainMessagePublishDtoGenerator = domainMessagePublishDtoGenerator;
    this.domainMessagePublishDtoConverterGenerator = domainMessagePublishDtoConverterGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<MetamodelRepository<?>> modelRepositories) {
    DomainMessagePublisher domainMessagePublisher = makeDomainMessagePublisher();
    domainMessagePublisherGenerator.generate(
        domainMessagePublisher,
        domainMessagePublisherExportInfo(getGenerationPath(), domainMessagePublisher),
        contextName);

    ScheduledDomainMessagePublisherRoute scheduledDomainMessagePublisherRoute =
        makeScheduledDomainMessagePublisherRoute();
    scheduledDomainMessagePublisherRouteGenerator.generate(
        scheduledDomainMessagePublisherRoute,
        scheduledDomainMessagePublisherRouteExportInfo(
            getGenerationPath(), scheduledDomainMessagePublisherRoute),
        contextName);

    ScheduledDomainMessagePublisher scheduledDomainMessagePublisher =
        makeScheduledDomainMessagePublisher();
    scheduledDomainMessagePublisherGenerator.generate(
        scheduledDomainMessagePublisher,
        scheduledDomainMessagePublisherExportInfo(
            getGenerationPath(), scheduledDomainMessagePublisher),
        contextName);

    DomainMessagePublishedDataConverter domainMessagePublishedDataConverter =
        makeDomainMessagePublishedDataConverter();
    domainMessagePublishedDataConverterGenerator.generate(
        domainMessagePublishedDataConverter,
        domainMessagePublishedDataConverterExportInfo(
            getGenerationPath(), domainMessagePublishedDataConverter),
        contextName);

    DomainMessagePublishDto domainMessagePublishDto = makeDomainMessagePublishDto();
    domainMessagePublishDtoGenerator.generate(
        domainMessagePublishDto,
        domainMessagePublishDtoExportInfo(getGenerationPath(), domainMessagePublishDto),
        contextName);

    DomainMessagePublishDtoConverter domainMessagePublishDtoConverter =
        makeDomainMessagePublishDtoConverter();
    domainMessagePublishDtoConverterGenerator.generate(
        domainMessagePublishDtoConverter,
        domainMessagePublishDtoConverterExportInfo(
            getGenerationPath(), domainMessagePublishDtoConverter),
        contextName);
  }

  // ===============================================================================================
  // PRIVATE - Objects
  // ===============================================================================================

  private DomainMessagePublisher makeDomainMessagePublisher() {
    return new DomainMessagePublisher(
        new ObjectName(
            String.format(
                "%sDomainMessagePublisher", TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())),
        contextName);
  }

  private ScheduledDomainMessagePublisherRoute makeScheduledDomainMessagePublisherRoute() {
    return new ScheduledDomainMessagePublisherRoute(
        new ObjectName(
            String.format(
                "%sScheduledDomainMessagePublisherRoute",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())),
        makeScheduledDomainMessagePublisher());
  }

  private ScheduledDomainMessagePublisher makeScheduledDomainMessagePublisher() {
    return new ScheduledDomainMessagePublisher(
        new ObjectName(
            String.format(
                "%sScheduledDomainMessagePublisher",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private DomainMessagePublishedDataConverter makeDomainMessagePublishedDataConverter() {
    return new DomainMessagePublishedDataConverter(
        new ObjectName(
            String.format(
                "%sDomainMessagePublishedDataConverter",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private DomainMessagePublishDtoConverter makeDomainMessagePublishDtoConverter() {
    return new DomainMessagePublishDtoConverter(
        new ObjectName(
            String.format(
                "%sDomainMessagePublishDtoConverter",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private DomainMessagePublishDto makeDomainMessagePublishDto() {
    return new DomainMessagePublishDto(
        new ObjectName(
            String.format(
                "%sDomainMessagePublishDto", TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  // ===============================================================================================
  // PRIVATE - ExportInfo
  // ===============================================================================================

  private ExportInfo domainMessagePublisherExportInfo(
      Path generationPath, DomainMessagePublisher domainMessagePublisher) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainMessagePublisher.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(domainMessagePublisher.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo scheduledDomainMessagePublisherRouteExportInfo(
      Path generationPath,
      ScheduledDomainMessagePublisherRoute scheduledDomainMessagePublisherRoute) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            scheduledDomainMessagePublisherRoute.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(
                scheduledDomainMessagePublisherRoute.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo scheduledDomainMessagePublisherExportInfo(
      Path generationPath, ScheduledDomainMessagePublisher scheduledDomainMessagePublisher) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            scheduledDomainMessagePublisher.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(scheduledDomainMessagePublisher.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo domainMessagePublishedDataConverterExportInfo(
      Path generationPath,
      DomainMessagePublishedDataConverter domainMessagePublishedDataConverter) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainMessagePublishedDataConverter.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(
                domainMessagePublishedDataConverter.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo domainMessagePublishDtoConverterExportInfo(
      Path generationPath, DomainMessagePublishDtoConverter domainMessagePublishDtoConverter) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainMessagePublishDtoConverter.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(domainMessagePublishDtoConverter.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo domainMessagePublishDtoExportInfo(
      Path generationPath, DomainMessagePublishDto domainMessagePublishDto) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainMessagePublishDto.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(domainMessagePublishDto.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }
}
