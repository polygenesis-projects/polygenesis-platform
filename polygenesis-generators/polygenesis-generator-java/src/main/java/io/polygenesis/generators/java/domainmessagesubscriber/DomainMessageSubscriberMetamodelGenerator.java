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

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.domainmessagesubscriber.abstractsubscriber.DomainMessageAbstractSubscriber;
import io.polygenesis.generators.java.domainmessagesubscriber.abstractsubscriber.DomainMessageAbstractSubscriberGenerator;
import io.polygenesis.generators.java.domainmessagesubscriber.dispatcher.DomainMessageDispatcher;
import io.polygenesis.generators.java.domainmessagesubscriber.dispatcher.DomainMessageDispatcherGenerator;
import io.polygenesis.generators.java.domainmessagesubscriber.incomingdomainmessage.IncomingDomainMessage;
import io.polygenesis.generators.java.domainmessagesubscriber.incomingdomainmessage.IncomingDomainMessageGenerator;
import io.polygenesis.generators.java.domainmessagesubscriber.registry.DomainMessageSubscriberRegistry;
import io.polygenesis.generators.java.domainmessagesubscriber.registry.DomainMessageSubscriberRegistryGenerator;
import io.polygenesis.generators.java.domainmessagesubscriber.subscriber.DomainMessageSubscriber;
import io.polygenesis.generators.java.domainmessagesubscriber.subscriber.DomainMessageSubscriberGenerator;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import io.polygenesis.models.messaging.subscriber.SubscriberMetamodel;
import io.polygenesis.models.messaging.subscriber.SubscriberMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * The type Domain message subscriber metamodel generator.
 *
 * @author Christos Tsakostas
 */
public class DomainMessageSubscriberMetamodelGenerator extends AbstractMetamodelGenerator {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final ContextName contextName;
  private final DomainMessageAbstractSubscriberGenerator domainMessageAbstractSubscriberGenerator;
  private final DomainMessageDispatcherGenerator domainMessageDispatcherGenerator;
  private final DomainMessageSubscriberRegistryGenerator domainMessageSubscriberRegistryGenerator;
  private final DomainMessageSubscriberGenerator domainMessageSubscriberGenerator;
  private final IncomingDomainMessageGenerator incomingDomainMessageGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message subscriber metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @param domainMessageAbstractSubscriberGenerator the domain message abstract subscriber
   *     generator
   * @param domainMessageDispatcherGenerator the domain message dispatcher generator
   * @param domainMessageSubscriberRegistryGenerator the domain message subscriber registry
   *     generator
   * @param domainMessageSubscriberGenerator the domain message subscriber generator
   * @param incomingDomainMessageGenerator the incoming domain message generator
   */
  public DomainMessageSubscriberMetamodelGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ContextName contextName,
      DomainMessageAbstractSubscriberGenerator domainMessageAbstractSubscriberGenerator,
      DomainMessageDispatcherGenerator domainMessageDispatcherGenerator,
      DomainMessageSubscriberRegistryGenerator domainMessageSubscriberRegistryGenerator,
      DomainMessageSubscriberGenerator domainMessageSubscriberGenerator,
      IncomingDomainMessageGenerator incomingDomainMessageGenerator) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.domainMessageAbstractSubscriberGenerator = domainMessageAbstractSubscriberGenerator;
    this.domainMessageDispatcherGenerator = domainMessageDispatcherGenerator;
    this.domainMessageSubscriberRegistryGenerator = domainMessageSubscriberRegistryGenerator;
    this.domainMessageSubscriberGenerator = domainMessageSubscriberGenerator;
    this.incomingDomainMessageGenerator = incomingDomainMessageGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<MetamodelRepository<?>> modelRepositories) {
    DomainMessageAbstractSubscriber domainMessageAbstractSubscriber = makeAbstractSubscriber();
    domainMessageAbstractSubscriberGenerator.generate(
        domainMessageAbstractSubscriber,
        abstractSubscriberExportInfo(getGenerationPath(), domainMessageAbstractSubscriber),
        contextName);

    DomainMessageDispatcher dispatcher = makeBatchProcessDispatcher();
    domainMessageDispatcherGenerator.generate(
        dispatcher, dispatcherExportInfo(getGenerationPath(), dispatcher));

    DomainMessageSubscriberRegistry domainMessageSubscriberRegistry = makeRegistry();
    domainMessageSubscriberRegistryGenerator.generate(
        domainMessageSubscriberRegistry,
        registryExportInfo(getGenerationPath(), domainMessageSubscriberRegistry));

    IncomingDomainMessage incomingDomainMessage = makeIncomingDomainMessage();
    incomingDomainMessageGenerator.generate(
        incomingDomainMessage,
        incomingDomainMessageExportInfo(getGenerationPath(), incomingDomainMessage));

    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, SubscriberMetamodelRepository.class)
        .getItems()
        .forEach(
            subscriber ->
                domainMessageSubscriberGenerator.generate(
                    makeDomainMessageSubscriber(subscriber),
                    subscriberExportInfo(getGenerationPath(), subscriber),
                    domainMessageAbstractSubscriber));
  }

  // ===============================================================================================
  // PRIVATE - Objects
  // ===============================================================================================

  private DomainMessageAbstractSubscriber makeAbstractSubscriber() {
    return new DomainMessageAbstractSubscriber(
        new ObjectName(
            String.format(
                "%sDomainMessageSubscriber", TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private DomainMessageDispatcher makeBatchProcessDispatcher() {
    return new DomainMessageDispatcher(
        new ObjectName(
            String.format(
                "%sDomainMessageDispatcher", TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private DomainMessageSubscriberRegistry makeRegistry() {
    return new DomainMessageSubscriberRegistry(
        new ObjectName(
            String.format(
                "%sDomainMessageSubscriberRegistry",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private IncomingDomainMessage makeIncomingDomainMessage() {
    return new IncomingDomainMessage(
        new ObjectName(
            String.format(
                "%sIncomingDomainMessage", TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  private DomainMessageSubscriber makeDomainMessageSubscriber(
      SubscriberMetamodel subscriberMetamodel) {
    return new DomainMessageSubscriber(
        subscriberMetamodel.getPackageName(),
        subscriberMetamodel.getName(),
        subscriberMetamodel.getMessageData(),
        subscriberMetamodel.getSupportedMessageTypes(),
        subscriberMetamodel.getRelatedThing(),
        subscriberMetamodel.getEnsureExistenceServiceMethod(),
        subscriberMetamodel.getCommandServiceMethod(),
        contextName,
        rootPackageName);
  }

  // ===============================================================================================
  // PRIVATE - ExportInfo
  // ===============================================================================================

  private ExportInfo abstractSubscriberExportInfo(
      Path generationPath, DomainMessageAbstractSubscriber domainMessageAbstractSubscriber) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainMessageAbstractSubscriber.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(domainMessageAbstractSubscriber.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo dispatcherExportInfo(Path generationPath, DomainMessageDispatcher dispatcher) {
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
      Path generationPath, DomainMessageSubscriberRegistry domainMessageSubscriberRegistry) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainMessageSubscriberRegistry.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(domainMessageSubscriberRegistry.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo subscriberExportInfo(
      Path generationPath, SubscriberMetamodel subscriberMetamodel) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            subscriberMetamodel.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(subscriberMetamodel.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  private ExportInfo incomingDomainMessageExportInfo(
      Path generationPath, IncomingDomainMessage incomingDomainMessage) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            incomingDomainMessage.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(incomingDomainMessage.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }
}
