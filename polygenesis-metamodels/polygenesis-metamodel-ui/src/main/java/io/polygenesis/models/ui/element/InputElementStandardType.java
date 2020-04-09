/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.models.ui.element;

public class InputElementStandardType {

  private InputElementStandardType() {
    throw new IllegalStateException("Utility class");
  }

  public static final String EDIT_TEXT = "EDIT_TEXT";
  public static final String EDIT_LONG_TEXT = "EDIT_LONG_TEXT";
  public static final String EDIT_NUMBER = "EDIT_NUMBER";
  public static final String EDIT_DATE = "EDIT_DATE";

  public static final String SELECT_SINGLE = "SELECT_SINGLE";
  public static final String SELECT_MULTIPLE = "SELECT_MULTIPLE";

  public static final String TOGGLE = "TOGGLE";
  public static final String TOGGLE_WITH_RESET = "TOGGLE_WITH_RESET";
}
