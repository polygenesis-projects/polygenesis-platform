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

package io.polygenesis.core;

/**
 * The type Data type transformer.
 *
 * @author Christos Tsakostas
 */
public interface DataTypeTransformer {

  /**
   * Convert string.
   *
   * @param dataType the data type
   * @return the string
   */
  String convert(String dataType);

  /**
   * Gets modifier public.
   *
   * @return the modifier public
   */
  String getModifierPublic();

  /**
   * Gets modifier protected.
   *
   * @return the modifier protected
   */
  String getModifierProtected();

  /**
   * Gets modifier private.
   *
   * @return the modifier private
   */
  String getModifierPrivate();

  /**
   * Gets modifier abstract.
   *
   * @return the modifier abstract
   */
  String getModifierAbstract();

  /**
   * Gets void.
   *
   * @return the void
   */
  String getVoid();
}
