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

package io.polygenesis.generators.java.domain.aggregateroot;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.domain.DomainObjectClassTransformer;
import io.polygenesis.generators.java.domain.aggregateroot.activity.statemutation.AggregateRootStateMutationMethodTransformer;
import io.polygenesis.generators.java.shared.transformer.MethodTransformer;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Aggregate root transformer.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootTransformer extends DomainObjectClassTransformer<DomainObject, Function> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final AggregateRootStateMutationMethodTransformer
      aggregateRootStateMutationMethodTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   * @param aggregateRootStateMutationMethodTransformer the state mutation method representable
   */
  public AggregateRootTransformer(
      DataTypeTransformer dataTypeTransformer,
      MethodTransformer<Function> methodTransformer,
      AggregateRootStateMutationMethodTransformer aggregateRootStateMutationMethodTransformer) {
    super(dataTypeTransformer, methodTransformer);
    this.aggregateRootStateMutationMethodTransformer = aggregateRootStateMutationMethodTransformer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public TemplateData transform(DomainObject source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<String> imports(DomainObject source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.addAll(super.imports(source, args));

    source
        .getStateMutationMethods()
        .forEach(
            stateMutationMethod ->
                imports.addAll(
                    aggregateRootStateMutationMethodTransformer.imports(
                        stateMutationMethod, args)));

    return imports;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(DomainObject source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.addAll(super.methodRepresentations(source, args));

    source
        .getConstructors()
        .forEach(
            stateMutationMethod ->
                methodRepresentations.add(
                    aggregateRootStateMutationMethodTransformer.create(stateMutationMethod)));

    source
        .getStateMutationMethods()
        .forEach(
            stateMutationMethod ->
                methodRepresentations.add(
                    aggregateRootStateMutationMethodTransformer.create(stateMutationMethod)));

    return methodRepresentations;
  }

  @Override
  public String packageName(DomainObject source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> annotations(DomainObject source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    if (source.getInstantiationType().equals(InstantiationType.CONCRETE)) {
      annotations.add("@Entity");
      annotations.add(
          String.format(
              "@Table(name = Constants.DEFAULT_TABLE_PREFIX + \"%s\")",
              TextConverter.toLowerUnderscore(source.getObjectName().getText())));
    } else {
      annotations.add("@MappedSuperclass");
    }

    return annotations;
  }

  @Override
  public String description(DomainObject source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Aggregate Root.");

    return stringBuilder.toString();
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String modifiers(DomainObject source, Object... args) {
    if (source.getInstantiationType().equals(InstantiationType.CONCRETE)) {
      return dataTypeTransformer.getModifierPublic();
    } else {
      return dataTypeTransformer.getModifierPublic()
          + " "
          + dataTypeTransformer.getModifierAbstract();
    }
  }
}
