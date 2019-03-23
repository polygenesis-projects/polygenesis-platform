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
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Argument;
import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.ReturnValue;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingBuilder;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.apiimpl.DomainObjectConverter;
import io.polygenesis.models.apiimpl.DomainObjectConverterMethod;
import io.polygenesis.models.domain.BaseDomainObject;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.models.domain.ValueObject;
import java.util.Arrays;
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
    Set<DomainObjectConverterMethod> methods = new LinkedHashSet<>();

    deduceValueObjectFromDto(methods, domainObject, services);
    // TODO
    // deduceFetchOneDtoFromDomainObject(domainObject, services);
    deduceFetchCollectionDtoFromDomainObject(methods, domainObject, services);

    return new DomainObjectConverter(domainObject, methods);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void deduceValueObjectFromDto(
      Set<DomainObjectConverterMethod> methods,
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
                                  makeValueObjectConversion(methods, dto, valueObject);
                                }
                              });
                    });
              }
            });
  }

  private void makeValueObjectConversion(
      Set<DomainObjectConverterMethod> methods, Dto dto, ValueObject valueObject) {
    Thing thing = ThingBuilder.generic().setThingName(new ThingName("Converter")).createThing();

    Function functionToVo =
        new Function(
            thing,
            new Goal("CONVERT_DTO_TO_VO"),
            new FunctionName("convertToVo"),
            new LinkedHashSet<>(Arrays.asList(new Argument(dto.getDataGroup()))),
            new ReturnValue(valueObject.getData()));

    methods.add(new DomainObjectConverterMethod(functionToVo, dto, valueObject));

    Function functionToDto =
        new Function(
            thing,
            new Goal("CONVERT_VO_TO_DTO"),
            new FunctionName("convertToDto"),
            new LinkedHashSet<>(Arrays.asList(new Argument(valueObject.getData()))),
            new ReturnValue(dto.getDataGroup()));

    methods.add(new DomainObjectConverterMethod(functionToDto, valueObject, dto));
  }

  // TODO
  //  private void deduceFetchOneDtoFromDomainObject(
  //      Set<DomainObjectConverterMethod> methods,
  //      BaseDomainObject<?> domainObject,
  //      Set<Service> services) {
  //
  //    services.forEach(
  //        service -> {
  //          service
  //              .getServiceMethods()
  //              .stream()
  //              .filter(method -> method.getFunction().getGoal().isFetchOne())
  //              .forEach(
  //                  method -> {
  //                    // TODO
  //                  });
  //        });
  //  }

  private void deduceFetchCollectionDtoFromDomainObject(
      Set<DomainObjectConverterMethod> methods,
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
                    Dto dtoCollectionRecord =
                        findDtoInServiceFromDataGroup(
                            service,
                            method
                                .getResponseDto()
                                .getArrayElementAsOptional()
                                .orElseThrow(IllegalArgumentException::new));

                    makeCollectionRecord(methods, dtoCollectionRecord, domainObject);
                  });
        });
  }

  private void makeCollectionRecord(
      Set<DomainObjectConverterMethod> methods,
      Dto dtoCollectionRecord,
      BaseDomainObject<?> domainObject) {

    Thing thing = ThingBuilder.generic().setThingName(new ThingName("Converter")).createThing();

    Function function =
        new Function(
            thing,
            new Goal("CONVERT_DOMAIN_OBJECT_TO_COLLECTION_RECORD"),
            new FunctionName(
                String.format(
                    "convertTo%s",
                    TextConverter.toUpperCamel(dtoCollectionRecord.getDataGroup().getDataType()))),
            new LinkedHashSet<>(
                Arrays.asList(new Argument(transformDomainObjectToDataGroup(domainObject)))),
            new ReturnValue(dtoCollectionRecord.getDataGroup()));

    methods.add(new DomainObjectConverterMethod(function, domainObject, dtoCollectionRecord));
  }

  /**
   * Transform domain object to data group data group.
   *
   * @param domainObject the domain object
   * @return the data group
   */
  private DataGroup transformDomainObjectToDataGroup(BaseDomainObject<?> domainObject) {

    DataGroup dataGroup =
        new DataGroup(
            new ObjectName(domainObject.getObjectName().getText()), domainObject.getPackageName());

    domainObject.getProperties().forEach(property -> dataGroup.addData(property.getData()));

    return dataGroup;
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
