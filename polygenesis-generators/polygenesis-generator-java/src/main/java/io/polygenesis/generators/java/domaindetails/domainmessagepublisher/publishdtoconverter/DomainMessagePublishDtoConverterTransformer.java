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

package io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publishdtoconverter;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Domain message publish dto converter transformer.
 *
 * @author Christos Tsakostas
 */
public class DomainMessagePublishDtoConverterTransformer
    extends AbstractClassTransformer<DomainMessagePublishDtoConverter, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message publish dto converter transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public DomainMessagePublishDtoConverterTransformer(
      DataTypeTransformer dataTypeTransformer,
      DomainMessagePublishDtoConverterMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public TemplateData transform(DomainMessagePublishDtoConverter source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      DomainMessagePublishDtoConverter source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(methodTransformer.create(source.getGetContext(), args));

    return methodRepresentations;
  }

  @Override
  public String packageName(DomainMessagePublishDtoConverter source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(DomainMessagePublishDtoConverter source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.domain.AbstractDomainMessagePublishDtoConverter");
    imports.add("org.springframework.stereotype.Service");

    return imports;
  }

  @Override
  public Set<String> annotations(DomainMessagePublishDtoConverter source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Service"));
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      DomainMessagePublishDtoConverter source, Object... args) {
    ContextName contextName = (ContextName) args[0];
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    constructorRepresentations.add(
        createEmptyConstructorWithImplementation(
            TextConverter.toUpperCamel(source.getObjectName().getText()),
            new LinkedHashSet<>(),
            String.format(
                "\t\tsuper(%sDomainMessagePublishDto.class);",
                TextConverter.toUpperCamel(contextName.getText()))));

    return constructorRepresentations;
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String fullObjectName(DomainMessagePublishDtoConverter source, Object... args) {
    ContextName contextName = (ContextName) args[0];
    return String.format(
        "%s%n\t\textends AbstractDomainMessagePublishDtoConverter<%sDomainMessagePublishDto>",
        simpleObjectName(source, args), TextConverter.toUpperCamel(contextName.getText()));
  }
}
