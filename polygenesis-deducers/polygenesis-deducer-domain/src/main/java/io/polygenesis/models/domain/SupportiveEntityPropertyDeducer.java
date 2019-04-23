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

package io.polygenesis.models.domain;

import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Thing;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Supportive entity property deducer.
 *
 * @author Christos Tsakostas
 */
public class SupportiveEntityPropertyDeducer extends BasePropertyDeducer {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from set.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the set
   */
  @SuppressWarnings("rawtypes")
  public Set<DomainObjectProperty> deduceFrom(Thing thing, PackageName rootPackageName) {
    Set<DomainObjectProperty> properties = new LinkedHashSet<>();

    ensureThingProperties(thing);

    properties.addAll(
        thing
            .getThingProperties()
            .stream()
            .map(thingProperty -> thingPropertyToBaseProperty(thingProperty))
            .collect(Collectors.toCollection(LinkedHashSet::new)));

    assertThatThingIdentityExists(thing, properties);

    return properties;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  protected void assertThatThingIdentityExists(Thing thing, Set<DomainObjectProperty> properties) {
    Optional<DomainObjectProperty> optionalDomainObjectProperty =
        properties
            .stream()
            .filter(
                property -> property.getPropertyType().equals(PropertyType.SUPPORTIVE_ENTITY_ID))
            .findFirst();

    if (!optionalDomainObjectProperty.isPresent()) {
      throw new IllegalArgumentException(
          String.format(
              "No Thing Identity found in supportive entity=%s", thing.getThingName().getText()));
    }
  }
}
