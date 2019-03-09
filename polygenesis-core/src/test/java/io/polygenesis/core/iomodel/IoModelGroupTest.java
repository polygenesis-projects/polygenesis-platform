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
import io.polygenesis.core.datatype.ClassDataType;
import io.polygenesis.core.datatype.DataTypeName;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.datatype.PrimitiveDataType;
import io.polygenesis.core.datatype.PrimitiveType;
import org.junit.Test;

/** @author Christos Tsakostas */
public class IoModelGroupTest extends AbstractEqualityTest<IoModelGroup> {

  @Test
  public void shouldInitializeIoModelGroup() {
    IoModelGroup ioModelGroup =
        new IoModelGroup(
            new ClassDataType(new DataTypeName("SomeClass"), new PackageName("com.dummy")),
            new VariableName("someVariableName"));

    assertThat(ioModelGroup).isNotNull();
    assertThat(ioModelGroup.getDataType())
        .isEqualTo(new ClassDataType(new DataTypeName("SomeClass"), new PackageName("com.dummy")));
    assertThat(ioModelGroup.getVariableName()).isEqualTo(new VariableName("someVariableName"));

    IoModelArray childIoModelArray =
        new IoModelArray(
            ioModelGroup,
            null,
            new ClassDataType(new DataTypeName("SomeClass"), new PackageName("com.dummy")),
            new VariableName("someVariableName"));
    ioModelGroup.addIoModelArray(childIoModelArray);

    IoModelGroup childIoModelGroup =
        new IoModelGroup(
            ioModelGroup,
            new ClassDataType(new DataTypeName("SomeClass"), new PackageName("com.dummy")),
            new VariableName("someVariableName"));
    ioModelGroup.addIoModelGroup(childIoModelGroup);

    IoModelPrimitive childIoModelPrimitive =
        new IoModelPrimitive(
            new PrimitiveDataType(PrimitiveType.STRING),
            new VariableName("someVariableName"),
            ioModelGroup,
            null,
            DataBusinessType.ANY);
    ioModelGroup.addIoModelPrimitive(childIoModelPrimitive);

    assertThat(ioModelGroup.getModels().size()).isEqualTo(3);

    assertThat(childIoModelArray.isPrimitive()).isFalse();
    assertThat(childIoModelGroup.isPrimitive()).isFalse();
    assertThat(childIoModelPrimitive.isPrimitive()).isTrue();
  }

  @Test
  public void shouldInitializeIoModelArrayWithParent() {
    IoModelGroup parent =
        new IoModelGroup(
            new ClassDataType(new DataTypeName("SomeClass"), new PackageName("com.dummy")),
            new VariableName("someVariableName"));

    IoModelGroup ioModelGroup = new IoModelGroup(parent);

    assertThat(ioModelGroup).isNotNull();
    assertThat(ioModelGroup.getParent()).isNotNull();
    assertThat(ioModelGroup.getParent().getDataType())
        .isEqualTo(new ClassDataType(new DataTypeName("SomeClass"), new PackageName("com.dummy")));
    assertThat(ioModelGroup.getParent().getVariableName())
        .isEqualTo(new VariableName("someVariableName"));
  }

  @Override
  public IoModelGroup createObject1() {
    return new IoModelGroup(
        new ClassDataType(new DataTypeName("SomeClass"), new PackageName("com.dummy")),
        new VariableName("someVariableName"));
  }

  @Override
  public IoModelGroup createObject2() {
    return new IoModelGroup(
        new ClassDataType(new DataTypeName("SomeClass"), new PackageName("com.dummy")),
        new VariableName("someOtherVariableName"));
  }
}
