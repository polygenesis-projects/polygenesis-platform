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

package io.polygenesis.models.domain;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Set;

/**
 * The type Base domain entity.
 *
 * @author Christos Tsakostas
 */
public abstract class BaseDomainEntity extends BaseDomainObject {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Base domain entity.
   *
   * @param domainObjectType the domain object type
   * @param instantiationType the instantiation type
   * @param objectName the object name
   * @param packageName the package name
   * @param properties the properties
   * @param constructors the constructors
   * @param multiTenant the multi tenant
   */
  @SuppressWarnings("rawtypes")
  public BaseDomainEntity(
      DomainObjectType domainObjectType,
      InstantiationType instantiationType,
      ObjectName objectName,
      PackageName packageName,
      Set<DomainObjectProperty> properties,
      Set<Constructor> constructors,
      Boolean multiTenant) {
    super(
        domainObjectType,
        instantiationType,
        objectName,
        packageName,
        properties,
        constructors,
        multiTenant);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

}
