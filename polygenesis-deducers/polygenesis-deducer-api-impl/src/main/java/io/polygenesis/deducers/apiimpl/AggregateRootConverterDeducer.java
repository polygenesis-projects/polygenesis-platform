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
import io.polygenesis.models.apiimpl.DomainEntityConverter;
import io.polygenesis.models.apiimpl.DomainEntityConverterMethod;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.AggregateRootMetamodelRepository;
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
   * @param aggregateRootMetamodelRepository the domain metamodel repository
   * @return the set
   */
  public Set<DomainEntityConverter> deduce(
      ThingRepository thingRepository,
      ServiceMetamodelRepository serviceModelRepository,
      AggregateRootMetamodelRepository aggregateRootMetamodelRepository) {
    Set<DomainEntityConverter> domainEntityConverters = new LinkedHashSet<>();

    Set<Thing> things = findThingsInApiAndDomainAggregateRootScopes(thingRepository);
    Set<AggregateRoot> aggregateRoots = aggregateRootMetamodelRepository.findByThings(things);

    fillConverters(
        domainEntityConverters,
        serviceModelRepository,
        aggregateRootMetamodelRepository,
        aggregateRoots);

    return domainEntityConverters;
  }

  // ===============================================================================================
  // PRIVATE - FILL
  // ===============================================================================================

  private void fillConverters(
      Set<DomainEntityConverter> domainEntityConverters,
      ServiceMetamodelRepository serviceModelRepository,
      AggregateRootMetamodelRepository aggregateRootMetamodelRepository,
      Set<AggregateRoot> aggregateRoots) {
    aggregateRoots.forEach(
        aggregateRoot -> {
          Optional<DomainEntityConverter> optionalDomainEntityConverter =
              deduceConverterForAggregateRoot(
                  serviceModelRepository, aggregateRootMetamodelRepository, aggregateRoot);
          if (optionalDomainEntityConverter.isPresent()) {
            domainEntityConverters.add(optionalDomainEntityConverter.get());
          }
        });
  }

  // ===============================================================================================
  // PRIVATE - DEDUCE
  // ===============================================================================================

  private Optional<DomainEntityConverter> deduceConverterForAggregateRoot(
      ServiceMetamodelRepository serviceModelRepository,
      AggregateRootMetamodelRepository aggregateRootMetamodelRepository,
      AggregateRoot aggregateRoot) {
    Set<DomainEntityConverterMethod> methods = new LinkedHashSet<>();

    // ValueObjectDto
    methods.addAll(
        valueObjectDtoDeducer.deduceMethods(
            serviceModelRepository,
            aggregateRootMetamodelRepository,
            aggregateRoot,
            aggregateRootMetamodelRepository.getAllValueObjectsByAggregateRoot(aggregateRoot)));

    // CollectionRecords conversion methods
    methods.addAll(
        collectionRecordDeducer.deduceMethods(
            serviceModelRepository, aggregateRootMetamodelRepository, aggregateRoot));

    if (methods.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(new DomainEntityConverter(aggregateRoot, methods));
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
