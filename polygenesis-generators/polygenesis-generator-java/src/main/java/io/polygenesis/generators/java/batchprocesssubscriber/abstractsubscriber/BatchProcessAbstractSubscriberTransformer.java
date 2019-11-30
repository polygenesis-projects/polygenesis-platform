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

package io.polygenesis.generators.java.batchprocesssubscriber.abstractsubscriber;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Batch process abstract subscriber transformer.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessAbstractSubscriberTransformer
    extends AbstractClassTransformer<BatchProcessAbstractSubscriber, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process abstract subscriber transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public BatchProcessAbstractSubscriberTransformer(
      DataTypeTransformer dataTypeTransformer,
      BatchProcessMethodAbstractSubscriberTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(BatchProcessAbstractSubscriber source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(
      BatchProcessAbstractSubscriber source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    fieldRepresentations.add(
        FieldRepresentation.withModifiers(
            "BatchProcessService",
            "batchProcessService",
            dataTypeTransformer.getModifierPrivate()));

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      BatchProcessAbstractSubscriber source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(new ParameterRepresentation("ObjectMapper", "objectMapper"));

    parameterRepresentations.add(
        new ParameterRepresentation("BatchProcessService", "batchProcessService"));

    constructorRepresentations.add(
        new ConstructorRepresentation(
            new LinkedHashSet<>(),
            description(source),
            dataTypeTransformer.getModifierPublic(),
            parameterRepresentations,
            "\t\tsuper(objectMapper);\n\t\tthis.batchProcessService = batchProcessService;"));

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      BatchProcessAbstractSubscriber source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(methodTransformer.create(source.getFunction(), source));

    return methodRepresentations;
  }

  @Override
  public String packageName(BatchProcessAbstractSubscriber source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(BatchProcessAbstractSubscriber source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.fasterxml.jackson.databind.ObjectMapper");
    imports.add("com.oregor.trinity4j.api.clients.batchprocess.BatchProcessService");
    imports.add("com.oregor.trinity4j.api.clients.subscriber.AbstractMessageSubscriber");
    imports.add("java.io.IOException");
    imports.add("com.oregor.trinity4j.api.clients.batchprocess.BatchProcessMessage");
    imports.add("com.oregor.trinity4j.commons.assertion.Assertion");

    return imports;
  }

  @Override
  public Set<String> annotations(BatchProcessAbstractSubscriber source, Object... args) {
    return super.annotations(source, args);
  }

  @Override
  public String description(BatchProcessAbstractSubscriber source, Object... args) {
    return super.description(source, args);
  }

  @Override
  public String modifiers(BatchProcessAbstractSubscriber source, Object... args) {
    return String.format(
        "%s %s",
        dataTypeTransformer.getModifierPublic(), dataTypeTransformer.getModifierAbstract());
  }

  @Override
  public String simpleObjectName(BatchProcessAbstractSubscriber source, Object... args) {
    return super.simpleObjectName(source, args);
  }

  @Override
  public String fullObjectName(BatchProcessAbstractSubscriber source, Object... args) {
    return String.format(
        "%s extends AbstractMessageSubscriber", super.simpleObjectName(source, args));
  }
}
