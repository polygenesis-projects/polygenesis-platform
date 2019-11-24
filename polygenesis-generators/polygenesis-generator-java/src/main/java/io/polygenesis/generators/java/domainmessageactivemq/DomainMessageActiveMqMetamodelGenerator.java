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

package io.polygenesis.generators.java.domainmessageactivemq;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodelGenerator;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.domainmessageactivemq.dispatcherroute.DomainMessageDispatcherRoute;
import io.polygenesis.generators.java.domainmessageactivemq.dispatcherroute.DomainMessageDispatcherRouteGenerator;
import io.polygenesis.generators.java.domainmessagesubscriber.dispatcher.DomainMessageDispatcher;
import io.polygenesis.generators.java.shared.FolderFileConstants;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * The type Domain message active mq metamodel generator.
 *
 * @author Christos Tsakostas
 */
public class DomainMessageActiveMqMetamodelGenerator extends AbstractMetamodelGenerator {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final ContextName contextName;
  private final DomainMessageDispatcherRouteGenerator domainMessageDispatcherRouteGenerator;
  // TODO: private final DomainMessageForwarderGenerator domainMessageForwarderGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message active mq metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @param domainMessageDispatcherRouteGenerator the domain message dispatcher route generator
   */
  public DomainMessageActiveMqMetamodelGenerator(
      Path generationPath,
      PackageName rootPackageName,
      ContextName contextName,
      DomainMessageDispatcherRouteGenerator domainMessageDispatcherRouteGenerator) {
    super(generationPath);
    this.rootPackageName = rootPackageName;
    this.contextName = contextName;
    this.domainMessageDispatcherRouteGenerator = domainMessageDispatcherRouteGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Set<MetamodelRepository<?>> modelRepositories) {
    DomainMessageDispatcherRoute domainMessageDispatcherRoute = makeDomainMessageDispatcherRoute();
    domainMessageDispatcherRouteGenerator.generate(
        domainMessageDispatcherRoute,
        domainMessageDispatcherRouteExportInfo(getGenerationPath(), domainMessageDispatcherRoute),
        contextName);

    // TODO disable for now
    //    DomainMessageForwarder domainMessageForwarder = makeDomainMessageForwarder();
    //    domainMessageForwarderGenerator.generate(
    //        domainMessageForwarder,
    //        domainMessageForwarderExportInfo(getGenerationPath(), domainMessageForwarder),
    //        contextName);
  }

  // ===============================================================================================
  // PRIVATE - Objects
  // ===============================================================================================

  private DomainMessageDispatcherRoute makeDomainMessageDispatcherRoute() {
    return new DomainMessageDispatcherRoute(
        new ObjectName(
            String.format(
                "%sDomainMessageDispatcherRoute",
                TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())),
        makeDomainMessageDispatcher());
  }

  //  private DomainMessageForwarder makeDomainMessageForwarder() {
  //    return new DomainMessageForwarder(
  //        new ObjectName(
  //            String.format(
  //                "%sDomainMessageForwarder", TextConverter.toUpperCamel(contextName.getText()))),
  //        new PackageName(String.format("%s", rootPackageName.getText())));
  //  }

  private DomainMessageDispatcher makeDomainMessageDispatcher() {
    return new DomainMessageDispatcher(
        new ObjectName(
            String.format(
                "%sDomainMessageDispatcher", TextConverter.toUpperCamel(contextName.getText()))),
        new PackageName(String.format("%s", rootPackageName.getText())));
  }

  // ===============================================================================================
  // PRIVATE - ExportInfo
  // ===============================================================================================

  private ExportInfo domainMessageDispatcherRouteExportInfo(
      Path generationPath, DomainMessageDispatcherRoute domainMessageDispatcherRoute) {
    return ExportInfo.file(
        Paths.get(
            generationPath.toString(),
            FolderFileConstants.SRC,
            FolderFileConstants.MAIN,
            FolderFileConstants.JAVA,
            domainMessageDispatcherRoute.getPackageName().toPath().toString()),
        String.format(
            "%s%s",
            TextConverter.toUpperCamel(domainMessageDispatcherRoute.getObjectName().getText()),
            FolderFileConstants.JAVA_POSTFIX));
  }

  //  private ExportInfo domainMessageForwarderExportInfo(
  //      Path generationPath, DomainMessageForwarder domainMessageForwarder) {
  //    return ExportInfo.file(
  //        Paths.get(
  //            generationPath.toString(),
  //            FolderFileConstants.SRC,
  //            FolderFileConstants.MAIN,
  //            FolderFileConstants.JAVA,
  //            domainMessageForwarder.getPackageName().toPath().toString()),
  //        String.format(
  //            "%s%s",
  //            TextConverter.toUpperCamel(domainMessageForwarder.getObjectName().getText()),
  //            FolderFileConstants.JAVA_POSTFIX));
  //  }
}
