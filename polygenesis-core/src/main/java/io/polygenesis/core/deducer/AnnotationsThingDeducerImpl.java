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

package io.polygenesis.core.deducer;

import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.core.ThingRepositoryImpl;
import java.util.Set;

/**
 * Currently, the only way to deduce the Things Repository is by Java Annotations.
 *
 * <p>Alternatively, you can manually instantiate the Core Model with PolyGenesis DSL.
 *
 * @author Christos Tsakostas
 */
public class AnnotationsThingDeducerImpl implements ThingDeducer {

  private final ClassScanner classScanner;
  private final ThingScanner thingScanner;
  private final FunctionIdentifier functionIdentifier;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Annotations core deducer.
   *
   * @param classScanner the class scanner
   * @param thingScanner the thing scanner
   * @param functionIdentifier the goal identifier
   */
  public AnnotationsThingDeducerImpl(
      ClassScanner classScanner, ThingScanner thingScanner, FunctionIdentifier functionIdentifier) {
    this.classScanner = classScanner;
    this.thingScanner = thingScanner;
    this.functionIdentifier = functionIdentifier;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================
  @Override
  public ThingRepository deduce(ThingDeducerRequest deducerRequest) {
    Set<Class<?>> classes =
        classScanner.identify(
            deducerRequest.getPackagesToScan(),
            deducerRequest.getInterfaces(),
            deducerRequest.getInterfacesInclusionOrExclusionType());

    Set<Thing> things = thingScanner.identifyThingsInInterfaces(classes);

    things.forEach(thing -> thing.addFunctions(functionIdentifier.identifyGoalsOf(thing, classes)));

    return new ThingRepositoryImpl(things);
  }
}
