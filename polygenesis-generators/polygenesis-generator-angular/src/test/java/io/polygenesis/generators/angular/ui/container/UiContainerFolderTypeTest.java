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

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.generators.angular.exporters.ui.container.UiContainerFolderType;
import org.junit.Test;

/** @author Christos Tsakostas */
public class UiContainerFolderTypeTest {

  @Test
  public void testType() {
    assertThat(UiContainerFolderType.COMPONENT.toString()).isEqualTo("components");
    assertThat(UiContainerFolderType.LAYOUT.toString()).isEqualTo("layouts");
    assertThat(UiContainerFolderType.PAGE.toString()).isEqualTo("pages");
  }
}
