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

package io.polygenesis.generators.java.domainmessagesubscriber.dispatcher;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
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
 * The type Domain message dispatcher transformer.
 *
 * @author Christos Tsakostas
 */
public class DomainMessageDispatcherTransformer
    extends AbstractClassTransformer<DomainMessageDispatcher, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message dispatcher transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public DomainMessageDispatcherTransformer(
      DataTypeTransformer dataTypeTransformer,
      DomainMessageMethodDispatcherTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(DomainMessageDispatcher source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @SuppressWarnings("CPD-START")
  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(
      DomainMessageDispatcher source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    // TODO
    // private static final Logger LOG =
    // LoggerFactory.getLogger(GdprBatchProcessMessageDispatcher.class);

    fieldRepresentations.add(
        FieldRepresentation.withModifiers(
            "ObjectMapper", "objectMapper", dataTypeTransformer.getModifierPrivate()));

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      DomainMessageDispatcher source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation(
            TextConverter.toUpperCamel(source.getObjectName().getText())
                .replace("Dispatcher", "SubscriberRegistry"),
            "messageSubscriberRegistry"));

    parameterRepresentations.add(new ParameterRepresentation("ObjectMapper", "objectMapper"));

    constructorRepresentations.add(
        new ConstructorRepresentation(
            new LinkedHashSet<>(),
            description(source),
            dataTypeTransformer.getModifierPublic(),
            parameterRepresentations,
            "\t\tsuper(messageSubscriberRegistry);\n\t\tthis.objectMapper = objectMapper;"));

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      DomainMessageDispatcher source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(methodTransformer.create(source.getFunction(), source));

    return methodRepresentations;
  }

  @Override
  public String packageName(DomainMessageDispatcher source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(DomainMessageDispatcher source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.api.clients.subscriber.AbstractMessageDispatcher");
    imports.add("com.fasterxml.jackson.databind.ObjectMapper");
    imports.add("com.fasterxml.jackson.databind.JsonNode");
    imports.add("org.springframework.stereotype.Service");
    imports.add("java.io.IOException");
    imports.add("org.slf4j.Logger");
    imports.add("org.slf4j.LoggerFactory");

    return imports;
  }

  @Override
  public Set<String> annotations(DomainMessageDispatcher source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Service"));
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String fullObjectName(DomainMessageDispatcher source, Object... args) {
    return String.format(
        "%s extends AbstractMessageDispatcher<%s>",
        super.simpleObjectName(source, args),
        TextConverter.toUpperCamel(source.getObjectName().getText())
            .replace("Dispatcher", "Subscriber"));
  }
}
