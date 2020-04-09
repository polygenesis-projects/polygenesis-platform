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

package io.polygenesis.generators.java.batchprocesssubscriber;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.batchprocesssubscriber.abstractsubscriber.BatchProcessAbstractSubscriberGenerator;
import io.polygenesis.generators.java.batchprocesssubscriber.abstractsubscriber.BatchProcessAbstractSubscriberTransformer;
import io.polygenesis.generators.java.batchprocesssubscriber.abstractsubscriber.BatchProcessMethodAbstractSubscriberTransformer;
import io.polygenesis.generators.java.batchprocesssubscriber.abstractsubscriber.activity.ProcessActivityGenerator;
import io.polygenesis.generators.java.batchprocesssubscriber.abstractsubscriber.activity.ProcessActivityTransformer;
import io.polygenesis.generators.java.batchprocesssubscriber.dispatcher.BatchProcessDispatcherGenerator;
import io.polygenesis.generators.java.batchprocesssubscriber.dispatcher.BatchProcessDispatcherTransformer;
import io.polygenesis.generators.java.batchprocesssubscriber.dispatcher.BatchProcessMethodDispatcherTransformer;
import io.polygenesis.generators.java.batchprocesssubscriber.dispatcher.activity.BatchProcessExtractMessageTypeActivityGenerator;
import io.polygenesis.generators.java.batchprocesssubscriber.dispatcher.activity.BatchProcessExtractMessageTypeActivityTransformer;
import io.polygenesis.generators.java.batchprocesssubscriber.registry.BatchProcessMethodSubscriberRegistryTransformer;
import io.polygenesis.generators.java.batchprocesssubscriber.registry.BatchProcessSubscriberRegistryGenerator;
import io.polygenesis.generators.java.batchprocesssubscriber.registry.BatchProcessSubscriberRegistryTransformer;
import io.polygenesis.generators.java.batchprocesssubscriber.subscriber.BatchProcessMethodSubscriberTransformer;
import io.polygenesis.generators.java.batchprocesssubscriber.subscriber.BatchProcessSubscriberGenerator;
import io.polygenesis.generators.java.batchprocesssubscriber.subscriber.BatchProcessSubscriberTransformer;
import io.polygenesis.generators.java.batchprocesssubscriber.subscriber.activity.GetSupportedMessageTypesActivityGenerator;
import io.polygenesis.generators.java.batchprocesssubscriber.subscriber.activity.GetSupportedMessageTypesActivityTransformer;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import java.nio.file.Path;

/**
 * The type Batch process subscriber metamodel generator factory.
 *
 * @author Christos Tsakostas
 */
public final class BatchProcessSubscriberMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static BatchProcessAbstractSubscriberGenerator batchProcessAbstractSubscriberGenerator;
  private static BatchProcessDispatcherGenerator batchProcessDispatcherGenerator;
  private static BatchProcessSubscriberRegistryGenerator batchProcessSubscriberRegistryGenerator;
  private static BatchProcessSubscriberGenerator batchProcessSubscriberGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    Exporter exporter = new ActiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    batchProcessAbstractSubscriberGenerator =
        new BatchProcessAbstractSubscriberGenerator(
            new BatchProcessAbstractSubscriberTransformer(
                dataTypeTransformer,
                new BatchProcessMethodAbstractSubscriberTransformer(
                    dataTypeTransformer,
                    new ProcessActivityGenerator(
                        new ProcessActivityTransformer(), templateEngine))),
            templateEngine,
            exporter);

    batchProcessDispatcherGenerator =
        new BatchProcessDispatcherGenerator(
            new BatchProcessDispatcherTransformer(
                dataTypeTransformer,
                new BatchProcessMethodDispatcherTransformer(
                    dataTypeTransformer,
                    new BatchProcessExtractMessageTypeActivityGenerator(
                        new BatchProcessExtractMessageTypeActivityTransformer(), templateEngine))),
            templateEngine,
            exporter);

    batchProcessSubscriberRegistryGenerator =
        new BatchProcessSubscriberRegistryGenerator(
            new BatchProcessSubscriberRegistryTransformer(
                dataTypeTransformer,
                new BatchProcessMethodSubscriberRegistryTransformer(dataTypeTransformer)),
            templateEngine,
            exporter);

    batchProcessSubscriberGenerator =
        new BatchProcessSubscriberGenerator(
            new BatchProcessSubscriberTransformer(
                dataTypeTransformer,
                new BatchProcessMethodSubscriberTransformer(
                    dataTypeTransformer,
                    new GetSupportedMessageTypesActivityGenerator(
                        new GetSupportedMessageTypesActivityTransformer(), templateEngine))),
            templateEngine,
            exporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private BatchProcessSubscriberMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance batch process subscriber metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @return the batch process subscriber metamodel generator
   */
  public static BatchProcessSubscriberMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ContextName contextName) {
    return new BatchProcessSubscriberMetamodelGenerator(
        generationPath,
        rootPackageName,
        contextName,
        batchProcessAbstractSubscriberGenerator,
        batchProcessDispatcherGenerator,
        batchProcessSubscriberRegistryGenerator,
        batchProcessSubscriberGenerator);
  }
}
