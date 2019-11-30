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

package io.polygenesis.generators.java.batchprocess.process;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.models.periodicprocess.BatchProcessMetamodel;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Batch process transformer.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessTransformer
    extends AbstractClassTransformer<BatchProcessMetamodel, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public BatchProcessTransformer(
      DataTypeTransformer dataTypeTransformer, BatchProcessMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(BatchProcessMetamodel source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public String packageName(BatchProcessMetamodel source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(BatchProcessMetamodel source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.fasterxml.jackson.databind.ObjectMapper");
    imports.add("com.oregor.trinity4j.api.clients.batchprocess.AbstractBatchProcessService");
    imports.add("com.oregor.trinity4j.api.clients.batchprocess.BatchProcessMessagePublisher");
    imports.add("org.springframework.stereotype.Service");
    imports.add("org.springframework.beans.factory.annotation.Value");

    imports.add(
        String.format(
            "%s.%s",
            source.getQueryCollectionItem().getDataObject().getPackageName().getText(),
            TextConverter.toUpperCamel(
                source.getQueryCollectionItem().getDataObject().getObjectName().getText())));

    return imports;
  }

  @Override
  public String description(BatchProcessMetamodel source, Object... args) {
    return String.format(
        "The %s.", TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));
  }

  @Override
  public Set<String> annotations(BatchProcessMetamodel source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Service");

    return annotations;
  }

  @Override
  public String fullObjectName(BatchProcessMetamodel source, Object... args) {
    return String.format(
        "%s extends AbstractBatchProcessService<%s>",
        simpleObjectName(source, args),
        TextConverter.toUpperCamel(
            source.getQueryCollectionItem().getDataObject().getObjectName().getText()));
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      BatchProcessMetamodel source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(new ParameterRepresentation("ObjectMapper", "objectMapper"));

    parameterRepresentations.add(
        new ParameterRepresentation(
            "BatchProcessMessagePublisher", "batchProcessMessagePublisher"));

    parameterRepresentations.add(
        new ParameterRepresentation(
            String.format(
                "%sCommand", TextConverter.toUpperCamel(source.getObjectName().getText())),
            String.format(
                "%sCommand", TextConverter.toLowerCamel(source.getObjectName().getText()))));

    parameterRepresentations.add(
        new ParameterRepresentation(
            String.format("%sQuery", TextConverter.toUpperCamel(source.getObjectName().getText())),
            String.format(
                "%sQuery", TextConverter.toLowerCamel(source.getObjectName().getText()))));

    parameterRepresentations.add(
        new ParameterRepresentation(
            "@Value(\"${defaultPageSize:100}\") Integer", "defaultPageSize"));

    StringBuilder implementation = new StringBuilder();
    implementation.append("\t\tsuper(");
    implementation.append("objectMapper, batchProcessMessagePublisher, ");
    implementation.append(
        String.format("%sCommand", TextConverter.toLowerCamel(source.getObjectName().getText())));
    implementation.append(", ");
    implementation.append(
        String.format("%sQuery", TextConverter.toLowerCamel(source.getObjectName().getText())));
    implementation.append(", ");
    implementation.append("defaultPageSize");
    implementation.append(", ");
    implementation.append("\"");
    implementation.append(simpleObjectName(source, args));
    implementation.append("\"");
    implementation.append(");");

    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    constructorRepresentations.add(
        new ConstructorRepresentation(
            new LinkedHashSet<>(),
            String.format(
                "Instantiates a new %s.",
                TextConverter.toUpperCamelSpaces(source.getObjectName().getText())),
            dataTypeTransformer.getModifierPublic(),
            parameterRepresentations,
            implementation.toString()));

    return constructorRepresentations;
  }
}
