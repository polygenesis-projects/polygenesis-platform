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

package io.polygenesis.models.domain.domainmessage;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.Constructor;
import io.polygenesis.models.domain.DomainEvent;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.models.domain.PropertyType;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Domain event constructor deducer.
 *
 * @author Christos Tsakostas
 */
public class DomainEventConstructorDeducer {

  /**
   * Deduce from domain event.
   *
   * @param rootPackageName the root package name
   * @param thing the thing
   * @param constructor the constructor
   * @return the domain event
   */
  @SuppressWarnings("CPD-START")
  public DomainEvent deduceFrom(PackageName rootPackageName, Thing thing, Constructor constructor) {
    Set<DomainObjectProperty<?>> properties = constructor.getProperties();

    DomainEvent domainEvent =
        new DomainEvent(
            InstantiationType.CONCRETE,
            new ObjectName(
                String.format(
                    "%sCreated", TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageName, thing),
            properties,
            thing.getMultiTenant());

    Set<Constructor> constructors =
        new LinkedHashSet<>(
            Collections.singletonList(makeDomainEventConstructor(constructor, rootPackageName)));

    domainEvent.addConstructors(constructors);

    return domainEvent;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Constructor makeDomainEventConstructor(
      Constructor constructor, PackageName rootPackageName) {
    Set<DomainObjectProperty<?>> properties = new LinkedHashSet<>();

    constructor
        .getProperties()
        .forEach(
            property -> {
              if (!property.getPropertyType().equals(PropertyType.AGGREGATE_ROOT_ID)
                  && !property.getPropertyType().equals(PropertyType.TENANT_ID)) {
                properties.add(property);
              }
            });

    return new Constructor(null, constructor.getFunction(), properties, rootPackageName);
  }
}
