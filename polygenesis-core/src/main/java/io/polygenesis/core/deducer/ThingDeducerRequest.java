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

import com.oregor.ddd4j.check.assertion.Assertion;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Core deducer request.
 *
 * @author Christos Tsakostas
 */
public class ThingDeducerRequest {

  private Set<String> packagesToScan;
  private Set<String> interfaces;
  private InclusionOrExclusionType interfacesInclusionOrExclusionType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Core deducer request.
   *
   * @param packagesToScan the packages to scan
   */
  public ThingDeducerRequest(Set<String> packagesToScan) {
    this(packagesToScan, new LinkedHashSet<>(), InclusionOrExclusionType.NONE);
  }

  /**
   * Instantiates a new Core deducer request.
   *
   * @param packagesToScan the packages to scan
   * @param interfaces the interfaces
   * @param interfacesInclusionOrExclusionType the interfaces inclusion or exclusion type
   */
  public ThingDeducerRequest(
      Set<String> packagesToScan,
      Set<String> interfaces,
      InclusionOrExclusionType interfacesInclusionOrExclusionType) {
    setPackagesToScan(packagesToScan);
    setInterfaces(interfaces);
    setInterfacesInclusionOrExclusionType(interfacesInclusionOrExclusionType);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets packages to scan.
   *
   * @return the packages to scan
   */
  public Set<String> getPackagesToScan() {
    return packagesToScan;
  }

  /**
   * Gets interfaces.
   *
   * @return the interfaces
   */
  public Set<String> getInterfaces() {
    return interfaces;
  }

  /**
   * Gets interfaces inclusion or exclusion type.
   *
   * @return the interfaces inclusion or exclusion type
   */
  public InclusionOrExclusionType getInterfacesInclusionOrExclusionType() {
    return interfacesInclusionOrExclusionType;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setPackagesToScan(Set<String> packagesToScan) {
    Assertion.isNotNull(packagesToScan, "packagesToScan is required");

    this.packagesToScan = packagesToScan;
  }

  private void setInterfaces(Set<String> interfaces) {
    Assertion.isNotNull(interfaces, "interfaces is required");

    this.interfaces = interfaces;
  }

  private void setInterfacesInclusionOrExclusionType(
      InclusionOrExclusionType interfacesInclusionOrExclusionType) {
    Assertion.isNotNull(
        interfacesInclusionOrExclusionType, "interfacesInclusionOrExclusionType is required");

    this.interfacesInclusionOrExclusionType = interfacesInclusionOrExclusionType;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================
}
