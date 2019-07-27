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

package io.polygenesis.abstraction.thing;

import io.polygenesis.core.TemplateData;
import io.polygenesis.core.TemplateEngine;

/**
 * The type Abstract activity template generator.
 *
 * @param <S> the type parameter
 * @author Christos Tsakostas
 */
public abstract class AbstractActivityTemplateGenerator<S extends FunctionProvider>
    implements ActivityGenerator<S> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final ActivityTemplateTransformer<S> templateTransformer;
  private final TemplateEngine templateEngine;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract activity template generator.
   *
   * @param templateTransformer the template transformer
   * @param templateEngine the template engine
   */
  public AbstractActivityTemplateGenerator(
      ActivityTemplateTransformer<S> templateTransformer, TemplateEngine templateEngine) {
    this.templateTransformer = templateTransformer;
    this.templateEngine = templateEngine;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public String generate(S source, Object... args) {
    TemplateData templateData = templateTransformer.transform(source, args);
    return templateEngine.generate(templateData).toString();
  }
}
