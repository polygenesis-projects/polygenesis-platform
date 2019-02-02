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
import io.polygenesis.core.datatype.PrimaryType;
import io.polygenesis.core.datatype.PrimitiveDataType;
import org.junit.Test;

/** @author Christos Tsakostas */
public class IoModelTest extends AbstractEqualityTest<TestIoModel> {

  @Test
  public void shouldInitializeTestIoModel() {
    IoModelGroup parent =
        new IoModelGroup(
            new GenericTypeName("java.util.list"),
            new ClassDataType(
                new DataTypeName(PrimaryType.STRING.name()), new PackageName("com.dummy")),
            new VariableName("someVariableName"));

    TestIoModel testIoModel =
        new TestIoModel(
            new PrimitiveDataType(new DataTypeName(PrimaryType.STRING.name())),
            new VariableName("someVariableName"),
            parent);

    assertThat(testIoModel).isNotNull();
    assertThat(testIoModel.getGenericType()).isNull();
    assertThat(testIoModel.getDataType())
        .isEqualTo(new PrimitiveDataType(new DataTypeName(PrimaryType.STRING.name())));
    assertThat(testIoModel.getVariableName()).isEqualTo(new VariableName("someVariableName"));
  }

  // ===============================================================================================
  // Equality and Hash
  // ===============================================================================================
  @Override
  public TestIoModel createObject1() {
    return new TestIoModel(
        new GenericTypeName("genericType"),
        new ClassDataType(
            new DataTypeName(PrimaryType.STRING.name()), new PackageName("com.dummy")),
        new VariableName("variableName"));
  }

  @Override
  public TestIoModel createObject2() {
    return new TestIoModel(
        new GenericTypeName("genericType"),
        new ClassDataType(
            new DataTypeName(PrimaryType.STRING.name()), new PackageName("com.dummy")),
        new VariableName("someOtherVariableName"));
  }
}
