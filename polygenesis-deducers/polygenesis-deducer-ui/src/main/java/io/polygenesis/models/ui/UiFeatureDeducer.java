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

package io.polygenesis.models.ui;

import io.polygenesis.core.Deducer;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.ThingRepository;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Ui feature deducer.
 *
 * @author Christos Tsakostas
 */
public class UiFeatureDeducer implements Deducer<UiFeatureModelRepository> {

  private final FeatureDeducer featureDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Ui feature deducer.
   *
   * @param featureDeducer the feature deducer
   */
  public UiFeatureDeducer(FeatureDeducer featureDeducer) {
    this.featureDeducer = featureDeducer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================
  @Override
  public UiFeatureModelRepository deduce(
      ThingRepository thingRepository, Set<ModelRepository> modelRepositories) {
    Set<Feature> features = new LinkedHashSet<>();

    thingRepository
        .getApiThings()
        .forEach(thing -> features.add(featureDeducer.deduceFeatureFromThing(thing)));

    return new UiFeatureModelRepository(features);
  }
}
