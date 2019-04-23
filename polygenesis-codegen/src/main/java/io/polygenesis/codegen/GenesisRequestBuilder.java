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

package io.polygenesis.codegen;

/**
 * The type Genesis request builder.
 *
 * @author Christos Tsakostas
 */
public class GenesisRequestBuilder {

  private final GenesisRequest genesisRequest;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private GenesisRequestBuilder() {
    this.genesisRequest = new GenesisRequest();
  }

  // ===============================================================================================
  // START
  // ===============================================================================================

  /**
   * Starts the GenesisRequest DSL.
   *
   * @return genesis request builder
   */
  public static GenesisRequestBuilder empty() {
    return new GenesisRequestBuilder();
  }

  // ===============================================================================================
  // END
  // ===============================================================================================

  /**
   * Validates and returns the GenesisRequest.
   *
   * @return genesis request
   */
  public final GenesisRequest build() {

    return genesisRequest;
  }

  // ===============================================================================================
  // WITH
  // ===============================================================================================

  /**
   * With annotations request.
   *
   * @param annotationsRequest the annotations request
   * @return the genesis request builder
   */
  public final GenesisRequestBuilder withAnnotationsRequest(AnnotationsRequest annotationsRequest) {
    this.genesisRequest.setAnnotationsRequest(annotationsRequest);
    return this;
  }

  // ===============================================================================================
  // ENDS ANNOTATIONS REQUEST BUILDER
  // ===============================================================================================

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

}
