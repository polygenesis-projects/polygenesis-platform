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

import java.util.Set;

/**
 * Collection of {@link AbstractElement Elements}.
 *
 * @author Christos Tsakostas
 */
public class ElementGroup extends AbstractElement {

  private Set<AbstractElement> elements;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Element group.
   *
   * @param elementName the element name
   * @param elements the elements
   */
  public ElementGroup(ElementName elementName, Set<AbstractElement> elements) {
    super(elementName);
    setElements(elements);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets elements.
   *
   * @return the elements
   */
  public Set<AbstractElement> getElements() {
    return elements;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets elements.
   *
   * @param elements the elements
   */
  private void setElements(Set<AbstractElement> elements) {
    this.elements = elements;
  }
}
