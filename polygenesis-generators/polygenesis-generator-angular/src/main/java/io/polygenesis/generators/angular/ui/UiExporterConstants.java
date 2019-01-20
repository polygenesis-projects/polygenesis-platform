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

  public static final String PATH_NGRX = "ngrx";

  public static final String FTL_ACTION =
      "polygenesis-angular-generator/ngrx/analytical/action/index.ftl";
  public static final String FTL_REDUCER =
      "polygenesis-angular-generator/ngrx/analytical/reducer/index.ftl";
  public static final String FTL_INDEX_REDUCER =
      "polygenesis-angular-generator/ngrx/shared/index-reducer/index.ftl";
  public static final String FTL_EFFECT =
      "polygenesis-angular-generator/ngrx/shared/effect/index.ftl";
  public static final String FTL_SERVICE =
      "polygenesis-angular-generator/ngrx/shared/service/index.ftl";
  public static final String FTL_MODEL =
      "polygenesis-angular-generator/ngrx/shared/model/index.ftl";
  public static final String FTL_MODULE =
      "polygenesis-angular-generator/ui/default/module/index.ftl";

  public static final String PATH_ACTIONS = "actions";
  public static final String PATH_REDUCERS = "reducers";
  public static final String PATH_INDEX_REDUCER = "reducers";
  public static final String PATH_EFFECTS = "effects";
  public static final String PATH_SERVICES = "services";
  public static final String PATH_MODELS = "models";

  public static final String POSTFIX_ACTIONS_TS = ".actions.ts";
  public static final String POSTFIX_REDUCERS_TS = ".reducers.ts";
  public static final String POSTFIX_INDEX_REDUCER_TS = "index.ts";
  public static final String POSTFIX_EFFECTS_TS = ".effects.ts";
  public static final String POSTFIX_SERVICE_TS = ".service.ts";
  public static final String POSTFIX_MODEL_TS = ".model.ts";
  public static final String POSTFIX_MODULE_TS = ".module.ts";
}
