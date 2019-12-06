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

package io.polygenesis.generators.java.domain.aggregateentity;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.domain.DomainObjectClassTransformer;
import io.polygenesis.generators.java.domain.aggregateentity.activity.statemutation.AggregateEntityStateMutationMethodTransformer;
import io.polygenesis.models.domain.AggregateEntity;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * The type Aggregate entity transformer.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntityTransformer
    extends DomainObjectClassTransformer<AggregateEntity, Function> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final AggregateEntityStateMutationMethodTransformer
      aggregateEntityStateMutationMethodTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   * @param aggregateEntityStateMutationMethodTransformer the aggregate entity state mutation method
   *     transformer
   */
  public AggregateEntityTransformer(
      DataTypeTransformer dataTypeTransformer,
      AggregateEntityMethodTransformer methodTransformer,
      AggregateEntityStateMutationMethodTransformer aggregateEntityStateMutationMethodTransformer) {
    super(dataTypeTransformer, methodTransformer);
    this.aggregateEntityStateMutationMethodTransformer =
        aggregateEntityStateMutationMethodTransformer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(AggregateEntity source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(AggregateEntity source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.addAll(super.methodRepresentations(source, args));

    source
        .getConstructors()
        .forEach(
            stateMutationMethod ->
                methodRepresentations.add(
                    aggregateEntityStateMutationMethodTransformer.create(stateMutationMethod)));

    source
        .getStateMutationMethods()
        .forEach(
            stateMutationMethod ->
                methodRepresentations.add(
                    aggregateEntityStateMutationMethodTransformer.create(stateMutationMethod)));

    return methodRepresentations;
  }

  @Override
  public Set<String> annotations(AggregateEntity source, Object... args) {
    DomainObject domainObjectParent = (DomainObject) args[1];

    Set<String> annotations = new LinkedHashSet<>();

    if (source.getInstantiationType().equals(InstantiationType.CONCRETE)) {
      annotations.add("@Entity");
      annotations.add(
          String.format(
              "@Table(name = Constants.DEFAULT_TABLE_PREFIX + \"%s_%s\")",
              TextConverter.toLowerUnderscore(domainObjectParent.getObjectName().getText()),
              TextConverter.toLowerUnderscore(
                  TextConverter.toPlural(source.getObjectName().getText()))));
    } else {
      annotations.add("@MappedSuperclass");
    }

    return annotations;
  }

  @Override
  public String description(AggregateEntity source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Aggregate Entity.");

    return stringBuilder.toString();
  }
}
