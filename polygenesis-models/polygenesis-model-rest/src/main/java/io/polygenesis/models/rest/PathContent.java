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

package io.polygenesis.models.rest;

import java.util.Objects;

/**
 * The type Abstract path content.
 *
 * @author Christos Tsakostas
 */
public abstract class PathContent {

  private PathContentType pathContentType;
  private String content;

  // ===============================================================================================
  // CONSTRUCTORS
  // ===============================================================================================

  public PathContent(PathContentType pathContentType, String content) {
    setPathContentType(pathContentType);
    setContent(content);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets path content type.
   *
   * @return the path content type
   */
  public PathContentType getPathContentType() {
    return pathContentType;
  }

  /**
   * Gets content.
   *
   * @return the content
   */
  public String getContent() {
    return content;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets path content type.
   *
   * @param pathContentType the path content type
   */
  private void setPathContentType(PathContentType pathContentType) {
    this.pathContentType = pathContentType;
  }

  /**
   * Sets content.
   *
   * @param content the content
   */
  private void setContent(String content) {
    this.content = content;
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
    PathContent that = (PathContent) o;
    return pathContentType == that.pathContentType && Objects.equals(content, that.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pathContentType, content);
  }
}
