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

package io.polygenesis.generators.java.repository.inmemory.initialization;

import io.polygenesis.abstraction.data.DataRepository;
import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractNameablePackageable;
import io.polygenesis.core.Generatable;
import io.polygenesis.generators.java.domain.supportiveentity.repository.SupportiveEntityRepository;

/**
 * The type Supportive entity initialization.
 *
 * @author Christos Tsakostas
 */
public class SupportiveEntityInitialization extends AbstractNameablePackageable
    implements Generatable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private SupportiveEntityRepository supportiveEntityRepository;
  private Function afterPropertiesSet;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Supportive entity initialization.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param supportiveEntityRepository the supportive entity repository
   */
  public SupportiveEntityInitialization(
      ObjectName objectName,
      PackageName packageName,
      SupportiveEntityRepository supportiveEntityRepository) {
    super(objectName, packageName);
    this.supportiveEntityRepository = supportiveEntityRepository;
    this.afterPropertiesSet = makeAfterPropertiesSet();
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

  /**
   * Gets after properties set.
   *
   * @return the after properties set
   */
  public Function getAfterPropertiesSet() {
    return afterPropertiesSet;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Function makeAfterPropertiesSet() {
    Thing thing =
        ThingBuilder.domainDetailRepositoryInMemory("supportiveEntityInitialization").createThing();

    return new Function(
        thing,
        Purpose.afterPropertiesSet(),
        FunctionName.ofVerbOnly("afterPropertiesSet"),
        null,
        new DataRepository(),
        Activity.empty(),
        thing.getAbstractionsScopes());
  }
}
