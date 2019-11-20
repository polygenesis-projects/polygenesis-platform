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

package io.polygenesis.generators.java.domainmessageactivemq.forwarder;

import io.polygenesis.core.AbstractUnitTemplateGenerator;
import io.polygenesis.core.Exporter;
import io.polygenesis.core.TemplateEngine;

/**
 * The type Domain message forwarder generator.
 *
 * @author Christos Tsakostas
 */
public class DomainMessageForwarderGenerator
    extends AbstractUnitTemplateGenerator<DomainMessageForwarder> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message forwarder generator.
   *
   * @param templateTransformer the template transformer
   * @param templateEngine the template engine
   * @param exporter the exporter
   */
  public DomainMessageForwarderGenerator(
      DomainMessageForwarderTransformer templateTransformer,
      TemplateEngine templateEngine,
      Exporter exporter) {
    super(templateTransformer, templateEngine, exporter);
  }
}
