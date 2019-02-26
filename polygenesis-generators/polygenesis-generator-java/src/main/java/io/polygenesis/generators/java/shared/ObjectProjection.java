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

package io.polygenesis.generators.java.shared;

import io.polygenesis.core.iomodel.IoModelGroup;
import java.util.Set;

/**
 * Object projection is a transformation of data to a friendly format for the Java Generators.
 *
 * @author Christos Tsakostas
 */
public class ObjectProjection extends AbstractProjection {

  private IoModelGroup modelGroup;
  private Set<ArgumentProjection> variables;
  private Set<ConstructorProjection> constructors;
  private Set<FunctionProjection> functionProjections;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Object projection.
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
  public ObjectProjection(
      String packageName,
      Set<String> imports,
      String description,
      String objectName,
      String objectNameWithOptionalExtendsImplements,
      Set<ArgumentProjection> variables,
      Set<ConstructorProjection> constructors,
      Set<FunctionProjection> functionProjections) {
    super(packageName, imports, description, objectName, objectNameWithOptionalExtendsImplements);
    setVariables(variables);
    setConstructors(constructors);
    setFunctionProjections(functionProjections);
  }

  /**
   * Instantiates a new Object projection.
   *
   * @param packageName the package name
   * @param imports the imports
   * @param description the description
   * @param objectName the object name
   * @param objectNameWithOptionalExtendsImplements the object name with optional extends implements
   * @param modelGroup the model group
   * @param variables the variables
   * @param constructors the constructors
   * @param functionProjections the method projections
   */
  public ObjectProjection(
      String packageName,
      Set<String> imports,
      String description,
      String objectName,
      String objectNameWithOptionalExtendsImplements,
      IoModelGroup modelGroup,
      Set<ArgumentProjection> variables,
      Set<ConstructorProjection> constructors,
      Set<FunctionProjection> functionProjections) {
    super(packageName, imports, description, objectName, objectNameWithOptionalExtendsImplements);
    setModelGroup(modelGroup);
    setVariables(variables);
    setConstructors(constructors);
    setFunctionProjections(functionProjections);
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
  public Set<ArgumentProjection> getVariables() {
    return variables;
  }

  /**
   * Gets constructors.
   *
   * @return the constructors
   */
  public Set<ConstructorProjection> getConstructors() {
    return constructors;
  }

  /**
   * Gets method projections.
   *
   * @return the method projections
   */
  public Set<FunctionProjection> getFunctionProjections() {
    return functionProjections;
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
  private void setVariables(Set<ArgumentProjection> variables) {
    this.variables = variables;
  }

  /**
   * Sets constructors.
   *
   * @param constructors the constructors
   */
  private void setConstructors(Set<ConstructorProjection> constructors) {
    this.constructors = constructors;
  }

  /**
   * Sets method projections.
   *
   * @param functionProjections the method projections
   */
  public void setFunctionProjections(Set<FunctionProjection> functionProjections) {
    this.functionProjections = functionProjections;
  }
}
