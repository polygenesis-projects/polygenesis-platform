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

package io.polygenesis.core.iomodel;

import io.polygenesis.commons.text.Text;

/** @author Christos Tsakostas */
class TestIoModel extends IoModel {

  public TestIoModel(IoModelGroup parent) {
    super(parent);
  }

  public TestIoModel(Text dataType, Text variableName) {
    super(dataType, variableName);
  }

  public TestIoModel(Text dataType, Text variableName, IoModelGroup parent) {
    super(dataType, variableName, parent);
  }

  public TestIoModel(Text genericType, Text dataType, Text variableName) {
    super(genericType, dataType, variableName);
  }
}