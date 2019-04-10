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

package io.polygenesis.generators.angular.ui.container;

/**
 * The enum Ui container folder type.
 *
 * @author Christos Tsakostas
 */
public enum UiContainerFolderType {
  /** Component ui container folder type. */
  COMPONENT("components"),
  /** Layout ui container folder type. */
  LAYOUT("layouts"),
  /** Page ui container folder type. */
  PAGE("pages");

  private final String type;

  UiContainerFolderType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return this.type;
  }
}
