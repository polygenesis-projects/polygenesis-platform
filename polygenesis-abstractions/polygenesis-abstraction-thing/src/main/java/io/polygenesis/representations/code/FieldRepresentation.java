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

package io.polygenesis.representations.code;

import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.commons.assertion.Assertion;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Field representation.
 *
 * @author Christos Tsakostas
 */
public class FieldRepresentation extends AbstractDataRepresentation {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String modifiers;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * With modifiers field representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param modifiers the modifiers
   * @return the field representation
   */
  public static FieldRepresentation withModifiers(
      String dataType, String variableName, String modifiers) {
    return new FieldRepresentation(
        dataType, variableName, new LinkedHashSet<>(), DataPurpose.any(), modifiers);
  }

  /**
   * With annotations field representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param annotations the annotations
   * @param modifiers the modifiers
   * @return the field representation
   */
  public static FieldRepresentation withAnnotations(
      String dataType, String variableName, Set<String> annotations, String modifiers) {
    return new FieldRepresentation(
        dataType, variableName, annotations, DataPurpose.any(), modifiers);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Field representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param modifiers the modifiers
   */
  public FieldRepresentation(String dataType, String variableName, String modifiers) {
    super(dataType, variableName);
    this.modifiers = modifiers;
  }

  /**
   * Instantiates a new Field representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param dataPurpose the data purpose
   * @param modifiers the modifiers
   */
  public FieldRepresentation(
      String dataType, String variableName, DataPurpose dataPurpose, String modifiers) {
    super(dataType, variableName, dataPurpose);
    this.modifiers = modifiers;
  }

  /**
   * Instantiates a new Field representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param annotations the annotations
   * @param modifiers the modifiers
   */
  public FieldRepresentation(
      String dataType, String variableName, Set<String> annotations, String modifiers) {
    super(dataType, variableName, annotations);
    this.modifiers = modifiers;
  }

  /**
   * Instantiates a new Field representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param annotations the annotations
   * @param dataPurpose the data purpose
   * @param modifiers the modifiers
   */
  public FieldRepresentation(
      String dataType,
      String variableName,
      Set<String> annotations,
      DataPurpose dataPurpose,
      String modifiers) {
    super(dataType, variableName, annotations, dataPurpose);
    setModifiers(modifiers);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets modifiers.
   *
   * @return the modifiers
   */
  public String getModifiers() {
    return modifiers;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets modifiers.
   *
   * @param modifiers the modifiers
   */
  private void setModifiers(String modifiers) {
    Assertion.isNotNull(modifiers, "modifiers is required");
    this.modifiers = modifiers;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    FieldRepresentation that = (FieldRepresentation) o;
    return Objects.equals(modifiers, that.modifiers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), modifiers);
  }
}
