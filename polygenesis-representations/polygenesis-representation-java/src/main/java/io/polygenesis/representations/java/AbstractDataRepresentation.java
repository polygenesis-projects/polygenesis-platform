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

package io.polygenesis.representations.java;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Abstract data representation.
 *
 * @author Christos Tsakostas
 */
public class AbstractDataRepresentation {

  private String dataType;
  private String variableName;
  private Set<String> annotations;
  private Boolean isThingIdentity;

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
    this(dataType, variableName, new LinkedHashSet<>(), false);
  }

  /**
   * Instantiates a new Abstract data representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param isThingIdentity the is thing identity
   */
  public AbstractDataRepresentation(String dataType, String variableName, Boolean isThingIdentity) {
    this(dataType, variableName, new LinkedHashSet<>(), isThingIdentity);
  }

  /**
   * Instantiates a new Abstract data representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param annotations the annotations
   */
  public AbstractDataRepresentation(String dataType, String variableName, Set<String> annotations) {
    this(dataType, variableName, annotations, false);
  }

  /**
   * Instantiates a new Abstract data representation.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param annotations the annotations
   * @param isThingIdentity the is thing identity
   */
  public AbstractDataRepresentation(
      String dataType, String variableName, Set<String> annotations, Boolean isThingIdentity) {
    setDataType(dataType);
    setVariableName(variableName);
    setAnnotations(annotations);
    setThingIdentity(isThingIdentity);
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
   * Gets thing identity.
   *
   * @return the thing identity
   */
  public Boolean getThingIdentity() {
    return isThingIdentity;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets data type.
   *
   * @param dataType the data type
   */
  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  /**
   * Sets variable name.
   *
   * @param variableName the variable name
   */
  public void setVariableName(String variableName) {
    this.variableName = variableName;
  }

  /**
   * Sets annotations.
   *
   * @param annotations the annotations
   */
  public void setAnnotations(Set<String> annotations) {
    this.annotations = annotations;
  }

  /**
   * Sets thing identity.
   *
   * @param thingIdentity the thing identity
   */
  public void setThingIdentity(Boolean thingIdentity) {
    isThingIdentity = thingIdentity;
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
        && Objects.equals(isThingIdentity, that.isThingIdentity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataType, variableName, annotations, isThingIdentity);
  }
}
