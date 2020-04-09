/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.generators.angular.project.approutingmodule;

import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.angular.project.approutingmodule.source.AppRoutingModule;
import io.polygenesis.transformers.typescript.AbstractTypescriptSimpleClassTransformer;
import java.util.HashMap;
import java.util.Map;

public class AppRoutingModuleTransformer
    extends AbstractTypescriptSimpleClassTransformer<AppRoutingModule> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new App routing module transformer.
   *
   * @param dataTypeTransformer the data type transformer
   */
  public AppRoutingModuleTransformer(DataTypeTransformer dataTypeTransformer) {
    super(dataTypeTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public TemplateData transform(AppRoutingModule source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("data", create(source, args));
    return new TemplateData(dataModel, "polygenesis-typescript/Class.ts.ftl");
  }
}
