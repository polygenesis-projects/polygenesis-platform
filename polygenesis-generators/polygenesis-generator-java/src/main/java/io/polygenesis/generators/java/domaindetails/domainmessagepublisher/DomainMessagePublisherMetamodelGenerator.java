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

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.batchprocesssubscriber.dispatcher.BatchProcessDispatcher;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisherroute.ScheduledDomainMessagePublisherRoute;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisherroute.ScheduledDomainMessagePublisherRouteGenerator;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.BatchProcessMessagePublisher;
import io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher.ScheduledDomainMessagePublisherGenerator;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * @author Christos Tsakostas
 */
public class DomainMessagePublisherMetamodelGenerator extends AbstractMetamodelGenerator {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final ContextName contextName;
  private final ScheduledDomainMessagePublisherRouteGenerator scheduledDomainMessagePublisherRouteGenerator;
  private final ScheduledDomainMessagePublisherGenerator scheduledDomainMessagePublisherGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public DomainMessagePublisherMetamodelGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ContextName contextName,
      ScheduledDomainMessagePublisherRouteGenerator scheduledDomainMessagePublisherRouteGenerator,
      ScheduledDomainMessagePublisherGenerator scheduledDomainMessagePublisherGenerator) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.scheduledDomainMessagePublisherRouteGenerator = scheduledDomainMessagePublisherRouteGenerator;
    this.scheduledDomainMessagePublisherGenerator = scheduledDomainMessagePublisherGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public void generate(Set<MetamodelRepository> modelRepositories) {
    ScheduledDomainMessagePublisherRoute scheduledDomainMessagePublisherRoute = makeScheduledDomainMessagePublisherRoute();
    scheduledDomainMessagePublisherRouteGenerator.generate(
        scheduledDomainMessagePublisherRoute,
        batchProcessDispatcherRouteExportInfo(getGenerationPath(),
            scheduledDomainMessagePublisherRoute),
        contextName);

    BatchProcessMessagePublisher batchProcessMessagePublisher = makeBatchProcessMessagePublisher();
    scheduledDomainMessagePublisherGenerator.generate(
        batchProcessMessagePublisher,
        batchProcessMessagePublisherExportInfo(getGenerationPath(), batchProcessMessagePublisher),
        contextName);
  }

  // ===============================================================================================
  // PRIVATE - Objects
  // ===============================================================================================

  private ScheduledDomainMessagePublisherRoute makeScheduledDomainMessagePublisherRoute() {
    return new ScheduledDomainMessagePublisherRoute(
        new ObjectName(
            String.format(
                "%sScheduledDomainMessagePublisherRoute",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())),
        makeBatchProcessDispatcher());
  }

  private BatchProcessMessagePublisher makeBatchProcessMessagePublisher() {
    return new BatchProcessMessagePublisher(
        new ObjectName(
            String.format(
                "%sBatchProcessMessagePublisher",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private BatchProcessDispatcher makeBatchProcessDispatcher() {
    return new BatchProcessDispatcher(
        new ObjectName(
            String.format(
                "%sBatchProcessMessageDispatcher",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  // ===============================================================================================
  // PRIVATE - ExportInfo
  // ===============================================================================================

  private ExportInfo batchProcessDispatcherRouteExportInfo(
      Path generationPath, ScheduledDomainMessagePublisherRoute scheduledDomainMessagePublisherRoute) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            scheduledDomainMessagePublisherRoute.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(scheduledDomainMessagePublisherRoute.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo batchProcessMessagePublisherExportInfo(
      Path generationPath, BatchProcessMessagePublisher batchProcessMessagePublisher) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            batchProcessMessagePublisher.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(batchProcessMessagePublisher.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }
}
