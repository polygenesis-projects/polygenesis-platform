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

package io.polygenesis.metamodels.appflutter.widget;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingName;
import io.polygenesis.abstraction.thing.ThingProperty;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.metamodels.appflutter.AbstractFlutterThing;
import io.polygenesis.metamodels.ui.widget.Widget;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Flutter widget.
 *
 * @author Christos Tsakostas
 */
public class FlutterWidget extends AbstractFlutterThing<FlutterWidget> {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private Widget widget;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Of flutter app.
   *
   * @param widget the widget
   * @return the flutter app
   */
  public static FlutterWidget of(Widget widget) {
    Set<AbstractionScope> abstractionScopes = new LinkedHashSet<>();
    abstractionScopes.add(AbstractionScope.app());

    FlutterWidget flutterWidget =
        new FlutterWidget(
            abstractionScopes,
            widget.getFeature().getContextName(),
            new ThingName(widget.getName().getText()),
            new LinkedHashSet<>(),
            false,
            null,
            new LinkedHashSet<>(),
            widget);

    flutterWidget.addBuildFunction(flutterWidget);

    return flutterWidget;
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Flutter widget.
   *
   * @param abstractionScopes the abstraction scopes
   * @param contextName the context name
   * @param thingName the thing name
   * @param thingProperties the thing properties
   * @param multiTenant the multi tenant
   * @param optionalParent the optional parent
   * @param metadata the metadata
   * @param widget the widget
   */
  private FlutterWidget(
      Set<AbstractionScope> abstractionScopes,
      ContextName contextName,
      ThingName thingName,
      Set<ThingProperty> thingProperties,
      Boolean multiTenant,
      Thing optionalParent,
      Set<KeyValue> metadata,
      Widget widget) {
    super(
        abstractionScopes,
        contextName,
        thingName,
        thingProperties,
        multiTenant,
        optionalParent,
        metadata);
    setWidget(widget);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets widget.
   *
   * @return the widget
   */
  public Widget getWidget() {
    return widget;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets widget.
   *
   * @param widget the widget
   */
  private void setWidget(Widget widget) {
    Assertion.isNotNull(widget, "widget is required");
    this.widget = widget;
  }
}
