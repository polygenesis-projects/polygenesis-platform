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

package io.polygenesis.craftsman;

import java.util.Set;

/**
 * The type Annotations request builder.
 *
 * @author Christos Tsakostas
 */
public class AnnotationsRequestBuilder {

  private final AnnotationsRequest annotationsRequest;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Annotations request builder. */
  public AnnotationsRequestBuilder() {
    this.annotationsRequest = new AnnotationsRequest();
  }

  // ===============================================================================================
  // START
  // ===============================================================================================

  /**
   * Starts the AnnotationsRequestBuilder DSL.
   *
   * @return genesis request builder
   */
  public static AnnotationsRequestBuilder empty() {
    return new AnnotationsRequestBuilder();
  }

  // ===============================================================================================
  // END
  // ===============================================================================================

  /**
   * Build annotations request.
   *
   * @return the annotations request
   */
  public final AnnotationsRequest build() {
    return annotationsRequest;
  }

  // ===============================================================================================
  // WITH
  // ===============================================================================================

  /**
   * With packages to scan annotations request builder.
   *
   * @param packagesToScan the packages to scan
   * @return the annotations request builder
   */
  public final AnnotationsRequestBuilder withPackagesToScan(Set<String> packagesToScan) {
    this.annotationsRequest.setPackagesToScan(packagesToScan);
    return this;
  }

  /**
   * With interfaces included annotations request builder.
   *
   * @param interfacesIncluded the interfaces included
   * @return the annotations request builder
   */
  public final AnnotationsRequestBuilder withInterfacesIncluded(Set<String> interfacesIncluded) {
    this.annotationsRequest.setInterfacesIncluded(interfacesIncluded);
    return this;
  }

  /**
   * With interfaces excluded annotations request builder.
   *
   * @param interfacesExcluded the interfaces excluded
   * @return the annotations request builder
   */
  public final AnnotationsRequestBuilder withInterfacesExcluded(Set<String> interfacesExcluded) {
    this.annotationsRequest.setInterfacesExcluded(interfacesExcluded);
    return this;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

}
