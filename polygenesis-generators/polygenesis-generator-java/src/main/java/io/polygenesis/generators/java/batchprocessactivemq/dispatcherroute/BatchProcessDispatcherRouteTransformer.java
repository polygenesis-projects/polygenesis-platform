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

package io.polygenesis.generators.java.batchprocessactivemq.dispatcherroute;

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
 * The type Batch process dispatcher transformer.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessDispatcherRouteTransformer
    extends AbstractClassTransformer<BatchProcessDispatcherRoute, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process dispatcher transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public BatchProcessDispatcherRouteTransformer(
      DataTypeTransformer dataTypeTransformer,
      BatchProcessMethodDispatcherRouteTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(BatchProcessDispatcherRoute source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> fieldRepresentations(
      BatchProcessDispatcherRoute source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    fieldRepresentations.add(
        new FieldRepresentation(
            TextConverter.toUpperCamel(
                source.getBatchProcessDispatcher().getObjectName().getText()),
            "dispatcher"));

    fieldRepresentations.add(new FieldRepresentation("String", "endpoint"));

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      BatchProcessDispatcherRoute source, Object... args) {
    ContextName contextName = (ContextName) args[0];
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation(
            TextConverter.toUpperCamel(
                source.getBatchProcessDispatcher().getObjectName().getText()),
            "dispatcher"));

    String endpoint =
        String.format(
            "context.%s.api-client.batch-process.subscriber", contextName.getText().toLowerCase());
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
      BatchProcessDispatcherRoute source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(methodTransformer.create(source.getConfigure(), args));

    return methodRepresentations;
  }

  @Override
  public String packageName(BatchProcessDispatcherRoute source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(BatchProcessDispatcherRoute source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.shared.camel.DeadLetterRouteBuilder");
    imports.add("org.springframework.stereotype.Component");
    imports.add("org.springframework.beans.factory.annotation.Value");

    return imports;
  }

  @Override
  public Set<String> annotations(BatchProcessDispatcherRoute source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Component"));
  }

  @Override
  public String description(BatchProcessDispatcherRoute source, Object... args) {
    return super.description(source, args);
  }

  @Override
  public String modifiers(BatchProcessDispatcherRoute source, Object... args) {
    return super.modifiers(source, args);
  }

  @Override
  public String simpleObjectName(BatchProcessDispatcherRoute source, Object... args) {
    return super.simpleObjectName(source, args);
  }

  @Override
  public String fullObjectName(BatchProcessDispatcherRoute source, Object... args) {
    return String.format("%s extends DeadLetterRouteBuilder", simpleObjectName(source, args));
  }
}
