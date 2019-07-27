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

package io.polygenesis.models.domain.supportiveentity;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.domain.SupportiveEntity;
import io.polygenesis.models.domain.SupportiveEntityMetamodelRepository;
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
  private final SupportiveEntityPropertyDeducer supportiveEntityPropertyDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Supportive entity deducer.
   *
   * @param rootPackageName the root package name
   * @param supportiveEntityPropertyDeducer the supportive entity property deducer
   */
  public SupportiveEntityDeducer(
      PackageName rootPackageName,
      SupportiveEntityPropertyDeducer supportiveEntityPropertyDeducer) {
    this.rootPackageName = rootPackageName;
    this.supportiveEntityPropertyDeducer = supportiveEntityPropertyDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  @Override
  public SupportiveEntityMetamodelRepository deduce(
      Set<AbstractionRepository<?>> abstractionRepositories,
      Set<MetamodelRepository<?>> metamodelRepositories) {
    Set<SupportiveEntity> supportiveEntities = new LinkedHashSet<>();

    CoreRegistry.getAbstractionRepositoryResolver()
        .resolve(abstractionRepositories, ThingRepository.class)
        .getAbstractionItemsByScope(AbstractionScope.domainSupportiveEntity())
        .forEach(thing -> makeSupportiveEntity(supportiveEntities, thing, rootPackageName));

    return new SupportiveEntityMetamodelRepository(supportiveEntities);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make supportive entity.
   *
   * @param supportiveEntities the supportive entities
   * @param thing the thing
   * @param rootPackageName the root package name
   */
  private void makeSupportiveEntity(
      Set<SupportiveEntity> supportiveEntities, Thing thing, PackageName rootPackageName) {
    supportiveEntities.add(
        new SupportiveEntity(
            new ObjectName(thing.getThingName().getText()),
            new PackageName(
                String.format(
                    "%s.%s.%s",
                    rootPackageName.getText(), "supportive", thing.getThingName().getText())),
            supportiveEntityPropertyDeducer.deduceFromThing(thing, rootPackageName),
            supportiveEntityPropertyDeducer.deduceConstructors(thing, rootPackageName)));
  }
}
