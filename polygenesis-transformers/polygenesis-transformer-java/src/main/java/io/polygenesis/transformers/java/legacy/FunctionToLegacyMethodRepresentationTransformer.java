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

package io.polygenesis.transformers.java.legacy;

import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Function to method representation transformer.
 *
 * @author Christos Tsakostas
 */
public class FunctionToLegacyMethodRepresentationTransformer
    extends AbstractLegacyMethodTransformer<Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Function to legacy method representation transformer.
   *
   * @param dataTypeTransformer the data type transformer
   */
  public FunctionToLegacyMethodRepresentationTransformer(DataTypeTransformer dataTypeTransformer) {
    super(dataTypeTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public MethodRepresentationType methodType(Function source, Object... args) {
    return MethodRepresentationType.ANY;
  }

  @Override
  public Set<String> imports(Function source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<String> annotations(Function source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String description(Function source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getName().getText()));
    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(Function source, Object... args) {
    return "";
  }

  @Override
  public String methodName(Function source, Object... args) {
    return source.getName().getText();
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(Function source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    source
        .getArguments()
        .forEach(
            argument ->
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        dataTypeTransformer.convert(argument.getData().getDataType()),
                        argument.getData().getVariableName().getText())));

    return parameterRepresentations;
  }

  @Override
  public String returnValue(Function source, Object... args) {
    if (source.getReturnValue() != null) {
      return makeVariableDataType(source.getReturnValue().getData());
    } else {
      return dataTypeTransformer.convert(PrimitiveType.VOID.name());
    }
  }

  @Override
  public String implementation(Function source, Object... args) {
    return "";
  }
}
