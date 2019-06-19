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

package io.polygenesis.abstraction.thing;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.Context;
import io.polygenesis.core.ContextGenerator;

/**
 * The type Thing context.
 *
 * @author Christos Tsakostas
 */
public class ThingContext implements Context<Thing> {

  private Name name;
  private ThingRepository thingRepository;
  private ContextGenerator contextGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Thing context.
   *
   * @param name the name
   * @param thingRepository the thing repository
   * @param contextGenerator the architecture generator
   */
  public ThingContext(
      Name name, ThingRepository thingRepository, ContextGenerator contextGenerator) {
    setName(name);
    setThingRepository(thingRepository);
    setContextGenerator(contextGenerator);
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(Name name) {
    Assertion.isNotNull(name, "name is required");
    this.name = name;
  }

  /**
   * Sets thing repository.
   *
   * @param thingRepository the thing repository
   */
  public void setThingRepository(ThingRepository thingRepository) {
    Assertion.isNotNull(thingRepository, "thingRepository is required");
    this.thingRepository = thingRepository;
  }

  /**
   * Sets architecture generator.
   *
   * @param contextGenerator the architecture generator
   */
  private void setContextGenerator(ContextGenerator contextGenerator) {
    Assertion.isNotNull(contextGenerator, "contextGenerator is required");
    this.contextGenerator = contextGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Name getName() {
    return name;
  }

  @Override
  public AbstractionRepository<Thing> getAbstractionRepository() {
    return thingRepository;
  }

  @Override
  public void addAbstraction(Thing abstraction) {
    abstraction.assignContextName(new ContextName(getName().getText()));
    thingRepository.addAbstractionItem(abstraction);
  }

  @Override
  public ContextGenerator getContextGenerator() {
    return contextGenerator;
  }
}
