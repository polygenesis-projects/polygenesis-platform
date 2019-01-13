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

import io.polygenesis.commons.assertions.Assertion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Finds the types of a class.
 *
 * @author Christos Tsakostas
 */
public class TypesAnalyzer {

  /**
   * Gets all extended or implemented types recursively.
   *
   * @param clazz the clazz
   * @return the all extended or implemented types recursively
   */
  public Set<Class<?>> getAllExtendedOrImplementedTypesRecursively(Class<?> clazz) {
    Assertion.isNotNull(clazz, "clazz is required");

    List<Class<?>> res = new ArrayList<>();

    if (clazz.getCanonicalName().startsWith("java.")) {
      return new HashSet<>(res);
    }

    while (!clazz.getCanonicalName().startsWith("java.")) {
      res.add(clazz);

      // First, add all the interfaces implemented by this class
      Class<?>[] interfaces = clazz.getInterfaces();
      if (interfaces.length > 0) {
        res.addAll(Arrays.asList(interfaces));

        for (Class<?> interfaze : interfaces) {
          res.addAll(getAllExtendedOrImplementedTypesRecursively(interfaze));
        }
      }

      // Add the super class
      Class<?> superClass = clazz.getSuperclass();

      // Interfaces does not have java,lang.Object as superclass, they have null, so break the cycle
      // and return
      if (superClass == null) {
        break;
      }

      // Now inspect the superclass
      clazz = superClass;
    }

    return new HashSet<>(res);
  }
}
