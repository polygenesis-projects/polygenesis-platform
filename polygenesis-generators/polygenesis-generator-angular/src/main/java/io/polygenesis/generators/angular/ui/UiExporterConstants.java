/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

package io.polygenesis.generators.angular.ui;

/**
 * UI Exporter Constants.
 *
 * @author Christos Tsakostas
 */
public final class UiExporterConstants {

  private UiExporterConstants() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // FREEMARKER TEMPLATES
  // ===============================================================================================

  public static final String FTL_MODULE =
      "polygenesis-angular-generator/ui/default/module/index.ftl";

  public static final String FTL_CONTAINER_HTML =
      "polygenesis-angular-generator/ui/default/container/inline/html/index.ftl";
  public static final String FTL_CONTAINER_SCSS =
      "polygenesis-angular-generator/ui/default/container/inline/scss/index.ftl";
  public static final String FTL_CONTAINER_TS =
      "polygenesis-angular-generator/ui/default/container/inline/ts/index.ftl";
  public static final String FTL_CONTAINER_SPEC_TS =
      "polygenesis-angular-generator/ui/default/container/inline/spec/index.ftl";

  public static final String FTL_APP_SHARED_LAYOUT_HTML =
      "polygenesis-angular-generator/src/app/shared/layouts/layout.component.html.ftl";
  public static final String FTL_APP_SHARED_LAYOUT_TS =
      "polygenesis-angular-generator/src/app/shared/layouts/layout.component.ts.ftl";
  public static final String FTL_APP_SHARED_LAYOUT_SPEC_TS =
      "polygenesis-angular-generator/src/app/shared/layouts/layout.component.spec.ts.ftl";
  public static final String FTL_APP_SHARED_LAYOUT_SCSS =
      "polygenesis-angular-generator/src/app/shared/layouts/layout.component.scss.ftl";

  // ===============================================================================================
  // OUTPUT FILES
  // ===============================================================================================

  public static final String POSTFIX_MODULE_TS = ".module.ts";

  public static final String POSTFIX_COMPONENT_HTML = ".component.html";
  public static final String POSTFIX_COMPONENT_SCSS = ".component.scss";
  public static final String POSTFIX_COMPONENT_TYPESCRIPT = ".component.ts";
  public static final String POSTFIX_COMPONENT_TYPESCRIPT_SPEC = ".component.spec.ts";
}
