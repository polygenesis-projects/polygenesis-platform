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

package io.polygenesis.commons.feature;

import io.polygenesis.commons.text.AbstractText;

/**
 * Denotes the name of business feature. A feature can be anything, such as an entity or a concept,
 * whatever makes sense to the business.
 *
 * <p>A <b>Feature</b> is equivalent to a <b>Thing</b>. <italic>Thing</italic> is the preferred name
 * when working on the PolyGenesis Core model. <italic>Feature</italic> is preferred whe working on
 * other models such as the UI and Reactive State Management.
 *
 * @author Christos Tsakostas
 */
public class FeatureName extends AbstractText {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Feature.
   *
   * @param text the text
   */
  public FeatureName(String text) {
    super(text);
  }
}
