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

package io.polygenesis.models.domain.common;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.Constructor;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.PropertyType;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Constructors deducer.
 *
 * @author Christos Tsakostas
 */
public class ConstructorsDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final DataToDomainObjectPropertyConverter dataToDomainObjectPropertyConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Constructors deducer.
   *
   * @param dataToDomainObjectPropertyConverter the data to domain object property converter
   */
  public ConstructorsDeducer(
      DataToDomainObjectPropertyConverter dataToDomainObjectPropertyConverter) {
    this.dataToDomainObjectPropertyConverter = dataToDomainObjectPropertyConverter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce constructors set.
   *
   * @param superClass the super class
   * @param identityProperties the identity properties
   * @param thing the thing
   * @return the set
   */
  public Set<Constructor> deduceConstructors(
      PackageName rootPackageName,
      DomainObject domainObject,
      DomainObject superClass,
      Set<DomainObjectProperty<?>> identityProperties,
      Thing thing) {
    Set<Constructor> constructors = new LinkedHashSet<>();

    thing
        .getFunctions()
        .stream()
        .filter(
            function ->
                function.getPurpose().isCreate() || function.getPurpose().isEnsureExistence())
        .forEach(
            function -> {
              Set<Data> thingProperties =
                  function.getArguments().findDataExcludingIdentitiesAndPaging();
              Set<DomainObjectProperty<?>> properties = new LinkedHashSet<>();

              properties.addAll(identityProperties);
              properties.addAll(
                  dataToDomainObjectPropertyConverter.convertMany(domainObject, thingProperties));

              // Inherit properties from superclass
              if (superClass != null && !superClass.getConstructors().isEmpty()) {
                superClass
                    .getConstructors()
                    .stream()
                    .forEach(
                        superClassConstructor -> {
                          // Super class
                          Set<DomainObjectProperty<?>> superClassProperties =
                              superClassConstructor
                                  .getProperties()
                                  .stream()
                                  .filter(
                                      property ->
                                          !property
                                                  .getPropertyType()
                                                  .equals(PropertyType.AGGREGATE_ROOT_ID)
                                              && !property
                                                  .getPropertyType()
                                                  .equals(PropertyType.ABSTRACT_AGGREGATE_ROOT_ID)
                                              && !property
                                                  .getPropertyType()
                                                  .equals(PropertyType.AGGREGATE_ENTITY_ID)
                                              && !property
                                                  .getPropertyType()
                                                  .equals(PropertyType.TENANT_ID))
                                  .collect(Collectors.toCollection(LinkedHashSet::new));

                          // Add
                          constructors.add(
                              new Constructor(
                                  null,
                                  function,
                                  properties,
                                  superClassProperties,
                                  rootPackageName));
                        });
              } else {
                // Add
                constructors.add(new Constructor(null, function, properties, rootPackageName));
              }
            });

    return constructors;
  }
}
