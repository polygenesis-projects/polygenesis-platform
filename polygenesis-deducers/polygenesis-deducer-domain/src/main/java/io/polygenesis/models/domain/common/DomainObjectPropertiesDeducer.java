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

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Domain object properties deducer.
 *
 * @author Christos Tsakostas
 */
public class DomainObjectPropertiesDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  /** The Data to domain object property converter. */
  protected final DataToDomainObjectPropertyConverter dataToDomainObjectPropertyConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain object properties deducer.
   *
   * @param dataToDomainObjectPropertyConverter the data to domain object property converter
   */
  public DomainObjectPropertiesDeducer(
      DataToDomainObjectPropertyConverter dataToDomainObjectPropertyConverter) {
    this.dataToDomainObjectPropertyConverter = dataToDomainObjectPropertyConverter;
  }

  // ===============================================================================================
  // PUBLIC
  // ===============================================================================================

  /**
   * Deduce root domain object properties from thing set.
   *
   * @param superClass the super class
   * @param identityProperties the identity properties
   * @param thing the thing
   * @return the set
   */
  public Set<DomainObjectProperty<?>> deduceRootDomainObjectPropertiesFromThing(
      DomainObject domainObject,
      DomainObject superClass,
      Set<DomainObjectProperty<?>> identityProperties,
      Thing thing) {
    Assertion.isNotNull(thing.getThingIdentity(), "thing.getThingIdentity() is required");

    Set<DomainObjectProperty<?>> properties = new LinkedHashSet<>();

    properties.addAll(identityProperties);

    properties.addAll(
        thing
            .getThingProperties()
            .findDataExcludingIdentitiesAndPaging()
            .stream()
            .map(data -> dataToDomainObjectPropertyConverter.convert(domainObject, data))
            .filter(property -> !checkIfPropertyIsDefinedInSuperClass(property, superClass))
            .collect(Collectors.toCollection(LinkedHashSet::new)));

    return properties;
  }

  /**
   * Deduce domain object properties from thing set.
   *
   * @param thingChild the thing child
   * @param identityProperties the identity properties
   * @return the set
   */
  public Set<DomainObjectProperty<?>> deduceEntityDomainObjectPropertiesFromThing(
      DomainObject domainObject,
      Thing thingChild,
      Set<DomainObjectProperty<?>> identityProperties) {
    Assertion.isNotNull(thingChild.getThingIdentity(), "thingChild.getThingIdentity() is required");

    Set<DomainObjectProperty<?>> properties = new LinkedHashSet<>();

    properties.addAll(identityProperties);

    properties.addAll(
        thingChild
            .getThingProperties()
            .findDataExcludingIdentitiesAndPaging()
            .stream()
            .map(data -> dataToDomainObjectPropertyConverter.convert(domainObject, data))
            .collect(Collectors.toCollection(LinkedHashSet::new)));

    return properties;
  }

  /**
   * Deduce domain object properties from thing set.
   *
   * @param identityProperties the identity properties
   * @param thing the thing
   * @return the set
   */
  public Set<DomainObjectProperty<?>> deduceDomainObjectPropertiesFromThing(
      DomainObject domainObject, Set<DomainObjectProperty<?>> identityProperties, Thing thing) {
    Assertion.isNotNull(thing.getThingIdentity(), "thing.getThingIdentity() is required");

    Set<DomainObjectProperty<?>> properties = new LinkedHashSet<>();

    properties.addAll(identityProperties);

    properties.addAll(
        thing
            .getThingProperties()
            .findDataExcludingIdentitiesAndPaging()
            .stream()
            .map(data -> dataToDomainObjectPropertyConverter.convert(domainObject, data))
            .collect(Collectors.toCollection(LinkedHashSet::new)));

    return properties;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private boolean checkIfPropertyIsDefinedInSuperClass(
      DomainObjectProperty<?> property, DomainObject superClass) {
    if (superClass == null) {
      return false;
    }

    return superClass
        .getProperties()
        .stream()
        .anyMatch(
            propertyInSuperclass -> propertyInSuperclass.getData().equals(property.getData()));
  }
}
