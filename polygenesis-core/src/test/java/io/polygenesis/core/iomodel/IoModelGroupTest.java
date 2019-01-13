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

import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.commons.text.Text;
import org.junit.Test;

/** @author Christos Tsakostas */
public class IoModelGroupTest extends AbstractEqualityTest<IoModelGroup> {

  @Test
  public void shouldInitializeIoModelGroup() {
    IoModelGroup ioModelGroup =
        new IoModelGroup(
            new Text("java.util.list"), new Text("java.lang.String"), new Text("someVariableName"));

    assertThat(ioModelGroup).isNotNull();
    assertThat(ioModelGroup.getGenericType()).isEqualTo(new Text("java.util.list"));
    assertThat(ioModelGroup.getDataType()).isEqualTo(new Text("java.lang.String"));
    assertThat(ioModelGroup.getVariableName()).isEqualTo(new Text("someVariableName"));

    IoModelArray childIoModelArray =
        new IoModelArray(
            new Text("java.util.list"), new Text("java.lang.String"), new Text("someVariableName"));
    ioModelGroup.addIoModelArray(childIoModelArray);

    IoModelGroup childIoModelGroup =
        new IoModelGroup(
            new Text("java.util.list"), new Text("java.lang.String"), new Text("someVariableName"));
    ioModelGroup.addIoModelGroup(childIoModelGroup);

    IoModelPrimitive childIoModelPrimitive =
        new IoModelPrimitive(new Text("java.lang.String"), new Text("someVariableName"), null);
    ioModelGroup.addIoModelPrimitive(childIoModelPrimitive);

    assertThat(ioModelGroup.getModels().size()).isEqualTo(3);

    assertThat(childIoModelArray.isPrimitive()).isTrue();
    assertThat(childIoModelGroup.isPrimitive()).isFalse();
    assertThat(childIoModelPrimitive.isPrimitive()).isTrue();
  }

  @Test
  public void shouldInitializeIoModelArrayWithParent() {
    IoModelGroup parent =
        new IoModelGroup(
            new Text("java.util.list"), new Text("java.lang.String"), new Text("someVariableName"));

    IoModelGroup ioModelGroup = new IoModelGroup(parent);

    assertThat(ioModelGroup).isNotNull();
    assertThat(ioModelGroup.getParent()).isNotNull();
    assertThat(ioModelGroup.getParent().getGenericType()).isEqualTo(new Text("java.util.list"));
    assertThat(ioModelGroup.getParent().getDataType()).isEqualTo(new Text("java.lang.String"));
    assertThat(ioModelGroup.getParent().getVariableName()).isEqualTo(new Text("someVariableName"));
  }

  @Override
  public IoModelGroup createObject1() {
    return new IoModelGroup(
        new Text("java.util.list"), new Text("java.lang.String"), new Text("someVariableName"));
  }

  @Override
  public IoModelGroup createObject2() {
    return new IoModelGroup(
        new Text("java.util.list"),
        new Text("java.lang.String"),
        new Text("someOtherVariableName"));
  }
}
