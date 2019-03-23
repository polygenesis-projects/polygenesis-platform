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

package io.polygenesis.deducers.apiimpl;

import io.polygenesis.core.data.Data;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.apiimpl.DomainObjectConverter;
import io.polygenesis.models.apiimpl.FetchCollectionDtoFromDomainObject;
import io.polygenesis.models.apiimpl.FetchOneDtoFromDomainObject;
import io.polygenesis.models.apiimpl.ValueObjectFromDto;
import io.polygenesis.models.domain.BaseDomainObject;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.models.domain.ValueObject;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Domain object converter deducer.
 *
 * @author Christos Tsakostas
 */
public class DomainObjectConverterDeducer {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce domain object converter.
   *
   * @param domainObject the domain object
   * @param services the services
   * @return the domain object converter
   */
  public DomainObjectConverter deduce(BaseDomainObject<?> domainObject, Set<Service> services) {
    Set<ValueObjectFromDto> valueObjectFromDtos = new LinkedHashSet<>();
    Set<FetchOneDtoFromDomainObject> fetchOneDtoFromDomainObjects = new LinkedHashSet<>();
    Set<FetchCollectionDtoFromDomainObject> fetchCollectionDtoFromDomainObjects =
        new LinkedHashSet<>();

    deduceValueObjectFromDto(valueObjectFromDtos, domainObject, services);
    deduceFetchOneDtoFromDomainObject(fetchOneDtoFromDomainObjects, domainObject, services);
    deduceFetchCollectionDtoFromDomainObject(
        fetchCollectionDtoFromDomainObjects, domainObject, services);

    return new DomainObjectConverter(
        domainObject,
        valueObjectFromDtos,
        fetchOneDtoFromDomainObjects,
        fetchCollectionDtoFromDomainObjects);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void deduceValueObjectFromDto(
      Set<ValueObjectFromDto> valueObjectFromDtos,
      BaseDomainObject<?> domainObject,
      Set<Service> services) {

    domainObject
        .getProperties()
        .forEach(
            property -> {
              if (property.getPropertyType().equals(PropertyType.VALUE_OBJECT)) {
                ValueObject valueObject = (ValueObject) property;

                services.forEach(
                    service -> {
                      service
                          .getDtos()
                          .forEach(
                              dto -> {
                                if (dto.getDataGroup().equals(valueObject.getData().asDto())) {
                                  valueObjectFromDtos.add(new ValueObjectFromDto(valueObject, dto));
                                }
                              });
                    });
              }
            });
  }

  private void deduceFetchOneDtoFromDomainObject(
      Set<FetchOneDtoFromDomainObject> fetchOneDtoFromDomainObjects,
      BaseDomainObject<?> domainObject,
      Set<Service> services) {

    services.forEach(
        service -> {
          service
              .getServiceMethods()
              .stream()
              .filter(method -> method.getFunction().getGoal().isFetchOne())
              .forEach(
                  method -> {
                    fetchOneDtoFromDomainObjects.add(
                        new FetchOneDtoFromDomainObject(
                            findDtoInServiceFromDataGroup(
                                service,
                                method.getFunction().getReturnValue().getData().getAsDataGroup()),
                            domainObject));
                  });
        });
  }

  private void deduceFetchCollectionDtoFromDomainObject(
      Set<FetchCollectionDtoFromDomainObject> fetchCollectionDtoFromDomainObjects,
      BaseDomainObject<?> domainObject,
      Set<Service> services) {

    services.forEach(
        service -> {
          service
              .getServiceMethods()
              .stream()
              .filter(
                  method ->
                      method.getFunction().getGoal().isFetchCollection()
                          || method.getFunction().getGoal().isFetchPagedCollection())
              .forEach(
                  method -> {
                    fetchCollectionDtoFromDomainObjects.add(
                        new FetchCollectionDtoFromDomainObject(
                            findDtoInServiceFromDataGroup(
                                service,
                                method
                                    .getResponseDto()
                                    .getArrayElementAsOptional()
                                    .orElseThrow(IllegalArgumentException::new)),
                            domainObject));
                  });
        });
  }

  private Dto findDtoInServiceFromDataGroup(Service service, Data dataGroup) {
    return service
        .getDtos()
        .stream()
        .filter(dto -> dto.getDataGroup().equals(dataGroup))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    String.format("Cannot find %s in Service DTOs", dataGroup.getDataType())));
  }
}
