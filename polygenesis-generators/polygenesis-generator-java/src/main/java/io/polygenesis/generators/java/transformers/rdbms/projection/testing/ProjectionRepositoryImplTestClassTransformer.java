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

package io.polygenesis.generators.java.transformers.rdbms.projection.testing;

import io.polygenesis.generators.java.transformers.rdbms.testing.PersistenceImplTestClassTransformer;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;

/**
 * The type Projection repository impl test class representable.
 *
 * @author Christos Tsakostas
 */
public class ProjectionRepositoryImplTestClassTransformer
    extends PersistenceImplTestClassTransformer {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Projection repository impl test class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public ProjectionRepositoryImplTestClassTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

}
