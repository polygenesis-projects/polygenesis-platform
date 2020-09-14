/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataRepository;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.AbstractMetamodel;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMethod;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class BatchProcessMetamodel extends AbstractMetamodel {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private PackageName packageName;
  private ServiceMethod commandServiceMethod;
  private ServiceMethod queryServiceMethod;
  private Dto queryCollectionItem;
  private Service service;
  private Set<BatchProcessMethod> batchProcessMethods;

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
    setService(commandServiceMethod.getService());

    initializeBatchProcessMethods();
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

  /**
   * Gets service.
   *
   * @return the service
   */
  public Service getService() {
    return service;
  }

  /**
   * Gets batch process methods.
   *
   * @return the batch process methods
   */
  public Set<BatchProcessMethod> getBatchProcessMethods() {
    return batchProcessMethods;
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
  private void setQueryCollectionItem(Dto queryCollectionItem) {
    Assertion.isNotNull(queryCollectionItem, "queryCollectionItem is required");
    this.queryCollectionItem = queryCollectionItem;
  }

  /**
   * Sets service.
   *
   * @param service the service
   */
  private void setService(Service service) {
    Assertion.isNotNull(service, "service is required");
    this.service = service;
  }

  /**
   * Sets batch process methods.
   *
   * @param batchProcessMethods the batch process methods
   */
  private void setBatchProcessMethods(Set<BatchProcessMethod> batchProcessMethods) {
    Assertion.isNotNull(batchProcessMethods, "batchProcessMethods is required");
    this.batchProcessMethods = batchProcessMethods;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void initializeBatchProcessMethods() {
    setBatchProcessMethods(new LinkedHashSet<>());

    getBatchProcessMethods().add(new BatchProcessMethod(this, makeGetBatchProcessServiceName()));
  }

  private Function makeGetBatchProcessServiceName() {
    Thing thing = ThingBuilder.apiClientBatchProcess("batchProcess").createThing();

    return new Function(
        thing,
        Purpose.batchProcessServiceName(),
        FunctionName.ofVerbOnly("getBatchProcessServiceName"),
        DataPrimitive.of(PrimitiveType.STRING, VariableName.response()),
        new DataRepository(),
        Activity.empty(),
        thing.getAbstractionsScopes(),
        new LinkedHashSet<>(Collections.singleton(FunctionRole.system())));
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
