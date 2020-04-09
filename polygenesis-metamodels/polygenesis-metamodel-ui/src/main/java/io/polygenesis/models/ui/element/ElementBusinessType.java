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

public class ElementBusinessType {

  private ElementBusinessType() {
    throw new IllegalStateException("Utility class");
  }

  public static final String ADDRESS_CITY = "ADDRESS_CITY";
  public static final String ADDRESS_POSTAL_CODE = "ADDRESS_POSTAL_CODE";
  public static final String ADDRESS_STREET = "ADDRESS_STREET";

  public static final String EMAIL = "EMAIL";
  public static final String PASS = "PASS";
  public static final String TEL = "TEL";
  public static final String URL = "URL";
}
