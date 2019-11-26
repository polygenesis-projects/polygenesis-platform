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

package io.polygenesis.generators.java.repository.inmemory.supportiveentity;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Supportive entity repository impl transformer.
 *
 * @author Christos Tsakostas
 */
public class SupportiveEntityRepositoryImplTransformer
    extends AbstractClassTransformer<SupportiveEntityRepositoryImpl, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Supportive entity repository impl transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public SupportiveEntityRepositoryImplTransformer(
      DataTypeTransformer dataTypeTransformer,
      SupportiveEntityRepositoryImplMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(SupportiveEntityRepositoryImpl source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> staticFieldRepresentations(
      SupportiveEntityRepositoryImpl source, Object... args) {
    return super.staticFieldRepresentations(source, args);
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(
      SupportiveEntityRepositoryImpl source, Object... args) {
    return super.stateFieldRepresentations(source, args);
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      SupportiveEntityRepositoryImpl source, Object... args) {
    return super.constructorRepresentations(source, args);
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      SupportiveEntityRepositoryImpl source, Object... args) {
    return super.methodRepresentations(source, args);
  }

  @Override
  public String packageName(SupportiveEntityRepositoryImpl source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(SupportiveEntityRepositoryImpl source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.domain.AbstractSupportiveEntityRepositoryInMemory");
    imports.add("org.springframework.stereotype.Repository");

    return imports;
  }

  @Override
  public Set<String> annotations(SupportiveEntityRepositoryImpl source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Repository");

    return annotations;
  }

  @Override
  public String description(SupportiveEntityRepositoryImpl source, Object... args) {
    return super.description(source, args);
  }

  @Override
  public String modifiers(SupportiveEntityRepositoryImpl source, Object... args) {
    return super.modifiers(source, args);
  }

  @Override
  public String simpleObjectName(SupportiveEntityRepositoryImpl source, Object... args) {
    return super.simpleObjectName(source, args);
  }

  @Override
  public String fullObjectName(SupportiveEntityRepositoryImpl source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    String entityName =
        TextConverter.toUpperCamel(source.getObjectName().getText()).replace("RepositoryImpl", "");

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
    stringBuilder.append("\n");
    stringBuilder.append("\t\textends AbstractSupportiveEntityRepositoryInMemory<");
    stringBuilder.append(String.format("%sId", entityName));
    stringBuilder.append(", ");
    stringBuilder.append(entityName);
    stringBuilder.append(">");
    stringBuilder.append("\n");
    stringBuilder.append("\t\timplements ");
    stringBuilder.append(
        TextConverter.toUpperCamel(
            source.getSupportiveEntityRepository().getObjectName().getText()));

    return stringBuilder.toString();
  }
}
