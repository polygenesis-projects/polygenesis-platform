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

package io.polygenesis.core;

import java.nio.file.Path;
import java.util.Set;

/**
 * The type Abstract context generator.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractContextGenerator extends AbstractPathGenerator
    implements ContextGenerator {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<MetamodelGenerator> metamodelGenerators;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract context generator.
   *
   * @param generationPath the generation path
   * @param metamodelGenerators the metamodel generators
   */
  public AbstractContextGenerator(
      Path generationPath, Set<MetamodelGenerator> metamodelGenerators) {
    super(generationPath);
    this.metamodelGenerators = metamodelGenerators;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets metamodel generators.
   *
   * @return the metamodel generators
   */
  public Set<MetamodelGenerator> getMetamodelGenerators() {
    return metamodelGenerators;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public void generate(Context<? extends Abstraction> context) {
    getMetamodelGenerators()
        .forEach(
            metamodelGenerator -> metamodelGenerator.generate(context.getMetamodelRepositories()));
  }
}
