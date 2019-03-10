/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

package io.polygenesis.generators.angular.reactivestate.model;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.models.reactivestate.Model;
import io.polygenesis.representations.commons.FieldRepresentation;
import io.polygenesis.representations.typescript.FromDataTypeToTypescriptConverter;
import io.polygenesis.representations.typescript.model.ModelRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Model representable.
 *
 * @author Christos Tsakostas
 */
public class ModelRepresentable {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FromDataTypeToTypescriptConverter fromDataTypeToTypescriptConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Model representable.
   *
   * @param fromDataTypeToTypescriptConverter the from data type to typescript converter
   */
  public ModelRepresentable(FromDataTypeToTypescriptConverter fromDataTypeToTypescriptConverter) {
    this.fromDataTypeToTypescriptConverter = fromDataTypeToTypescriptConverter;
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
        TextConverter.toUpperCamel(model.getModel().getDataType()), fieldRepresentations(model));
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

    if (payloadModel.getModel().isDataGroup()) {
      DataGroup dataGroup = (DataGroup) payloadModel.getModel();

      dataGroup
          .getModels()
          .forEach(
              model -> {
                fieldRepresentations.add(
                    new FieldRepresentation(
                        fromDataTypeToTypescriptConverter.getDeclaredVariableType(model),
                        model.getVariableName().getText()));
              });

    } else {
      throw new IllegalArgumentException();
    }

    return fieldRepresentations;
  }
}
