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

package io.polygenesis.generators.java.domainmessageactivemq.forwarder.activity;

import io.polygenesis.abstraction.thing.AbstractActivityTemplateGenerator;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.TemplateEngine;

public class DomainMessageForwarderSendActivityGenerator
    extends AbstractActivityTemplateGenerator<Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message forwarder send activity generator.
   *
   * @param templateTransformer the template transformer
   * @param templateEngine the template engine
   */
  public DomainMessageForwarderSendActivityGenerator(
      DomainMessageForwarderSendActivityTransformer templateTransformer,
      TemplateEngine templateEngine) {
    super(templateTransformer, templateEngine);
  }
}
