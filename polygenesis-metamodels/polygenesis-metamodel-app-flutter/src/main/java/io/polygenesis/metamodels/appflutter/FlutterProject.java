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

package io.polygenesis.metamodels.appflutter;

import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.core.AbstractProject;
import io.polygenesis.core.Abstraction;
import io.polygenesis.core.Context;
import java.util.Set;

public class FlutterProject extends AbstractProject {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private FlutterApp flutterApp;
  private FlutterMain flutterMain;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Flutter project.
   *
   * @param name the name
   * @param contexts the contexts
   */
  public FlutterProject(Name name, Set<Context<? extends Abstraction>> contexts) {
    super(name, contexts);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets flutter app.
   *
   * @return the flutter app
   */
  public FlutterApp getFlutterApp() {
    return flutterApp;
  }

  /**
   * Gets flutter main.
   *
   * @return the flutter main
   */
  public FlutterMain getFlutterMain() {
    return flutterMain;
  }
}
