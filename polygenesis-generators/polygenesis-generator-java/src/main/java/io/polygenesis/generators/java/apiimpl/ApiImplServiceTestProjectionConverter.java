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

import io.polygenesis.commons.converter.Converter;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.shared.ObjectProjection;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import java.util.Set;

/**
 * The type Api impl service projection maker.
 *
 * @author Christos Tsakostas
 */
public class ApiImplServiceTestProjectionConverter extends ApiImplServiceProjectionConverter
    implements Converter<ServiceImplementation, ObjectProjection> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Api impl service projection maker.
   *
   * @param methodProjectionMaker the method projection maker
   */
  public ApiImplServiceTestProjectionConverter(
      ApiImplMethodProjectionConverter methodProjectionMaker) {
    super(methodProjectionMaker);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectProjection convert(ServiceImplementation serviceImplementation, Object... args) {
    Service service = serviceImplementation.getService();

    return new ObjectProjection(
        projectPackageName(service),
        projectImports(service),
        projectDescription(service),
        projectObjectName(service),
        projectObjectNameWithOptionalExtendsImplements(service),
        projectVariables(serviceImplementation),
        projectConstructors(service),
        fillFunctionProjections(service));
  }

  @Override
  protected Set<String> projectImports(Service service) {
    Set<String> imports = super.projectImports(service);

    imports.add("static org.assertj.core.api.Assertions.assertThat");
    imports.add("static org.assertj.core.api.Assertions.assertThatThrownBy");
    imports.add("static org.mockito.Mockito.mock");
    imports.add("org.junit.Before");
    imports.add("org.junit.Test");

    return imports;
  }

  @Override
  protected String projectObjectName(Service service) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(service.getServiceName().getText()));
    stringBuilder.append("Impl");

    return stringBuilder.toString();
  }

  @Override
  protected String projectObjectNameWithOptionalExtendsImplements(Service service) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(service.getServiceName().getText()));
    stringBuilder.append("ImplTest");

    return stringBuilder.toString();
  }
}
