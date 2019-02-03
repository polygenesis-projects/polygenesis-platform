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

package io.polygenesis.generators.java.api;

import io.polygenesis.models.api.Service;
import java.util.Set;

/**
 * Service projection transforms data from {@link io.polygenesis.models.api.Service} to a friendly
 * format for the Java Generators.
 *
 * @author Christos Tsakostas
 */
public class ServiceProjection extends AbstractProjection {

  private Service service;
  private Set<MethodProjection> methodProjections;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service projection.
   *
   * @param imports the imports
   * @param description the description
   * @param objectNameWithOptionalExtendsImplements the object name with optional extends implements
   * @param service the service
   * @param methodProjections the method projections
   */
  public ServiceProjection(
      Set<String> imports,
      String description,
      String objectNameWithOptionalExtendsImplements,
      Service service,
      Set<MethodProjection> methodProjections) {
    super(imports, description, objectNameWithOptionalExtendsImplements);
    setService(service);
    setMethodProjections(methodProjections);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets service.
   *
   * @return the service
   */
  public Service getService() {
    return service;
  }

  /**
   * Gets method projections.
   *
   * @return the method projections
   */
  public Set<MethodProjection> getMethodProjections() {
    return methodProjections;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets service.
   *
   * @param service the service
   */
  private void setService(Service service) {
    this.service = service;
  }

  /**
   * Sets method projections.
   *
   * @param methodProjections the method projections
   */
  private void setMethodProjections(Set<MethodProjection> methodProjections) {
    this.methodProjections = methodProjections;
  }
}
