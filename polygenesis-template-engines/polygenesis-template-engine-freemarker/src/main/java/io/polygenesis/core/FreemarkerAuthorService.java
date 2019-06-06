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

/**
 * Author used in generated code.
 *
 * @author Christos Tsakostas
 */
public class FreemarkerAuthorService {

  private static String author = "PolyGenesis Platform";

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private FreemarkerAuthorService() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // STATIC METHODS
  // ===============================================================================================

  /**
   * Gets author.
   *
   * @return the author
   */
  public static String getAuthor() {
    return author;
  }

  /**
   * Sets author.
   *
   * @param author the author
   */
  public static void setAuthor(String author) {
    FreemarkerAuthorService.author = author;
  }
}
