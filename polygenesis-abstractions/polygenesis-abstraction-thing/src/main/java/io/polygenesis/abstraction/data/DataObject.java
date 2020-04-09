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

package io.polygenesis.abstraction.data;

import static java.util.stream.Collectors.toCollection;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Data object.
 *
 * @author Christos Tsakostas
 */
public class DataObject extends AbstractData {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final ObjectName objectName;
  private final PackageName packageName;
  private final Set<Data> models;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * As data object data object.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @return the data object
   */
  public static DataObject asDataObject(ObjectName objectName, PackageName packageName) {
    return new DataObject(
        new VariableName(objectName.getText()),
        DataPurpose.any(),
        DataValidator.empty(),
        objectName,
        packageName,
        new LinkedHashSet<>(),
        DataSourceType.EXTERNALLY_PROVIDED);
  }

  /**
   * As data object data object.
   *
   * @param objectName the object name
   * @param variableName the variable name
   * @param packageName the package name
   * @return the data object
   */
  public static DataObject asDataObject(
      ObjectName objectName, VariableName variableName, PackageName packageName) {
    return new DataObject(
        variableName,
        DataPurpose.any(),
        DataValidator.empty(),
        objectName,
        packageName,
        new LinkedHashSet<>(),
        DataSourceType.EXTERNALLY_PROVIDED);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Data object.
   *
   * @param objectName the object name
   * @param packageName the package name
   */
  public DataObject(ObjectName objectName, PackageName packageName) {
    this(
        new VariableName(objectName.getText()),
        DataPurpose.any(),
        DataValidator.empty(),
        objectName,
        packageName,
        new LinkedHashSet<>(),
        DataSourceType.DEFAULT);
  }

  /**
   * Instantiates a new Data object.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param variableName the variable name
   */
  public DataObject(ObjectName objectName, PackageName packageName, VariableName variableName) {
    this(
        variableName,
        DataPurpose.any(),
        DataValidator.empty(),
        objectName,
        packageName,
        new LinkedHashSet<>(),
        DataSourceType.DEFAULT);
  }

  /**
   * Instantiates a new Data object.
   *
   * @param variableName the variable name
   * @param dataPurpose the data purpose
   * @param dataValidator the data validator
   * @param objectName the object name
   * @param packageName the package name
   * @param models the models
   * @param dataSourceType the data source type
   */
  public DataObject(
      VariableName variableName,
      DataPurpose dataPurpose,
      DataValidator dataValidator,
      ObjectName objectName,
      PackageName packageName,
      Set<Data> models,
      DataSourceType dataSourceType) {
    super(DataPrimaryType.OBJECT, variableName, dataPurpose, dataValidator, dataSourceType);
    this.objectName = objectName;
    this.packageName = packageName;
    this.models = models;
  }

  // ===============================================================================================
  // CHANGES
  // ===============================================================================================

  /**
   * With new object name data object.
   *
   * @param objectName the object name
   * @return the data object
   */
  public DataObject withNewObjectName(ObjectName objectName) {
    return new DataObject(
        getVariableName(),
        getDataPurpose(),
        getDataValidator(),
        objectName,
        getPackageName(),
        getModels(),
        getDataSourceType());
  }

  /**
   * With variable name data object.
   *
   * @param variableName the variable name
   * @return the data object
   */
  public DataObject withVariableName(String variableName) {
    return new DataObject(
        new VariableName(variableName),
        getDataPurpose(),
        getDataValidator(),
        objectName,
        getPackageName(),
        getModels(),
        getDataSourceType());
  }

  /**
   * With variable name equal to object name data object.
   *
   * @return the data object
   */
  public DataObject withVariableNameEqualToObjectName() {
    return new DataObject(
        new VariableName(getObjectName().getText()),
        getDataPurpose(),
        getDataValidator(),
        objectName,
        getPackageName(),
        getModels(),
        getDataSourceType());
  }

  /**
   * As dto data object.
   *
   * @return the data object
   */
  public DataObject asDto() {
    return new DataObject(
        getVariableName(),
        getDataPurpose(),
        getDataValidator(),
        new ObjectName(String.format("%sDto", getObjectName().getText())),
        getPackageName(),
        getModels(),
        getDataSourceType());
  }

  // ===============================================================================================
  // BEHAVIOUR
  // ===============================================================================================

  /**
   * Add data.
   *
   * @param data the data
   */
  public void addData(Data data) {
    Assertion.isNotNull(data, "data is required");
    models.add(data);
  }

  /**
   * Add data.
   *
   * @param data the data
   */
  public void addData(Set<Data> data) {
    Assertion.isNotNull(data, "data is required");
    data.forEach(this::addData);
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
    DataObject that = (DataObject) o;
    return Objects.equals(objectName, that.objectName)
        && Objects.equals(packageName, that.packageName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), objectName, packageName);
  }
}
