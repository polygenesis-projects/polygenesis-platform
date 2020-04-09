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

package io.polygenesis.generators.java.batchprocesssubscriber;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.batchprocesssubscriber.abstractsubscriber.BatchProcessAbstractSubscriber;
import io.polygenesis.generators.java.batchprocesssubscriber.abstractsubscriber.BatchProcessAbstractSubscriberGenerator;
import io.polygenesis.generators.java.batchprocesssubscriber.dispatcher.BatchProcessDispatcher;
import io.polygenesis.generators.java.batchprocesssubscriber.dispatcher.BatchProcessDispatcherGenerator;
import io.polygenesis.generators.java.batchprocesssubscriber.registry.BatchProcessSubscriberRegistry;
import io.polygenesis.generators.java.batchprocesssubscriber.registry.BatchProcessSubscriberRegistryGenerator;
import io.polygenesis.generators.java.batchprocesssubscriber.subscriber.BatchProcessSubscriber;
import io.polygenesis.generators.java.batchprocesssubscriber.subscriber.BatchProcessSubscriberGenerator;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import io.polygenesis.models.periodicprocess.BatchProcessMetamodel;
import io.polygenesis.models.periodicprocess.BatchProcessMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class BatchProcessSubscriberMetamodelGenerator extends AbstractMetamodelGenerator {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final ContextName contextName;
  private final BatchProcessAbstractSubscriberGenerator batchProcessAbstractSubscriberGenerator;
  private final BatchProcessDispatcherGenerator batchProcessDispatcherGenerator;
  private final BatchProcessSubscriberRegistryGenerator batchProcessSubscriberRegistryGenerator;
  private final BatchProcessSubscriberGenerator batchProcessSubscriberGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process subscriber metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @param batchProcessAbstractSubscriberGenerator the batch process abstract subscriber generator
   * @param batchProcessDispatcherGenerator the batch process dispatcher generator
   * @param batchProcessSubscriberRegistryGenerator the batch process subscriber registry generator
   * @param batchProcessSubscriberGenerator the batch process subscriber generator
   */
  public BatchProcessSubscriberMetamodelGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ContextName contextName,
      BatchProcessAbstractSubscriberGenerator batchProcessAbstractSubscriberGenerator,
      BatchProcessDispatcherGenerator batchProcessDispatcherGenerator,
      BatchProcessSubscriberRegistryGenerator batchProcessSubscriberRegistryGenerator,
      BatchProcessSubscriberGenerator batchProcessSubscriberGenerator) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.batchProcessAbstractSubscriberGenerator = batchProcessAbstractSubscriberGenerator;
    this.batchProcessDispatcherGenerator = batchProcessDispatcherGenerator;
    this.batchProcessSubscriberRegistryGenerator = batchProcessSubscriberRegistryGenerator;
    this.batchProcessSubscriberGenerator = batchProcessSubscriberGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<MetamodelRepository<?>> modelRepositories) {
    BatchProcessAbstractSubscriber batchProcessAbstractSubscriber = makeAbstractSubscriber();
    batchProcessAbstractSubscriberGenerator.generate(
        batchProcessAbstractSubscriber,
        abstractSubscriberExportInfo(getGenerationPath(), batchProcessAbstractSubscriber));

    BatchProcessDispatcher dispatcher = makeBatchProcessDispatcher();
    batchProcessDispatcherGenerator.generate(
        dispatcher, dispatcherExportInfo(getGenerationPath(), dispatcher));

    BatchProcessSubscriberRegistry batchProcessSubscriberRegistry = makeRegistry();
    batchProcessSubscriberRegistryGenerator.generate(
        batchProcessSubscriberRegistry,
        registryExportInfo(getGenerationPath(), batchProcessSubscriberRegistry));

    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, BatchProcessMetamodelRepository.class)
        .getItems()
        .forEach(
            batchProcessMetamodel ->
                batchProcessSubscriberGenerator.generate(
                    makeBatchProcessSubscriber(batchProcessMetamodel),
                    subscriberExportInfo(getGenerationPath(), batchProcessMetamodel),
                    batchProcessAbstractSubscriber));
  }

  // ===============================================================================================
  // PRIVATE - Objects
  // ===============================================================================================

  /**
   * Make abstract subscriber abstract subscriber.
   *
   * @return the abstract subscriber
   */
  private BatchProcessAbstractSubscriber makeAbstractSubscriber() {
    return new BatchProcessAbstractSubscriber(
        new ObjectName(
            String.format(
                "%sBatchProcessMessageSubscriber",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  /**
   * Make dispatcher dispatcher.
   *
   * @return the dispatcher
   */
  private BatchProcessDispatcher makeBatchProcessDispatcher() {
    return new BatchProcessDispatcher(
        new ObjectName(
            String.format(
                "%sBatchProcessMessageDispatcher",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  /**
   * Make registry registry.
   *
   * @return the registry
   */
  private BatchProcessSubscriberRegistry makeRegistry() {
    return new BatchProcessSubscriberRegistry(
        new ObjectName(
            String.format(
                "%sBatchProcessMessageSubscriberRegistry",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  /**
   * Make batch process subscriber batch process subscriber.
   *
   * @param batchProcessMetamodel the batch process metamodel
   * @return the batch process subscriber
   */
  private BatchProcessSubscriber makeBatchProcessSubscriber(
      BatchProcessMetamodel batchProcessMetamodel) {
    return new BatchProcessSubscriber(
        batchProcessMetamodel.getObjectName(),
        batchProcessMetamodel.getPackageName(),
        batchProcessMetamodel.getCommandServiceMethod(),
        batchProcessMetamodel.getQueryServiceMethod(),
        batchProcessMetamodel.getQueryCollectionItem());
  }

  // ===============================================================================================
  // PRIVATE - ExportInfo
  // ===============================================================================================

  private ExportInfo abstractSubscriberExportInfo(
      Path generationPath, BatchProcessAbstractSubscriber batchProcessAbstractSubscriber) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            batchProcessAbstractSubscriber.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(batchProcessAbstractSubscriber.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo dispatcherExportInfo(Path generationPath, BatchProcessDispatcher dispatcher) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            dispatcher.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(dispatcher.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo registryExportInfo(
      Path generationPath, BatchProcessSubscriberRegistry batchProcessSubscriberRegistry) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            batchProcessSubscriberRegistry.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(batchProcessSubscriberRegistry.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo subscriberExportInfo(
      Path generationPath, BatchProcessMetamodel batchProcessMetamodel) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            batchProcessMetamodel.getPackageName().toPath().toString()),
        String.format(
            "On%s%s",
            TextConverter.toUpperCamel(batchProcessMetamodel.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }
}
