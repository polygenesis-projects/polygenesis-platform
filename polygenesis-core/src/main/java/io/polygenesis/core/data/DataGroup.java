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
 * The type data group.
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

  /** Instantiates a new data group. */
  public DataGroup() {
    this(DataSource.user(), null, DataBusinessType.ANY, null, null, new LinkedHashSet<>());
  }

  /**
   * Instantiates a new data group.
   *
   * @param variableName the variable name
   */
  public DataGroup(VariableName variableName) {
    this(DataSource.user(), variableName, DataBusinessType.ANY, null, null, new LinkedHashSet<>());
  }

  /**
   * Instantiates a new data group.
   *
   * @param objectName the object name
   * @param packageName the package name
   */
  public DataGroup(ObjectName objectName, PackageName packageName) {
    this(
        DataSource.user(),
        new VariableName(objectName.getText()),
        DataBusinessType.ANY,
        objectName,
        packageName,
        new LinkedHashSet<>());
  }

  /**
   * Instantiates a new data group.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param variableName the variable name
   */
  public DataGroup(ObjectName objectName, PackageName packageName, VariableName variableName) {
    this(
        DataSource.user(),
        variableName,
        DataBusinessType.ANY,
        objectName,
        packageName,
        new LinkedHashSet<>());
  }

  /**
   * Instantiates a new Data group.
   *
   * @param dataSource the data source
   * @param variableName the variable name
   * @param dataBusinessType the data business type
   * @param objectName the object name
   * @param packageName the package name
   * @param models the models
   */
  public DataGroup(
      DataSource dataSource,
      VariableName variableName,
      DataBusinessType dataBusinessType,
      ObjectName objectName,
      PackageName packageName,
      Set<Data> models) {
    super(DataPrimaryType.OBJECT, dataSource, variableName, dataBusinessType);
    this.objectName = objectName;
    this.packageName = packageName;
    this.models = models;
  }

  // ===============================================================================================
  // CHANGES
  // ===============================================================================================

  /**
   * With new object name data group.
   *
   * @param objectName the object name
   * @return the data group
   */
  public DataGroup withNewObjectName(ObjectName objectName) {
    return new DataGroup(
        getDataSource(),
        getVariableName(),
        getDataBusinessType(),
        objectName,
        getPackageName(),
        getModels());
  }

  /**
   * With new variable name data group.
   *
   * @param variableName the variable name
   * @return the data group
   */
  public DataGroup withNewVariableName(VariableName variableName) {
    return new DataGroup(
        getDataSource(),
        variableName,
        getDataBusinessType(),
        getObjectName(),
        getPackageName(),
        getModels());
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
