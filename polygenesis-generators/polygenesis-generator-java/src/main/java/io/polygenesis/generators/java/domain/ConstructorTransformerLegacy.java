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

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.generators.java.implementations.domain.constructor.ConstructorImplementorRegistry;
import io.polygenesis.models.domain.Constructor;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.legacy.AbstractLegacyMethodTransformer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Constructor representable.
 *
 * @author Christos Tsakostas
 */
public class ConstructorTransformerLegacy extends AbstractLegacyMethodTransformer<Constructor> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final ConstructorImplementorRegistry constructorImplementorRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Constructor representable.
   *
   * @param dataTypeTransformer the from data type to java converter
   * @param constructorImplementorRegistry the constructor implementor registry
   */
  public ConstructorTransformerLegacy(
      DataTypeTransformer dataTypeTransformer,
      ConstructorImplementorRegistry constructorImplementorRegistry) {
    super(dataTypeTransformer);
    this.constructorImplementorRegistry = constructorImplementorRegistry;
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public MethodRepresentation create(Constructor source, Object... args) {
    MethodRepresentation methodRepresentation = super.create(source, args);

    if (constructorImplementorRegistry.isSupported(source)) {
      methodRepresentation.changeImplementationTo(
          constructorImplementorRegistry.implementation(source, methodRepresentation));
    }

    return methodRepresentation;
  }

  @Override
  public MethodRepresentationType methodType(Constructor source, Object... args) {
    return MethodRepresentationType.CONSTRUCTOR;
  }

  @Override
  public Set<String> imports(Constructor source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<String> annotations(Constructor source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String description(Constructor source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(source.getFunction().getName().getText()));
    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(Constructor source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String methodName(Constructor source, Object... args) {
    return TextConverter.toUpperCamel(source.getFunction().getThing().getThingName().getText());
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(Constructor source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    source
        .getProperties()
        .forEach(
            property ->
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        dataTypeTransformer.convert(property.getData().getDataType()),
                        property.getData().getVariableName().getText())));

    return parameterRepresentations;
  }

  @Override
  public String returnValue(Constructor source, Object... args) {
    return "";
  }

  @Override
  public String implementation(Constructor source, Object... args) {
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
