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

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;

public class DomainEvent extends DomainObject {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain event.
   *
   * @param instantiationType the instantiation type
   * @param objectName the object name
   * @param packageName the package name
   */
  public DomainEvent(
      InstantiationType instantiationType,
      ObjectName objectName,
      PackageName packageName,
      Boolean multiTenant) {
    super(DomainObjectType.DOMAIN_EVENT, instantiationType, objectName, packageName, multiTenant);
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public boolean hasSuperclass() {
    return false;
  }

  @Override
  public DomainObject getSuperClass() {
    throw new UnsupportedOperationException();
  }

  @Override
  public PropertyType getPropertyType() {
    throw new UnsupportedOperationException();
  }

  @Override
  public DataObject getData() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Data getTypeParameterData() {
    throw new UnsupportedOperationException();
  }
}
