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

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.core.datatype.ClassDataType;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The type Io model group.
 *
 * @author Christos Tsakostas
 */
public class IoModelGroup extends IoModel {

  private Set<IoModel> models;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model group.
   *
   * @param parent the parent
   */
  public IoModelGroup(IoModelGroup parent) {
    super(parent);
    setModels(new LinkedHashSet<>());
  }

  /**
   * Instantiates a new Io model group.
   *
   * @param dataType the data type
   */
  public IoModelGroup(ClassDataType dataType) {
    this(null, dataType, new VariableName(dataType.getDataTypeName().getText()));
  }

  /**
   * Instantiates a new Io model group.
   *
   * @param genericType the generic type
   * @param dataType the data type
   * @param variableName the variable name
   */
  public IoModelGroup(
      GenericTypeName genericType, ClassDataType dataType, VariableName variableName) {
    super(genericType, dataType, variableName);
    setModels(new LinkedHashSet<>());
  }

  // ===============================================================================================
  // BEHAVIOUR
  // ===============================================================================================

  /**
   * Add io model primitive boolean.
   *
   * @param model the model
   * @return the boolean
   */
  public boolean addIoModelPrimitive(IoModelPrimitive model) {

    return models.add(model);
  }

  /**
   * Add io model group boolean.
   *
   * @param model the model
   * @return the boolean
   */
  public boolean addIoModelGroup(IoModelGroup model) {
    return models.add(model);
  }

  /**
   * Add io model array boolean.
   *
   * @param model the model
   * @return the boolean
   */
  public boolean addIoModelArray(IoModelArray model) {
    return models.add(model);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets models.
   *
   * @return the models
   */
  public Set<IoModel> getModels() {
    return models;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setModels(Set<IoModel> models) {
    Assertion.isNotNull(models, "models is required");
    this.models = models;
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

    IoModelGroup that = (IoModelGroup) o;

    return new EqualsBuilder().appendSuper(super.equals(o)).append(models, that.models).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode();
  }
}
