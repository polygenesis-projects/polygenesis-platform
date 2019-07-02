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

package io.polygenesis.generators.java.domainmessageactivemq.publisher.activity;

import io.polygenesis.abstraction.thing.AbstractActivityTemplateGenerator;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.TemplateEngine;

/**
 * The type Domain message publisher send activity generator.
 *
 * @author Christos Tsakostas
 */
public class DomainMessagePublisherSendActivityGenerator
    extends AbstractActivityTemplateGenerator<Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message publisher send activity generator.
   *
   * @param templateTransformer the template transformer
   * @param templateEngine the template engine
   */
  public DomainMessagePublisherSendActivityGenerator(
      DomainMessagePublisherSendActivityTransformer templateTransformer,
      TemplateEngine templateEngine) {
    super(templateTransformer, templateEngine);
  }
}
