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

package io.polygenesis.commons.text;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Past tense.
 *
 * @author Christos Tsakostas
 */
public final class PastTense {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================
  private static final Set<String> pastTenseBlacklist = new LinkedHashSet<>(Arrays.asList("data"));

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private PastTense() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Make string.
   *
   * @param input the input
   * @return the string
   */
  public static String make(String input) {
    if (pastTenseBlacklist.contains(input.toLowerCase())) {
      return input;
    }

    String pastTense = input;

    if (input.endsWith("e")) {
      pastTense = pastTense + "d";
    } else {
      pastTense = pastTense + "ed";
    }

    return pastTense;
  }
}
