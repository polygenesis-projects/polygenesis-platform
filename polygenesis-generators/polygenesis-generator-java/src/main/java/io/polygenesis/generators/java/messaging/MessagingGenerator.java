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

package io.polygenesis.generators.java.messaging;

import io.polygenesis.core.AbstractGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.messaging.exporter.SubscriberExporter;
import io.polygenesis.models.messaging.subscriber.SubscriberMetamodelRepository;
import java.nio.file.Path;
import java.util.Set;

/**
 * The type Messaging generator.
 *
 * @author Christos Tsakostas
 */
public class MessagingGenerator extends AbstractGenerator {

  private final SubscriberExporter subscriberExporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Messaging generator.
   *
   * @param generationPath the generation path
   * @param subscriberExporter the subscriber exporter
   */
  public MessagingGenerator(Path generationPath, SubscriberExporter subscriberExporter) {
    super(generationPath);
    this.subscriberExporter = subscriberExporter;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public void generate(Set<MetamodelRepository> modelRepositories) {
    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, SubscriberMetamodelRepository.class)
        .getItems()
        .forEach(
            subscriber -> {
              subscriberExporter.export(getGenerationPath(), subscriber);
            });
  }
}
