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

package io.polygenesis.cli;

import io.polygenesis.cli.model.Project;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Poly genesis request.
 *
 * @author Christos Tsakostas
 */
@Data
@EqualsAndHashCode
public class PolyGenesisRequest {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Project project;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================
  /** Instantiates a new Poly genesis request. */
  public PolyGenesisRequest(String projectName) {
    this.project = new Project(projectName.toLowerCase());
  }
}
