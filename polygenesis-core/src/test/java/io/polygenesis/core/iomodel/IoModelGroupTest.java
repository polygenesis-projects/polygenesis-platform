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
import io.polygenesis.core.data.ObjectName;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.datatype.PrimitiveType;
import org.junit.Test;

/** @author Christos Tsakostas */
public class IoModelGroupTest extends AbstractEqualityTest<IoModelGroup> {

  @Test
  public void shouldInitializeIoModelGroup() {
    IoModelGroup ioModelGroup =
        new IoModelGroup(
            new ObjectName("SomeClass"),
            new PackageName("com.dummy"),
            new VariableName("someVariableName"));

    assertThat(ioModelGroup).isNotNull();
    assertThat(ioModelGroup.getDataType()).isEqualTo(new ObjectName("SomeClass").getText());
    assertThat(ioModelGroup.getVariableName()).isEqualTo(new VariableName("someVariableName"));

    IoModelArray childIoModelArray =
        new IoModelArray(new VariableName("someVariableName"), ioModelGroup);
    ioModelGroup.addIoModelArray(childIoModelArray);

    IoModelGroup childIoModelGroup =
        new IoModelGroup(
            new ObjectName("SomeClass"),
            new PackageName("com.dummy"),
            new VariableName("someVariableName"));
    ioModelGroup.addIoModelGroup(childIoModelGroup);

    IoModelPrimitive childIoModelPrimitive =
        new IoModelPrimitive(
            PrimitiveType.STRING, new VariableName("someVariableName"), null, DataBusinessType.ANY);
    ioModelGroup.addIoModelPrimitive(childIoModelPrimitive);

    assertThat(ioModelGroup.getModels().size()).isEqualTo(3);

    assertThat(childIoModelArray.isPrimitive()).isFalse();
    assertThat(childIoModelGroup.isPrimitive()).isFalse();
    assertThat(childIoModelPrimitive.isPrimitive()).isTrue();
  }

  @Override
  public IoModelGroup createObject1() {
    return new IoModelGroup(
        new ObjectName("SomeClass"),
        new PackageName("com.dummy"),
        new VariableName("someVariableName"));
  }

  @Override
  public IoModelGroup createObject2() {
    return new IoModelGroup(
        new ObjectName("SomeClass"),
        new PackageName("com.dummy"),
        new VariableName("someOtherVariableName"));
  }
}
