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

package io.polygenesis.core.data;

import static java.util.stream.Collectors.toCollection;

import com.oregor.ddd4j.check.assertion.Assertion;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Io model group.
 *
 * @author Christos Tsakostas
 */
public class DataGroup extends Data {

  private final ObjectName objectName;
  private final PackageName packageName;
  private final Set<Data> models;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Io model group. */
  public DataGroup() {
    this(null, null, null, new LinkedHashSet<>());
  }

  /**
   * Instantiates a new Io model group.
   *
   * @param variableName the variable name
   */
  public DataGroup(VariableName variableName) {
    this(null, null, variableName, new LinkedHashSet<>());
  }

  /**
   * Instantiates a new Io model group.
   *
   * @param objectName the object name
   * @param packageName the package name
   */
  public DataGroup(ObjectName objectName, PackageName packageName) {
    this(objectName, packageName, new VariableName(objectName.getText()), new LinkedHashSet<>());
  }

  /**
   * Instantiates a new Io model group.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param variableName the variable name
   */
  public DataGroup(ObjectName objectName, PackageName packageName, VariableName variableName) {
    this(objectName, packageName, variableName, new LinkedHashSet<>());
  }

  private DataGroup(
      ObjectName objectName, PackageName packageName, VariableName variableName, Set<Data> models) {
    super(DataKind.OBJECT, variableName);
    this.objectName = objectName;
    this.packageName = packageName;
    this.models = models;
  }

  // ===============================================================================================
  // CHANGES
  // ===============================================================================================

  /**
   * With new object name io model group.
   *
   * @param objectName the object name
   * @return the io model group
   */
  public DataGroup withNewObjectName(ObjectName objectName) {
    return new DataGroup(objectName, getPackageName(), getVariableName(), getModels());
  }

  /**
   * With new variable name io model group.
   *
   * @param variableName the variable name
   * @return the io model group
   */
  public DataGroup withNewVariableName(VariableName variableName) {
    return new DataGroup(getObjectName(), getPackageName(), variableName, getModels());
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
  public boolean addIoModelPrimitive(DataPrimitive model) {
    Assertion.isNotNull(model, "Model Primitive is required");
    return models.add(model);
  }

  /**
   * Add io model group boolean.
   *
   * @param model the model
   * @return the boolean
   */
  public boolean addIoModelGroup(DataGroup model) {
    Assertion.isNotNull(model, "Model Group is required");
    return models.add(model);
  }

  /**
   * Add io model array boolean.
   *
   * @param model the model
   * @return the boolean
   */
  public boolean addIoModelArray(DataArray model) {
    Assertion.isNotNull(model, "Array Model is required");
    return models.add(model);
  }

  /**
   * Add io model.
   *
   * @param model the model
   */
  public void addIoModel(Data model) {
    Assertion.isNotNull(model, "Model is required");

    if (model.isDataGroup()) {
      DataGroup ioModelGroup =
          new DataGroup(
              ((DataGroup) model).getObjectName(),
              ((DataGroup) model).getPackageName(),
              model.getVariableName(),
              ((DataGroup) model).getModels());

      models.add(ioModelGroup);
    } else if (model.isDataPrimitive()) {
      DataPrimitive ioModelPrimitive =
          new DataPrimitive(
              ((DataPrimitive) model).getPrimitiveType(),
              model.getVariableName(),
              ((DataPrimitive) model).getAnnotations(),
              ((DataPrimitive) model).getDataBusinessType());

      models.add(ioModelPrimitive);
    } else if (model.isDataArray()) {
      DataArray ioModelArray =
          new DataArray(model.getVariableName(), ((DataArray) model).getArrayElement());
      models.add(ioModelArray);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets object name.
   *
   * @return the object name
   */
  public ObjectName getObjectName() {
    return objectName;
  }

  /**
   * Gets package name.
   *
   * @return the package name
   */
  public PackageName getPackageName() {
    return packageName;
  }

  /**
   * Gets models.
   *
   * @return the models
   */
  public Set<Data> getModels() {
    return models.stream().collect(toCollection(LinkedHashSet::new));
  }

  // ===============================================================================================
  // ABSTRACT IMPLEMENTATION
  // ===============================================================================================

  @Override
  public String getDataType() {
    return getObjectName().getText();
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
    DataGroup that = (DataGroup) o;
    return Objects.equals(objectName, that.objectName)
        && Objects.equals(packageName, that.packageName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), objectName, packageName);
  }
}
