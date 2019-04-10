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

import io.polygenesis.core.Function;
import io.polygenesis.models.ui.element.ElementGroup;
import io.polygenesis.models.ui.element.ElementName;
import java.util.LinkedHashSet;

/**
 * The type ElementGroup deducer.
 *
 * @author Christos Tsakostas
 */
public class ComponentDeducer {

  private final ElementDeducer elementDeducer;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new ElementGroup deducer.
   *
   * @param elementDeducer the element deducer
   */
  public ComponentDeducer(ElementDeducer elementDeducer) {
    this.elementDeducer = elementDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce container from thing function.
   *
   * @param feature the feature
   * @param function the function
   * @return the container
   */
  public ElementGroup deduceComponentFromData(Feature feature, Function function) {
    ElementGroup elementGroup =
        new ElementGroup(
            new ElementName(feature.getFeatureName().getText()), new LinkedHashSet<>());

    // TODO
    elementDeducer.deduceElementFromData(feature, function);

    return elementGroup;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

}
