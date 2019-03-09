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

import static java.util.stream.Collectors.toCollection;

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.core.datatype.ClassDataType;
import io.polygenesis.core.datatype.PrimitiveDataType;
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

  private final Set<IoModel> models;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model group.
   */
  public IoModelGroup() {
    this.models = new LinkedHashSet<>();
  }

  /**
   * Instantiates a new Io model group.
   *
   * <p>Variable name is automatically set after class data type name.
   *
   * @param dataType the data type
   */
  public IoModelGroup(ClassDataType dataType) {
    this(dataType, new VariableName(dataType.getDataTypeName().getText()), new LinkedHashSet<>());
  }

  /**
   * Instantiates a new Io model group.
   *
   * @param dataType the data type
   * @param variableName the variable name
   */
  public IoModelGroup(ClassDataType dataType, VariableName variableName) {
    this(dataType, variableName, new LinkedHashSet<>());
  }

  /**
   * Instantiates a new Io model group.
   *
   * @param dataType the data type
   * @param variableName the variable name
   * @param models the models
   */
  private IoModelGroup(ClassDataType dataType,
      VariableName variableName,
      Set<IoModel> models) {
    super(dataType, variableName);
    this.models = models;
  }

  // ===============================================================================================
  // CHANGES
  // ===============================================================================================

  /**
   * With new class data type io model group.
   *
   * @param dataType the data type
   * @return the io model group
   */
  public IoModelGroup withNewClassDataType(ClassDataType dataType) {
    return new IoModelGroup(dataType, getVariableName(), getModels());
  }

  /**
   * With new variable name io model group.
   *
   * @param variableName the variable name
   * @return the io model group
   */
  public IoModelGroup withNewVariableName(VariableName variableName) {
    return new IoModelGroup((ClassDataType) getDataType(), variableName, getModels());
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
    Assertion.isNotNull(model, "Model Primitive is required");
    return models.add(model);
  }

  /**
   * Add io model group boolean.
   *
   * @param model the model
   * @return the boolean
   */
  public boolean addIoModelGroup(IoModelGroup model) {
    Assertion.isNotNull(model, "Model Group is required");
    return models.add(model);
  }

  /**
   * Add io model array boolean.
   *
   * @param model the model
   * @return the boolean
   */
  public boolean addIoModelArray(IoModelArray model) {
    Assertion.isNotNull(model, "Array Model is required");
    return models.add(model);
  }

  /**
   * Add io model.
   *
   * @param model the model
   */
  public void addIoModel(IoModel model) {
    Assertion.isNotNull(model, "Model is required");

    if (model.isIoModelGroup()) {
      IoModelGroup ioModelGroup =
          new IoModelGroup(
              ((IoModelGroup) model).getClassDataType(),
              model.getVariableName(),
              ((IoModelGroup) model).getModels());

      models.add(ioModelGroup);
    } else if (model.isPrimitive()) {
      IoModelPrimitive ioModelPrimitive =
          new IoModelPrimitive(
              (PrimitiveDataType) model.getDataType(),
              model.getVariableName(),
              ((IoModelPrimitive) model).getAnnotations(),
              ((IoModelPrimitive) model).getDataBusinessType());

      models.add(ioModelPrimitive);
    } else {
      throw new UnsupportedOperationException();
    }
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
    return models.stream().collect(toCollection(LinkedHashSet::new));
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets class data type.
   *
   * @return the class data type
   */
  public ClassDataType getClassDataType() {
    if (!getDataType().isClass()) {
      throw new IllegalStateException(
          String.format(
              "Datatype=%s is not ClassDataType", getDataType().getDataTypeName().getText()));
    }
    return (ClassDataType) getDataType();
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
