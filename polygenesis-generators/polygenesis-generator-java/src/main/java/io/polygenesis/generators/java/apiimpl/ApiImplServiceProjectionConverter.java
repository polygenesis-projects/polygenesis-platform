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
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.shared.AbstractServiceProjectionMaker;
import io.polygenesis.generators.java.shared.ConstructorProjection;
import io.polygenesis.generators.java.shared.ObjectProjection;
import io.polygenesis.models.api.Service;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Api impl service projection maker.
 *
 * @author Christos Tsakostas
 */
public class ApiImplServiceProjectionConverter extends AbstractServiceProjectionMaker
    implements Converter<Service, ObjectProjection> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Api impl service projection maker.
   *
   * @param methodProjectionMaker the method projection maker
   */
  public ApiImplServiceProjectionConverter(ApiImplMethodProjectionMaker methodProjectionMaker) {
    super(methodProjectionMaker);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectProjection convert(Service source, Object... arg) {
    return super.make(source);
  }

  @Override
  protected Set<String> projectImports(Service service) {
    Set<String> imports = super.projectImports(service);

    imports.add("com.oregor.ddd4j.check.assertion.Assertion");
    imports.add("org.springframework.stereotype.Service");
    imports.add("org.springframework.transaction.annotation.Transactional");

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
    stringBuilder.append("Impl");
    stringBuilder.append(" implements ");
    stringBuilder.append(TextConverter.toUpperCamel(service.getServiceName().getText()));

    return stringBuilder.toString();
  }

  @Override
  protected Set<KeyValue> projectVariables(Service service) {
    return new LinkedHashSet<>(
        Arrays.asList(
            new KeyValue(
                TextConverter.toUpperCamel(service.getThingName().getText()) + "Persistence",
                TextConverter.toLowerCamel(service.getThingName().getText()) + "Persistence")));
  }

  @Override
  protected Set<ConstructorProjection> projectConstructors(Service service) {
    return new LinkedHashSet<>(
        Arrays.asList(new ConstructorProjection(projectVariables(service), "")));
  }
}
