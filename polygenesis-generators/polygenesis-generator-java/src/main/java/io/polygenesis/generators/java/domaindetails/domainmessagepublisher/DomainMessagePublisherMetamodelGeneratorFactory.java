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

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.dataconverter.DomainMessagePublishedDataConverterGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.dataconverter.DomainMessagePublishedDataConverterMethodTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.dataconverter.DomainMessagePublishedDataConverterTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publishdto.DomainMessagePublishDtoGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publishdto.DomainMessagePublishDtoMethodTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publishdto.DomainMessagePublishDtoTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publishdtoconverter.DomainMessagePublishDtoConverterGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publishdtoconverter.DomainMessagePublishDtoConverterMethodTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publishdtoconverter.DomainMessagePublishDtoConverterTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publishdtoconverter.activity.DomainMessagePublishDtoConverterActivityRegistry;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.DomainMessagePublisherGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.DomainMessagePublisherMethodTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.DomainMessagePublisherTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.activity.DomainMessagePublisherSendActivityGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.activity.DomainMessagePublisherSendActivityTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisher.ScheduledDomainMessagePublisherGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisher.ScheduledDomainMessagePublisherMethodTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisher.ScheduledDomainMessagePublisherTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisherroute.ScheduledDomainMessagePublisherRouteGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisherroute.ScheduledDomainMessagePublisherRouteMethodTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisherroute.ScheduledDomainMessagePublisherRouteTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisherroute.activity.ConfigureActivityGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisherroute.activity.ConfigureActivityTransformer;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import java.nio.file.Path;

public final class DomainMessagePublisherMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static DomainMessagePublisherGenerator domainMessagePublisherGenerator;
  private static ScheduledDomainMessagePublisherRouteGenerator
      scheduledDomainMessagePublisherRouteGenerator;
  private static ScheduledDomainMessagePublisherGenerator scheduledDomainMessagePublisherGenerator;

  private static DomainMessagePublishedDataConverterGenerator
      domainMessagePublishedDataConverterGenerator;

  private static DomainMessagePublishDtoGenerator domainMessagePublishDtoGenerator;
  private static DomainMessagePublishDtoConverterGenerator
      domainMessagePublishDtoConverterGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    Exporter exporter = new ActiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    domainMessagePublisherGenerator =
        new DomainMessagePublisherGenerator(
            new DomainMessagePublisherTransformer(
                dataTypeTransformer,
                new DomainMessagePublisherMethodTransformer(
                    dataTypeTransformer,
                    new DomainMessagePublisherSendActivityGenerator(
                        new DomainMessagePublisherSendActivityTransformer(), templateEngine))),
            templateEngine,
            exporter);

    scheduledDomainMessagePublisherRouteGenerator =
        new ScheduledDomainMessagePublisherRouteGenerator(
            new ScheduledDomainMessagePublisherRouteTransformer(
                dataTypeTransformer,
                new ScheduledDomainMessagePublisherRouteMethodTransformer(
                    dataTypeTransformer,
                    new ConfigureActivityGenerator(
                        new ConfigureActivityTransformer(), templateEngine))),
            templateEngine,
            exporter);

    scheduledDomainMessagePublisherGenerator =
        new ScheduledDomainMessagePublisherGenerator(
            new ScheduledDomainMessagePublisherTransformer(
                dataTypeTransformer,
                new ScheduledDomainMessagePublisherMethodTransformer(dataTypeTransformer)),
            templateEngine,
            exporter);

    domainMessagePublishedDataConverterGenerator =
        new DomainMessagePublishedDataConverterGenerator(
            new DomainMessagePublishedDataConverterTransformer(
                dataTypeTransformer,
                new DomainMessagePublishedDataConverterMethodTransformer(dataTypeTransformer)),
            templateEngine,
            exporter);

    domainMessagePublishDtoGenerator =
        new DomainMessagePublishDtoGenerator(
            new DomainMessagePublishDtoTransformer(
                dataTypeTransformer,
                new DomainMessagePublishDtoMethodTransformer(dataTypeTransformer)),
            templateEngine,
            exporter);

    domainMessagePublishDtoConverterGenerator =
        new DomainMessagePublishDtoConverterGenerator(
            new DomainMessagePublishDtoConverterTransformer(
                dataTypeTransformer,
                new DomainMessagePublishDtoConverterMethodTransformer(
                    dataTypeTransformer, new DomainMessagePublishDtoConverterActivityRegistry())),
            templateEngine,
            exporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private DomainMessagePublisherMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance domain message publisher metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @return the domain message publisher metamodel generator
   */
  public static DomainMessagePublisherMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ContextName contextName) {
    return new DomainMessagePublisherMetamodelGenerator(
        generationPath,
        rootPackageName,
        contextName,
        domainMessagePublisherGenerator,
        scheduledDomainMessagePublisherRouteGenerator,
        scheduledDomainMessagePublisherGenerator,
        domainMessagePublishedDataConverterGenerator,
        domainMessagePublishDtoGenerator,
        domainMessagePublishDtoConverterGenerator);
  }
}
