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

package io.polygenesis.commons.text;

import io.polygenesis.commons.assertions.Assertion;
import java.io.Serializable;
import java.util.Objects;

/**
 * Value object for string fields.
 *
 * <p>It enforces input to lower camel format.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractText implements Serializable {

  private static final long serialVersionUID = 1L;

  private String text;
  private String original;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Simple text.
   *
   * @param text the text
   */
  public AbstractText(String text) {
    setText(text);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets text.
   *
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * Gets original.
   *
   * @return the original
   */
  public String getOriginal() {
    return original;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void setText(String text) {
    Assertion.isNotEmpty(text, "text is required");
    this.text = TextConverter.toLowerCamel(text);
    this.original = text;
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
    AbstractText that = (AbstractText) o;
    return Objects.equals(text, that.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text);
  }
}
