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

package io.polygenesis.deducers.apiimpl;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataGroup;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.abstraction.thing.dsl.FunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import io.polygenesis.models.apiimpl.DomainEntityConverter;
import io.polygenesis.models.apiimpl.DomainEntityConverterMetamodelRepository;
import io.polygenesis.models.apiimpl.DomainEntityConverterMethod;
import io.polygenesis.models.domain.BaseDomainEntity;
import io.polygenesis.models.domain.DomainMetamodelRepository;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.models.domain.ValueObject;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The type Domain entity converter deducer.
 *
 * @author Christos Tsakostas
 */
public class DomainEntityConverterDeducer extends BaseApiImplementationDeducer
    implements Deducer<DomainEntityConverterMetamodelRepository> {

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public DomainEntityConverterMetamodelRepository deduce(
      Set<AbstractionRepository> abstractionRepositories,
      Set<MetamodelRepository> modelRepositories) {
    Set<DomainEntityConverter> domainEntityConverters = new LinkedHashSet<>();

    ServiceMetamodelRepository serviceModelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(modelRepositories, ServiceMetamodelRepository.class);

    DomainMetamodelRepository domainModelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(modelRepositories, DomainMetamodelRepository.class);

    fillDomainEntityConverters(
        domainEntityConverters,
        CoreRegistry.getAbstractionRepositoryResolver()
            .resolve(abstractionRepositories, ThingRepository.class),
        serviceModelRepository,
        domainModelRepository);

    return new DomainEntityConverterMetamodelRepository(domainEntityConverters);
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
      ServiceMetamodelRepository serviceModelRepository,
      DomainMetamodelRepository domainModelRepository) {
    thingRepository
        .getAbstractionItemsByScope(AbstractionScope.api())
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
      BaseDomainEntity domainEntity, Set<Service> services) {
    Set<DomainEntityConverterMethod> methods = new LinkedHashSet<>();

    deduceValueObjectFromDto(methods, domainEntity, services);
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
      BaseDomainEntity domainObject,
      Set<Service> services) {

    domainObject
        .getProperties()
        .forEach(
            property -> {
              if (property.getPropertyType().equals(PropertyType.VALUE_OBJECT)) {
                ValueObject valueObject = (ValueObject) property;

                services.forEach(
                    service ->
                        service
                            .getDtos()
                            .forEach(
                                dto -> {
                                  if (dto.getDataGroup().equals(valueObject.getData().asDto())) {
                                    makeValueObjectConversion(methods, dto, valueObject);
                                  }
                                }));
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
    Thing thing = ThingBuilder.endToEnd().setThingName("Converter").createThing();

    Dto dtoToUse = dto.withVariableNameEqualToObjectName();
    ValueObject valueObjectToUse = valueObject.withVariableNameEqualToObjectName();

    Function functionToVo =
        FunctionBuilder.of(thing, "convertToVo", Purpose.convertDtoToVo())
            .setReturnValue(valueObjectToUse.getData())
            .addArgument(dtoToUse.getDataGroup())
            .build();

    methods.add(new DomainEntityConverterMethod(functionToVo, dtoToUse, valueObjectToUse));

    Function functionToDto =
        FunctionBuilder.of(thing, "convertToDto", Purpose.convertVoToDto())
            .setReturnValue(dto.getDataGroup())
            .addArgument(valueObjectToUse.getData())
            .build();

    methods.add(new DomainEntityConverterMethod(functionToDto, valueObjectToUse, dto));
  }

  /**
   * Deduce fetch collection dto from domain object.
   *
   * @param methods the methods
   * @param domainObject the domain object
   * @param services the services
   */
  private void deduceFetchCollectionDtoFromDomainObject(
      Set<DomainEntityConverterMethod> methods,
      BaseDomainEntity domainObject,
      Set<Service> services) {

    services.forEach(
        service ->
            service
                .getServiceMethods()
                .stream()
                .filter(
                    method ->
                        method.getFunction().getPurpose().isFetchCollection()
                            || method.getFunction().getPurpose().isFetchPagedCollection())
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
                    }));
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
      BaseDomainEntity domainObject) {

    Thing thing = ThingBuilder.endToEnd().setThingName("Converter").createThing();

    Function function =
        FunctionBuilder.of(
                thing,
                String.format(
                    "convertTo%s",
                    TextConverter.toUpperCamel(dtoCollectionRecord.getDataGroup().getDataType())),
                Purpose.convertDomainObjectToCollectionRecord())
            .setReturnValue(dtoCollectionRecord.getDataGroup())
            .addArgument(transformDomainObjectToDataGroup(domainObject))
            .build();

    methods.add(new DomainEntityConverterMethod(function, domainObject, dtoCollectionRecord));
  }

  /**
   * Transform domain object to data group data group.
   *
   * @param domainObject the domain object
   * @return the data group
   */
  private DataGroup transformDomainObjectToDataGroup(BaseDomainEntity domainObject) {

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
