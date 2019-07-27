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

package io.polygenesis.core;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.Name;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Project builder.
 *
 * @author Christos Tsakostas
 */
public class ProjectBuilder {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Name name;
  private Set<Context<? extends Abstraction>> contexts;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Of project builder.
   *
   * @param name the name
   * @return the project builder
   */
  public static ProjectBuilder of(String name) {
    return new ProjectBuilder(name);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Project builder.
   *
   * @param name the name
   */
  private ProjectBuilder(String name) {
    Assertion.isNotNull(name, "name is required");
    this.name = new Name(name);
    this.contexts = new LinkedHashSet<>();
  }

  // ===============================================================================================
  // BEHAVIOR
  // ===============================================================================================

  /**
   * Add context project builder.
   *
   * @param context the context
   * @return the project builder
   */
  public ProjectBuilder addContext(Context<? extends Abstraction> context) {
    Assertion.isNotNull(context, "context is required");
    this.contexts.add(context);
    return this;
  }

  // ===============================================================================================
  // BUILD
  // ===============================================================================================

  /**
   * Build project.
   *
   * @return the project
   */
  public <T extends Project> T build(Class<T> projectClass) {
    try {
      Constructor<?>[] allConstructors = projectClass.getConstructors();

      Object[] objects = {this.name, this.contexts};

      return projectClass.cast(allConstructors[0].newInstance(objects));
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }
}
