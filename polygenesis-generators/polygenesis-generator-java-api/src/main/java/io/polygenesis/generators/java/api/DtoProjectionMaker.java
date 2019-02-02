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
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.core.datatype.DataTypeName;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.iomodel.IoModelGroup;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Io model group projection maker.
 *
 * @author Christos Tsakostas
 */
public class DtoProjectionMaker {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private FromDataTypeToJavaConverter fromDataTypeToJavaConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model group projection maker.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public DtoProjectionMaker(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    this.fromDataTypeToJavaConverter = fromDataTypeToJavaConverter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Make io model group projection.
   *
   * @param modelGroup the io model group
   * @return the io model group projection
   */
  public DtoProjection make(IoModelGroup modelGroup) {
    return new DtoProjection(
        projectImports(modelGroup),
        projectDescription(modelGroup),
        projectObjectNameWithOptionalExtendsImplements(modelGroup),
        modelGroup,
        projectVariables(modelGroup));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<String> projectImports(IoModelGroup modelGroup) {
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

  private String projectDescription(IoModelGroup modelGroup) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(modelGroup.getDataType().getDataTypeName().getText()));

    stringBuilder.append(" DTO.");

    return stringBuilder.toString();
  }

  private String projectObjectNameWithOptionalExtendsImplements(IoModelGroup modelGroup) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamel(modelGroup.getDataType().getDataTypeName().getText()));

    return stringBuilder.toString();
  }

  private Set<KeyValue> projectVariables(IoModelGroup modelGroup) {
    Set<KeyValue> variables = new LinkedHashSet<>();

    modelGroup
        .getModels()
        .forEach(
            model -> {
              variables.add(
                  new KeyValue(
                      fromDataTypeToJavaConverter.getDeclaredVariableType(model),
                      model.getVariableName().getText()));
            });

    return variables;
  }

  private String makeCanonicalObjectName(PackageName packageName, DataTypeName dataTypeName) {
    return packageName.getText() + "." + TextConverter.toUpperCamel(dataTypeName.getText());
  }
}
