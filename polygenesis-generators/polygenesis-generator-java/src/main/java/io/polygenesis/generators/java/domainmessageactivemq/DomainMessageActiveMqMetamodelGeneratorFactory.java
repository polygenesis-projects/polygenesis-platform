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

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ActiveFileExporter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.domainmessageactivemq.dispatcherroute.DomainMessageDispatcherRouteGenerator;
import io.polygenesis.generators.java.domainmessageactivemq.dispatcherroute.DomainMessageDispatcherRouteTransformer;
import io.polygenesis.generators.java.domainmessageactivemq.dispatcherroute.DomainMessageMethodDispatcherRouteTransformer;
import io.polygenesis.generators.java.domainmessageactivemq.dispatcherroute.activity.DomainMessageConfigureActivityGenerator;
import io.polygenesis.generators.java.domainmessageactivemq.dispatcherroute.activity.DomainMessageConfigureActivityTransformer;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import java.nio.file.Path;

/**
 * The type Domain message active mq metamodel generator factory.
 *
 * @author Christos Tsakostas
 */
public final class DomainMessageActiveMqMetamodelGeneratorFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private static DomainMessageDispatcherRouteGenerator domainMessageDispatcherRouteGenerator;
  // TODO private static DomainMessageForwarderGenerator domainMessageForwarderGenerator;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    Exporter exporter = new ActiveFileExporter();
    DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();

    domainMessageDispatcherRouteGenerator =
        new DomainMessageDispatcherRouteGenerator(
            new DomainMessageDispatcherRouteTransformer(
                dataTypeTransformer,
                new DomainMessageMethodDispatcherRouteTransformer(
                    dataTypeTransformer,
                    new DomainMessageConfigureActivityGenerator(
                        new DomainMessageConfigureActivityTransformer(), templateEngine))),
            templateEngine,
            exporter);

    // TODO
    //    domainMessageForwarderGenerator =
    //        new DomainMessageForwarderGenerator(
    //            new DomainMessageForwarderTransformer(
    //                dataTypeTransformer,
    //                new DomainMessageForwarderMethodTransformer(
    //                    dataTypeTransformer,
    //                    new DomainMessageForwarderSendActivityGenerator(
    //                        new DomainMessageForwarderSendActivityTransformer(),
    // templateEngine))),
    //            templateEngine,
    //            exporter);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private DomainMessageActiveMqMetamodelGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance domain message active mq metamodel generator.
   *
   * @param generationPath the generation path
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @return the domain message active mq metamodel generator
   */
  public static DomainMessageActiveMqMetamodelGenerator newInstance(
      Path generationPath, PackageName rootPackageName, ContextName contextName) {
    return new DomainMessageActiveMqMetamodelGenerator(
        generationPath, rootPackageName, contextName, domainMessageDispatcherRouteGenerator
        // TODO domainMessageForwarderGenerator
        );
  }
}
