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

package io.polygenesis.core;

import com.oregor.ddd4j.check.assertion.Assertion;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * {@link Thing} is defined as a concept or an entity on or for which a {@link Goal} is defined.
 * Most commonly, the names of the domain business concepts will be defined as things, being
 * concrete entities or more abstract concepts.
 *
 * <p>Therefore, a {@link Thing} can be anything that makes sense to code generation.
 *
 * <p>Example of concrete business concepts: Customer, User, LoginContext etc.
 *
 * <p>Example of more abstract concepts: Sum calculation etc.
 *
 * @author Christos Tsakostas
 */
public class Thing {

  /** The name of a {@link Thing}. */
  private ThingName name;

  /** Optionally a {@link Thing} may be the child of another {@link Thing} acting as the parent. */
  private Thing parent;

  private Set<Function> functions;

  private Boolean multiTenant;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Thing.
   *
   * @param thingName the thing name
   */
  public Thing(ThingName thingName) {
    setName(thingName);
    setFunctions(new LinkedHashSet<>());
    setMultiTenant(false);
  }

  /**
   * Instantiates a new Thing.
   *
   * @param thingName the thing name
   * @param multiTenant the multi tenant
   */
  public Thing(ThingName thingName, Boolean multiTenant) {
    setName(thingName);
    setFunctions(new LinkedHashSet<>());
    setMultiTenant(multiTenant);
  }

  /**
   * Instantiates a new Thing with parent.
   *
   * @param thingName the thing name
   * @param parentThing the parent thing
   */
  public Thing(ThingName thingName, Thing parentThing) {
    setName(thingName);
    setParent(parentThing);
    setFunctions(new LinkedHashSet<>());
    setMultiTenant(parentThing.getMultiTenant());
  }

  // ===============================================================================================
  // APPENDERS
  // ===============================================================================================

  /**
   * Append function.
   *
   * @param function the function
   */
  public void appendFunction(Function function) {
    this.functions.add(function);
  }

  /**
   * Append functions.
   *
   * @param functions the functions
   */
  public void appendFunctions(Set<Function> functions) {
    this.functions.addAll(functions);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets name.
   *
   * @return the name
   */
  public ThingName getName() {
    return name;
  }

  /**
   * Gets parent.
   *
   * @return the parent
   */
  public Thing getParent() {
    return parent;
  }

  /**
   * Gets functions.
   *
   * @return the functions
   */
  public Set<Function> getFunctions() {
    return functions;
  }

  /**
   * Gets multi tenant.
   *
   * @return the multi tenant
   */
  public Boolean getMultiTenant() {
    return multiTenant;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets name.
   *
   * @param name the name
   */
  private void setName(ThingName name) {
    this.name = name;
  }

  /**
   * Sets parent.
   *
   * @param parent the parent
   */
  private void setParent(Thing parent) {
    this.parent = parent;
  }

  /**
   * Sets functions.
   *
   * @param functions the functions
   */
  private void setFunctions(Set<Function> functions) {
    this.functions = functions;
  }

  /**
   * Sets multi tenant.
   *
   * @param multiTenant the multi tenant
   */
  private void setMultiTenant(Boolean multiTenant) {
    Assertion.isNotNull(multiTenant, "multiTenant is required");
    this.multiTenant = multiTenant;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Thing thing = (Thing) o;
    return Objects.equals(name, thing.name)
        && Objects.equals(parent, thing.parent)
        && Objects.equals(multiTenant, thing.multiTenant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, parent, multiTenant);
  }
}
