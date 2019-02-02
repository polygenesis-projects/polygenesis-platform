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

package io.polygenesis.generators.java.api;

import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.core.iomodel.IoModelGroup;
import java.util.Set;

/**
 * The type Io model group projection.
 *
 * @author Christos Tsakostas
 */
public class DtoProjection extends AbstractProjection {

  private IoModelGroup modelGroup;
  private Set<KeyValue> variables;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Dto projection.
   *
   * @param imports the imports
   * @param description the description
   * @param objectNameWithOptionalExtendsImplements the object name with optional extends implements
   * @param modelGroup the model group
   * @param variables the variables
   */
  public DtoProjection(
      Set<String> imports,
      String description,
      String objectNameWithOptionalExtendsImplements,
      IoModelGroup modelGroup,
      Set<KeyValue> variables) {
    super(imports, description, objectNameWithOptionalExtendsImplements);
    setModelGroup(modelGroup);
    setVariables(variables);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets model group.
   *
   * @return the model group
   */
  public IoModelGroup getModelGroup() {
    return modelGroup;
  }

  /**
   * Gets variables.
   *
   * @return the variables
   */
  public Set<KeyValue> getVariables() {
    return variables;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets model group.
   *
   * @param modelGroup the model group
   */
  private void setModelGroup(IoModelGroup modelGroup) {
    this.modelGroup = modelGroup;
  }

  /**
   * Sets variables.
   *
   * @param variables the variables
   */
  private void setVariables(Set<KeyValue> variables) {
    this.variables = variables;
  }
}
