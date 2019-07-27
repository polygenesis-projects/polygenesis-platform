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

package io.polygenesis.deducers.ui.internal;

import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.metamodels.ui.feature.Feature;
import io.polygenesis.metamodels.ui.screen.Screen;
import io.polygenesis.metamodels.ui.screen.ScreenType;
import io.polygenesis.models.api.ServiceMethod;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Screens deducer.
 *
 * @author Christos Tsakostas
 */
public class ScreensDeducer {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static Map<Purpose, ScreenType> mapPurposeToScreenType;

  static {
    mapPurposeToScreenType = new LinkedHashMap<>();

    mapPurposeToScreenType.put(Purpose.create(), ScreenType.CREATE);
    mapPurposeToScreenType.put(Purpose.fetchOne(), ScreenType.DETAIL);
    mapPurposeToScreenType.put(Purpose.fetchPagedCollection(), ScreenType.COLLECTION);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce set.
   *
   * @param feature the feature
   * @param thing the thing
   * @param serviceMethods the service methods
   * @return the set
   */
  public Set<Screen> deduce(Feature feature, Thing thing, Set<ServiceMethod> serviceMethods) {
    Set<Screen> screens = new LinkedHashSet<>();

    serviceMethods.forEach(
        serviceMethod -> {
          // TODO
        });

    thing
        .getFunctions()
        .stream()
        .filter(function -> function.supportsUi())
        .map(
            function ->
                new Screen(
                    feature,
                    new Name(
                        String.format(
                            "%s%s",
                            feature.getFeatureName().getText(),
                            mapPurposeToScreenType.get(function.getPurpose()))),
                    mapPurposeToScreenType.get(function.getPurpose())))
        .collect(Collectors.toCollection(LinkedHashSet::new));

    return screens;
  }
}
