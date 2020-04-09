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

package io.polygenesis.abstraction.thing;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.Context;
import io.polygenesis.core.ContextGenerator;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import java.util.LinkedHashSet;
import java.util.Set;

public class ThingContext implements Context<Thing> {

  private Name name;
  private ThingRepository thingRepository;
  private ContextGenerator contextGenerator;
  private Set<MetamodelRepository<?>> metamodelRepositories;

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

  /**
   * Sets metamodel repositories.
   *
   * @param metamodelRepositories the metamodel repositories
   */
  private void setMetamodelRepositories(Set<MetamodelRepository<?>> metamodelRepositories) {
    Assertion.isNotNull(metamodelRepositories, "metamodelRepositories is required");
    this.metamodelRepositories = metamodelRepositories;
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

  @Override
  public Set<MetamodelRepository<?>> getMetamodelRepositories() {
    return metamodelRepositories;
  }

  @Override
  public void populateMetamodelRepositories(Set<Deducer<?>> deducers) {
    setMetamodelRepositories(new LinkedHashSet<>());

    Set<AbstractionRepository<?>> abstractionRepositories = new LinkedHashSet<>();
    abstractionRepositories.add(thingRepository);

    deducers.forEach(
        deducer -> {
          MetamodelRepository<?> metamodelRepository =
              deducer.deduce(abstractionRepositories, metamodelRepositories);
          if (metamodelRepository == null) {
            throw new IllegalArgumentException(
                String.format(
                    "Metamodel repository is null for deducer=%s",
                    deducer.getClass().getCanonicalName()));
          }
          getMetamodelRepositories().add(metamodelRepository);
        });
  }
}
