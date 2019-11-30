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

package io.polygenesis.generators.java.domain.aggregateroot.activity.statemutation;

import io.polygenesis.abstraction.thing.AbstractActivityTemplateGenerator;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.models.domain.StateMutationMethod;

/**
 * The type Entity modify activity generator.
 *
 * @author Christos Tsakostas
 */
public class EntityModifyActivityGenerator
    extends AbstractActivityTemplateGenerator<StateMutationMethod> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Entity modify activity generator.
   *
   * @param templateTransformer the template transformer
   * @param templateEngine the template engine
   */
  public EntityModifyActivityGenerator(
      EntityModifyActivityTransformer templateTransformer, TemplateEngine templateEngine) {
    super(templateTransformer, templateEngine);
  }
}