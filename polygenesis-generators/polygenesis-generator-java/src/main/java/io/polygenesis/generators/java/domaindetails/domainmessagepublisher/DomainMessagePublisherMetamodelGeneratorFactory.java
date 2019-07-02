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

package io.polygenesis.generators.java.domaindetails.domainmessagepublisher;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.JavaDataTypeTransformer;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisherroute.ScheduledDomainMessagePublisherRouteGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisherroute.ScheduledDomainMessagePublisherRouteTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisherroute.ScheduledDomainMessagePublisherRouteMethodTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisherroute.activity.ConfigureActivityGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisherroute.activity.ConfigureActivityTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.ScheduledDomainMessagePublisherMethodTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.ScheduledDomainMessagePublisherGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.ScheduledDomainMessagePublisherTransformer;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.activity.BatchProcessPublisherSendActivityGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.activity.BatchProcessPublisherSendActivityTransformer;
import java.nio.file.Path;

/**
 * @author Christos Tsakostas
 */
public final class DomainMessagePublisherMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static ScheduledDomainMessagePublisherRouteGenerator scheduledDomainMessagePublisherRouteGenerator;
  private static ScheduledDomainMessagePublisherGenerator scheduledDomainMessagePublisherGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    Exporter exporter = new ActiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

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
                new ScheduledDomainMessagePublisherMethodTransformer(
                    dataTypeTransformer,
                    new BatchProcessPublisherSendActivityGenerator(
                        new BatchProcessPublisherSendActivityTransformer(), templateEngine))),
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

  public static DomainMessagePublisherMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ContextName contextName) {
    return new DomainMessagePublisherMetamodelGenerator(
        generationPath,
        rootPackageName,
        contextName,
        scheduledDomainMessagePublisherRouteGenerator,
        scheduledDomainMessagePublisherGenerator);
  }
}
