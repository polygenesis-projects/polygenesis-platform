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

package io.polygenesis.deducers.scheduler;

import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.scheduler.Scheduler;
import io.polygenesis.models.scheduler.SchedulerRepository;
import io.polygenesis.models.scheduler.Trigger;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Scheduler deducer.
 *
 * @author Christos Tsakostas
 */
public class SchedulerDeducer implements Deducer<SchedulerRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Scheduler deducer.
   *
   * @param rootPackageName the root package name
   */
  public SchedulerDeducer(PackageName rootPackageName) {
    this.rootPackageName = rootPackageName;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets root package name.
   *
   * @return the root package name
   */
  public PackageName getRootPackageName() {
    return rootPackageName;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public SchedulerRepository deduce(
      Set<AbstractionRepository> abstractionRepositories,
      Set<MetamodelRepository> metamodelRepositories) {
    Set<Scheduler> schedulers = new LinkedHashSet<>();

    CoreRegistry.getAbstractionRepositoryResolver()
        .resolve(abstractionRepositories, ThingRepository.class)
        .getAbstractionItemsByScope(AbstractionScope.apiClientScheduler())
        .forEach(
            thing ->
                schedulers.add(
                    new Scheduler(
                        new ObjectName(thing.getThingName().getText()),
                        getRootPackageName(),
                        new Trigger("someTrigger"),
                        new LinkedHashSet<>())));

    return new SchedulerRepository(schedulers);
  }
}
