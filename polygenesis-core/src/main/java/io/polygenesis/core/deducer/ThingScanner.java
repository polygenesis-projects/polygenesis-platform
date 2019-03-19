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

package io.polygenesis.core.deducer;

import io.polygenesis.annotations.core.GFunction;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingBuilder;
import io.polygenesis.core.ThingName;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * Identifies Things in provided interfaces.
 *
 * @author Christos Tsakostas
 */
public class ThingScanner {

  private static final Logger LOG = LoggerFactory.getLogger(ThingScanner.class);

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Identify things in interfaces.
   *
   * @param classes the provided classes of the interfaces
   * @return the set of Things
   */
  public Set<Thing> identifyThingsInInterfaces(Set<Class<?>> classes) {
    Set<Thing> things = new LinkedHashSet<>();

    classes.forEach(clazz -> things.addAll(identifyThingsInOneInterface(clazz)));

    LOG.info("{} Things detected", things.size());

    return things;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<Thing> identifyThingsInOneInterface(Class<?> clazz) {
    Set<Thing> things = new LinkedHashSet<>();

    Stream.of(clazz.getMethods())
        .forEach(
            p -> {
              Optional<Thing> optionalThing = this.identifyThingInMethod(p);
              optionalThing.ifPresent(things::add);
            });

    return things;
  }

  private Optional<Thing> identifyThingInMethod(Method method) {
    GFunction annotationGFunction = AnnotationUtils.findAnnotation(method, GFunction.class);
    if (annotationGFunction != null) {
      Thing thing =
          ThingBuilder.generic()
              .setThingName(new ThingName(annotationGFunction.thingName()))
              .createThing();
      return Optional.of(thing);
    } else {
      return Optional.empty();
    }
  }
}
