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

package io.polygenesis.generators.java.apiimpl;

import io.polygenesis.commons.converter.Converter;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Argument;
import io.polygenesis.core.Function;
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.generators.java.shared.AbstractFunctionProjectionMaker;
import java.util.stream.Collectors;

/**
 * The type Aggregate root converter method projection converter.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootConverterMethodProjectionConverter extends AbstractFunctionProjectionMaker
    implements Converter<Function, AggregateRootConverterMethodProjection> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Api impl method projection maker.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public AggregateRootConverterMethodProjectionConverter(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public AggregateRootConverterMethodProjection convert(Function function, Object... args) {
    return new AggregateRootConverterMethodProjection(
        function,
        projectName(function),
        projectDescription(),
        projectReturnValue(function),
        projectArguments(function),
        projectAnnotations(),
        projectImplementation(function));
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Project implementation string.
   *
   * @param function the function
   * @return the string
   */
  protected String projectImplementation(Function function) {
    StringBuilder stringBuilder = new StringBuilder();

    Argument argument =
        function.getArguments().stream().findFirst().orElseThrow(IllegalArgumentException::new);

    stringBuilder.append("\t\treturn new ");
    stringBuilder.append(
        TextConverter.toUpperCamel(
            function
                .getReturnValue()
                .getAsIoModelGroup()
                .getDataType()
                .getDataTypeName()
                .getText()));
    stringBuilder.append("(\n");

    stringBuilder.append(
        function
            .getReturnValue()
            .getAsIoModelGroup()
            .getModels()
            .stream()
            .map(
                model ->
                    String.format(
                        "\t\t\t\t%s.get%s()",
                        TextConverter.toLowerCamel(argument.getModel().getVariableName().getText()),
                        TextConverter.toUpperCamel(model.getVariableName().getText())))
            .collect(Collectors.joining(",\n")));
    stringBuilder.append("\n");
    stringBuilder.append("\t\t);");

    return stringBuilder.toString();
  }
}
