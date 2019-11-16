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

package io.polygenesis.generators.java.rdbms.domainmessage.publisheddatarepositoryimpl;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Domain message published data repository impl transformer.
 *
 * @author Christos Tsakostas
 */
public class DomainMessagePublishedDataRepositoryImplTransformer
    extends AbstractClassTransformer<DomainMessagePublishedDataRepositoryImpl, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message published data repository impl transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public DomainMessagePublishedDataRepositoryImplTransformer(
      DataTypeTransformer dataTypeTransformer,
      DomainMessagePublishedDataRepositoryImplMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public TemplateData transform(DomainMessagePublishedDataRepositoryImpl source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public String packageName(DomainMessagePublishedDataRepositoryImpl source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(DomainMessagePublishedDataRepositoryImpl source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.domain.AbstractJpaUuidRepository");
    imports.add("org.springframework.stereotype.Repository");

    return imports;
  }

  @Override
  public Set<String> annotations(DomainMessagePublishedDataRepositoryImpl source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Repository");

    return annotations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      DomainMessagePublishedDataRepositoryImpl source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    ObjectName contextName = (ObjectName) args[0];
    String contextNameUpperCamel = TextConverter.toUpperCamel(contextName.getText());

    parameterRepresentations.add(
        new ParameterRepresentation(
            String.format("%sSpringDomainMessagePublishedDataRepository", contextNameUpperCamel),
            "springDataGenericRepository"));

    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    ConstructorRepresentation constructorRepresentation =
        new ConstructorRepresentation(
            new LinkedHashSet<>(),
            description(source, args),
            dataTypeTransformer.getModifierPublic(),
            parameterRepresentations,
            "\t\tsuper(springDataGenericRepository);");

    constructorRepresentations.add(constructorRepresentation);

    return constructorRepresentations;
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String fullObjectName(DomainMessagePublishedDataRepositoryImpl source, Object... args) {
    ObjectName contextName = (ObjectName) args[0];
    return String.format(
        "%s%n\t\textends AbstractJpaUuidRepository<%sDomainMessagePublishedData>"
            + "%n\t\timplements %sDomainMessagePublishedDataRepository",
        simpleObjectName(source, args),
        TextConverter.toUpperCamel(contextName.getText()),
        TextConverter.toUpperCamel(contextName.getText()));
  }
}
