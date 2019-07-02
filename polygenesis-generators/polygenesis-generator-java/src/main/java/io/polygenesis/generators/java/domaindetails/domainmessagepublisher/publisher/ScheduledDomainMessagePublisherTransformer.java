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

package io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publisher;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.shared.transformer.AbstractClassTransformer;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Christos Tsakostas
 */
public class ScheduledDomainMessagePublisherTransformer
    extends AbstractClassTransformer<BatchProcessMessagePublisher, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

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
  public TemplateData transform(BatchProcessMessagePublisher source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> fieldRepresentations(
      BatchProcessMessagePublisher source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    fieldRepresentations.add(new FieldRepresentation("ProducerTemplate", "producerTemplate"));

    fieldRepresentations.add(new FieldRepresentation("String", "endpoint"));

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      BatchProcessMessagePublisher source, Object... args) {
    ContextName contextName = (ContextName) args[0];
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation("ProducerTemplate", "producerTemplate"));

    String endpoint =
        String.format(
            "context.%s.api-client.batch-process.publisher", contextName.getText().toLowerCase());
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
      BatchProcessMessagePublisher source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(methodTransformer.create(source.getSend(), args));

    return methodRepresentations;
  }

  @Override
  public String packageName(BatchProcessMessagePublisher source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(BatchProcessMessagePublisher source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.api.clients.batchprocess.BatchProcessMessagePublisher");
    imports.add("org.springframework.stereotype.Service");
    imports.add("org.apache.camel.ProducerTemplate");
    imports.add("org.springframework.beans.factory.annotation.Value");

    return imports;
  }

  @Override
  public Set<String> annotations(BatchProcessMessagePublisher source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Service"));
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String fullObjectName(BatchProcessMessagePublisher source, Object... args) {
    return String.format(
        "%s implements BatchProcessMessagePublisher", simpleObjectName(source, args));
  }
}
