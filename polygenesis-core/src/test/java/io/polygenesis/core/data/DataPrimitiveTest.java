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

package io.polygenesis.core.data;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.test.AbstractEqualityTest;
import java.util.LinkedHashSet;
import org.junit.Test;

/** @author Christos Tsakostas */
public class DataPrimitiveTest extends AbstractEqualityTest<DataPrimitive> {

  @Test
  public void shouldInitializeIoModelPrimitive() {
    DataPrimitive ioModelPrimitive = createObject1();

    assertThat(ioModelPrimitive).isNotNull();
    assertThat(ioModelPrimitive.getDataType()).isEqualTo(PrimitiveType.STRING.name());
    assertThat(ioModelPrimitive.getVariableName()).isEqualTo(new VariableName("someVariableName"));

    assertThat(ioModelPrimitive.getAnnotations()).isNotNull();
    assertThat(ioModelPrimitive.getAnnotations().size()).isEqualTo(0);

    assertThat(ioModelPrimitive.getThingIdentity()).isFalse();
  }

  @Test
  public void shouldInitializeIoModelPrimitiveWithParent() {
    DataPrimitive ioModelPrimitive =
        new DataPrimitive(
            PrimitiveType.STRING,
            new VariableName("someVariableName"),
            new LinkedHashSet<>(),
            DataBusinessType.ANY);

    assertThat(ioModelPrimitive).isNotNull();

    assertThat(ioModelPrimitive.getAnnotations()).isNotNull();
    assertThat(ioModelPrimitive.getAnnotations().size()).isEqualTo(0);
  }

  // ===============================================================================================
  // Equality and Hash
  // ===============================================================================================

  @Override
  public DataPrimitive createObject1() {
    return new DataPrimitive(
        PrimitiveType.STRING,
        new VariableName("someVariableName"),
        new LinkedHashSet<>(),
        DataBusinessType.ANY);
  }

  @Override
  public DataPrimitive createObject2() {
    return new DataPrimitive(
        PrimitiveType.STRING,
        new VariableName("someOtherVariableName"),
        new LinkedHashSet<>(),
        DataBusinessType.ANY);
  }
}