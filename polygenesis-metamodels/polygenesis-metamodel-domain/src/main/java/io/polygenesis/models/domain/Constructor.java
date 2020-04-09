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

package io.polygenesis.models.domain;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Set;

public class Constructor extends StateMutationMethod {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Constructor.
   *
   * @param mutatesObject the mutates object
   * @param function the function
   * @param properties the properties
   * @param rootPackageName the root package name
   */
  public Constructor(
      DomainObject mutatesObject,
      Function function,
      Set<DomainObjectProperty<?>> properties,
      PackageName rootPackageName) {
    super(mutatesObject, function, properties, rootPackageName);
  }

  /**
   * Instantiates a new Constructor.
   *
   * @param mutatesObject the mutates object
   * @param function the function
   * @param properties the properties
   * @param superClassProperties the super class properties
   * @param rootPackageName the root package name
   */
  public Constructor(
      DomainObject mutatesObject,
      Function function,
      Set<DomainObjectProperty<?>> properties,
      Set<DomainObjectProperty<?>> superClassProperties,
      PackageName rootPackageName) {
    super(mutatesObject, function, properties, superClassProperties, rootPackageName);
  }
}
