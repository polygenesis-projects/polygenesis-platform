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

package io.polygenesis.models.domain;

import static io.polygenesis.models.domain.PropertyType.AGGREGATE_ENTITY_COLLECTION;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.GoalType;
import io.polygenesis.core.ReturnValue;
import io.polygenesis.core.Thing;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type State mutation method deducer.
 *
 * @author Christos Tsakostas
 */
public class StateMutationMethodDeducer {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce set.
   *
   * @param thing the thing
   * @param properties the properties
   * @return the set
   */
  @SuppressWarnings("rawtypes")
  public Set<StateMutationMethod> deduce(Thing thing, Set<DomainObjectProperty> properties) {
    Set<StateMutationMethod> stateMutationMethods = new LinkedHashSet<>();

    properties.forEach(
        property -> {
          if (property.getPropertyType().equals(AGGREGATE_ENTITY_COLLECTION)) {
            stateMutationMethods.addAll(
                forAggregateEntityCollection(
                    thing, AggregateEntityCollection.class.cast(property)));
          }
        });

    return stateMutationMethods;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * For aggregate entity collection set.
   *
   * @param thing the thing
   * @param aggregateEntityCollection the aggregate entity collection
   * @return the set
   */
  protected Set<StateMutationMethod> forAggregateEntityCollection(
      Thing thing, AggregateEntityCollection aggregateEntityCollection) {
    Set<StateMutationMethod> stateMutationMethods = new LinkedHashSet<>();

    stateMutationMethods.add(
        aggregateRootCreateEntity(thing, aggregateEntityCollection.getAggregateEntity()));

    stateMutationMethods.add(
        aggregateRootUpdateEntity(thing, aggregateEntityCollection.getAggregateEntity()));

    stateMutationMethods.add(
        aggregateRootDeleteEntity(thing, aggregateEntityCollection.getAggregateEntity()));

    return stateMutationMethods;
  }

  /**
   * Aggregate root create entity state mutation method.
   *
   * @param thing the thing
   * @param aggregateEntity the aggregate entity
   * @return the state mutation method
   */
  protected StateMutationMethod aggregateRootCreateEntity(
      Thing thing, AggregateEntity aggregateEntity) {
    return new StateMutationMethod(
        new Function(
            thing,
            new Goal(GoalType.AGGREGATE_ROOT_CREATE_ENTITY),
            new FunctionName(
                String.format(
                    "define%s",
                    TextConverter.toUpperCamel(aggregateEntity.getObjectName().getText()))),
            new LinkedHashSet<>(),
            new ReturnValue(aggregateEntity.getData())));
  }

  protected StateMutationMethod aggregateRootUpdateEntity(
      Thing thing, AggregateEntity aggregateEntity) {
    return new StateMutationMethod(
        new Function(
            thing,
            new Goal(GoalType.AGGREGATE_ROOT_UPDATE_ENTITY),
            new FunctionName(
                String.format(
                    "modify%s",
                    TextConverter.toUpperCamel(aggregateEntity.getObjectName().getText()))),
            new LinkedHashSet<>(),
            new ReturnValue(aggregateEntity.getData())));
  }

  protected StateMutationMethod aggregateRootDeleteEntity(
      Thing thing, AggregateEntity aggregateEntity) {
    return new StateMutationMethod(
        new Function(
            thing,
            new Goal(GoalType.AGGREGATE_ROOT_DELETE_ENTITY),
            new FunctionName(
                String.format(
                    "remove%s",
                    TextConverter.toUpperCamel(aggregateEntity.getObjectName().getText()))),
            new LinkedHashSet<>(),
            new ReturnValue(aggregateEntity.getData())));
  }
}
