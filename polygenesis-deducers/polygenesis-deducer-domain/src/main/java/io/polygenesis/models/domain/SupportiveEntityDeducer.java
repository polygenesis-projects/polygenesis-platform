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

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.core.ThingType;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Supportive entity deducer.
 *
 * @author Christos Tsakostas
 */
public class SupportiveEntityDeducer implements Deducer<SupportiveEntityMetamodelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final DomainObjectConstructorDeducer domainObjectConstructorDeducer;
  private final SupportiveEntityPropertyDeducer supportiveEntityPropertyDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Supportive entity deducer.
   *
   * @param rootPackageName the root package name
   * @param domainObjectConstructorDeducer the domain object constructor deducer
   * @param supportiveEntityPropertyDeducer the helper entity property deducer
   */
  public SupportiveEntityDeducer(
      PackageName rootPackageName,
      DomainObjectConstructorDeducer domainObjectConstructorDeducer,
      SupportiveEntityPropertyDeducer supportiveEntityPropertyDeducer) {
    this.rootPackageName = rootPackageName;
    this.domainObjectConstructorDeducer = domainObjectConstructorDeducer;
    this.supportiveEntityPropertyDeducer = supportiveEntityPropertyDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public SupportiveEntityMetamodelRepository deduce(
      ThingRepository thingRepository, Set<MetamodelRepository> modelRepositories) {
    Set<SupportiveEntity> helperEntities = new LinkedHashSet<>();

    thingRepository
        .getDomainModelThings()
        .stream()
        .filter(thing -> thing.getThingType().equals(ThingType.DOMAIN_SUPPORTIVE_ENTITY))
        .forEach(thing -> makeSupportiveEntity(helperEntities, thing, rootPackageName));

    return new SupportiveEntityMetamodelRepository(helperEntities);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make supportive entity.
   *
   * @param helperEntities the helper entities
   * @param thing the thing
   * @param rootPackageName the root package name
   */
  protected void makeSupportiveEntity(
      Set<SupportiveEntity> helperEntities, Thing thing, PackageName rootPackageName) {
    helperEntities.add(
        new SupportiveEntity(
            new ObjectName(thing.getThingName().getText()),
            new PackageName(String.format("%s.%s", rootPackageName.getText(), "supportive")),
            supportiveEntityPropertyDeducer.deduceFrom(thing),
            domainObjectConstructorDeducer.deduceConstructorFromFunctionCreate(
                thing, rootPackageName)));
  }
}
