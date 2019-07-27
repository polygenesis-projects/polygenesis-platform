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

package io.polygenesis.metamodels.ui.widget;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Nameable;
import io.polygenesis.metamodels.ui.feature.Feature;
import java.util.Objects;

/**
 * The type Widget.
 *
 * @author Christos Tsakostas
 */
public class Widget implements Nameable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Feature feature;
  private Name name;
  private DataObject dataObject;
  private ComponentType componentType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Widget.
   *
   * @param feature the feature
   * @param name the name
   * @param dataObject the data object
   * @param componentType the component type
   */
  public Widget(Feature feature, Name name, DataObject dataObject, ComponentType componentType) {
    setFeature(feature);
    setName(name);
    setDataObject(dataObject);
    setComponentType(componentType);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets feature.
   *
   * @return the feature
   */
  public Feature getFeature() {
    return feature;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public Name getName() {
    return name;
  }

  /**
   * Gets data object.
   *
   * @return the data object
   */
  public DataObject getDataObject() {
    return dataObject;
  }

  /**
   * Gets component type.
   *
   * @return the component type
   */
  public ComponentType getComponentType() {
    return componentType;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets feature.
   *
   * @param feature the feature
   */
  private void setFeature(Feature feature) {
    Assertion.isNotNull(feature, "feature is required");
    this.feature = feature;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(Name name) {
    Assertion.isNotNull(name, "name is required");
    this.name = name;
  }

  /**
   * Sets data object.
   *
   * @param dataObject the data object
   */
  public void setDataObject(DataObject dataObject) {
    Assertion.isNotNull(dataObject, "dataObject is required");
    this.dataObject = dataObject;
  }

  /**
   * Sets component type.
   *
   * @param componentType the component type
   */
  private void setComponentType(ComponentType componentType) {
    Assertion.isNotNull(componentType, "componentType is required");
    this.componentType = componentType;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return new ObjectName(getName().getText());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Widget widget = (Widget) o;
    return Objects.equals(feature, widget.feature)
        && Objects.equals(name, widget.name)
        && Objects.equals(dataObject, widget.dataObject)
        && componentType == widget.componentType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(feature, name, dataObject, componentType);
  }
}
