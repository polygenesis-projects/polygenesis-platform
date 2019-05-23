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

package io.polygenesis.models.domain.projection;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.models.domain.Persistence;
import io.polygenesis.models.domain.Projection;
import io.polygenesis.models.domain.ProjectionMetamodelRepository;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Projection deducer.
 *
 * @author Christos Tsakostas
 */
public class ProjectionDeducer implements Deducer<ProjectionMetamodelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final PackageName rootPackageName;
  private final ProjectionPropertyDeducer projectionPropertyDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Projection deducer.
   *
   * @param rootPackageName the root package name
   * @param projectionPropertyDeducer the projection property deducer
   */
  public ProjectionDeducer(
      PackageName rootPackageName, ProjectionPropertyDeducer projectionPropertyDeducer) {
    this.rootPackageName = rootPackageName;
    this.projectionPropertyDeducer = projectionPropertyDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  public ProjectionMetamodelRepository deduce(
      Set<AbstractionRepository> abstractionRepositories,
      Set<MetamodelRepository> modelRepositories) {
    Set<Projection> projections = new LinkedHashSet<>();

    CoreRegistry.getAbstractionRepositoryResolver()
        .resolve(abstractionRepositories, ThingRepository.class)
        .getAbstractionItemsByScope(AbstractionScope.projection())
        .forEach(thing -> makeProjection(projections, thing, rootPackageName));

    return new ProjectionMetamodelRepository(projections);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void makeProjection(
      Set<Projection> projections, Thing thing, PackageName rootPackageName) {

    Persistence persistence =
        new Persistence(
            rootPackageName.withSubPackage(thing.getThingName().getText().toLowerCase()),
            new ObjectName(
                String.format(
                    "%sRepository", TextConverter.toUpperCamel(thing.getThingName().getText()))),
            new ObjectName(thing.getThingName().getText()),
            new ObjectName(String.format("%sId", thing.getThingName().getText())),
            thing.getMultiTenant());

    projections.add(
        new Projection(
            InstantiationType.CONCRETE,
            new ObjectName(thing.getThingName().getText()),
            new PackageName(
                String.format(
                    "%s.%s",
                    rootPackageName.getText(), thing.getThingName().getText().toLowerCase())),
            projectionPropertyDeducer.deduceFromThing(thing, rootPackageName),
            projectionPropertyDeducer.deduceConstructors(thing, rootPackageName),
            thing.getMultiTenant(),
            persistence,
            makeSuperclass(thing.getMultiTenant())));
  }

  private Projection makeSuperclass(Boolean multiTenant) {
    return new Projection(
        InstantiationType.ABSTRACT,
        multiTenant ? new ObjectName("TenantProjection") : new ObjectName("Projection"),
        new PackageName("com.oregor.trinity4j.domain"),
        new LinkedHashSet<>(),
        new LinkedHashSet<>(),
        multiTenant,
        null,
        null);
  }
}
