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

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import io.polygenesis.models.apiimpl.DomainObjectConverter;
import io.polygenesis.models.apiimpl.DomainObjectConverterMethod;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.DomainObjectMetamodelRepository;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The type Aggregate root converter deducer.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootConverterDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final ValueObjectDtoDeducer valueObjectDtoDeducer;
  private final CollectionRecordDeducer collectionRecordDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root converter deducer.
   *
   * @param valueObjectDtoDeducer the value object dto deducer
   * @param collectionRecordDeducer the collection record deducer
   */
  public AggregateRootConverterDeducer(
      ValueObjectDtoDeducer valueObjectDtoDeducer,
      CollectionRecordDeducer collectionRecordDeducer) {
    this.valueObjectDtoDeducer = valueObjectDtoDeducer;
    this.collectionRecordDeducer = collectionRecordDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce set.
   *
   * @param thingRepository the thing repository
   * @param serviceModelRepository the service model repository
   * @param domainObjectMetamodelRepository the domain metamodel repository
   * @return the set
   */
  public Set<DomainObjectConverter> deduce(
      ThingRepository thingRepository,
      ServiceMetamodelRepository serviceModelRepository,
      DomainObjectMetamodelRepository domainObjectMetamodelRepository) {
    Set<DomainObjectConverter> domainObjectConverters = new LinkedHashSet<>();

    Set<Thing> things = findThingsInApiAndDomainAggregateRootScopes(thingRepository);
    Set<DomainObject> aggregateRoots = domainObjectMetamodelRepository.findByThings(things);

    fillConverters(
        domainObjectConverters,
        serviceModelRepository,
        domainObjectMetamodelRepository,
        aggregateRoots);

    return domainObjectConverters;
  }

  // ===============================================================================================
  // PRIVATE - FILL
  // ===============================================================================================

  private void fillConverters(
      Set<DomainObjectConverter> domainObjectConverters,
      ServiceMetamodelRepository serviceModelRepository,
      DomainObjectMetamodelRepository domainObjectMetamodelRepository,
      Set<DomainObject> aggregateRoots) {
    aggregateRoots.forEach(
        aggregateRoot -> {
          Optional<DomainObjectConverter> optionalDomainObjectConverter =
              deduceConverterForAggregateRoot(
                  serviceModelRepository, domainObjectMetamodelRepository, aggregateRoot);
          if (optionalDomainObjectConverter.isPresent()) {
            domainObjectConverters.add(optionalDomainObjectConverter.get());
          }
        });
  }

  // ===============================================================================================
  // PRIVATE - DEDUCE
  // ===============================================================================================

  private Optional<DomainObjectConverter> deduceConverterForAggregateRoot(
      ServiceMetamodelRepository serviceModelRepository,
      DomainObjectMetamodelRepository domainObjectMetamodelRepository,
      DomainObject aggregateRoot) {
    Set<DomainObjectConverterMethod> methods = new LinkedHashSet<>();

    // ValueObjectDto
    methods.addAll(
        valueObjectDtoDeducer.deduceMethods(
            serviceModelRepository,
            domainObjectMetamodelRepository,
            aggregateRoot,
            domainObjectMetamodelRepository.getAllValueObjectsByDomainObject(aggregateRoot)));

    // CollectionRecords conversion methods
    methods.addAll(
        collectionRecordDeducer.deduceMethods(
            serviceModelRepository, domainObjectMetamodelRepository, aggregateRoot));

    if (methods.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(new DomainObjectConverter(aggregateRoot, methods));
    }
  }

  // ===============================================================================================
  // PRIVATE - HELPERS
  // ===============================================================================================

  private Set<Thing> findThingsInApiAndDomainAggregateRootScopes(ThingRepository thingRepository) {
    return thingRepository.getAbstractionItemsByScopes(
        new LinkedHashSet<>(
            Arrays.asList(AbstractionScope.api(), AbstractionScope.domainAggregateRoot())));
  }
}
