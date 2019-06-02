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

package io.polygenesis.generators.java.apidetail.transformer;

import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.apidetail.activity.service.ServiceMethodImplementationRegistry;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.apiimpl.ServiceMethodImplementation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformer.code.AbstractMethodTransformer;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Service implementation method representable.
 *
 * @author Christos Tsakostas
 */
public class ServiceMethodImplementationTransformer
    extends AbstractMethodTransformer<ServiceMethodImplementation> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final ServiceMethodImplementationRegistry serviceMethodImplementationRegistry;
  private final FreemarkerService freemarkerService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service method implementation representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param serviceMethodImplementationRegistry the service method implementation registry
   * @param freemarkerService the freemarker service
   */
  public ServiceMethodImplementationTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      ServiceMethodImplementationRegistry serviceMethodImplementationRegistry,
      FreemarkerService freemarkerService) {
    super(fromDataTypeToJavaConverter);
    this.serviceMethodImplementationRegistry = serviceMethodImplementationRegistry;
    this.freemarkerService = freemarkerService;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public MethodRepresentation create(ServiceMethodImplementation source, Object... args) {
    ServiceImplementation serviceImplementation = (ServiceImplementation) args[0];

    MethodRepresentation methodRepresentation = super.create(source, args);

    if (serviceMethodImplementationRegistry.isServiceMethodSupported(source.getServiceMethod())) {
      methodRepresentation.changeImplementationTo(
          serviceMethodImplementationRegistry
              .implementation(
                  freemarkerService,
                  serviceImplementation,
                  source.getServiceMethod(),
                  methodRepresentation)
              .orElseThrow(IllegalArgumentException::new));
    }

    return methodRepresentation;
  }

  @Override
  public MethodRepresentationType methodType(ServiceMethodImplementation source, Object... args) {
    return MethodRepresentationType.ANY;
  }

  @Override
  public Set<String> imports(ServiceMethodImplementation source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<String> annotations(ServiceMethodImplementation source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Override");

    return annotations;
  }

  @Override
  public String description(ServiceMethodImplementation source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(
            source.getServiceMethod().getFunction().getName().getText()));
    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(ServiceMethodImplementation source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String methodName(ServiceMethodImplementation source, Object... args) {
    return source.getServiceMethod().getFunction().getName().getText();
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(
      ServiceMethodImplementation source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    source
        .getServiceMethod()
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
  public String returnValue(ServiceMethodImplementation source, Object... args) {
    if (source.getServiceMethod().getFunction().getReturnValue() != null) {
      return makeVariableDataType(
          source.getServiceMethod().getFunction().getReturnValue().getData());
    } else {
      return fromDataTypeToJavaConverter.convert(PrimitiveType.VOID.name());
    }
  }
}
