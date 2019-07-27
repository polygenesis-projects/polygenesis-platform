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

package io.polygenesis.generators.java.domainmessagesubscriber;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.PassiveFileExporter;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.domainmessagesubscriber.abstractsubscriber.DomainMessageAbstractSubscriberGenerator;
import io.polygenesis.generators.java.domainmessagesubscriber.abstractsubscriber.DomainMessageAbstractSubscriberTransformer;
import io.polygenesis.generators.java.domainmessagesubscriber.abstractsubscriber.DomainMessageMethodAbstractSubscriberTransformer;
import io.polygenesis.generators.java.domainmessagesubscriber.dispatcher.DomainMessageDispatcherGenerator;
import io.polygenesis.generators.java.domainmessagesubscriber.dispatcher.DomainMessageDispatcherTransformer;
import io.polygenesis.generators.java.domainmessagesubscriber.dispatcher.DomainMessageMethodDispatcherTransformer;
import io.polygenesis.generators.java.domainmessagesubscriber.dispatcher.activity.ExtractMessageTypeActivityGenerator;
import io.polygenesis.generators.java.domainmessagesubscriber.dispatcher.activity.ExtractMessageTypeActivityTransformer;
import io.polygenesis.generators.java.domainmessagesubscriber.incomingdomainmessage.IncomingDomainMessageGenerator;
import io.polygenesis.generators.java.domainmessagesubscriber.incomingdomainmessage.IncomingDomainMessageMethodTransformer;
import io.polygenesis.generators.java.domainmessagesubscriber.incomingdomainmessage.IncomingDomainMessageTransformer;
import io.polygenesis.generators.java.domainmessagesubscriber.incomingdomainmessage.activity.IncomingDomainMessageActivityRegistry;
import io.polygenesis.generators.java.domainmessagesubscriber.registry.DomainMessageMethodSubscriberRegistryTransformer;
import io.polygenesis.generators.java.domainmessagesubscriber.registry.DomainMessageSubscriberRegistryGenerator;
import io.polygenesis.generators.java.domainmessagesubscriber.registry.DomainMessageSubscriberRegistryTransformer;
import io.polygenesis.generators.java.domainmessagesubscriber.subscriber.DomainMessageMethodSubscriberTransformer;
import io.polygenesis.generators.java.domainmessagesubscriber.subscriber.DomainMessageSubscriberGenerator;
import io.polygenesis.generators.java.domainmessagesubscriber.subscriber.DomainMessageSubscriberTransformer;
import io.polygenesis.generators.java.domainmessagesubscriber.subscriber.activity.DomainMessageSubscriberActivityRegistry;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import java.nio.file.Path;

/**
 * The type Domain message subscriber metamodel generator factory.
 *
 * @author Christos Tsakostas
 */
public final class DomainMessageSubscriberMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static DomainMessageAbstractSubscriberGenerator domainMessageAbstractSubscriberGenerator;
  private static DomainMessageDispatcherGenerator domainMessageDispatcherGenerator;
  private static DomainMessageSubscriberRegistryGenerator domainMessageSubscriberRegistryGenerator;
  private static DomainMessageSubscriberGenerator domainMessageSubscriberGenerator;
  private static IncomingDomainMessageGenerator incomingDomainMessageGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    final TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    final Exporter activeFileExporter = new ActiveFileExporter();
    final Exporter passiveFileExporter = new PassiveFileExporter();
    final DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    domainMessageAbstractSubscriberGenerator =
        new DomainMessageAbstractSubscriberGenerator(
            new DomainMessageAbstractSubscriberTransformer(
                dataTypeTransformer,
                new DomainMessageMethodAbstractSubscriberTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);

    domainMessageDispatcherGenerator =
        new DomainMessageDispatcherGenerator(
            new DomainMessageDispatcherTransformer(
                dataTypeTransformer,
                new DomainMessageMethodDispatcherTransformer(
                    dataTypeTransformer,
                    new ExtractMessageTypeActivityGenerator(
                        new ExtractMessageTypeActivityTransformer(), templateEngine))),
            templateEngine,
            activeFileExporter);

    domainMessageSubscriberRegistryGenerator =
        new DomainMessageSubscriberRegistryGenerator(
            new DomainMessageSubscriberRegistryTransformer(
                dataTypeTransformer,
                new DomainMessageMethodSubscriberRegistryTransformer(dataTypeTransformer)),
            templateEngine,
            activeFileExporter);

    domainMessageSubscriberGenerator =
        new DomainMessageSubscriberGenerator(
            new DomainMessageSubscriberTransformer(
                dataTypeTransformer,
                new DomainMessageMethodSubscriberTransformer(
                    dataTypeTransformer, new DomainMessageSubscriberActivityRegistry())),
            templateEngine,
            passiveFileExporter);

    incomingDomainMessageGenerator =
        new IncomingDomainMessageGenerator(
            new IncomingDomainMessageTransformer(
                dataTypeTransformer,
                new IncomingDomainMessageMethodTransformer(
                    dataTypeTransformer, new IncomingDomainMessageActivityRegistry())),
            templateEngine,
            passiveFileExporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private DomainMessageSubscriberMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance domain message subscriber metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @return the domain message subscriber metamodel generator
   */
  public static DomainMessageSubscriberMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ContextName contextName) {
    return new DomainMessageSubscriberMetamodelGenerator(
        generationPath,
        rootPackageName,
        contextName,
        domainMessageAbstractSubscriberGenerator,
        domainMessageDispatcherGenerator,
        domainMessageSubscriberRegistryGenerator,
        domainMessageSubscriberGenerator,
        incomingDomainMessageGenerator);
  }
}
