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
import io.polygenesis.core.Model;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.ThingRepository;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Domain service deducer.
 *
 * @author Christos Tsakostas
 */
public class DomainServiceDeducer implements Deducer<DomainServiceRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain service deducer.
   *
   * @param rootPackageName the root package name
   */
  public DomainServiceDeducer(PackageName rootPackageName) {
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

  @Override
  public DomainServiceRepository deduce(
      ThingRepository thingRepository, Set<ModelRepository<? extends Model>> modelRepositories) {
    Set<DomainService> domainServices = new LinkedHashSet<>();

    thingRepository
        .getDomainServiceThings()
        .forEach(
            thing -> {
              DomainService domainService =
                  new DomainService(
                      new ObjectName(thing.getThingName().getText()),
                      new PackageName(
                          String.format("%s.%s", getRootPackageName().getText(), "service")));

              thing
                  .getFunctions()
                  .stream()
                  .forEach(function -> domainService.appendFunction(function));

              domainServices.add(domainService);
            });

    return new DomainServiceRepository(domainServices);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

}
