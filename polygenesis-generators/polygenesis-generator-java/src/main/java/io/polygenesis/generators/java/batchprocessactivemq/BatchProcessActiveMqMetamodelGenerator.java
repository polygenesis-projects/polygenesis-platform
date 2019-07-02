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

package io.polygenesis.generators.java.batchprocessactivemq;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.batchprocessactivemq.dispatcherroute.BatchProcessDispatcherRoute;
import io.polygenesis.generators.java.batchprocessactivemq.dispatcherroute.BatchProcessDispatcherRouteGenerator;
import io.polygenesis.generators.java.batchprocessactivemq.publisher.BatchProcessMessagePublisher;
import io.polygenesis.generators.java.batchprocessactivemq.publisher.BatchProcessPublisherGenerator;
import io.polygenesis.generators.java.batchprocesssubscriber.dispatcher.BatchProcessDispatcher;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * The type Batch process subscriber metamodel generator.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessActiveMqMetamodelGenerator extends AbstractMetamodelGenerator {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final ContextName contextName;
  private final BatchProcessDispatcherRouteGenerator batchProcessDispatcherRouteGenerator;
  private final BatchProcessPublisherGenerator batchProcessPublisherGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process active mq metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @param batchProcessDispatcherRouteGenerator the batch process dispatcher route generator
   * @param batchProcessPublisherGenerator the batch process publisher generator
   */
  public BatchProcessActiveMqMetamodelGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ContextName contextName,
      BatchProcessDispatcherRouteGenerator batchProcessDispatcherRouteGenerator,
      BatchProcessPublisherGenerator batchProcessPublisherGenerator) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.batchProcessDispatcherRouteGenerator = batchProcessDispatcherRouteGenerator;
    this.batchProcessPublisherGenerator = batchProcessPublisherGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public void generate(Set<MetamodelRepository> modelRepositories) {
    BatchProcessDispatcherRoute batchProcessDispatcherRoute = makeBatchProcessDispatcherRoute();
    batchProcessDispatcherRouteGenerator.generate(
        batchProcessDispatcherRoute,
        batchProcessDispatcherRouteExportInfo(getGenerationPath(), batchProcessDispatcherRoute),
        contextName);

    BatchProcessMessagePublisher batchProcessMessagePublisher = makeBatchProcessMessagePublisher();
    batchProcessPublisherGenerator.generate(
        batchProcessMessagePublisher,
        batchProcessMessagePublisherExportInfo(getGenerationPath(), batchProcessMessagePublisher),
        contextName);
  }

  // ===============================================================================================
  // PRIVATE - Objects
  // ===============================================================================================

  /**
   * Make batch process dispatcher route batch process dispatcher route.
   *
   * @return the batch process dispatcher route
   */
  private BatchProcessDispatcherRoute makeBatchProcessDispatcherRoute() {
    return new BatchProcessDispatcherRoute(
        new ObjectName(
            String.format(
                "%sBatchProcessMessageDispatcherRoute",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())),
        makeBatchProcessDispatcher());
  }

  /**
   * Make batch process message publisher batch process message publisher.
   *
   * @return the batch process message publisher
   */
  private BatchProcessMessagePublisher makeBatchProcessMessagePublisher() {
    return new BatchProcessMessagePublisher(
        new ObjectName(
            String.format(
                "%sBatchProcessMessagePublisher",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  /**
   * Make batch process dispatcher batch process dispatcher.
   *
   * @return the batch process dispatcher
   */
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
      Path generationPath, BatchProcessDispatcherRoute batchProcessDispatcherRoute) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            batchProcessDispatcherRoute.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(batchProcessDispatcherRoute.getObjectName().getText()),
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
