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

package io.polygenesis.representations.code;

import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.commons.assertion.Assertion;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class FieldRepresentation extends AbstractDataRepresentation {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String modifiers;
  private String initialValue;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * With initial value field representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param modifiers the modifiers
   * @return the field representation
   */
  public static FieldRepresentation withInitialValue(
      String dataType, String variableName, String modifiers, String initialValue) {
    return new FieldRepresentation(
        dataType, variableName, new LinkedHashSet<>(), DataPurpose.any(), modifiers, initialValue);
  }

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
        dataType, variableName, new LinkedHashSet<>(), DataPurpose.any(), modifiers, null);
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
        dataType, variableName, annotations, DataPurpose.any(), modifiers, null);
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
      String modifiers,
      String initialValue) {
    super(dataType, variableName, annotations, dataPurpose);
    setModifiers(modifiers);

    if (initialValue != null) {
      setInitialValue(initialValue);
    }
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

  /**
   * Gets initial value.
   *
   * @return the initial value
   */
  public String getInitialValue() {
    return initialValue;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setModifiers(String modifiers) {
    Assertion.isNotNull(modifiers, "modifiers is required");
    this.modifiers = modifiers;
  }

  private void setInitialValue(String initialValue) {
    Assertion.isNotNull(initialValue, "initialValue is required");
    this.initialValue = initialValue;
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
    return Objects.equals(modifiers, that.modifiers)
        && Objects.equals(initialValue, that.initialValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), modifiers, initialValue);
  }
}
