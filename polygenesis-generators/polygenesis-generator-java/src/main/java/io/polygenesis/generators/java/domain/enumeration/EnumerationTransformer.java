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

package io.polygenesis.generators.java.domain.enumeration;

import static java.util.stream.Collectors.toCollection;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.models.domain.Enumeration;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.EnumerationValueRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.java.AbstractEnumerationTransformer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Enumeration transformer.
 *
 * @author Christos Tsakostas
 */
public class EnumerationTransformer extends AbstractEnumerationTransformer<Enumeration, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Enumeration transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  @SuppressWarnings("CPD-START")
  public EnumerationTransformer(
      DataTypeTransformer dataTypeTransformer, EnumerationMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(Enumeration source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Enum.java.ftl");
  }

  @Override
  public Set<EnumerationValueRepresentation> enumerationValueRepresentations(
      Enumeration source, Object... args) {
    return source
        .getData()
        .getAsDataEnumeration()
        .getEnumerationValues()
        .stream()
        .map(
            enumerationValue ->
                new EnumerationValueRepresentation(
                    enumerationValue.getValue(), enumerationValue.getInitial()))
        .collect(toCollection(LinkedHashSet::new));
  }

  @Override
  public Set<FieldRepresentation> staticFieldRepresentations(Enumeration source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      Enumeration source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Enumeration source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String packageName(Enumeration source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(Enumeration source, Object... args) {
    Set<String> imports = new TreeSet<>();
    return imports;
  }

  @Override
  public Set<String> annotations(Enumeration source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String description(Enumeration source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Enumeration.");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(Enumeration source, Object... args) {
    return dataTypeTransformer.getModifierPublic();
  }

  @Override
  public String simpleObjectName(Enumeration source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));

    return stringBuilder.toString();
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String fullObjectName(Enumeration source, Object... args) {
    return simpleObjectName(source, args);
  }
}
