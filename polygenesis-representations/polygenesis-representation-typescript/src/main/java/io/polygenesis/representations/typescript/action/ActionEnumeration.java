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

package io.polygenesis.representations.typescript.action;

import io.polygenesis.commons.keyvalue.KeyValue;
import java.util.Set;

/**
 * The type Action enumeration.
 *
 * @author Christos Tsakostas
 */
public class ActionEnumeration extends AbstractActionEnumerationOrUnion {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Action enumeration.
   *
   * @param name the name
   * @param keyValues the key values
   */
  public ActionEnumeration(String name, Set<KeyValue> keyValues) {
    super(name, keyValues);
  }
}