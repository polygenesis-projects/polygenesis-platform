/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.generators.java.domainservicedetail;

import static java.util.stream.Collectors.toCollection;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.models.domain.DomainService;
import io.polygenesis.models.domain.DomainServiceMethod;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DomainServiceDetailTransformer
    extends AbstractClassTransformer<DomainService, DomainServiceMethod> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain service detail transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public DomainServiceDetailTransformer(
      DataTypeTransformer dataTypeTransformer,
      DomainServiceDetailMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(DomainService source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));
    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @SuppressWarnings("CPD-START")
  @Override
  public Set<MethodRepresentation> methodRepresentations(DomainService source, Object... args) {
    return source.getDomainServiceMethods().stream()
        .map(methodTransformer::create)
        .collect(toCollection(LinkedHashSet::new));
  }

  @Override
  public String packageName(DomainService source, Object... args) {
    return source.getPackageName().getText();
  }

  @SuppressWarnings("CPD-END")
  @Override
  public Set<String> imports(DomainService source, Object... args) {
    Set<String> imports = new TreeSet<>();

    source
        .getDomainServiceMethods()
        .forEach(method -> imports.addAll(methodTransformer.imports(method)));

    imports.add("org.springframework.stereotype.Service");

    return imports;
  }

  @Override
  public String description(DomainService source, Object... args) {
    return String.format(
        "The %s Implementation.",
        TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));
  }

  @Override
  public Set<String> annotations(DomainService source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Service");

    return annotations;
  }

  @Override
  public String simpleObjectName(DomainService source, Object... args) {
    return String.format("%sImpl", TextConverter.toUpperCamel(source.getObjectName().getText()));
  }

  @Override
  public String fullObjectName(DomainService source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        String.format("%sImpl", TextConverter.toUpperCamel(source.getObjectName().getText())));
    stringBuilder.append(" implements ");
    stringBuilder.append(
        String.format("%s", TextConverter.toUpperCamel(source.getObjectName().getText())));

    return stringBuilder.toString();
  }
}
