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

package io.polygenesis.core;

import java.io.ByteArrayOutputStream;

/**
 * The type Abstract template generator.
 *
 * @param <S> the type parameter
 * @author Christos Tsakostas
 */
public class AbstractUnitTemplateGenerator<S> implements UnitGenerator<S> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final TemplateTransformer<S> templateTransformer;
  private final TemplateEngine templateEngine;
  private final Exporter exporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract unit template generator.
   *
   * @param templateTransformer the template provider
   * @param templateEngine the template engine
   * @param exporter the exporter
   */
  public AbstractUnitTemplateGenerator(
      TemplateTransformer<S> templateTransformer,
      TemplateEngine templateEngine,
      Exporter exporter) {
    this.templateTransformer = templateTransformer;
    this.templateEngine = templateEngine;
    this.exporter = exporter;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(S source, ExportInfo exportInfo, Object... args) {
    TemplateData templateData = templateTransformer.transform(source, args);
    ByteArrayOutputStream byteArrayOutputStream = templateEngine.generate(templateData);
    exporter.export(byteArrayOutputStream, exportInfo);
  }
}