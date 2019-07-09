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

package io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisher;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.shared.transformer.AbstractClassTransformer;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Scheduled domain message publisher transformer.
 *
 * @author Christos Tsakostas
 */
public class ScheduledDomainMessagePublisherTransformer
    extends AbstractClassTransformer<ScheduledDomainMessagePublisher, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Scheduled domain message publisher transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public ScheduledDomainMessagePublisherTransformer(
      DataTypeTransformer dataTypeTransformer,
      ScheduledDomainMessagePublisherMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public TemplateData transform(ScheduledDomainMessagePublisher source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      ScheduledDomainMessagePublisher source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    ContextName contextName = (ContextName) args[0];
    String contextNameUpperCamel = TextConverter.toUpperCamel(contextName.getText());

    parameterRepresentations.add(
        new ParameterRepresentation(
            String.format("%sDomainMessageDataRepository", contextNameUpperCamel),
            "domainMessageDataRepository"));

    parameterRepresentations.add(
        new ParameterRepresentation(
            String.format("%sDomainMessagePublishedDataRepository", contextNameUpperCamel),
            "domainMessagePublishedDataRepository"));

    parameterRepresentations.add(
        new ParameterRepresentation(
            String.format("%sDomainMessagePublishedDataConverter", contextNameUpperCamel),
            "domainMessagePublishedDataConverter"));

    parameterRepresentations.add(
        new ParameterRepresentation(
            String.format("%sDomainMessagePublishDtoConverter", contextNameUpperCamel),
            "domainMessagePublishDtoConverter"));

    parameterRepresentations.add(
        new ParameterRepresentation(
            String.format("%sDomainMessagePublisher", contextNameUpperCamel),
            "domainMessagePublisher"));

    parameterRepresentations.add(
        new ParameterRepresentation(
            "@Value(\"${defaultPageSize:10}\") Integer", "defaultPageSize"));

    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    ConstructorRepresentation constructorRepresentation =
        new ConstructorRepresentation(
            new LinkedHashSet<>(),
            description(source, args),
            dataTypeTransformer.getModifierPublic(),
            parameterRepresentations,
            "\t\tsuper(domainMessageDataRepository, domainMessagePublishedDataRepository,\n"
                + "\t\t\t\tdomainMessagePublishedDataConverter, domainMessagePublishDtoConverter,\n"
                + "\t\t\t\tdomainMessagePublisher,\n"
                + "\t\t\t\tdefaultPageSize);");

    constructorRepresentations.add(constructorRepresentation);

    return constructorRepresentations;
  }

  @Override
  public String packageName(ScheduledDomainMessagePublisher source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(ScheduledDomainMessagePublisher source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.domain.AbstractScheduledDomainMessagePublisher");
    imports.add("org.springframework.stereotype.Service");
    imports.add("org.springframework.beans.factory.annotation.Value");
    imports.add("org.springframework.transaction.annotation.Transactional");

    return imports;
  }

  @Override
  public Set<String> annotations(ScheduledDomainMessagePublisher source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Service", "@Transactional"));
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String fullObjectName(ScheduledDomainMessagePublisher source, Object... args) {
    ContextName contextName = (ContextName) args[0];
    return String.format(
        "%s%n\t\textends AbstractScheduledDomainMessagePublisher<%sDomainMessagePublishedData, "
            + "%sDomainMessagePublishDto>",
        simpleObjectName(source, args),
        TextConverter.toUpperCamel(contextName.getText()),
        TextConverter.toUpperCamel(contextName.getText()));
  }
}
