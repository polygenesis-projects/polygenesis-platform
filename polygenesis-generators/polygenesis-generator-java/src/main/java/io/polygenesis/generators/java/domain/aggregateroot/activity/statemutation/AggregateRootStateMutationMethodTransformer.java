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

package io.polygenesis.generators.java.domain.aggregateroot.activity.statemutation;

import static io.polygenesis.models.domain.PropertyType.ABSTRACT_AGGREGATE_ROOT_ID;
import static io.polygenesis.models.domain.PropertyType.AGGREGATE_ROOT_ID;

import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.models.domain.StateMutationMethod;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Aggregate root state mutation method transformer.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootStateMutationMethodTransformer
    extends AbstractMethodTransformer<StateMutationMethod> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final AggregateRootStateMutationActivityRegistry aggregateRootActivityRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root state mutation method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param aggregateRootActivityRegistry the aggregate root activity registry
   */
  public AggregateRootStateMutationMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      AggregateRootStateMutationActivityRegistry aggregateRootActivityRegistry) {
    super(dataTypeTransformer);
    this.aggregateRootActivityRegistry = aggregateRootActivityRegistry;
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
              source.getFunction().getName().getText()));
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
      Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

      // 1. Start by adding IDs
      source
          .getProperties()
          .stream()
          .filter(
              property ->
                  property.getPropertyType().equals(ABSTRACT_AGGREGATE_ROOT_ID)
                      || property.getPropertyType().equals(AGGREGATE_ROOT_ID))
          .forEach(
              property -> {
                String dataType = dataTypeTransformer.convert(property.getData().getDataType());
                DataPurpose dataPurpose = DataPurpose.thingIdentity();

                if (property.getPropertyType().equals(ABSTRACT_AGGREGATE_ROOT_ID)) {
                  dataType = "I";
                }
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        dataType, property.getData().getVariableName().getText(), dataPurpose));
              });

      // 2. Continue with superClass properties
      if (source.getSuperClassProperties() != null) {
        source
            .getSuperClassProperties()
            .stream()
            .filter(
                property ->
                    !property.getPropertyType().equals(ABSTRACT_AGGREGATE_ROOT_ID)
                        && !property.getPropertyType().equals(AGGREGATE_ROOT_ID))
            .forEach(
                property -> {
                  parameterRepresentations.add(
                      new ParameterRepresentation(
                          dataTypeTransformer.convert(property.getData().getDataType()),
                          property.getData().getVariableName().getText(),
                          DataPurpose.superclassParameter()));
                });
      }

      // 3. Finish with this class properties
      source
          .getProperties()
          .stream()
          .filter(
              property ->
                  !property.getPropertyType().equals(ABSTRACT_AGGREGATE_ROOT_ID)
                      && !property.getPropertyType().equals(AGGREGATE_ROOT_ID))
          .forEach(
              property -> {
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        dataTypeTransformer.convert(property.getData().getDataType()),
                        property.getData().getVariableName().getText(),
                        DataPurpose.any()));
              });

      return parameterRepresentations;
    } else {
      return super.parameterRepresentations(source, args);
    }
  }

  @Override
  public String returnValue(StateMutationMethod source, Object... args) {
    if (source.getFunction().getPurpose().isCreate()) {
      return "";
    } else {
      if (source.getFunction().getReturnValue() != null) {
        return makeVariableDataType(source.getFunction().getReturnValue().getData());
      } else {
        return dataTypeTransformer.convert(PrimitiveType.VOID.name());
      }
    }
  }

  @Override
  public String implementation(StateMutationMethod source, Object... args) {
    if (aggregateRootActivityRegistry.isActivitySupportedFor(source)) {
      return aggregateRootActivityRegistry.activityFor(
          source, parameterRepresentations(source, args), source.getDomainEvent(), args);
    } else {
      return super.implementation(source, args);
    }
  }
}
