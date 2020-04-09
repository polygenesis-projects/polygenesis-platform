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

package io.polygenesis.generators.java.domain.aggregateentity.activity.statemutation;

import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.generators.java.common.ParameterRepresentationsService;
import io.polygenesis.models.domain.StateMutationMethod;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Aggregate entity state mutation method transformer.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntityStateMutationMethodTransformer
    extends AbstractMethodTransformer<StateMutationMethod> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final AggregateEntityStateMutationActivityRegistry
      aggregateEntityStateMutationActivityRegistry;
  private final ParameterRepresentationsService parameterRepresentationsService;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity state mutation method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param aggregateEntityStateMutationActivityRegistry the aggregate root activity registry
   * @param parameterRepresentationsService the parameter representations service
   */
  @SuppressWarnings("CPD-START")
  public AggregateEntityStateMutationMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      AggregateEntityStateMutationActivityRegistry aggregateEntityStateMutationActivityRegistry,
      ParameterRepresentationsService parameterRepresentationsService) {
    super(dataTypeTransformer);
    this.aggregateEntityStateMutationActivityRegistry =
        aggregateEntityStateMutationActivityRegistry;
    this.parameterRepresentationsService = parameterRepresentationsService;
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public String methodName(StateMutationMethod source, Object... args) {
    if (source.getFunction().getPurpose().isCreate()) {
      return TextConverter.toUpperCamel(source.getFunction().getThing().getThingName().getText());
    } else {
      return super.methodName(source, args);
    }
  }

  @Override
  public MethodRepresentationType methodType(StateMutationMethod source, Object... args) {
    if (source.getFunction().getPurpose().isCreate()) {
      return MethodRepresentationType.CONSTRUCTOR;
    } else if (source.getFunction().getPurpose().isModify()) {
      return MethodRepresentationType.MODIFY;
    } else {
      throw new IllegalArgumentException(
          String.format(
              "Function with name=%s is not a StateMutationMethod",
              source.getFunction().getName().getFullName()));
    }
  }

  @Override
  public String modifiers(StateMutationMethod source, Object... args) {
    return dataTypeTransformer.getModifierPublic();
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(
      StateMutationMethod source, Object... args) {
    if (source.getFunction().getPurpose().isCreate()) {
      return parameterRepresentationsService.getArgumentsForConstruction(
          source.getProperties(),
          source.getSuperClassProperties() != null
              ? source.getSuperClassProperties()
              : new LinkedHashSet<>());
    } else if (source.getFunction().getPurpose().equals(Purpose.modify())) {
      return parameterRepresentationsService.getMethodArgumentsWithoutIdentitiesAndReferences(
          source.getProperties());
    } else {
      return super.parameterRepresentations(source, args);
    }
  }

  @Override
  public String returnValue(StateMutationMethod source, Object... args) {
    if (source.getFunction().getPurpose().isCreate()) {
      return "";
    } else {
      if (!source.getReturnValue().getData().isEmpty()) {
        return makeVariableDataType(
            source.getReturnValue().getData().stream().findFirst().orElseThrow());
      } else {
        return dataTypeTransformer.convert(PrimitiveType.VOID.name());
      }
    }
  }

  @Override
  public String implementation(StateMutationMethod source, Object... args) {
    if (aggregateEntityStateMutationActivityRegistry.isActivitySupportedFor(source)) {
      return aggregateEntityStateMutationActivityRegistry.activityFor(
          source, parameterRepresentations(source, args), source.getDomainEvent(), args);
    } else {
      return super.implementation(source, args);
    }
  }
}
