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

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.core.datatype.DataTypeName;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.iomodel.IoModelGroup;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Abstract object projection maker.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractObjectProjectionMaker implements ObjectProjectionMaker {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  /** The From data type to java converter. */
  protected final FromDataTypeToJavaConverter fromDataTypeToJavaConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract object projection maker.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public AbstractObjectProjectionMaker(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    this.fromDataTypeToJavaConverter = fromDataTypeToJavaConverter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Make object projection.
   *
   * @param modelGroup the model group
   * @return the object projection
   */
  public ObjectProjection make(IoModelGroup modelGroup) {
    return new ObjectProjection(
        projectPackageName(modelGroup),
        projectImports(modelGroup),
        projectDescription(modelGroup),
        projectObjectName(modelGroup),
        projectObjectNameWithOptionalExtendsImplements(modelGroup),
        modelGroup,
        projectVariables(modelGroup),
        projectConstructors(modelGroup),
        projectMethods());
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Project package name string.
   *
   * @param modelGroup the model group
   * @return the string
   */
  protected String projectPackageName(IoModelGroup modelGroup) {
    return modelGroup
        .getDataType()
        .getOptionalPackageName()
        .map(packageName -> packageName.getText())
        .orElseThrow(() -> new IllegalArgumentException());
  }

  /**
   * Project imports set.
   *
   * @param modelGroup the model group
   * @return the set
   */
  protected Set<String> projectImports(IoModelGroup modelGroup) {
    Set<String> imports = new LinkedHashSet<>();

    modelGroup
        .getModels()
        .forEach(
            model -> {
              model
                  .getDataType()
                  .getOptionalPackageName()
                  .filter(
                      packageName ->
                          !packageName.equals(
                              modelGroup.getDataType().getOptionalPackageName().get()))
                  .ifPresent(
                      packageName ->
                          imports.add(
                              makeCanonicalObjectName(
                                  packageName, model.getDataType().getDataTypeName())));
            });

    return imports;
  }

  /**
   * Project description string.
   *
   * @param modelGroup the model group
   * @return the string
   */
  protected String projectDescription(IoModelGroup modelGroup) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(modelGroup.getDataType().getDataTypeName().getText()));

    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  /**
   * Project object name string.
   *
   * @param modelGroup the model group
   * @return the string
   */
  protected String projectObjectName(IoModelGroup modelGroup) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamel(modelGroup.getDataType().getDataTypeName().getText()));

    return stringBuilder.toString();
  }

  /**
   * Project object name with optional extends implements string.
   *
   * @param modelGroup the model group
   * @return the string
   */
  protected String projectObjectNameWithOptionalExtendsImplements(IoModelGroup modelGroup) {
    return projectObjectName(modelGroup);
  }

  /**
   * Project variables set.
   *
   * @param modelGroup the model group
   * @return the set
   */
  protected Set<ArgumentProjection> projectVariables(IoModelGroup modelGroup) {
    Set<ArgumentProjection> variables = new LinkedHashSet<>();

    modelGroup
        .getModels()
        .forEach(
            model -> {
              variables.add(
                  new ArgumentProjection(
                      fromDataTypeToJavaConverter.getDeclaredVariableType(model),
                      model.getVariableName().getText()));
            });

    return variables;
  }

  /**
   * Project constructors set.
   *
   * @return the set
   */
  protected Set<ConstructorProjection> projectConstructors(IoModelGroup modelGroup) {
    return new LinkedHashSet<>(
        Arrays.asList(new ConstructorProjection(projectVariables(modelGroup), "")));
  }

  /**
   * Project methods set.
   *
   * @return the set
   */
  protected Set<FunctionProjection> projectMethods() {
    // TODO
    return new LinkedHashSet<>();
  }

  /**
   * Make canonical object name string.
   *
   * @param packageName the package name
   * @param dataTypeName the data type name
   * @return the string
   */
  protected String makeCanonicalObjectName(PackageName packageName, DataTypeName dataTypeName) {
    return packageName.getText() + "." + TextConverter.toUpperCamel(dataTypeName.getText());
  }
}
