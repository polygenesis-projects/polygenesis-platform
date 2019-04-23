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

package io.polygenesis.models.domain;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;

/**
 * The type Persistence.
 *
 * @author Christos Tsakostas
 */
public class Persistence {

  private PackageName packageName;
  private ObjectName objectName;
  private ObjectName aggregateRootObjectName;
  private ObjectName aggregateRootIdObjectName;
  private Boolean multiTenant;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Persistence.
   *
   * @param packageName the package name
   * @param objectName the name
   * @param aggregateRootObjectName the aggregate root name
   * @param aggregateRootIdObjectName the aggregate root id name
   * @param multiTenant the multi tenant
   */
  public Persistence(
      PackageName packageName,
      ObjectName objectName,
      ObjectName aggregateRootObjectName,
      ObjectName aggregateRootIdObjectName,
      Boolean multiTenant) {
    setPackageName(packageName);
    setObjectName(objectName);
    setAggregateRootObjectName(aggregateRootObjectName);
    setAggregateRootIdObjectName(aggregateRootIdObjectName);
    setMultiTenant(multiTenant);
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
   * Gets name.
   *
   * @return the name
   */
  public ObjectName getObjectName() {
    return objectName;
  }

  /**
   * Gets aggregate root name.
   *
   * @return the aggregate root name
   */
  public ObjectName getAggregateRootObjectName() {
    return aggregateRootObjectName;
  }

  /**
   * Gets aggregate root id name.
   *
   * @return the aggregate root id name
   */
  public ObjectName getAggregateRootIdObjectName() {
    return aggregateRootIdObjectName;
  }

  /**
   * Gets multi tenant.
   *
   * @return the multi tenant
   */
  public Boolean getMultiTenant() {
    return multiTenant;
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
    this.packageName = packageName;
  }

  /**
   * Sets name.
   *
   * @param objectName the name
   */
  private void setObjectName(ObjectName objectName) {
    this.objectName = objectName;
  }

  /**
   * Sets aggregate root name.
   *
   * @param aggregateRootObjectName the aggregate root name
   */
  private void setAggregateRootObjectName(ObjectName aggregateRootObjectName) {
    this.aggregateRootObjectName = aggregateRootObjectName;
  }

  /**
   * Sets aggregate root id name.
   *
   * @param aggregateRootIdObjectName the aggregate root id name
   */
  private void setAggregateRootIdObjectName(ObjectName aggregateRootIdObjectName) {
    this.aggregateRootIdObjectName = aggregateRootIdObjectName;
  }

  /**
   * Sets multi tenant.
   *
   * @param multiTenant the multi tenant
   */
  private void setMultiTenant(Boolean multiTenant) {
    this.multiTenant = multiTenant;
  }
}
