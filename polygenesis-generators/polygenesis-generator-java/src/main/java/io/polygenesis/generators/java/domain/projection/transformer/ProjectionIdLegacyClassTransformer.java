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

package io.polygenesis.generators.java.domain.projection.transformer;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.models.domain.Projection;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.legacy.AbstractLegacyClassTransformer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Projection id class representable.
 *
 * @author Christos Tsakostas
 */
public class ProjectionIdLegacyClassTransformer extends AbstractLegacyClassTransformer<Projection> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Projection id class representable.
   *
   * @param dataTypeTransformer the from data type to java converter
   */
  public ProjectionIdLegacyClassTransformer(DataTypeTransformer dataTypeTransformer) {
    super(dataTypeTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(Projection source, Object... args) {
    return new LinkedHashSet<>(
        Arrays.asList(
            FieldRepresentation.withModifiers(
                "static final long",
                "serialVersionUID = 1L",
                dataTypeTransformer.getModifierPrivate())));
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      Projection source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    // ---------------------------------------------------------------------------------------------
    // Create no-args constructor
    // ---------------------------------------------------------------------------------------------
    constructorRepresentations.add(createNoArgsConstructorForPersistence());

    // ---------------------------------------------------------------------------------------------
    // Create constructor with parameters
    // ---------------------------------------------------------------------------------------------
    constructorRepresentations.add(
        createConstructorWithImplementation(
            source.getObjectName().getText(),
            new LinkedHashSet<>(Arrays.asList(new ParameterRepresentation("UUID", "rootId"))),
            "\t\tsuper(rootId);"));

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Projection source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String packageName(Projection source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(Projection source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.domain.ProjectionId");
    imports.add("javax.persistence.Embeddable");
    imports.add("java.util.UUID");

    return imports;
  }

  @Override
  public Set<String> annotations(Projection source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Embeddable"));
  }

  @Override
  public String description(Projection source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Projection Id.");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(Projection source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(Projection source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(source.getObjectName().getText()));
    stringBuilder.append("Id");

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(Projection source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
    stringBuilder.append("Id");
    stringBuilder.append(" extends ");

    stringBuilder.append("ProjectionId");

    return stringBuilder.toString();
  }
}
