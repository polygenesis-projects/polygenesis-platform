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

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingName;
import io.polygenesis.abstraction.thing.dsl.FunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import io.polygenesis.models.apiimpl.DomainObjectConverterMethod;
import io.polygenesis.models.domain.DomainMetamodelRepository;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.ValueObject;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The type Value object dto deducer.
 *
 * @author Christos Tsakostas
 */
public class ValueObjectDtoDeducer {

  /**
   * Deduce methods set.
   *
   * @param serviceModelRepository the service model repository
   * @param domainMetamodelRepository the domain metamodel repository
   * @param domainObject the aggregate root
   * @param valueObjects the value objects
   * @return the set
   */
  public Set<DomainObjectConverterMethod> deduceMethods(
      ServiceMetamodelRepository serviceModelRepository,
      DomainMetamodelRepository<?> domainMetamodelRepository,
      DomainObject domainObject,
      Set<ValueObject> valueObjects) {
    // Prepare DomainObjectConverterMethods
    Set<DomainObjectConverterMethod> methods = new LinkedHashSet<>();

    // Find ValueObject-Dto Pairs
    Set<ValueObjectDtoPair> valueObjectDtoPairs =
        findValueObjectDtoPairs(serviceModelRepository, domainObject, valueObjects);

    // Make and add conversion methods
    valueObjectDtoPairs.forEach(
        valueObjectPair -> methods.addAll(makeValueObjectConversionMethods(valueObjectPair)));

    // CollectionRecords conversion methods
    CollectionRecordDeducer collectionRecordDeducer = new CollectionRecordDeducer();

    methods.addAll(
        collectionRecordDeducer.deduceMethods(
            serviceModelRepository, domainMetamodelRepository, domainObject));

    return methods;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<ValueObjectDtoPair> findValueObjectDtoPairs(
      ServiceMetamodelRepository serviceModelRepository,
      DomainObject domainObject,
      Set<ValueObject> valueObjects) {
    Set<ValueObjectDtoPair> valueObjectDtoPairs = new LinkedHashSet<>();

    Set<Service> services =
        serviceModelRepository.getServicesBy(new ThingName(domainObject.getObjectName().getText()));

    valueObjects.forEach(
        valueObject -> {
          services
              .stream()
              .flatMap(service -> service.getDtos().stream())
              .forEach(
                  dto -> {
                    if (dto.getDataObject().equals(valueObject.getData().asDto())) {
                      valueObjectDtoPairs.add(new ValueObjectDtoPair(valueObject, dto));
                    }
                  });
        });

    return valueObjectDtoPairs;
  }

  private Set<DomainObjectConverterMethod> makeValueObjectConversionMethods(
      ValueObjectDtoPair pair) {
    Set<DomainObjectConverterMethod> methods = new LinkedHashSet<>();

    Thing thing = ThingBuilder.endToEnd("Converter").createThing(PackageName.any());

    Dto dtoToUse = pair.getDto().withVariableNameEqualToObjectName();
    ValueObject valueObjectToUse = pair.getValueObject().withVariableNameEqualToObjectName();

    // TO VALUE OBJECT
    Function functionToVo =
        FunctionBuilder.of(
                thing,
                String.format(
                    "convertTo%sVo",
                    TextConverter.toUpperCamel(valueObjectToUse.getObjectName().getText())),
                Purpose.convertDtoToVo())
            .setReturnValue(valueObjectToUse.getData())
            .addArgument(dtoToUse.getDataObject())
            .build();

    methods.add(new DomainObjectConverterMethod(functionToVo, dtoToUse, valueObjectToUse));

    // TO DTO
    Optional<DomainObjectConverterMethod> optionalDomainObjectConverterMethod =
        methods
            .stream()
            .filter(
                domainObjectConverterMethod ->
                    domainObjectConverterMethod
                        .getFunction()
                        .getName()
                        .getText()
                        .equals(
                            String.format(
                                "convertTo%s",
                                TextConverter.toUpperCamel(
                                    pair.getDto().getObjectName().getText()))))
            .findFirst();

    if (!optionalDomainObjectConverterMethod.isPresent()) {
      Function functionToDto =
          FunctionBuilder.of(
                  thing,
                  String.format(
                      "convertTo%s",
                      TextConverter.toUpperCamel(pair.getDto().getObjectName().getText())),
                  Purpose.convertVoToDto())
              .setReturnValue(pair.getDto().getDataObject())
              .addArgument(valueObjectToUse.getData())
              .build();

      methods.add(new DomainObjectConverterMethod(functionToDto, valueObjectToUse, pair.getDto()));
    }

    return methods;
  }
}
