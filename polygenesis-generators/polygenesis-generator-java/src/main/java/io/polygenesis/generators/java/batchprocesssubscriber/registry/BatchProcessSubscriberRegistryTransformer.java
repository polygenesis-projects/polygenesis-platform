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

package io.polygenesis.generators.java.batchprocesssubscriber.registry;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.shared.transformer.AbstractClassTransformer;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Batch process subscriber registry transformer.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessSubscriberRegistryTransformer
    extends AbstractClassTransformer<Registry, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process subscriber registry transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public BatchProcessSubscriberRegistryTransformer(
      DataTypeTransformer dataTypeTransformer,
      BatchProcessMethodSubscriberRegistryTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(Registry source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> fieldRepresentations(Registry source, Object... args) {
    return super.fieldRepresentations(source, args);
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      Registry source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation(
            String.format(
                "Optional<List<%s>>",
                TextConverter.toUpperCamel(source.getObjectName().getText())
                    .replace("SubscriberRegistry", "Subscriber")),
            "subscribers"));

    constructorRepresentations.add(
        new ConstructorRepresentation(
            new LinkedHashSet<>(),
            description(source),
            dataTypeTransformer.getModifierPublic(),
            parameterRepresentations,
            "\t\tsuper(subscribers);"));

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Registry source, Object... args) {
    return super.methodRepresentations(source, args);
  }

  @Override
  public String packageName(Registry source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(Registry source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add(
        "com.oregor.trinity4j.commons.messaging.subscriber.AbstractMessageSubscriberRegistry");
    imports.add("java.util.List");
    imports.add("java.util.Optional");

    return imports;
  }

  @Override
  public Set<String> annotations(Registry source, Object... args) {
    return super.annotations(source, args);
  }

  @Override
  public String description(Registry source, Object... args) {
    return super.description(source, args);
  }

  @Override
  public String modifiers(Registry source, Object... args) {
    return super.modifiers(source, args);
  }

  @Override
  public String simpleObjectName(Registry source, Object... args) {
    return super.simpleObjectName(source, args);
  }

  @Override
  public String fullObjectName(Registry source, Object... args) {
    return String.format(
        "%s extends AbstractMessageSubscriberRegistry<%s>",
        super.simpleObjectName(source, args),
        TextConverter.toUpperCamel(source.getObjectName().getText()).replace("Registry", ""));
  }
}
