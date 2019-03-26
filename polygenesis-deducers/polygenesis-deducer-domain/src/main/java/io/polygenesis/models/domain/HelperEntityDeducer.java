/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.core.ThingScopeType;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Helper entity deducer.
 *
 * @author Christos Tsakostas
 */
public class HelperEntityDeducer implements Deducer<HelperEntityModelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final DomainObjectConstructorDeducer domainObjectConstructorDeducer;
  private final HelperEntityPropertyDeducer helperEntityPropertyDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Helper entity deducer.
   *
   * @param domainObjectConstructorDeducer the domain object constructor deducer
   * @param helperEntityPropertyDeducer the helper entity property deducer
   */
  public HelperEntityDeducer(
      DomainObjectConstructorDeducer domainObjectConstructorDeducer,
      HelperEntityPropertyDeducer helperEntityPropertyDeducer) {
    this.domainObjectConstructorDeducer = domainObjectConstructorDeducer;
    this.helperEntityPropertyDeducer = helperEntityPropertyDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  @Override
  public HelperEntityModelRepository deduce(
      ThingRepository thingRepository, Set<ModelRepository> modelRepositories) {
    // TODO
    return new HelperEntityModelRepository(new LinkedHashSet<>());
  }

  /**
   * Deduce from set.
   *
   * @param thingRepository the thing repository
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<HelperEntity> deduceFrom(
      ThingRepository thingRepository, PackageName rootPackageName) {
    Set<HelperEntity> helperEntities = new LinkedHashSet<>();

    thingRepository
        .getDomainModelThings()
        .stream()
        .filter(thing -> thing.getThingScopeType().equals(ThingScopeType.DOMAIN_HELPER_ENTITY))
        .forEach(
            thing -> {
              if (!thing.getOptionalParent().isPresent() || thingRepository.isVirtualChild(thing)) {
                makeHelperEntity(helperEntities, thing, rootPackageName);
              }
            });

    return helperEntities;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make helper entity.
   *
   * @param helperEntities the helper entities
   * @param thing the thing
   * @param rootPackageName the root package name
   */
  protected void makeHelperEntity(
      Set<HelperEntity> helperEntities, Thing thing, PackageName rootPackageName) {
    helperEntities.add(
        new HelperEntity(
            new ObjectName(thing.getThingName().getText()),
            new PackageName(String.format("%s.%s", rootPackageName.getText(), "helper")),
            helperEntityPropertyDeducer.deduceFrom(thing, rootPackageName),
            domainObjectConstructorDeducer.deduceFrom(thing, rootPackageName)));
  }
}
