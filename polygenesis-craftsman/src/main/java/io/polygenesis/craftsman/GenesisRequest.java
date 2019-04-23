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

/**
 * The type Genesis Request.
 *
 * @author Christos Tsakostas
 */
public class GenesisRequest {

  private AnnotationsRequest annotationsRequest;
  private String generationPath;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets annotations request.
   *
   * @return the annotations request
   */
  public AnnotationsRequest getAnnotationsRequest() {
    return annotationsRequest;
  }

  /**
   * Gets generation path.
   *
   * @return the generation path
   */
  public String getGenerationPath() {
    return generationPath;
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets annotations request.
   *
   * @param annotationsRequest the annotations request
   */
  public void setAnnotationsRequest(AnnotationsRequest annotationsRequest) {
    this.annotationsRequest = annotationsRequest;
  }

  /**
   * Sets generation path.
   *
   * @param generationPath the generation path
   */
  public void setGenerationPath(String generationPath) {
    this.generationPath = generationPath;
  }
}
