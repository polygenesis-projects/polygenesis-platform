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

package io.polygenesis.models.domain;

import io.polygenesis.commons.text.Name;
import io.polygenesis.core.datatype.PackageName;

/**
 * The type Aggregate root.
 *
 * @author Christos Tsakostas
 */
public class AggregateRoot {

  private PackageName packageName;
  private Name name;

  //  private AggregateRootName aggregateRootName;
  //  private Set<AggregateEntity> aggregateEntities;
  //  private Set<AggregateEntityCollection> aggregateEntityCollections;
  //  private Set<ValueObject> valueObjects;
  //  private Set<ValueObjectCollection> valueObjectCollections;
  //  private Set<StateMutationCommand> stateMutationCommands;
  //  private Set<StateQuery> stateQueries;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root.
   *
   * @param packageName the package name
   * @param name the name
   */
  public AggregateRoot(PackageName packageName, Name name) {
    setPackageName(packageName);
    setName(name);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets package name.
   *
   * @return the package name
   */
  public PackageName getPackageName() {
    return packageName;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public Name getName() {
    return name;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets package name.
   *
   * @param packageName the package name
   */
  private void setPackageName(PackageName packageName) {
    this.packageName = packageName;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(Name name) {
    this.name = name;
  }
}
