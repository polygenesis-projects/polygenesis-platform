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

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.text.Text;
import org.junit.Test;

/** @author Christos Tsakostas */
public class IoModelArrayTest {

  @Test
  public void shouldInitializeIoModelArray() {
    IoModelArray ioModelArray =
        new IoModelArray(
            new Text("java.util.list"), new Text("java.lang.String"), new Text("someVariableName"));

    assertThat(ioModelArray).isNotNull();
    assertThat(ioModelArray.getGenericType()).isEqualTo(new Text("java.util.list"));
    assertThat(ioModelArray.getDataType()).isEqualTo(new Text("java.lang.String"));
    assertThat(ioModelArray.getVariableName()).isEqualTo(new Text("someVariableName"));
  }

  @Test
  public void shouldInitializeIoModelArrayWithParent() {
    IoModelGroup parent =
        new IoModelGroup(
            new Text("java.util.list"), new Text("java.lang.String"), new Text("someVariableName"));

    IoModelArray ioModelArray = new IoModelArray(parent);

    assertThat(ioModelArray).isNotNull();
    assertThat(ioModelArray.getParent()).isNotNull();
    assertThat(ioModelArray.getParent().getGenericType()).isEqualTo(new Text("java.util.list"));
    assertThat(ioModelArray.getParent().getDataType()).isEqualTo(new Text("java.lang.String"));
    assertThat(ioModelArray.getParent().getVariableName()).isEqualTo(new Text("someVariableName"));
  }
}
