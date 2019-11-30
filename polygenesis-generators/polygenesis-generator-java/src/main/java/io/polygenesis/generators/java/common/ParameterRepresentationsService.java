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

package io.polygenesis.generators.java.common;

import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Parameter representations service.
 *
 * @author Christos Tsakostas
 */
public class ParameterRepresentationsService {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final DataTypeTransformer dataTypeTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Parameter representations service.
   *
   * @param dataTypeTransformer the data type transformer
   */
  public ParameterRepresentationsService(DataTypeTransformer dataTypeTransformer) {
    this.dataTypeTransformer = dataTypeTransformer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Gets arguments for construction.
   *
   * @param thisClassProperties the this class properties
   * @param superClassProperties the super class properties
   * @return the arguments for construction
   */
  public Set<ParameterRepresentation> getArgumentsForConstruction(
      Set<DomainObjectProperty<?>> thisClassProperties,
      Set<DomainObjectProperty<?>> superClassProperties) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    // 1. Start by adding IDs
    thisClassProperties
        .stream()
        .filter(
            property ->
                property.getPropertyType().equals(PropertyType.ABSTRACT_AGGREGATE_ROOT_ID)
                    || property.getPropertyType().equals(PropertyType.AGGREGATE_ROOT_ID)
                    || property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY_ID)
                    || property.getPropertyType().equals(PropertyType.TENANT_ID))
        .forEach(
            property -> {
              String dataType = makeDataType(property);
              DataPurpose dataPurpose = DataPurpose.thingIdentity();

              if (property.getPropertyType().equals(PropertyType.ABSTRACT_AGGREGATE_ROOT_ID)) {
                dataType = "I";
              }
              parameterRepresentations.add(
                  new ParameterRepresentation(
                      dataType, property.getData().getVariableName().getText(), dataPurpose));
            });

    // 2. Continue with superClass properties
    superClassProperties
        .stream()
        .filter(
            property ->
                !property.getPropertyType().equals(PropertyType.ABSTRACT_AGGREGATE_ROOT_ID)
                    && !property.getPropertyType().equals(PropertyType.AGGREGATE_ROOT_ID)
                    && !property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY_ID)
                    && !property.getPropertyType().equals(PropertyType.TENANT_ID))
        .forEach(
            property -> {
              parameterRepresentations.add(
                  new ParameterRepresentation(
                      makeDataType(property),
                      property.getData().getVariableName().getText(),
                      DataPurpose.superclassParameter()));
            });

    // 3. Finish with this class properties
    thisClassProperties
        .stream()
        .filter(
            property ->
                !property.getPropertyType().equals(PropertyType.ABSTRACT_AGGREGATE_ROOT_ID)
                    && !property.getPropertyType().equals(PropertyType.AGGREGATE_ROOT_ID)
                    && !property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY_ID)
                    && !property.getPropertyType().equals(PropertyType.TENANT_ID))
        .forEach(
            property -> {
              parameterRepresentations.add(
                  new ParameterRepresentation(
                      makeDataType(property),
                      property.getData().getVariableName().getText(),
                      DataPurpose.any()));
            });

    return parameterRepresentations;
  }

  /**
   * Gets method arguments for create or modify aggregate entity.
   *
   * @param thisClassProperties the this class properties
   * @return the method arguments for create or modify aggregate entity
   */
  public Set<ParameterRepresentation> getMethodArgumentsForCreateOrModifyAggregateEntity(
      Set<DomainObjectProperty<?>> thisClassProperties) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    // 3. Finish with this class properties
    thisClassProperties
        .stream()
        .filter(
            property ->
                !property.getPropertyType().equals(PropertyType.ABSTRACT_AGGREGATE_ROOT_ID)
                    && !property.getPropertyType().equals(PropertyType.AGGREGATE_ROOT_ID)
                    && !property.getPropertyType().equals(PropertyType.AGGREGATE_ENTITY_ID)
                    && !property.getPropertyType().equals(PropertyType.TENANT_ID)
                    && !property.getPropertyType().equals(PropertyType.REFERENCE_TO_AGGREGATE_ROOT)
                    && !property
                        .getPropertyType()
                        .equals(PropertyType.REFERENCE_TO_ABSTRACT_AGGREGATE_ROOT))
        .forEach(
            property -> {
              parameterRepresentations.add(
                  new ParameterRepresentation(
                      makeDataType(property),
                      property.getData().getVariableName().getText(),
                      DataPurpose.any()));
            });

    return parameterRepresentations;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private String makeDataType(DomainObjectProperty<?> property) {
    if (property.getPropertyType().equals(PropertyType.REFERENCE_TO_ABSTRACT_AGGREGATE_ROOT)) {
      return dataTypeTransformer.convert(property.getData().getDataType()) + "<?>";
    } else {
      return dataTypeTransformer.convert(property.getData().getDataType());
    }
  }
}
