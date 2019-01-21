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

package io.polygenesis.core.iomodel;

import java.lang.annotation.Annotation;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The type Io model primitive.
 *
 * @author Christos Tsakostas
 */
public class IoModelPrimitive extends IoModel {

  private Set<Annotation> annotations;
  private Boolean isThingIdentity;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model primitive.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param annotations the annotations
   */
  public IoModelPrimitive(
      DataTypeName dataType, VariableName variableName, Set<Annotation> annotations) {
    super(dataType, variableName);
    setAnnotations(annotations);
    setThingIdentity(false);
  }

  /**
   * Instantiates a new Io model primitive.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param parent the parent
   * @param annotations the annotations
   */
  public IoModelPrimitive(
      DataTypeName dataType,
      VariableName variableName,
      IoModelGroup parent,
      Set<Annotation> annotations) {
    super(dataType, variableName, parent);
    setAnnotations(annotations);
    setThingIdentity(false);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Get annotations.
   *
   * @return the annotations
   */
  public Set<Annotation> getAnnotations() {
    return annotations;
  }

  /**
   * Checks id primitive model is thing identity.
   *
   * @return the thing identity flag
   */
  public Boolean getThingIdentity() {
    return isThingIdentity;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setAnnotations(Set<Annotation> annotations) {
    this.annotations = annotations;
  }

  private void setThingIdentity(Boolean thingIdentity) {
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

    IoModelPrimitive that = (IoModelPrimitive) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(annotations, that.annotations)
        .append(isThingIdentity, that.isThingIdentity)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(annotations)
        .append(isThingIdentity)
        .toHashCode();
  }
}
