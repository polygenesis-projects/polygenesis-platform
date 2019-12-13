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
import io.polygenesis.models.api.DtoType;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import io.polygenesis.models.apiimpl.DomainObjectConverterMethod;
import io.polygenesis.models.domain.DomainMetamodelRepository;
import io.polygenesis.models.domain.DomainObject;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Collection record deducer.
 *
 * @author Christos Tsakostas
 */
public class CollectionRecordDeducer {

  /**
   * Deduce methods set.
   *
   * @param serviceModelRepository the service model repository
   * @param domainMetamodelRepository the domain metamodel repository
   * @param domainObject the aggregate root
   * @return the set
   */
  public Set<DomainObjectConverterMethod> deduceMethods(
      ServiceMetamodelRepository serviceModelRepository,
      DomainMetamodelRepository<?> domainMetamodelRepository,
      DomainObject domainObject) {
    Set<DomainObjectConverterMethod> methods = new LinkedHashSet<>();

    Set<CollectionRecordEntityPair> collectionRecordEntityPairs =
        findCollectionRecordEntityPairs(
            serviceModelRepository, domainMetamodelRepository, domainObject);

    collectionRecordEntityPairs.forEach(
        pair -> methods.add(makeCollectionRecordConversionMethod(pair)));

    return methods;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<CollectionRecordEntityPair> findCollectionRecordEntityPairs(
      ServiceMetamodelRepository serviceModelRepository,
      DomainMetamodelRepository<?> domainMetamodelRepository,
      DomainObject domainObject) {
    Set<CollectionRecordEntityPair> collectionRecordEntityPairs = new LinkedHashSet<>();

    Set<Service> services =
        serviceModelRepository.getServicesBy(new ThingName(domainObject.getObjectName().getText()));

    Set<Dto> dtos =
        services
            .stream()
            .flatMap(service -> service.getDtos().stream())
            .filter(dto -> dto.getDtoType().equals(DtoType.COLLECTION_RECORD))
            .collect(Collectors.toSet());

    dtos.forEach(
        dto -> {
          DomainObject entity =
              domainMetamodelRepository.findEntityByThingName(dto.getRelatedThing().getThingName());

          collectionRecordEntityPairs.add(new CollectionRecordEntityPair(dto, entity));
        });

    return collectionRecordEntityPairs;
  }

  private DomainObjectConverterMethod makeCollectionRecordConversionMethod(
      CollectionRecordEntityPair pair) {

    Thing thing = ThingBuilder.endToEnd("Converter").createThing(PackageName.any());

    Function function =
        FunctionBuilder.of(
                thing,
                String.format(
                    "convertTo%s",
                    TextConverter.toUpperCamel(pair.getDto().getDataObject().getDataType())),
                Purpose.convertDomainObjectToCollectionRecord())
            .setReturnValue(pair.getDto().getDataObject())
            .addArgument(pair.getEntity().asDataGroup())
            .build();

    return new DomainObjectConverterMethod(function, pair.getEntity(), pair.getDto());
  }
}
