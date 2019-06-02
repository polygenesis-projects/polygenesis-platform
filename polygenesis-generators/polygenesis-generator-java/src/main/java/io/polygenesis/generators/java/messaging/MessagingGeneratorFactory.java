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

import io.polygenesis.commons.freemarker.FreemarkerConfig;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.generators.java.messaging.activity.SubscriberActivityRegistry;
import io.polygenesis.generators.java.messaging.exporter.SubscriberExporter;
import io.polygenesis.generators.java.messaging.transformer.SubscriberClassTransformer;
import io.polygenesis.generators.java.messaging.transformer.SubscriberMethodTransformer;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import java.nio.file.Path;

/**
 * The type Messaging generator factory.
 *
 * @author Christos Tsakostas
 */
public final class MessagingGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static SubscriberExporter subscriberExporter;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    FreemarkerService freemarkerService =
        new FreemarkerService(FreemarkerConfig.getInstance().getConfiguration());

    FromDataTypeToJavaConverter fromDataTypeToJavaConverter = new FromDataTypeToJavaConverter();

    subscriberExporter =
        new SubscriberExporter(
            freemarkerService,
            new SubscriberClassTransformer(
                fromDataTypeToJavaConverter,
                new SubscriberMethodTransformer(
                    fromDataTypeToJavaConverter,
                    freemarkerService,
                    new SubscriberActivityRegistry())));
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private MessagingGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance messaging generator.
   *
   * @param generationPath the generation path
   * @return the messaging generator
   */
  public static MessagingGenerator newInstance(Path generationPath) {
    return new MessagingGenerator(generationPath, subscriberExporter);
  }
}
