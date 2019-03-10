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

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.ObjectName;
import io.polygenesis.core.data.VariableName;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.apiimpl.AggregateRootConverter;
import io.polygenesis.models.apiimpl.FetchCollectionDtoFromAggregateRoot;
import io.polygenesis.models.apiimpl.FetchOneDtoFromAggregateRoot;
import io.polygenesis.models.apiimpl.ValueObjectFromDto;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.models.domain.ValueObject;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Aggregate root converter deducer.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootConverterDeducer {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce aggregate root converter.
   *
   * @param aggregateRoot the aggregate root
   * @param services the services
   * @return the aggregate root converter
   */
  public AggregateRootConverter deduce(AggregateRoot aggregateRoot, Set<Service> services) {
    Set<ValueObjectFromDto> valueObjectFromDtos = new LinkedHashSet<>();
    Set<FetchOneDtoFromAggregateRoot> fetchOneDtoFromAggregateRoots = new LinkedHashSet<>();
    Set<FetchCollectionDtoFromAggregateRoot> fetchCollectionDtoFromAggregateRoots =
        new LinkedHashSet<>();

    deduceValueObjectFromDto(valueObjectFromDtos, aggregateRoot, services);
    deduceFetchOneDtoFromAggregateRoot(fetchOneDtoFromAggregateRoots, aggregateRoot, services);
    deduceFetchCollectionDtoFromAggregateRoot(
        fetchCollectionDtoFromAggregateRoots, aggregateRoot, services);

    return new AggregateRootConverter(
        new ObjectName(TextConverter.toUpperCamel(aggregateRoot.getName().getText() + "Converter")),
        aggregateRoot.getPackageName(),
        new VariableName(
            TextConverter.toLowerCamel(aggregateRoot.getName().getText() + "Converter")),
        valueObjectFromDtos,
        fetchOneDtoFromAggregateRoots,
        fetchCollectionDtoFromAggregateRoots);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void deduceValueObjectFromDto(
      Set<ValueObjectFromDto> valueObjectFromDtos,
      AggregateRoot aggregateRoot,
      Set<Service> services) {

    aggregateRoot
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
                                if (dto.getOriginatingDataGroup()
                                    .equals(valueObject.getOriginatingIoModelGroup())) {
                                  valueObjectFromDtos.add(new ValueObjectFromDto(valueObject, dto));
                                }
                              });
                    });
              }
            });
  }

  private void deduceFetchOneDtoFromAggregateRoot(
      Set<FetchOneDtoFromAggregateRoot> fetchOneDtoFromAggregateRoots,
      AggregateRoot aggregateRoot,
      Set<Service> services) {

    services.forEach(
        service -> {
          service
              .getMethods()
              .stream()
              .filter(method -> method.getFunction().getGoal().isFetchOne())
              .forEach(
                  method -> {
                    fetchOneDtoFromAggregateRoots.add(
                        new FetchOneDtoFromAggregateRoot(
                            findDtoInServiceFromIoModelGroup(
                                service,
                                method.getFunction().getReturnValue().getModel().getAsDataGroup()),
                            aggregateRoot));
                  });
        });
  }

  private void deduceFetchCollectionDtoFromAggregateRoot(
      Set<FetchCollectionDtoFromAggregateRoot> fetchCollectionDtoFromAggregateRoots,
      AggregateRoot aggregateRoot,
      Set<Service> services) {

    services.forEach(
        service -> {
          service
              .getMethods()
              .stream()
              .filter(
                  method ->
                      method.getFunction().getGoal().isFetchCollection()
                          || method.getFunction().getGoal().isFetchPagedCollection())
              .forEach(
                  method -> {
                    fetchCollectionDtoFromAggregateRoots.add(
                        new FetchCollectionDtoFromAggregateRoot(
                            findDtoInServiceFromIoModelGroup(
                                service,
                                method
                                    .getResponseDto()
                                    .getArrayElementAsOptional()
                                    .orElseThrow(IllegalArgumentException::new)),
                            aggregateRoot));
                  });
        });
  }

  private Dto findDtoInServiceFromIoModelGroup(Service service, Data dataGroup) {
    return service
        .getDtos()
        .stream()
        .filter(dto -> dto.getOriginatingDataGroup().equals(dataGroup))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    String.format("Cannot find %s in Service DTOs", dataGroup.getDataType())));
  }
}
