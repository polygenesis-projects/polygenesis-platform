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

package com.invoiceful.genesis.app;

import com.invoiceful.genesis.contexts.access.ContextAccess;
import io.polygenesis.commons.freemarker.FreemarkerAuthorService;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ContextGenerator;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.ProjectBuilder;
import io.polygenesis.craftsman.GenesisDefault;
import io.polygenesis.generators.flutter.context.FlutterContextGeneratorFactory;
import io.polygenesis.generators.flutter.project.FlutterProjectGenerator;
import io.polygenesis.generators.flutter.project.FlutterProjectGeneratorFactory;
import io.polygenesis.metamodels.appflutter.FlutterProject;
import java.nio.file.Paths;
import java.util.Set;

/** @author Christos Tsakostas */
public class InvoicefulAppFlutterCreator {

  private static final String FLUTTER_EXPORT_PATH = "/Users/tsakostas/work/repo/gitlab/invoiceful";
  private static final String FLUTTER_PROJECT_PATH = "invoiceful_app_flutter";
  private static final String FLUTTER_ROOT_PACKAGE = "com.invoiceful";

  public static void main(String[] args) {
    FreemarkerAuthorService.setAuthor("Christos Tsakostas");

    generateFlutter();
  }

  private static void generateFlutter() {
    FlutterProject project =
        ProjectBuilder.of("invoiceful")
            .addContext(
                ContextAccess.get(
                    new PackageName(String.format("%s.%s", FLUTTER_ROOT_PACKAGE, "access")),
                    contextGenerator("access"),
                    deducers("access")))
            .build(FlutterProject.class);

    FlutterProjectGenerator flutterProjectGenerator =
        FlutterProjectGeneratorFactory.newInstance(
            Paths.get(FLUTTER_EXPORT_PATH, FLUTTER_PROJECT_PATH));

    flutterProjectGenerator.generate(project);
  }

  private static Set<Deducer<?>> deducers(String context) {
    return GenesisDefault.flutterDeducers(String.format("%s.%s", FLUTTER_ROOT_PACKAGE, context));
  }

  private static ContextGenerator contextGenerator(String contextFolder) {
    return FlutterContextGeneratorFactory.newInstance(
        Paths.get(FLUTTER_EXPORT_PATH, FLUTTER_PROJECT_PATH),
        contextFolder,
        new PackageName(FLUTTER_ROOT_PACKAGE),
        new ContextName(contextFolder));
  }
}
