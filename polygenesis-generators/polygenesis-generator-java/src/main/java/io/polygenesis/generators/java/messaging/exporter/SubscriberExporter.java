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

package io.polygenesis.generators.java.messaging.exporter;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.messaging.transformer.SubscriberClassTransformer;
import io.polygenesis.models.messaging.subscriber.Subscriber;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Subscriber exporter.
 *
 * @author Christos Tsakostas
 */
public class SubscriberExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final SubscriberClassTransformer subscriberClassTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Subscriber exporter.
   *
   * @param freemarkerService the freemarker service
   * @param subscriberClassTransformer the subscriber class transformer
   */
  public SubscriberExporter(
      FreemarkerService freemarkerService, SubscriberClassTransformer subscriberClassTransformer) {
    this.freemarkerService = freemarkerService;
    this.subscriberClassTransformer = subscriberClassTransformer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Export.
   *
   * @param generationPath the generation path
   * @param subscriber the subscriber
   */
  public void export(Path generationPath, Subscriber subscriber) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", subscriberClassTransformer.create(subscriber));

    freemarkerService.export(
        dataModel,
        "polygenesis-representation-java/Class.java.ftl",
        makeFileName(generationPath, subscriber));
  }

  private Path makeFileName(Path generationPath, Subscriber service) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        service.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(service.getName().getText()) + ".java");
  }
}
