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

package io.polygenesis.core;

import java.io.ByteArrayOutputStream;

public class AbstractUnitGenerator<S extends Generatable> implements UnitGenerator<S> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private Transformer<S> transformer;
  private Exporter exporter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract unit generator.
   *
   * @param transformer the unit transformer
   * @param exporter the unit exporter
   */
  public AbstractUnitGenerator(Transformer<S> transformer, Exporter exporter) {
    this.transformer = transformer;
    this.exporter = exporter;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(S source, ExportInfo exportInfo, Object... args) {
    if (source.isExportable()) {
      ByteArrayOutputStream byteArrayOutputStream = transformer.transform(source);
      exporter.export(byteArrayOutputStream, exportInfo);
    }
  }
}
