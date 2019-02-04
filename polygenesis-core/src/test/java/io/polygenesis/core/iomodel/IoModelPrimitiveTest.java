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
import java.util.LinkedHashSet;
import org.junit.Test;

/** @author Christos Tsakostas */
public class IoModelPrimitiveTest extends AbstractEqualityTest<IoModelPrimitive> {

  @Test
  public void shouldInitializeIoModelPrimitive() {
    IoModelPrimitive ioModelPrimitive = createObject1();

    assertThat(ioModelPrimitive).isNotNull();
    assertThat(ioModelPrimitive.getDataType())
        .isEqualTo(new PrimitiveDataType(PrimitiveType.STRING));
    assertThat(ioModelPrimitive.getVariableName()).isEqualTo(new VariableName("someVariableName"));

    assertThat(ioModelPrimitive.getAnnotations()).isNotNull();
    assertThat(ioModelPrimitive.getAnnotations().size()).isEqualTo(0);

    assertThat(ioModelPrimitive.getThingIdentity()).isFalse();
  }

  @Test
  public void shouldInitializeIoModelPrimitiveWithParent() {
    IoModelGroup parent =
        new IoModelGroup(
            new ClassDataType(new DataTypeName("SomeClass"), new PackageName("com.dummy")),
            new VariableName("someVariableName"));

    IoModelPrimitive ioModelPrimitive =
        new IoModelPrimitive(
            new PrimitiveDataType(PrimitiveType.STRING),
            new VariableName("someVariableName"),
            parent,
            new LinkedHashSet<>(),
            false);

    assertThat(ioModelPrimitive).isNotNull();
    assertThat(ioModelPrimitive.getParent()).isNotNull();
    assertThat(ioModelPrimitive.getParent().getDataType())
        .isEqualTo(new ClassDataType(new DataTypeName("SomeClass"), new PackageName("com.dummy")));
    assertThat(ioModelPrimitive.getParent().getVariableName())
        .isEqualTo(new VariableName("someVariableName"));

    assertThat(ioModelPrimitive.getAnnotations()).isNotNull();
    assertThat(ioModelPrimitive.getAnnotations().size()).isEqualTo(0);
  }

  // ===============================================================================================
  // Equality and Hash
  // ===============================================================================================

  @Override
  public IoModelPrimitive createObject1() {
    return new IoModelPrimitive(
        new PrimitiveDataType(PrimitiveType.STRING),
        new VariableName("someVariableName"),
        new LinkedHashSet<>(),
        false);
  }

  @Override
  public IoModelPrimitive createObject2() {
    return new IoModelPrimitive(
        new PrimitiveDataType(PrimitiveType.STRING),
        new VariableName("someOtherVariableName"),
        new LinkedHashSet<>(),
        false);
  }
}
