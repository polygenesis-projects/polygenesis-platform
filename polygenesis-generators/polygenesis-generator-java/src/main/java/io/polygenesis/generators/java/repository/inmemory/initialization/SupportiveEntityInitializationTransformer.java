/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.generators.java.repository.inmemory.initialization;

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

public class SupportiveEntityInitializationTransformer
    extends AbstractClassTransformer<SupportiveEntityInitialization, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Supportive entity initialization transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public SupportiveEntityInitializationTransformer(
      DataTypeTransformer dataTypeTransformer,
      SupportiveEntityInitializationMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(SupportiveEntityInitialization source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> staticFieldRepresentations(
      SupportiveEntityInitialization source, Object... args) {
    return super.staticFieldRepresentations(source, args);
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(
      SupportiveEntityInitialization source, Object... args) {
    return super.stateFieldRepresentations(source, args);
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      SupportiveEntityInitialization source, Object... args) {
    return super.constructorRepresentations(source, args);
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      SupportiveEntityInitialization source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(methodTransformer.create(source.getAfterPropertiesSet(), args));

    return methodRepresentations;
  }

  @Override
  public String packageName(SupportiveEntityInitialization source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(SupportiveEntityInitialization source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("org.springframework.beans.factory.InitializingBean");
    imports.add("org.springframework.stereotype.Component");

    return imports;
  }

  @Override
  public Set<String> annotations(SupportiveEntityInitialization source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Component");

    return annotations;
  }

  @Override
  public String description(SupportiveEntityInitialization source, Object... args) {
    return super.description(source, args);
  }

  @Override
  public String modifiers(SupportiveEntityInitialization source, Object... args) {
    return super.modifiers(source, args);
  }

  @Override
  public String simpleObjectName(SupportiveEntityInitialization source, Object... args) {
    return super.simpleObjectName(source, args);
  }

  @Override
  public String fullObjectName(SupportiveEntityInitialization source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
    stringBuilder.append(" implements InitializingBean");

    return stringBuilder.toString();
  }
}
