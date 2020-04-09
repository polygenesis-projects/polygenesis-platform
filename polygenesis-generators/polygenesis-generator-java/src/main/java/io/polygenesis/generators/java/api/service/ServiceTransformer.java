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

package io.polygenesis.generators.java.api.service;

import static java.util.stream.Collectors.toCollection;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.java.AbstractInterfaceTransformer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * The type Service transformer.
 *
 * @author Christos Tsakostas
 */
public class ServiceTransformer extends AbstractInterfaceTransformer<Service, ServiceMethod> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public ServiceTransformer(
      DataTypeTransformer dataTypeTransformer, ServiceMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(Service source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Interface.java.ftl");
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Service source, Object... args) {
    return source
        .getServiceMethods()
        .stream()
        .map(methodTransformer::create)
        .collect(toCollection(LinkedHashSet::new));
  }

  @Override
  public String packageName(Service source, Object... args) {
    return source.getPackageName().getText();
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
