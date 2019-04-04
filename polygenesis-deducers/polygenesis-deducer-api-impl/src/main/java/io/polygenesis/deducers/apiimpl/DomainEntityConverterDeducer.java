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
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.ReturnValue;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingBuilder;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceModelRepository;
import io.polygenesis.models.apiimpl.DomainEntityConverter;
import io.polygenesis.models.apiimpl.DomainEntityConverterMethod;
import io.polygenesis.models.apiimpl.DomainEntityConverterModelRepository;
import io.polygenesis.models.domain.BaseDomainEntity;
import io.polygenesis.models.domain.DomainModelRepository;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.models.domain.ValueObject;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The type Domain entity converter deducer.
 *
 * @author Christos Tsakostas
 */
public class DomainEntityConverterDeducer extends BaseApiImplementationDeducer
    implements Deducer<DomainEntityConverterModelRepository> {

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public DomainEntityConverterModelRepository deduce(
      ThingRepository thingRepository, Set<ModelRepository> modelRepositories) {
    Set<DomainEntityConverter> domainEntityConverters = new LinkedHashSet<>();

    ServiceModelRepository serviceModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, ServiceModelRepository.class);

    DomainModelRepository domainModelRepository =
        CoreRegistry.getModelRepositoryResolver()
            .resolve(modelRepositories, DomainModelRepository.class);

    fillDomainEntityConverters(
        domainEntityConverters, thingRepository, serviceModelRepository, domainModelRepository);

    return new DomainEntityConverterModelRepository(domainEntityConverters);
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Fill domain entity converters.
   *
   * @param domainEntityConverters the domain entity converters
   * @param thingRepository the thing repository
   * @param serviceModelRepository the service model repository
   * @param domainModelRepository the domain model repository
   */
  protected void fillDomainEntityConverters(
      Set<DomainEntityConverter> domainEntityConverters,
      ThingRepository thingRepository,
      ServiceModelRepository serviceModelRepository,
      DomainModelRepository domainModelRepository) {
    thingRepository
        .getApiThings()
        .forEach(
            thing -> {
              Optional<BaseDomainEntity> optionalDomainObject =
                  getOptionalDomainEntity(thing, domainModelRepository);

              if (optionalDomainObject.isPresent()) {
                domainEntityConverters.add(
                    deduceDomainEntityConverter(
                        optionalDomainObject.get(),
                        serviceModelRepository.getServicesBy(thing.getThingName())));
              }
            });
  }

  /**
   * Deduce domain entity converter domain entity converter.
   *
   * @param domainEntity the domain entity
   * @param services the services
   * @return the domain entity converter
   */
  public DomainEntityConverter deduceDomainEntityConverter(
      BaseDomainEntity<?> domainEntity, Set<Service> services) {
    Set<DomainEntityConverterMethod> methods = new LinkedHashSet<>();

    deduceValueObjectFromDto(methods, domainEntity, services);
    // TODO
    // deduceFetchOneDtoFromDomainObject(domainEntity, services);
    deduceFetchCollectionDtoFromDomainObject(methods, domainEntity, services);

    return new DomainEntityConverter(domainEntity, methods);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Deduce value object from dto.
   *
   * @param methods the methods
   * @param domainObject the domain object
   * @param services the services
   */
  private void deduceValueObjectFromDto(
      Set<DomainEntityConverterMethod> methods,
      BaseDomainEntity<?> domainObject,
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

  /**
   * Make value object conversion.
   *
   * @param methods the methods
   * @param dto the dto
   * @param valueObject the value object
   */
  private void makeValueObjectConversion(
      Set<DomainEntityConverterMethod> methods, Dto dto, ValueObject valueObject) {
    Thing thing = ThingBuilder.generic().setThingName(new ThingName("Converter")).createThing();

    Function functionToVo =
        new Function(
            thing,
            new Goal("CONVERT_DTO_TO_VO"),
            new FunctionName("convertToVo"),
            new LinkedHashSet<>(Arrays.asList(new Argument(dto.getDataGroup()))),
            new ReturnValue(valueObject.getData()));

    methods.add(new DomainEntityConverterMethod(functionToVo, dto, valueObject));

    Function functionToDto =
        new Function(
            thing,
            new Goal("CONVERT_VO_TO_DTO"),
            new FunctionName("convertToDto"),
            new LinkedHashSet<>(Arrays.asList(new Argument(valueObject.getData()))),
            new ReturnValue(dto.getDataGroup()));

    methods.add(new DomainEntityConverterMethod(functionToDto, valueObject, dto));
  }

  // TODO
  //  private void deduceFetchOneDtoFromDomainObject(
  //      Set<DomainEntityConverterMethod> methods,
  //      BaseDomainEntity<?> domainObject,
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

  /**
   * Deduce fetch collection dto from domain object.
   *
   * @param methods the methods
   * @param domainObject the domain object
   * @param services the services
   */
  private void deduceFetchCollectionDtoFromDomainObject(
      Set<DomainEntityConverterMethod> methods,
      BaseDomainEntity<?> domainObject,
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

  /**
   * Make collection record.
   *
   * @param methods the methods
   * @param dtoCollectionRecord the dto collection record
   * @param domainObject the domain object
   */
  private void makeCollectionRecord(
      Set<DomainEntityConverterMethod> methods,
      Dto dtoCollectionRecord,
      BaseDomainEntity<?> domainObject) {

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

    methods.add(new DomainEntityConverterMethod(function, domainObject, dtoCollectionRecord));
  }

  /**
   * Transform domain object to data group data group.
   *
   * @param domainObject the domain object
   * @return the data group
   */
  private DataGroup transformDomainObjectToDataGroup(BaseDomainEntity<?> domainObject) {

    DataGroup dataGroup =
        new DataGroup(
            new ObjectName(domainObject.getObjectName().getText()), domainObject.getPackageName());

    domainObject.getProperties().forEach(property -> dataGroup.addData(property.getData()));

    return dataGroup;
  }

  /**
   * Find dto in service from data group dto.
   *
   * @param service the service
   * @param dataGroup the data group
   * @return the dto
   */
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
