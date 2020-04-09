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

package io.polygenesis.models.scheduler;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractMetamodel;
import io.polygenesis.models.api.ServiceMethod;
import java.util.Objects;
import java.util.Set;

/**
 * The type Scheduler.
 *
 * @author Christos Tsakostas
 */
public class Scheduler extends AbstractMetamodel {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private PackageName packageName;
  private Trigger trigger;
  private Set<ServiceMethod> serviceMethods;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Scheduler.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param trigger the trigger
   * @param serviceMethods the service methods
   */
  public Scheduler(
      ObjectName objectName,
      PackageName packageName,
      Trigger trigger,
      Set<ServiceMethod> serviceMethods) {
    super(objectName);
    this.packageName = packageName;
    setTrigger(trigger);
    setServiceMethods(serviceMethods);
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
   * Gets trigger.
   *
   * @return the trigger
   */
  public Trigger getTrigger() {
    return trigger;
  }

  /**
   * Gets service methods.
   *
   * @return the service methods
   */
  public Set<ServiceMethod> getServiceMethods() {
    return serviceMethods;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets trigger.
   *
   * @param trigger the trigger
   */
  private void setTrigger(Trigger trigger) {
    Assertion.isNotNull(trigger, "trigger is required");
    this.trigger = trigger;
  }

  /**
   * Sets service methods.
   *
   * @param serviceMethods the service methods
   */
  public void setServiceMethods(Set<ServiceMethod> serviceMethods) {
    Assertion.isNotNull(serviceMethods, "serviceMethods is required");
    this.serviceMethods = serviceMethods;
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
    if (!super.equals(o)) {
      return false;
    }
    Scheduler scheduler = (Scheduler) o;
    return Objects.equals(trigger, scheduler.trigger)
        && Objects.equals(serviceMethods, scheduler.serviceMethods);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), trigger, serviceMethods);
  }
}
