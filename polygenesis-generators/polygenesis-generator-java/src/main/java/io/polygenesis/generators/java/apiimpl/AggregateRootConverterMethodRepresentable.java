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

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Argument;
import io.polygenesis.core.Function;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.FunctionToMethodRepresentationConverter;
import java.util.stream.Collectors;

/**
 * The type Aggregate root converter method representable.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootConverterMethodRepresentable
    extends FunctionToMethodRepresentationConverter {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root converter method representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public AggregateRootConverterMethodRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public String modifiers(Function source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String implementation(Function source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    Argument argument =
        source.getArguments().stream().findFirst().orElseThrow(IllegalArgumentException::new);

    stringBuilder.append("\t\treturn new ");
    stringBuilder.append(
        TextConverter.toUpperCamel(
            source.getReturnValue().getData().getAsDataGroup().getDataType()));
    stringBuilder.append("(\n");

    stringBuilder.append(
        source
            .getReturnValue()
            .getData()
            .getAsDataGroup()
            .getModels()
            .stream()
            .map(
                model ->
                    String.format(
                        "\t\t\t\t%s.get%s()",
                        TextConverter.toLowerCamel(argument.getData().getVariableName().getText()),
                        TextConverter.toUpperCamel(model.getVariableName().getText())))
            .collect(Collectors.joining(",\n")));
    stringBuilder.append("\n");
    stringBuilder.append("\t\t);");

    return stringBuilder.toString();
  }
}
