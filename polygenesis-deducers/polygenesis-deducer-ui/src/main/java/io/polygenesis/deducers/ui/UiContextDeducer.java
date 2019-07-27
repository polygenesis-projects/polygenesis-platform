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

package io.polygenesis.deducers.ui;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.deducers.ui.internal.FeatureDeducer;
import io.polygenesis.metamodels.ui.UiContext;
import io.polygenesis.metamodels.ui.UiContextMetamodelRepository;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import io.polygenesis.models.api.ServiceMethod;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Ui context deducer.
 *
 * @author Christos Tsakostas
 */
public class UiContextDeducer implements Deducer<UiContextMetamodelRepository> {

  private final FeatureDeducer featureDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Ui context deducer.
   *
   * @param featureDeducer the feature deducer
   */
  public UiContextDeducer(FeatureDeducer featureDeducer) {
    this.featureDeducer = featureDeducer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public UiContextMetamodelRepository deduce(
      Set<AbstractionRepository<?>> abstractionRepositories,
      Set<MetamodelRepository<?>> metamodelRepositories) {
    UiContext uiContext = new UiContext();

    Set<Thing> uiThings =
        CoreRegistry.getAbstractionRepositoryResolver()
            .resolve(abstractionRepositories, ThingRepository.class)
            .getAbstractionItemsByScope(AbstractionScope.api());

    ServiceMetamodelRepository serviceMetamodelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, ServiceMetamodelRepository.class);

    uiThings.forEach(
        thing -> {
          Set<ServiceMethod> serviceMethods =
              serviceMetamodelRepository
                  .getServicesBy(thing.getThingName())
                  .stream()
                  .flatMap(service -> service.getServiceMethods().stream())
                  .collect(Collectors.toCollection(LinkedHashSet::new));

          uiContext.addFeature(featureDeducer.deduce(thing, serviceMethods));
        });

    return new UiContextMetamodelRepository(new LinkedHashSet<>(Arrays.asList(uiContext)));
  }
}
