/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.Constructor;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import java.util.LinkedHashSet;
import java.util.Set;

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
   * @param rootPackageName the root package name
   * @param domainObject the domain object
   * @param superClass the super class
   * @param thing the thing
   * @return the set
   */
  public Set<Constructor> deduceConstructors(
      PackageName rootPackageName,
      DomainObject domainObject,
      DomainObject superClass,
      Thing thing) {
    Set<Constructor> constructors = new LinkedHashSet<>();

    thing.getFunctions().stream()
        .filter(
            function ->
                function.getPurpose().isCreate() || function.getPurpose().isEnsureExistence())
        .forEach(
            function -> {
              Set<DomainObjectProperty<?>> properties = new LinkedHashSet<>();

              properties.addAll(
                  dataToDomainObjectPropertyConverter.convertMany(
                      domainObject, function.getArguments().getData()));

              // Inherit properties from superclass
              if (superClass != null && !superClass.getConstructors().isEmpty()) {
                superClass.getConstructors().stream()
                    .forEach(
                        superClassConstructor -> {
                          // Super class Properties
                          Set<DomainObjectProperty<?>> superClassProperties =
                              superClassConstructor.getProperties();

                          // Add
                          constructors.add(
                              new Constructor(
                                  domainObject,
                                  function,
                                  properties,
                                  superClassProperties,
                                  rootPackageName));
                        });
              } else {
                // Add
                constructors.add(
                    new Constructor(domainObject, function, properties, rootPackageName));
              }
            });

    return constructors;
  }
}
