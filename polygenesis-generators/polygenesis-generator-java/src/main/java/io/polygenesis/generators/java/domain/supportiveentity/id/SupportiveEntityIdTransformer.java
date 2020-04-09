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

package io.polygenesis.generators.java.domain.supportiveentity.id;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Supportive entity id transformer.
 *
 * @author Christos Tsakostas
 */
public class SupportiveEntityIdTransformer
    extends AbstractClassTransformer<SupportiveEntityId, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Supportive entity id transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public SupportiveEntityIdTransformer(
      DataTypeTransformer dataTypeTransformer,
      SupportiveEntityIdMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(SupportiveEntityId source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> staticFieldRepresentations(
      SupportiveEntityId source, Object... args) {
    return new LinkedHashSet<>(
        Arrays.asList(
            FieldRepresentation.withModifiers(
                "static final long",
                "serialVersionUID = 1L",
                dataTypeTransformer.getModifierPrivate())));
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      SupportiveEntityId source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    // ---------------------------------------------------------------------------------------------
    // Create no-args constructor
    // ---------------------------------------------------------------------------------------------
    constructorRepresentations.add(
        createNoArgsConstructorForPersistence(dataTypeTransformer.getModifierPrivate()));

    // ---------------------------------------------------------------------------------------------
    // Create constructor with parameters
    // ---------------------------------------------------------------------------------------------
    constructorRepresentations.add(
        createConstructorWithImplementation(
            source.getObjectName().getText(),
            new LinkedHashSet<>(
                Arrays.asList(new ParameterRepresentation("String", "supportiveEntityId"))),
            "\t\tsuper(supportiveEntityId);"));

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      SupportiveEntityId source, Object... args) {
    return super.methodRepresentations(source, args);
  }

  @Override
  public String packageName(SupportiveEntityId source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(SupportiveEntityId source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.domain.SupportiveEntityId");
    imports.add("javax.persistence.Embeddable");

    return imports;
  }

  @Override
  public Set<String> annotations(SupportiveEntityId source, Object... args) {
    return new LinkedHashSet<>(Collections.singletonList("@Embeddable"));
  }

  @Override
  public String description(SupportiveEntityId source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Supportive Entity Id.");

    return stringBuilder.toString();
  }

  @Override
  public String simpleObjectName(SupportiveEntityId source, Object... args) {
    return super.simpleObjectName(source, args);
  }

  @Override
  public String fullObjectName(SupportiveEntityId source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
    stringBuilder.append(" extends ");

    stringBuilder.append("SupportiveEntityId<String>");

    return stringBuilder.toString();
  }
}
