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

package io.polygenesis.generators.java.domainmessageactivemq.dispatcherroute;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
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
 * The type Domain message dispatcher route transformer.
 *
 * @author Christos Tsakostas
 */
public class DomainMessageDispatcherRouteTransformer
    extends AbstractClassTransformer<DomainMessageDispatcherRoute, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message dispatcher route transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public DomainMessageDispatcherRouteTransformer(
      DataTypeTransformer dataTypeTransformer,
      DomainMessageMethodDispatcherRouteTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(DomainMessageDispatcherRoute source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(
      DomainMessageDispatcherRoute source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    fieldRepresentations.add(
        FieldRepresentation.withModifiers(
            TextConverter.toUpperCamel(
                source.getDomainMessageDispatcher().getObjectName().getText()),
            "dispatcher",
            dataTypeTransformer.getModifierPrivate()));

    fieldRepresentations.add(
        FieldRepresentation.withModifiers(
            "String", "endpoint", dataTypeTransformer.getModifierPrivate()));

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      DomainMessageDispatcherRoute source, Object... args) {
    ContextName contextName = (ContextName) args[0];
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation(
            TextConverter.toUpperCamel(
                source.getDomainMessageDispatcher().getObjectName().getText()),
            "dispatcher"));

    String endpoint =
        String.format(
            "context.%s.api-client.domain-message.subscriber", contextName.getText().toLowerCase());
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
            "\t\tthis.dispatcher = dispatcher;\n\t\tthis.endpoint = endpoint;");

    constructorRepresentations.add(constructorRepresentation);

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      DomainMessageDispatcherRoute source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(methodTransformer.create(source.getConfigure(), args));

    return methodRepresentations;
  }

  @Override
  public String packageName(DomainMessageDispatcherRoute source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(DomainMessageDispatcherRoute source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("org.springframework.stereotype.Component");
    imports.add("org.springframework.beans.factory.annotation.Value");
    imports.add("com.oregor.trinity4j.shared.camel.DeadLetterRouteBuilder");

    return imports;
  }

  @Override
  public Set<String> annotations(DomainMessageDispatcherRoute source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Component"));
  }

  @Override
  public String fullObjectName(DomainMessageDispatcherRoute source, Object... args) {
    return String.format("%s extends DeadLetterRouteBuilder", simpleObjectName(source, args));
  }
}
