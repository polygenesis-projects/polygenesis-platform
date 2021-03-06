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

package io.polygenesis.models.api;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.thing.CqsType;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingName;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Generatable;
import io.polygenesis.core.Metamodel;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Service.
 *
 * @author Christos Tsakostas
 */
public class Service implements Generatable, Metamodel {

  private PackageName packageName;
  private ServiceName serviceName;
  private CqsType cqrsType;
  private ThingName thingName;

  private Set<ServiceMethod> serviceMethods;
  private Set<Dto> cachedDtos;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service.
   *
   * @param packageName the package name
   * @param serviceName the service name
   * @param cqrsType the cqrs type
   * @param thingName the thing name
   */
  public Service(
      PackageName packageName, ServiceName serviceName, CqsType cqrsType, ThingName thingName) {
    setPackageName(packageName);
    setServiceName(serviceName);
    setCqrsType(cqrsType);
    setThingName(thingName);

    setServiceMethods(new LinkedHashSet<>());
  }

  // ===============================================================================================
  // BEHAVIOR
  // ===============================================================================================

  /**
   * Append service method.
   *
   * @param function the function
   * @param requestDto the request dto
   * @param responseDto the response dto
   */
  public void appendServiceMethod(Function function, Dto requestDto, Dto responseDto) {
    serviceMethods.add(new ServiceMethod(this, function, requestDto, responseDto));
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return new ObjectName(serviceName.getText());
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
   * Gets service name.
   *
   * @return the service name
   */
  public ServiceName getServiceName() {
    return serviceName;
  }

  /**
   * Gets cqrs type.
   *
   * @return the cqrs type
   */
  public CqsType getCqrsType() {
    return cqrsType;
  }

  /**
   * Gets thing name.
   *
   * @return the thing name
   */
  public ThingName getThingName() {
    return thingName;
  }

  /**
   * Gets methods.
   *
   * @return the methods
   */
  public Set<ServiceMethod> getServiceMethods() {
    return serviceMethods;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets dtos.
   *
   * @return the dtos
   */
  public Set<Dto> getDtos() {
    if (cachedDtos == null) {
      cachedDtos = new LinkedHashSet<>();

      serviceMethods
          .stream()
          .forEach(
              method -> {
                addDto(cachedDtos, method.getRequestDto(), method);
                addDto(cachedDtos, method.getResponseDto(), method);
              });
    }

    return cachedDtos;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Add dto.
   *
   * @param dtos the dtos
   * @param dto the dto
   */
  private void addDto(Set<Dto> dtos, Dto dto, ServiceMethod serviceMethod) {
    dtos.add(dto);

    if (dto.getArrayElementAsOptional().isPresent()) {
      Data arrayElement =
          dto.getArrayElementAsOptional().orElseThrow(IllegalArgumentException::new);
      if (arrayElement.isDataGroup()) {

        Thing relatedThing =
            serviceMethod.getFunction().getDelegatesToFunction() != null
                ? serviceMethod.getFunction().getDelegatesToFunction().getThing()
                : dto.getRelatedThing();

        addDto(
            dtos,
            new Dto(
                relatedThing,
                DtoType.COLLECTION_RECORD,
                arrayElement.getAsDataObject(),
                false,
                dto),
            serviceMethod);
      }
    }

    // Add model group children of DataObject recursively
    dto.getDataObject()
        .getModels()
        .forEach(
            model -> {
              // TODO: check if model array element children should be added as well
              if (model.isDataGroup()) {
                DataObject dataObject = model.getAsDataObject();

                DtoType dtoType;
                if (dto.getDtoType().equals(DtoType.API_COLLECTION_REQUEST)
                    || dto.getDtoType().equals(DtoType.API_PAGED_COLLECTION_REQUEST)) {
                  dtoType = DtoType.COLLECTION_RECORD;
                } else {
                  dtoType = DtoType.INTERNAL;
                  dataObject =
                      dataObject.withNewObjectName(
                          new ObjectName(
                              String.format("%sDto", dataObject.getObjectName().getText())));
                }

                addDto(
                    dtos,
                    new Dto(dto.getRelatedThing(), dtoType, dataObject, false, dto),
                    serviceMethod);
              }
            });
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
   * Sets service name.
   *
   * @param serviceName the service name
   */
  private void setServiceName(ServiceName serviceName) {
    Assertion.isNotNull(serviceName, "serviceName is required");
    this.serviceName = serviceName;
  }

  /**
   * Sets cqrs type.
   *
   * @param cqrsType the cqrs type
   */
  private void setCqrsType(CqsType cqrsType) {
    Assertion.isNotNull(cqrsType, "cqrsType is required");
    this.cqrsType = cqrsType;
  }

  /**
   * Sets thing name.
   *
   * @param thingName the thing name
   */
  private void setThingName(ThingName thingName) {
    Assertion.isNotNull(thingName, "thingName is required");
    this.thingName = thingName;
  }

  /**
   * Sets methods.
   *
   * @param serviceMethods the methods
   */
  private void setServiceMethods(Set<ServiceMethod> serviceMethods) {
    Assertion.isNotNull(serviceMethods, "serviceMethods is required");
    this.serviceMethods = serviceMethods;
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
    Service service = (Service) o;
    return Objects.equals(packageName, service.packageName)
        && Objects.equals(serviceName, service.serviceName)
        && Objects.equals(serviceMethods, service.serviceMethods)
        && cqrsType == service.cqrsType
        && Objects.equals(thingName, service.thingName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(packageName, serviceName, serviceMethods, cqrsType, thingName);
  }
}
