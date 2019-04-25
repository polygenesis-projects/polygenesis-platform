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

package io.polygenesis.models.ui.element;

import io.polygenesis.commons.assertion.Assertion;

/**
 * Base class for anything appearing on the UI.
 *
 * <p>Denotes the building blocks of a UI, being user input, output, submission and supportive.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractElement {

  private ElementName elementName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract element.
   *
   * @param elementName the element name
   */
  public AbstractElement(ElementName elementName) {
    setElementName(elementName);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets element name.
   *
   * @return the element name
   */
  public ElementName getElementName() {
    return elementName;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets element name.
   *
   * @param elementName the element name
   */
  private void setElementName(ElementName elementName) {
    Assertion.isNotNull(elementName, "elementName is required");
    this.elementName = elementName;
  }
}
