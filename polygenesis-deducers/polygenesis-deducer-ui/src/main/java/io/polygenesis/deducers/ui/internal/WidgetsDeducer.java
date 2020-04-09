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

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.metamodels.ui.feature.Feature;
import io.polygenesis.metamodels.ui.screen.ScreenType;
import io.polygenesis.metamodels.ui.widget.ComponentType;
import io.polygenesis.metamodels.ui.widget.Widget;
import io.polygenesis.models.api.ServiceMethod;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class WidgetsDeducer {

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
  public Set<Widget> deduce(Feature feature, Thing thing, Set<ServiceMethod> serviceMethods) {
    Set<Widget> widgets = new LinkedHashSet<>();

    serviceMethods.forEach(
        serviceMethod -> {
          if (serviceMethod.getFunction().getPurpose().isCreate()) {
            widgets.addAll(deduceForCreate(feature, serviceMethod));
          } else if (serviceMethod.getFunction().getPurpose().isFetchOne()) {
            widgets.addAll(deduceForDetail(feature, serviceMethod));
          } else if (serviceMethod.getFunction().getPurpose().isFetchPagedCollection()) {
            widgets.addAll(deduceForPagedCollection(feature, serviceMethod));
          }
        });

    return widgets;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<Widget> deduceForCreate(Feature feature, ServiceMethod serviceMethod) {
    Set<Widget> widgets = new LinkedHashSet<>();

    DataObject dataObject = serviceMethod.getRequestDto().getDataObject();

    widgets.add(
        new Widget(
            feature,
            new Name(
                TextConverter.toLowerCamel(
                    String.format("%sCreate", feature.getFeatureName().getText()))),
            dataObject,
            ComponentType.CREATE));

    return widgets;
  }

  private Set<Widget> deduceForDetail(Feature feature, ServiceMethod serviceMethod) {
    Set<Widget> widgets = new LinkedHashSet<>();

    DataObject dataObject = serviceMethod.getRequestDto().getDataObject();

    widgets.add(
        new Widget(
            feature,
            new Name(
                TextConverter.toLowerCamel(
                    String.format("%sDetail", feature.getFeatureName().getText()))),
            dataObject,
            ComponentType.DETAIL));

    return widgets;
  }

  private Set<Widget> deduceForPagedCollection(Feature feature, ServiceMethod serviceMethod) {
    Set<Widget> widgets = new LinkedHashSet<>();

    DataObject dataObjectItem =
        serviceMethod
            .getResponseDto()
            .getArrayElementAsOptional()
            .orElseThrow(IllegalStateException::new)
            .getAsDataObject();

    widgets.add(
        new Widget(
            feature,
            new Name(
                TextConverter.toLowerCamel(
                    String.format("%sCollectionItem", feature.getFeatureName().getText()))),
            dataObjectItem,
            ComponentType.COLLECTION_ITEM));

    DataObject dataObjectCollection = serviceMethod.getResponseDto().getDataObject();

    widgets.add(
        new Widget(
            feature,
            new Name(
                TextConverter.toLowerCamel(
                    String.format("%sCollection", feature.getFeatureName().getText()))),
            dataObjectCollection,
            ComponentType.COLLECTION));

    return widgets;
  }
}
