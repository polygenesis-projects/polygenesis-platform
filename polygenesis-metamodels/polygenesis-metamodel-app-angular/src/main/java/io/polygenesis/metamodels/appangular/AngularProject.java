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

package io.polygenesis.metamodels.appangular;

import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.core.AbstractProject;
import io.polygenesis.core.Abstraction;
import io.polygenesis.core.Context;
import java.util.Set;

/**
 * The type Angular project.
 *
 * @author Christos Tsakostas
 */
public class AngularProject extends AbstractProject {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Angular project.
   *
   * @param name the name
   * @param contexts the contexts
   */
  public AngularProject(Name name, Set<Context<? extends Abstraction>> contexts) {
    super(name, contexts);
  }
}
