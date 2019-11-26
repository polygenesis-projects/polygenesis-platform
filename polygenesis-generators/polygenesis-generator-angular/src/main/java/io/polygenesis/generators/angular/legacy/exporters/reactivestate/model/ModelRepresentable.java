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

package io.polygenesis.generators.angular.legacy.exporters.reactivestate.model;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.angular.legacy.skeletons.FromDataTypeToTypescriptConverter;
import io.polygenesis.generators.angular.legacy.skeletons.model.ModelRepresentation;
import io.polygenesis.metamodels.stateredux.Model;
import io.polygenesis.representations.code.FieldRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type model representable.
 *
 * @author Christos Tsakostas
 */
public class ModelRepresentable {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FromDataTypeToTypescriptConverter dataTypeToTypescriptConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new model representable.
   *
   * @param dataTypeToTypescriptConverter the from data type to typescript converter
   */
  public ModelRepresentable(FromDataTypeToTypescriptConverter dataTypeToTypescriptConverter) {
    this.dataTypeToTypescriptConverter = dataTypeToTypescriptConverter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Create model representation.
   *
   * @param model the model
   * @return the model representation
   */
  public ModelRepresentation create(Model model) {
    return new ModelRepresentation(
        TextConverter.toUpperCamel(model.getData().getDataType()), fieldRepresentations(model));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Field representations set.
   *
   * @param payloadModel the payloadModel
   * @return the set
   */
  private Set<FieldRepresentation> fieldRepresentations(Model payloadModel) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    if (payloadModel.getData().isDataGroup()) {
      DataObject dataObject = (DataObject) payloadModel.getData();

      dataObject
          .getModels()
          .forEach(
              model ->
                  fieldRepresentations.add(
                      FieldRepresentation.withModifiers(
                          dataTypeToTypescriptConverter.getDeclaredVariableType(model),
                          model.getVariableName().getText(),
                          dataTypeToTypescriptConverter.getModifierPrivate())));

    } else {
      throw new IllegalArgumentException();
    }

    return fieldRepresentations;
  }
}
