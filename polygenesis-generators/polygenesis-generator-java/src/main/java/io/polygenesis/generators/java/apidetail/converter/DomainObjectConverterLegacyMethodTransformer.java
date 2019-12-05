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

package io.polygenesis.generators.java.apidetail.converter;

import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.generators.java.apidetail.converter.activity.ConverterMethodImplementationRegistry;
import io.polygenesis.models.apiimpl.DomainEntityConverterMethod;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.legacy.AbstractLegacyMethodTransformer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Domain object converter method representable.
 *
 * @author Christos Tsakostas
 */
public class DomainObjectConverterLegacyMethodTransformer
    extends AbstractLegacyMethodTransformer<DomainEntityConverterMethod> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final FreemarkerService freemarkerService;
  private final ConverterMethodImplementationRegistry converterMethodImplementationRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain object converter method representable.
   *
   * @param dataTypeTransformer the from data type to java converter
   * @param freemarkerService the freemarker service
   * @param converterMethodImplementationRegistry the converter method implementation registry
   */
  public DomainObjectConverterLegacyMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      FreemarkerService freemarkerService,
      ConverterMethodImplementationRegistry converterMethodImplementationRegistry) {
    super(dataTypeTransformer);
    this.freemarkerService = freemarkerService;
    this.converterMethodImplementationRegistry = converterMethodImplementationRegistry;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public MethodRepresentation create(DomainEntityConverterMethod source, Object... args) {
    MethodRepresentation methodRepresentation = super.create(source, args);

    if (converterMethodImplementationRegistry.isConverterMethodSupported(source)) {
      methodRepresentation.changeImplementationTo(
          converterMethodImplementationRegistry
              .implementation(freemarkerService, source, methodRepresentation)
              .orElseThrow(IllegalArgumentException::new));
    }

    return methodRepresentation;
  }

  @Override
  public MethodRepresentationType methodType(DomainEntityConverterMethod source, Object... args) {
    return MethodRepresentationType.ANY;
  }

  @Override
  public Set<String> imports(DomainEntityConverterMethod source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<String> annotations(DomainEntityConverterMethod source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String description(DomainEntityConverterMethod source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(source.getFunction().getName().getText()));
    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(DomainEntityConverterMethod source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String methodName(DomainEntityConverterMethod source, Object... args) {
    return source.getFunction().getName().getText();
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(
      DomainEntityConverterMethod source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    source
        .getFunction()
        .getArguments()
        .forEach(
            argument ->
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        dataTypeTransformer.convert(argument.getDataType()),
                        argument.getVariableName().getText())));

    return parameterRepresentations;
  }

  @Override
  public String returnValue(DomainEntityConverterMethod source, Object... args) {
    if (source.getFunction().getReturnValue() != null) {
      return makeVariableDataType(source.getFunction().getReturnValue());
    } else {
      return dataTypeTransformer.convert(PrimitiveType.VOID.name());
    }
  }

  @Override
  public String implementation(DomainEntityConverterMethod source, Object... args) {
    if (source.getFunction().getReturnValue() == null) {
      return "\t\t// TODO: implementation";
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("\t\t// TODO: implementation\n");
      stringBuilder.append("\t\treturn null;");
      return stringBuilder.toString();
    }
  }
}
