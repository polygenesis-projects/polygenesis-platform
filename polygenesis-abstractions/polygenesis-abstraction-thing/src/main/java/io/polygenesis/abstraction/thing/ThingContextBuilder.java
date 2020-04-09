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
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.core.ContextGenerator;
import io.polygenesis.core.Deducer;
import java.util.LinkedHashSet;
import java.util.Set;

public class ThingContextBuilder {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private ThingContext thingContext;
  private Set<Deducer<?>> deducers;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Of thing context builder.
   *
   * @param name the name
   * @param contextGenerator the context generator
   * @return the thing context builder
   */
  public static ThingContextBuilder of(String name, ContextGenerator contextGenerator) {
    return new ThingContextBuilder(name, contextGenerator);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private ThingContextBuilder(String name, ContextGenerator contextGenerator) {
    this.thingContext =
        new ThingContext(
            new Name(name), new ThingRepository(new LinkedHashSet<>()), contextGenerator);
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Add thing thing context builder.
   *
   * @param thing the thing
   * @return the thing context builder
   */
  public ThingContextBuilder addThing(Thing thing) {
    thingContext.addAbstraction(thing);
    return this;
  }

  /**
   * With deducers thing context builder.
   *
   * @param deducers the deducers
   * @return the thing context builder
   */
  public ThingContextBuilder withDeducers(Set<Deducer<?>> deducers) {
    this.deducers = deducers;
    return this;
  }

  // ===============================================================================================
  // BUILD
  // ===============================================================================================

  /**
   * Build thing context.
   *
   * @return the thing context
   */
  public ThingContext build() {
    Assertion.isNotNull(deducers, "deducers is required");
    thingContext.populateMetamodelRepositories(deducers);
    return thingContext;
  }
}
