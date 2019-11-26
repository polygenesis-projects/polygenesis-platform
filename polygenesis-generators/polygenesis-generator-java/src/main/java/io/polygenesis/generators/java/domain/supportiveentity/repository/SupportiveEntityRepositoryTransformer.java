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

package io.polygenesis.generators.java.domain.supportiveentity.repository;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Supportive entity repository transformer.
 *
 * @author Christos Tsakostas
 */
public class SupportiveEntityRepositoryTransformer
    extends AbstractClassTransformer<SupportiveEntityRepository, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Supportive entity repository transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public SupportiveEntityRepositoryTransformer(
      DataTypeTransformer dataTypeTransformer,
      SupportiveEntityRepositoryMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(SupportiveEntityRepository source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source));

    return new TemplateData(dataModel, "polygenesis-representation-java/Interface.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> staticFieldRepresentations(
      SupportiveEntityRepository source, Object... args) {
    return super.staticFieldRepresentations(source, args);
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(
      SupportiveEntityRepository source, Object... args) {
    return super.stateFieldRepresentations(source, args);
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      SupportiveEntityRepository source, Object... args) {
    return super.constructorRepresentations(source, args);
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      SupportiveEntityRepository source, Object... args) {
    return super.methodRepresentations(source, args);
  }

  @Override
  public String packageName(SupportiveEntityRepository source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(SupportiveEntityRepository source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.domain.SupportiveEntityRepository");

    return imports;
  }

  @Override
  public Set<String> annotations(SupportiveEntityRepository source, Object... args) {
    return super.annotations(source, args);
  }

  @Override
  public String description(SupportiveEntityRepository source, Object... args) {
    return super.description(source, args);
  }

  @Override
  public String modifiers(SupportiveEntityRepository source, Object... args) {
    return super.modifiers(source, args);
  }

  @Override
  public String simpleObjectName(SupportiveEntityRepository source, Object... args) {
    return super.simpleObjectName(source, args);
  }

  @Override
  public String fullObjectName(SupportiveEntityRepository source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
    stringBuilder.append(" extends ");

    stringBuilder.append(
        String.format(
            "SupportiveEntityRepository<%sId, %s>",
            TextConverter.toUpperCamel(source.getSupportiveEntity().getObjectName().getText()),
            TextConverter.toUpperCamel(source.getSupportiveEntity().getObjectName().getText())));

    return stringBuilder.toString();
  }
}
