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

package io.polygenesis.models.ui;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.core.Thing;
import io.polygenesis.models.ui.container.AbstractContainer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Feature deducer.
 *
 * @author Christos Tsakostas
 */
public class FeatureDeducer {

  private final FeatureNameDeducer featureNameDeducer;
  private final ContainerDeducer containerDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Feature deducer.
   *
   * @param featureNameDeducer the feature name deducer
   */
  public FeatureDeducer(FeatureNameDeducer featureNameDeducer, ContainerDeducer containerDeducer) {
    Assertion.isNotNull(featureNameDeducer, "featureNameDeducer is required");
    this.featureNameDeducer = featureNameDeducer;
    Assertion.isNotNull(containerDeducer, "containerDeducer is required");
    this.containerDeducer = containerDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce {@link Feature} from {@link Thing}.
   *
   * @param thing the thing
   * @return the store
   */
  public Feature deduceFeatureFromThing(Thing thing) {
    Set<AbstractContainer> containers = new LinkedHashSet<>();
    Feature feature =
        new Feature(thing.getContextName(), featureNameDeducer.from(thing), containers);

    thing
        .getFunctions()
        .forEach(
            function ->
                containers.add(
                    containerDeducer.deduceContainerFromThingFunction(feature, function)));

    return feature;
  }
}
