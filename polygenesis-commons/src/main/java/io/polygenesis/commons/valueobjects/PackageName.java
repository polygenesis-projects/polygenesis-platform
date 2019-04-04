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

package io.polygenesis.commons.valueobjects;

import com.oregor.ddd4j.check.assertion.Assertion;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The type Package name.
 *
 * @author Christos Tsakostas
 */
public class PackageName {

  private static final Pattern pattern = Pattern.compile("^(?:\\w+|\\w+\\.\\w+)+$");

  private final String text;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Package name.
   *
   * @param text the text
   */
  public PackageName(String text) {
    guardText(text);
    this.text = text;
  }

  // ===============================================================================================
  // WITH
  // ===============================================================================================

  /**
   * With sub package package name.
   *
   * @param subPackage the sub package
   * @return the package name
   */
  public PackageName withSubPackage(String subPackage) {
    return new PackageName(String.format("%s.%s", getText(), subPackage));
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
   * To path path.
   *
   * @return the path
   */
  public Path toPath() {
    return Paths.get(getText().replaceAll("\\.", "/"));
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  private void guardText(String text) {
    Assertion.isTrue(
        pattern.matcher(text).matches(), String.format("Invalid package name=%s", text));
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
    PackageName that = (PackageName) o;
    return Objects.equals(text, that.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text);
  }
}
