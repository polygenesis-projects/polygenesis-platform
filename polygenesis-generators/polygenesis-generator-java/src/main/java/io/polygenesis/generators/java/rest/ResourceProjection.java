/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

package io.polygenesis.generators.java.rest;

import io.polygenesis.generators.java.shared.ArgumentProjection;
import io.polygenesis.generators.java.shared.ConstructorProjection;
import io.polygenesis.generators.java.shared.FunctionProjection;
import io.polygenesis.generators.java.shared.ObjectProjection;
import java.util.Set;

/**
 * The type Resource projection.
 *
 * @author Christos Tsakostas
 */
public class ResourceProjection extends ObjectProjection {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Resource projection.
   *
   * @param packageName the package name
   * @param imports the imports
   * @param description the description
   * @param objectName the object name
   * @param objectNameWithOptionalExtendsImplements the object name with optional extends implements
   * @param variables the variables
   * @param constructors the constructors
   * @param functionProjections the method projections
   */
  public ResourceProjection(
      String packageName,
      Set<String> imports,
      String description,
      String objectName,
      String objectNameWithOptionalExtendsImplements,
      Set<ArgumentProjection> variables,
      Set<ConstructorProjection> constructors,
      Set<FunctionProjection> functionProjections) {
    super(
        packageName,
        imports,
        description,
        objectName,
        objectNameWithOptionalExtendsImplements,
        variables,
        constructors,
        functionProjections);
  }
}
