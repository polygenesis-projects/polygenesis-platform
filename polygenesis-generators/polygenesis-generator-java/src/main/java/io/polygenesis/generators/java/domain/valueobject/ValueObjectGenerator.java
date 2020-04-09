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

package io.polygenesis.generators.java.domain.valueobject;

import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.core.AbstractUnitTemplateGenerator;
import io.polygenesis.core.ExportInfo;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.models.domain.ValueObject;

/**
 * The type Value object generator.
 *
 * @author Christos Tsakostas
 */
public class ValueObjectGenerator extends AbstractUnitTemplateGenerator<ValueObject> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Value object generator.
   *
   * @param templateTransformer the template transformer
   * @param templateEngine the template engine
   * @param exporter the exporter
   */
  public ValueObjectGenerator(
      ValueObjectTransformer templateTransformer,
      TemplateEngine templateEngine,
      Exporter exporter) {
    super(templateTransformer, templateEngine, exporter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(ValueObject source, ExportInfo exportInfo, Object... args) {
    if (source.isCommon()
        && !source.getData().getDataSourceType().equals(DataSourceType.EXTERNALLY_PROVIDED)) {
      super.generate(source, exportInfo, args);
    }
  }
}
