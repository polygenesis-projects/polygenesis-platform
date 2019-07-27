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

package io.polygenesis.transformers.web;

import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.generators.java.shared.transformer.HtmlTransformer;
import io.polygenesis.representations.code.HtmlRepresentation;

/**
 * The type Abstract html transformer.
 *
 * @param <S> the type parameter
 * @author Christos Tsakostas
 */
public abstract class AbstractHtmlTransformer<S> implements HtmlTransformer<S> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  /** The Data type transformer. */
  protected final DataTypeTransformer dataTypeTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract html transformer.
   *
   * @param dataTypeTransformer the data type transformer
   */
  public AbstractHtmlTransformer(DataTypeTransformer dataTypeTransformer) {
    this.dataTypeTransformer = dataTypeTransformer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public HtmlRepresentation create(S source, Object... args) {
    return new HtmlRepresentation();
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

}
