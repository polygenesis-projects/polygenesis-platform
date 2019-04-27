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

package io.polygenesis.generators.java.exporters.api;

import static java.util.stream.Collectors.toCollection;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Argument;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.generators.java.skeletons.AbstractInterfaceRepresentable;
import io.polygenesis.generators.java.skeletons.FromDataTypeToJavaConverter;
import io.polygenesis.generators.java.skeletons.FunctionToMethodRepresentationConverter;
import io.polygenesis.generators.java.skeletons.MethodRepresentation;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMethod;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Service interface representable.
 *
 * @author Christos Tsakostas
 */
public class ServiceInterfaceRepresentable extends AbstractInterfaceRepresentable<Service> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service interface representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param functionToMethodRepresentationConverter the function to method representation converter
   */
  public ServiceInterfaceRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      FunctionToMethodRepresentationConverter functionToMethodRepresentationConverter) {
    super(fromDataTypeToJavaConverter, functionToMethodRepresentationConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<MethodRepresentation> methodRepresentations(Service source, Object... args) {
    return source
        .getServiceMethods()
        .stream()
        .map(ServiceMethod::getFunction)
        .map(functionToMethodRepresentationConverter::create)
        .collect(toCollection(LinkedHashSet::new));
  }

  @Override
  public String packageName(Service source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(Service source, Object... args) {
    Set<String> imports = new TreeSet<>();

    source
        .getServiceMethods()
        .forEach(
            method -> {
              imports.addAll(functionToMethodRepresentationConverter.imports(method.getFunction()));

              if (method.getFunction().getReturnValue() != null
                  && method.getFunction().getReturnValue().getData().isDataGroup()) {
                DataGroup dataGroup =
                    method.getFunction().getReturnValue().getData().getAsDataGroup();

                if (!dataGroup.getPackageName().equals(source.getPackageName())) {
                  imports.add(
                      makeCanonicalObjectName(dataGroup.getPackageName(), dataGroup.getDataType()));
                }
              }

              method
                  .getFunction()
                  .getArguments()
                  .stream()
                  .filter(argument -> argument.getData().isDataGroup())
                  .map(Argument::getData)
                  .map(DataGroup.class::cast)
                  .forEach(
                      dataGroup -> {
                        if (!dataGroup.getPackageName().equals(source.getPackageName())) {
                          imports.add(
                              makeCanonicalObjectName(
                                  dataGroup.getPackageName(), dataGroup.getDataType()));
                        }
                      });
            });

    return imports;
  }

  @Override
  public Set<String> annotations(Service source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String description(Service source, Object... args) {
    String contract;

    switch (source.getCqrsType()) {
      case COMMAND:
        contract = "Commands contract";
        break;
      case QUERY:
        contract = "Queries contract";
        break;
      default:
        contract = "Contract";
        break;
    }

    return String.format(
        "%s for %s.",
        contract,
        TextConverter.toUpperCamelSpaces(TextConverter.toPlural(source.getThingName().getText())));
  }

  @Override
  public String modifiers(Service source, Object... args) {
    return "public";
  }

  @Override
  public String simpleObjectName(Service source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getServiceName().getText()));

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(Service source, Object... args) {
    return simpleObjectName(source);
  }
}
