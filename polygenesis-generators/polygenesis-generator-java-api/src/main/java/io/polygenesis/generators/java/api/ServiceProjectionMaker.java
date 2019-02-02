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

package io.polygenesis.generators.java.api;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.datatype.DataTypeName;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.models.api.Service;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Service projection maker.
 *
 * @author Christos Tsakostas
 */
public class ServiceProjectionMaker {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private MethodProjectionMaker methodProjectionMaker;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service projection maker.
   *
   * @param methodProjectionMaker the method projection maker
   */
  public ServiceProjectionMaker(MethodProjectionMaker methodProjectionMaker) {
    this.methodProjectionMaker = methodProjectionMaker;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Make service projection.
   *
   * @param service the service
   * @return the service projection
   */
  public ServiceProjection make(Service service) {
    ServiceProjection serviceProjection =
        new ServiceProjection(
            projectImports(service),
            projectDescription(service),
            projectObjectNameWithOptionalExtendsImplements(service),
            service,
            fillMethodProjections(service));

    return serviceProjection;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<String> projectImports(Service service) {
    Set<String> imports = new LinkedHashSet<>();

    service
        .getMethods()
        .forEach(
            method -> {
              method
                  .getReturnValue()
                  .getModel()
                  .getDataType()
                  .getOptionalPackageName()
                  .filter(packageName -> !packageName.equals(service.getPackageName()))
                  .ifPresent(
                      packageName ->
                          imports.add(
                              makeCanonicalObjectName(
                                  packageName,
                                  method
                                      .getReturnValue()
                                      .getModel()
                                      .getDataType()
                                      .getDataTypeName())));

              method
                  .getArguments()
                  .forEach(
                      argument -> {
                        argument
                            .getModel()
                            .getDataType()
                            .getOptionalPackageName()
                            .filter(packageName -> !packageName.equals(service.getPackageName()))
                            .ifPresent(
                                packageName ->
                                    imports.add(
                                        makeCanonicalObjectName(
                                            packageName,
                                            argument.getModel().getDataType().getDataTypeName())));
                      });
            });

    return imports;
  }

  private String projectDescription(Service service) {
    String contract;

    switch (service.getCqrsType()) {
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
        TextConverter.toUpperCamelSpaces(TextConverter.toPlural(service.getThingName().getText())));
  }

  private String projectObjectNameWithOptionalExtendsImplements(Service service) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(service.getServiceName().getText()));

    return stringBuilder.toString();
  }

  private Set<MethodProjection> fillMethodProjections(Service service) {
    Set<MethodProjection> methodProjections = new LinkedHashSet<>();

    service
        .getMethods()
        .forEach(method -> methodProjections.add(methodProjectionMaker.make(method)));

    return methodProjections;
  }

  private String makeCanonicalObjectName(PackageName packageName, DataTypeName dataTypeName) {
    return packageName.getText() + "." + TextConverter.toUpperCamel(dataTypeName.getText());
  }
}
