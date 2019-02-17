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

package io.polygenesis.generators.java.shared;

import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.datatype.DataTypeName;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.models.api.Service;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Abstract service projection maker.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractServiceProjectionMaker implements ServiceProjectionMaker {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final MethodProjectionMaker methodProjectionMaker;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract service projection maker.
   *
   * @param methodProjectionMaker the method projection maker
   */
  public AbstractServiceProjectionMaker(MethodProjectionMaker methodProjectionMaker) {
    this.methodProjectionMaker = methodProjectionMaker;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectProjection make(Service service) {
    return new ObjectProjection(
        projectPackageName(service),
        projectImports(service),
        projectDescription(service),
        projectObjectName(service),
        projectObjectNameWithOptionalExtendsImplements(service),
        projectVariables(service),
        projectConstructors(service),
        fillMethodProjections(service));
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  private String projectPackageName(Service service) {
    return service.getPackageName().getText();
  }

  /**
   * Project imports set.
   *
   * @param service the service
   * @return the set
   */
  protected Set<String> projectImports(Service service) {
    Set<String> imports = new TreeSet<>();

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

  /**
   * Project description string.
   *
   * @param service the service
   * @return the string
   */
  protected String projectDescription(Service service) {
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

  /**
   * Project object name string.
   *
   * @param service the service
   * @return the string
   */
  protected String projectObjectName(Service service) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(service.getServiceName().getText()));

    return stringBuilder.toString();
  }

  /**
   * Project object name with optional extends implements string.
   *
   * @param service the service
   * @return the string
   */
  protected String projectObjectNameWithOptionalExtendsImplements(Service service) {
    return projectObjectName(service);
  }

  /**
   * Fill method projections set.
   *
   * @param service the service
   * @return the set
   */
  protected Set<MethodProjection> fillMethodProjections(Service service) {
    Set<MethodProjection> methodProjections = new LinkedHashSet<>();

    service
        .getMethods()
        .forEach(method -> methodProjections.add(methodProjectionMaker.make(method)));

    return methodProjections;
  }

  /**
   * Project variables set.
   *
   * @param service the service
   * @return the set
   */
  protected Set<KeyValue> projectVariables(Service service) {
    return new LinkedHashSet<>(Arrays.asList());
  }

  /**
   * Project constructors set.
   *
   * @param service the service
   * @return the set
   */
  protected Set<ConstructorProjection> projectConstructors(Service service) {
    return new LinkedHashSet<>();
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make canonical object name string.
   *
   * @param packageName the package name
   * @param dataTypeName the data type name
   * @return the string
   */
  private String makeCanonicalObjectName(PackageName packageName, DataTypeName dataTypeName) {
    return packageName.getText() + "." + TextConverter.toUpperCamel(dataTypeName.getText());
  }
}
