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

package io.polygenesis.generators.java.batchprocesssubscriber.subscriber;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.batchprocesssubscriber.abstractsubscriber.BatchProcessAbstractSubscriber;
import io.polygenesis.generators.java.shared.transformer.AbstractClassTransformer;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Batch process subscriber transformer.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessSubscriberTransformer
    extends AbstractClassTransformer<BatchProcessSubscriber, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process subscriber transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public BatchProcessSubscriberTransformer(
      DataTypeTransformer dataTypeTransformer,
      BatchProcessMethodSubscriberTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(BatchProcessSubscriber source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      BatchProcessSubscriber source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(new ParameterRepresentation("ObjectMapper", "objectMapper"));

    parameterRepresentations.add(
        new ParameterRepresentation(
            TextConverter.toUpperCamel(source.getObjectName().getText()), "batchProcessService"));

    constructorRepresentations.add(
        new ConstructorRepresentation(
            new LinkedHashSet<>(),
            description(source),
            dataTypeTransformer.getModifierPublic(),
            parameterRepresentations,
            "\t\tsuper(objectMapper, batchProcessService);"));

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      BatchProcessSubscriber source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(
        methodTransformer.create(source.getGetSupportedMessageTypes(), source));

    return methodRepresentations;
  }

  @Override
  public String packageName(BatchProcessSubscriber source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(BatchProcessSubscriber source, Object... args) {
    BatchProcessAbstractSubscriber batchProcessAbstractSubscriber =
        (BatchProcessAbstractSubscriber) args[0];
    Set<String> imports = new TreeSet<>();

    imports.add(
        String.format(
            "%s.%s",
            batchProcessAbstractSubscriber.getPackageName().getText(),
            TextConverter.toUpperCamel(batchProcessAbstractSubscriber.getObjectName().getText())));

    imports.add("com.fasterxml.jackson.databind.ObjectMapper");
    imports.add("java.util.Arrays");
    imports.add("java.util.List");
    imports.add("org.springframework.stereotype.Service");

    return imports;
  }

  @Override
  public Set<String> annotations(BatchProcessSubscriber source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Service"));
  }

  @Override
  public String simpleObjectName(BatchProcessSubscriber source, Object... args) {
    return String.format("On%s", super.simpleObjectName(source, args));
  }

  @Override
  public String fullObjectName(BatchProcessSubscriber source, Object... args) {
    BatchProcessAbstractSubscriber batchProcessAbstractSubscriber =
        (BatchProcessAbstractSubscriber) args[0];

    return String.format(
        "%s extends %s",
        simpleObjectName(source),
        TextConverter.toUpperCamel(batchProcessAbstractSubscriber.getObjectName().getText()));
  }
}
