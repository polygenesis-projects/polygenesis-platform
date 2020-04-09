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

package io.polygenesis.generators.java.apidetail.converter.activity;

import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.Set;

public class ToCollectionRecordActivityTemplateData extends AbstractConverterActivityTemplateData {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new To collection record activity template data.
   *
   * @param parameterRepresentations the parameter representations
   * @param from the from
   * @param to the to
   */
  public ToCollectionRecordActivityTemplateData(
      Set<ParameterRepresentation> parameterRepresentations, Object from, Object to) {
    super(parameterRepresentations, from, to);
  }
}
