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

package io.polygenesis.models.periodicprocess;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodel;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.ServiceMethod;
import java.util.Objects;

/**
 * The type Batch process metamodel.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessMetamodel extends AbstractMetamodel {

  private PackageName packageName;
  private ServiceMethod commandServiceMethod;
  private ServiceMethod queryServiceMethod;
  private Dto queryCollectionItem;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process metamodel.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param commandServiceMethod the command service method
   * @param queryServiceMethod the query service method
   * @param queryCollectionItem the query collection item
   */
  public BatchProcessMetamodel(
      ObjectName objectName,
      PackageName packageName,
      ServiceMethod commandServiceMethod,
      ServiceMethod queryServiceMethod,
      Dto queryCollectionItem) {
    super(objectName);
    setPackageName(packageName);
    setCommandServiceMethod(commandServiceMethod);
    setQueryServiceMethod(queryServiceMethod);
    setQueryCollectionItem(queryCollectionItem);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets package name.
   *
   * @return the package name
   */
  public PackageName getPackageName() {
    return packageName;
  }

  /**
   * Gets command service method.
   *
   * @return the command service method
   */
  public ServiceMethod getCommandServiceMethod() {
    return commandServiceMethod;
  }

  /**
   * Gets query service method.
   *
   * @return the query service method
   */
  public ServiceMethod getQueryServiceMethod() {
    return queryServiceMethod;
  }

  /**
   * Gets query collection item.
   *
   * @return the query collection item
   */
  public Dto getQueryCollectionItem() {
    return queryCollectionItem;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets package name.
   *
   * @param packageName the package name
   */
  private void setPackageName(PackageName packageName) {
    Assertion.isNotNull(packageName, "packageName is required");
    this.packageName = packageName;
  }

  /**
   * Sets command service method.
   *
   * @param commandServiceMethod the command service method
   */
  private void setCommandServiceMethod(ServiceMethod commandServiceMethod) {
    Assertion.isNotNull(commandServiceMethod, "commandServiceMethod is required");
    this.commandServiceMethod = commandServiceMethod;
  }

  /**
   * Sets query service method.
   *
   * @param queryServiceMethod the query service method
   */
  private void setQueryServiceMethod(ServiceMethod queryServiceMethod) {
    Assertion.isNotNull(queryServiceMethod, "queryServiceMethod is required");
    this.queryServiceMethod = queryServiceMethod;
  }

  /**
   * Sets query collection item.
   *
   * @param queryCollectionItem the query collection item
   */
  public void setQueryCollectionItem(Dto queryCollectionItem) {
    Assertion.isNotNull(queryCollectionItem, "queryCollectionItem is required");
    this.queryCollectionItem = queryCollectionItem;
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
    BatchProcessMetamodel that = (BatchProcessMetamodel) o;
    return Objects.equals(packageName, that.packageName)
        && Objects.equals(commandServiceMethod, that.commandServiceMethod)
        && Objects.equals(queryServiceMethod, that.queryServiceMethod)
        && Objects.equals(queryCollectionItem, that.queryCollectionItem);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(),
        packageName,
        commandServiceMethod,
        queryServiceMethod,
        queryCollectionItem);
  }
}
