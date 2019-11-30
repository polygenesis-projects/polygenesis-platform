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
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import io.polygenesis.models.apiimpl.DomainEntityConverterMethod;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.DomainMetamodelRepository;
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
   * @param aggregateRoot the aggregate root
   * @param valueObjects the value objects
   * @return the set
   */
  public Set<DomainEntityConverterMethod> deduceMethods(
      ServiceMetamodelRepository serviceModelRepository,
      DomainMetamodelRepository<?> domainMetamodelRepository,
      AggregateRoot aggregateRoot,
      Set<ValueObject> valueObjects) {
    // Prepare DomainEntityConverterMethods
    Set<DomainEntityConverterMethod> methods = new LinkedHashSet<>();

    // Find ValueObject-Dto Pairs
    Set<ValueObjectDtoPair> valueObjectDtoPairs =
        findValueObjectDtoPairs(serviceModelRepository, aggregateRoot, valueObjects);

    // Make and add conversion methods
    valueObjectDtoPairs.forEach(
        valueObjectPair -> methods.addAll(makeValueObjectConversionMethods(valueObjectPair)));

    // CollectionRecords conversion methods
    CollectionRecordDeducer collectionRecordDeducer = new CollectionRecordDeducer();

    methods.addAll(
        collectionRecordDeducer.deduceMethods(
            serviceModelRepository, domainMetamodelRepository, aggregateRoot));

    return methods;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<ValueObjectDtoPair> findValueObjectDtoPairs(
      ServiceMetamodelRepository serviceModelRepository,
      AggregateRoot aggregateRoot,
      Set<ValueObject> valueObjects) {
    Set<ValueObjectDtoPair> valueObjectDtoPairs = new LinkedHashSet<>();

    Set<Service> services =
        serviceModelRepository.getServicesBy(
            new ThingName(aggregateRoot.getObjectName().getText()));

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

  private Set<DomainEntityConverterMethod> makeValueObjectConversionMethods(
      ValueObjectDtoPair pair) {
    Set<DomainEntityConverterMethod> methods = new LinkedHashSet<>();

    Thing thing = ThingBuilder.endToEnd("Converter").createThing();

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

    methods.add(new DomainEntityConverterMethod(functionToVo, dtoToUse, valueObjectToUse));

    // TO DTO
    Optional<DomainEntityConverterMethod> optionalDomainEntityConverterMethod =
        methods
            .stream()
            .filter(
                domainEntityConverterMethod ->
                    domainEntityConverterMethod
                        .getFunction()
                        .getName()
                        .getText()
                        .equals(
                            String.format(
                                "convertTo%s",
                                TextConverter.toUpperCamel(
                                    pair.getDto().getObjectName().getText()))))
            .findFirst();

    if (!optionalDomainEntityConverterMethod.isPresent()) {
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

      methods.add(new DomainEntityConverterMethod(functionToDto, valueObjectToUse, pair.getDto()));
    }

    return methods;
  }
}
