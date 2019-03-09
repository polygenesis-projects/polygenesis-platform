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
import io.polygenesis.core.data.ObjectName;
import io.polygenesis.core.datatype.PackageName;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Io model group.
 *
 * @author Christos Tsakostas
 */
public class IoModelGroup extends IoModel {

  private final ObjectName objectName;
  private final PackageName packageName;
  private final Set<IoModel> models;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model group.
   */
  public IoModelGroup() {
    this(null, null, null, new LinkedHashSet<>());
  }

  /**
   * Instantiates a new Io model group.
   *
   * @param variableName the variable name
   */
  public IoModelGroup(VariableName variableName) {
    this(null, null, variableName, new LinkedHashSet<>());
  }

  /**
   * Instantiates a new Io model group.
   *
   * @param objectName the object name
   * @param packageName the package name
   */
  public IoModelGroup(ObjectName objectName, PackageName packageName) {
    this(objectName, packageName, new VariableName(objectName.getText()), new LinkedHashSet<>());
  }

  /**
   * Instantiates a new Io model group.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param variableName the variable name
   */
  public IoModelGroup(ObjectName objectName, PackageName packageName, VariableName variableName) {
    this(objectName, packageName, variableName, new LinkedHashSet<>());
  }

  private IoModelGroup(ObjectName objectName,
      PackageName packageName,
      VariableName variableName,
      Set<IoModel> models) {
    super(DataKind.CLASS, variableName);
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
  public IoModelGroup withNewObjectName(ObjectName objectName) {
    return new IoModelGroup(objectName, getPackageName(), getVariableName(), getModels());
  }


  /**
   * With new variable name io model group.
   *
   * @param variableName the variable name
   * @return the io model group
   */
  public IoModelGroup withNewVariableName(VariableName variableName) {
    return new IoModelGroup(getObjectName(), getPackageName(), variableName, getModels());
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
              ((IoModelGroup) model).getObjectName(),
              ((IoModelGroup) model).getPackageName(),
              model.getVariableName(),
              ((IoModelGroup) model).getModels());

      models.add(ioModelGroup);
    } else if (model.isPrimitive()) {
      IoModelPrimitive ioModelPrimitive =
          new IoModelPrimitive(
              ((IoModelPrimitive) model).getPrimitiveType(),
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
  public Set<IoModel> getModels() {
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
    IoModelGroup that = (IoModelGroup) o;
    return Objects.equals(objectName, that.objectName) &&
        Objects.equals(packageName, that.packageName) &&
        Objects.equals(models, that.models);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), objectName, packageName, models);
  }
}
