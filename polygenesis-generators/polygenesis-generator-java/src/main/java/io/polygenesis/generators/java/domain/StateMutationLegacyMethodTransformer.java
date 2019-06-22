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

package io.polygenesis.generators.java.domain;

import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.implementations.domain.StateMutationMethodImplementorRegistry;
import io.polygenesis.generators.java.shared.transformer.AbstractLegacyMethodTransformer;
import io.polygenesis.generators.java.shared.transformer.FromDataTypeToJavaConverter;
import io.polygenesis.models.domain.StateMutationMethod;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type State mutation method representable.
 *
 * @author Christos Tsakostas
 */
public class StateMutationLegacyMethodTransformer
    extends AbstractLegacyMethodTransformer<StateMutationMethod> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final StateMutationMethodImplementorRegistry stateMutationMethodImplementorRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new State mutation method representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param stateMutationMethodImplementorRegistry the state mutation method implementor registry
   */
  public StateMutationLegacyMethodTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      StateMutationMethodImplementorRegistry stateMutationMethodImplementorRegistry) {
    super(fromDataTypeToJavaConverter);
    this.stateMutationMethodImplementorRegistry = stateMutationMethodImplementorRegistry;
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public MethodRepresentation create(StateMutationMethod source, Object... args) {
    MethodRepresentation methodRepresentation = super.create(source, args);

    if (stateMutationMethodImplementorRegistry.isSupported(source)) {
      methodRepresentation.changeImplementationTo(
          stateMutationMethodImplementorRegistry.implementation(source, methodRepresentation));
    }

    return methodRepresentation;
  }

  @Override
  public MethodRepresentationType methodType(StateMutationMethod source, Object... args) {
    return MethodRepresentationType.MODIFY;
  }

  @Override
  public Set<String> imports(StateMutationMethod source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<String> annotations(StateMutationMethod source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String description(StateMutationMethod source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(source.getFunction().getName().getText()));
    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(StateMutationMethod source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String methodName(StateMutationMethod source, Object... args) {
    return source.getFunction().getName().getText();
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(
      StateMutationMethod source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    source
        .getFunction()
        .getArguments()
        .forEach(
            argument ->
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        fromDataTypeToJavaConverter.convert(argument.getData().getDataType()),
                        argument.getData().getVariableName().getText())));

    return parameterRepresentations;
  }

  @Override
  public String returnValue(StateMutationMethod source, Object... args) {
    if (source.getFunction().getReturnValue() != null) {
      return makeVariableDataType(source.getFunction().getReturnValue().getData());
    } else {
      return fromDataTypeToJavaConverter.convert(PrimitiveType.VOID.name());
    }
  }

  @Override
  public String implementation(StateMutationMethod source, Object... args) {
    if (source.getFunction().getReturnValue() == null) {
      return "\t\t// TODO [PolyGenesis]: write implementation here";
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("\t\t// TODO [PolyGenesis]: write implementation here\n");
      stringBuilder.append("\t\treturn null;");
      return stringBuilder.toString();
    }
  }
}
