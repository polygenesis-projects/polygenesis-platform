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
import io.polygenesis.core.Function;
import io.polygenesis.models.api.Method;
import io.polygenesis.models.apiimpl.AggregateRootConverter;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.representations.java.AbstractMethodRepresentable;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodType;
import io.polygenesis.representations.java.ParameterRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Service implementation method representable.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementationMethodRepresentable extends AbstractMethodRepresentable<Method> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final ServiceImplementationMethodCommand serviceImplementationMethodCommandRepresentable;
  private final ServiceImplementationMethodQuery serviceImplementationMethodQueryRepresentable;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service implementation method representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param serviceImplementationMethodCommandRepresentable the service implementation method
   *     command representable
   * @param serviceImplementationMethodQueryRepresentable the service implementation method query
   *     representable
   */
  public ServiceImplementationMethodRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      ServiceImplementationMethodCommand serviceImplementationMethodCommandRepresentable,
      ServiceImplementationMethodQuery serviceImplementationMethodQueryRepresentable) {
    super(fromDataTypeToJavaConverter);
    this.serviceImplementationMethodCommandRepresentable =
        serviceImplementationMethodCommandRepresentable;
    this.serviceImplementationMethodQueryRepresentable =
        serviceImplementationMethodQueryRepresentable;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public MethodType methodType(Method source, Object... args) {
    return MethodType.ANY;
  }

  @Override
  public Set<String> annotations(Method source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Override");

    return annotations;
  }

  @Override
  public String description(Method source, Object... args) {
    return "Some description here please.";
  }

  @Override
  public String modifiers(Method source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String methodName(Method source, Object... args) {
    return source.getFunction().getName().getText();
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(Method source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    source
        .getFunction()
        .getArguments()
        .forEach(
            argument -> {
              parameterRepresentations.add(
                  new ParameterRepresentation(
                      fromDataTypeToJavaConverter.getDeclaredVariableType(argument.getModel()),
                      argument.getModel().getVariableName().getText()));
            });

    return parameterRepresentations;
  }

  @Override
  public String returnValue(Method source, Object... args) {
    // TODO - primitives
    return TextConverter.toUpperCamel(
        source.getFunction().getReturnValue().getModel().getDataType().getDataTypeName().getText());
  }

  @Override
  public String implementation(Method source, Object... args) {
    Function function = source.getFunction();
    AggregateRoot aggregateRoot = (AggregateRoot) args[0];
    AggregateRootConverter aggregateRootConverter = (AggregateRootConverter) args[1];

    if (function.getGoal().isCommand()) {
      return serviceImplementationMethodCommandRepresentable.makeCommandImplementation(
          function, aggregateRoot, aggregateRootConverter);
    } else {
      return serviceImplementationMethodQueryRepresentable.makeQueryImplementation(
          function, aggregateRoot, aggregateRootConverter);
    }
  }
}
