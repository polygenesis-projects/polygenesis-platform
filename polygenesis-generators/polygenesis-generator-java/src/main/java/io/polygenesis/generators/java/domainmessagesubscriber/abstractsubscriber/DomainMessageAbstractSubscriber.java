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

package io.polygenesis.generators.java.domainmessagesubscriber.abstractsubscriber;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractNameablePackageable;
import io.polygenesis.core.Generatable;

/**
 * The type Domain message abstract subscriber.
 *
 * @author Christos Tsakostas
 */
public class DomainMessageAbstractSubscriber extends AbstractNameablePackageable
    implements Generatable {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message abstract subscriber.
   *
   * @param objectName the object name
   * @param packageName the package name
   */
  public DomainMessageAbstractSubscriber(ObjectName objectName, PackageName packageName) {
    super(objectName, packageName);
  }
}
