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

package io.polygenesis.generators.java.domain.supportiveentity.entity;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.domain.DomainObjectClassTransformer;
import io.polygenesis.models.domain.SupportiveEntity;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Supportive entity transformer.
 *
 * @author Christos Tsakostas
 */
public class SupportiveEntityTransformer
    extends DomainObjectClassTransformer<SupportiveEntity, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Supportive entity transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public SupportiveEntityTransformer(
      DataTypeTransformer dataTypeTransformer,
      SupportiveEntityMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(SupportiveEntity source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> staticFieldRepresentations(
      SupportiveEntity source, Object... args) {
    return super.staticFieldRepresentations(source, args);
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(
      SupportiveEntity source, Object... args) {
    return super.stateFieldRepresentations(source, args);
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      SupportiveEntity source, Object... args) {
    return super.constructorRepresentations(source, args);
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(SupportiveEntity source, Object... args) {
    return super.methodRepresentations(source, args);
  }

  @Override
  public String packageName(SupportiveEntity source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(SupportiveEntity source, Object... args) {
    Set<String> imports = new TreeSet<>();

    if (source.getProperties().size() > 1) {
      imports.add("com.oregor.trinity4j.commons.assertion.Assertion");
    }
    imports.add("com.oregor.trinity4j.domain.SupportiveEntity");

    return imports;
  }

  @Override
  public Set<String> annotations(SupportiveEntity source, Object... args) {
    return super.annotations(source, args);
  }

  @Override
  public String description(SupportiveEntity source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Supportive Entity.");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(SupportiveEntity source, Object... args) {
    return super.modifiers(source, args);
  }

  @Override
  public String simpleObjectName(SupportiveEntity source, Object... args) {
    return super.simpleObjectName(source, args);
  }

  @Override
  public String fullObjectName(SupportiveEntity source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));

    stringBuilder.append(
        String.format(
            " extends SupportiveEntity<%sId>",
            TextConverter.toUpperCamel(source.getObjectName().getText())));

    return stringBuilder.toString();
  }
}
