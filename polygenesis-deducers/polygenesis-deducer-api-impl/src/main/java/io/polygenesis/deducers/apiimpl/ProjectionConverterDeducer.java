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
import io.polygenesis.models.domain.DomainMetamodelRepository;
import io.polygenesis.models.domain.Projection;
import io.polygenesis.models.domain.ProjectionMetamodelRepository;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The type Projection converter deducer.
 *
 * @author Christos Tsakostas
 */
public class ProjectionConverterDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final ValueObjectDtoDeducer valueObjectDtoDeducer;
  private final CollectionRecordDeducer collectionRecordDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Projection converter deducer.
   *
   * @param valueObjectDtoDeducer the value object dto deducer
   * @param collectionRecordDeducer the collection record deducer
   */
  public ProjectionConverterDeducer(
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
   * @param domainMetamodelRepository the projection metamodel repository
   * @return the set
   */
  public Set<DomainEntityConverter> deduce(
      ThingRepository thingRepository,
      ServiceMetamodelRepository serviceModelRepository,
      ProjectionMetamodelRepository domainMetamodelRepository) {
    Set<DomainEntityConverter> domainEntityConverters = new LinkedHashSet<>();

    Set<Thing> things = findThingsInApiAndProjectionScopes(thingRepository);
    Set<Projection> projections = domainMetamodelRepository.findByThings(things);

    fillConverters(
        domainEntityConverters, serviceModelRepository, domainMetamodelRepository, projections);

    return domainEntityConverters;
  }

  // ===============================================================================================
  // PRIVATE - FILL
  // ===============================================================================================

  private void fillConverters(
      Set<DomainEntityConverter> domainEntityConverters,
      ServiceMetamodelRepository serviceModelRepository,
      DomainMetamodelRepository<?> domainMetamodelRepository,
      Set<Projection> projections) {
    projections.forEach(
        projection -> {
          Optional<DomainEntityConverter> optionalDomainEntityConverter =
              deduceConverterForProjection(
                  serviceModelRepository, domainMetamodelRepository, projection);
          if (optionalDomainEntityConverter.isPresent()) {
            domainEntityConverters.add(optionalDomainEntityConverter.get());
          }
        });
  }

  // ===============================================================================================
  // PRIVATE - DEDUCE
  // ===============================================================================================

  private Optional<DomainEntityConverter> deduceConverterForProjection(
      ServiceMetamodelRepository serviceModelRepository,
      DomainMetamodelRepository<?> domainMetamodelRepository,
      Projection projection) {
    Set<DomainEntityConverterMethod> methods = new LinkedHashSet<>();

    // ValueObjectDto
    methods.addAll(
        valueObjectDtoDeducer.deduceMethods(
            serviceModelRepository, domainMetamodelRepository, null, new LinkedHashSet<>()));

    // CollectionRecords conversion methods
    methods.addAll(
        collectionRecordDeducer.deduceMethods(
            serviceModelRepository, domainMetamodelRepository, null));

    if (methods.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(new DomainEntityConverter(projection, methods));
    }
  }

  // ===============================================================================================
  // PRIVATE - HELPERS
  // ===============================================================================================

  private Set<Thing> findThingsInApiAndProjectionScopes(ThingRepository thingRepository) {
    return thingRepository.getAbstractionItemsByScopes(
        new LinkedHashSet<>(Arrays.asList(AbstractionScope.api(), AbstractionScope.projection())));
  }
}
