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

package io.polygenesis.generators.java.repository.inmemory.supportiveentity;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractNameablePackageable;
import io.polygenesis.core.Generatable;
import io.polygenesis.generators.java.domain.supportiveentity.repository.SupportiveEntityRepository;

public class SupportiveEntityRepositoryImpl extends AbstractNameablePackageable
    implements Generatable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private SupportiveEntityRepository supportiveEntityRepository;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Supportive entity repository.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param supportiveEntityRepository the supportive entity repository
   */
  public SupportiveEntityRepositoryImpl(
      ObjectName objectName,
      PackageName packageName,
      SupportiveEntityRepository supportiveEntityRepository) {
    super(objectName, packageName);
    this.supportiveEntityRepository = supportiveEntityRepository;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets supportive entity repository.
   *
   * @return the supportive entity repository
   */
  public SupportiveEntityRepository getSupportiveEntityRepository() {
    return supportiveEntityRepository;
  }
}
