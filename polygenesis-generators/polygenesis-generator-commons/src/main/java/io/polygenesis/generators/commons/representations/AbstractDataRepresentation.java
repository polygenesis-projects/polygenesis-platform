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

package io.polygenesis.generators.commons.representations;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.core.data.DataBusinessType;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Abstract data representation.
 *
 * @author Christos Tsakostas
 */
public class AbstractDataRepresentation {

  private String dataType;
  private String variableName;
  private Set<String> annotations;
  private DataBusinessType dataBusinessType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract data representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   */
  public AbstractDataRepresentation(String dataType, String variableName) {
    this(dataType, variableName, new LinkedHashSet<>(), DataBusinessType.ANY);
  }

  /**
   * Instantiates a new Abstract data representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param dataBusinessType the data business type
   */
  public AbstractDataRepresentation(
      String dataType, String variableName, DataBusinessType dataBusinessType) {
    this(dataType, variableName, new LinkedHashSet<>(), dataBusinessType);
  }

  /**
   * Instantiates a new Abstract data representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param annotations the annotations
   */
  public AbstractDataRepresentation(String dataType, String variableName, Set<String> annotations) {
    this(dataType, variableName, annotations, DataBusinessType.ANY);
  }

  /**
   * Instantiates a new Abstract data representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param annotations the annotations
   * @param dataBusinessType the data business type
   */
  public AbstractDataRepresentation(
      String dataType,
      String variableName,
      Set<String> annotations,
      DataBusinessType dataBusinessType) {
    setDataType(dataType);
    setVariableName(variableName);
    setAnnotations(annotations);
    setDataBusinessType(dataBusinessType);
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets all annotations.
   *
   * @return the all annotations
   */
  public String getAllAnnotations() {
    StringBuilder stringBuilder = new StringBuilder();

    String strAnnotations = getAnnotations().stream().collect(Collectors.joining(" "));

    if (!strAnnotations.isEmpty()) {
      stringBuilder.append(strAnnotations);
      stringBuilder.append(" ");
    }

    return stringBuilder.toString();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets data type.
   *
   * @return the data type
   */
  public String getDataType() {
    return dataType;
  }

  /**
   * Gets variable name.
   *
   * @return the variable name
   */
  public String getVariableName() {
    return variableName;
  }

  /**
   * Gets annotations.
   *
   * @return the annotations
   */
  public Set<String> getAnnotations() {
    return annotations;
  }

  /**
   * Gets data business type.
   *
   * @return the data business type
   */
  public DataBusinessType getDataBusinessType() {
    return dataBusinessType;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets data type.
   *
   * @param dataType the data type
   */
  private void setDataType(String dataType) {
    Assertion.isNotNull(dataType, "dataType is required");
    this.dataType = dataType;
  }

  /**
   * Sets variable name.
   *
   * @param variableName the variable name
   */
  private void setVariableName(String variableName) {
    Assertion.isNotNull(variableName, "variableName is required");
    this.variableName = variableName;
  }

  /**
   * Sets annotations.
   *
   * @param annotations the annotations
   */
  private void setAnnotations(Set<String> annotations) {
    Assertion.isNotNull(annotations, "annotations is required");
    this.annotations = annotations;
  }

  /**
   * Sets data business type.
   *
   * @param dataBusinessType the data business type
   */
  private void setDataBusinessType(DataBusinessType dataBusinessType) {
    Assertion.isNotNull(dataBusinessType, "dataBusinessType is required");
    this.dataBusinessType = dataBusinessType;
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
    AbstractDataRepresentation that = (AbstractDataRepresentation) o;
    return Objects.equals(dataType, that.dataType)
        && Objects.equals(variableName, that.variableName)
        && Objects.equals(annotations, that.annotations)
        && dataBusinessType == that.dataBusinessType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataType, variableName, annotations, dataBusinessType);
  }
}
