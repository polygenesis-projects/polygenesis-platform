/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.deducers.ui.internal;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.valueobjects.FeatureName;
import io.polygenesis.metamodels.ui.feature.Feature;
import io.polygenesis.models.api.ServiceMethod;
import java.util.Set;

public class FeatureDeducer {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private final ScreensDeducer screensDeducer;
  private final WidgetsDeducer widgetsDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Feature deducer.
   *
   * @param screensDeducer the screens deducer
   * @param widgetsDeducer the widgets deducer
   */
  public FeatureDeducer(ScreensDeducer screensDeducer, WidgetsDeducer widgetsDeducer) {
    this.screensDeducer = screensDeducer;
    this.widgetsDeducer = widgetsDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce feature.
   *
   * @param thing the thing
   * @param serviceMethods the service methods
   * @return the feature
   */
  public Feature deduce(Thing thing, Set<ServiceMethod> serviceMethods) {
    Feature feature =
        new Feature(new FeatureName(thing.getThingName().getText()), thing.getContextName());

    feature.addScreens(screensDeducer.deduce(feature, thing, serviceMethods));

    feature.addWidgets(widgetsDeducer.deduce(feature, thing, serviceMethods));

    return feature;
  }
}
