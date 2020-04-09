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
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.models.apiimpl.DomainObjectConverterMethod;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Domain object converter method transformer.
 *
 * @author Christos Tsakostas
 */
public class DomainObjectConverterMethodTransformer
    extends AbstractMethodTransformer<DomainObjectConverterMethod> {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private DomainObjectConverterActivityRegistry domainObjectConverterActivityRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain object converter method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param domainObjectConverterActivityRegistry the service aspect activity registry
   */
  public DomainObjectConverterMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      DomainObjectConverterActivityRegistry domainObjectConverterActivityRegistry) {
    super(dataTypeTransformer);
    this.domainObjectConverterActivityRegistry = domainObjectConverterActivityRegistry;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public MethodRepresentationType methodType(DomainObjectConverterMethod source, Object... args) {
    return MethodRepresentationType.ANY;
  }

  @Override
  public Set<String> imports(DomainObjectConverterMethod source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<String> annotations(DomainObjectConverterMethod source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String description(DomainObjectConverterMethod source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(source.getFunction().getName().getFullName()));
    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(DomainObjectConverterMethod source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String methodName(DomainObjectConverterMethod source, Object... args) {
    return source.getFunction().getName().getFullName();
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(
      DomainObjectConverterMethod source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    source
        .getFunction()
        .getArguments()
        .getData()
        .forEach(
            argument ->
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        dataTypeTransformer.convert(argument.getDataType()),
                        argument.getVariableName().getText())));

    return parameterRepresentations;
  }

  @Override
  public String returnValue(DomainObjectConverterMethod source, Object... args) {
    if (source.getFunction().getReturnValue() != null) {
      return makeVariableDataType(source.getFunction().getReturnValue());
    } else {
      return dataTypeTransformer.convert(PrimitiveType.VOID.name());
    }
  }

  @Override
  public String implementation(DomainObjectConverterMethod source, Object... args) {
    if (domainObjectConverterActivityRegistry.isActivitySupportedFor(source)) {
      return domainObjectConverterActivityRegistry.activityFor(source, args);
    } else {
      return super.implementation(source, args);
    }
  }
}
