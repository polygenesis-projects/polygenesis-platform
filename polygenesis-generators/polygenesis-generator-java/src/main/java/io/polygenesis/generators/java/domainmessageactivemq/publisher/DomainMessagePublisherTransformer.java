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

package io.polygenesis.generators.java.domainmessageactivemq.publisher;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Domain message publisher transformer.
 *
 * @author Christos Tsakostas
 */
public class DomainMessagePublisherTransformer
    extends AbstractClassTransformer<DomainMessagePublisher, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message publisher transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public DomainMessagePublisherTransformer(
      DataTypeTransformer dataTypeTransformer,
      DomainMessageMethodPublisherTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public TemplateData transform(DomainMessagePublisher source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> fieldRepresentations(
      DomainMessagePublisher source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    fieldRepresentations.add(new FieldRepresentation("ProducerTemplate", "producerTemplate"));

    fieldRepresentations.add(new FieldRepresentation("String", "endpoint"));

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      DomainMessagePublisher source, Object... args) {
    ContextName contextName = (ContextName) args[0];
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation("ProducerTemplate", "producerTemplate"));

    String endpoint =
        String.format(
            "context.%s.api-client.domain-message.publisher", contextName.getText().toLowerCase());
    parameterRepresentations.add(
        new ParameterRepresentation(
            String.format("@Value(\"${%s}\") String", endpoint), "endpoint"));

    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();
    ConstructorRepresentation constructorRepresentation =
        new ConstructorRepresentation(
            new LinkedHashSet<>(),
            description(source, args),
            dataTypeTransformer.getModifierPublic(),
            parameterRepresentations,
            "\t\tthis.producerTemplate = producerTemplate;\n\t\tthis.endpoint = endpoint;");

    constructorRepresentations.add(constructorRepresentation);

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      DomainMessagePublisher source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(methodTransformer.create(source.getSend(), args));

    return methodRepresentations;
  }

  @Override
  public String packageName(DomainMessagePublisher source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(DomainMessagePublisher source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.api.clients.domainmessage.DomainMessageForwarder");
    imports.add("org.springframework.stereotype.Service");
    imports.add("org.apache.camel.ProducerTemplate");
    imports.add("org.springframework.beans.factory.annotation.Value");

    return imports;
  }

  @Override
  public Set<String> annotations(DomainMessagePublisher source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Service"));
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String fullObjectName(DomainMessagePublisher source, Object... args) {
    return String.format("%s implements DomainMessageForwarder", simpleObjectName(source, args));
  }
}
