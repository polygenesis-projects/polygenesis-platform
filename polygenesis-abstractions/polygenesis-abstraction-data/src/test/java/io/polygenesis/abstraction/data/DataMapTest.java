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

package io.polygenesis.abstraction.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.commons.valueobjects.VariableName;
import org.junit.Test;

/** @author Christos Tsakostas */
public class DataMapTest extends AbstractEqualityTest<DataMap> {

  @Test
  public void shouldInitializeDataPrimitive() {
    DataMap dataMap = createObject1();

    assertThat(dataMap.getAsDataMap()).isNotNull();

    assertThat(dataMap.getKey()).isNotNull();
    assertThat(dataMap.getValue()).isNotNull();
    assertThat(dataMap.getDataType()).isEqualTo(DataPrimaryType.MAP.name());

    assertThatThrownBy(() -> dataMap.getAsDataArray()).isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> dataMap.getAsDataGroup()).isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> dataMap.getAsDataPrimitive())
        .isInstanceOf(IllegalStateException.class);
  }

  @Override
  public DataMap createObject1() {
    return new DataMap(
        new VariableName("var1"),
        DataPurpose.any(),
        DataValidator.empty(),
        DataPrimitive.of(PrimitiveType.STRING, new VariableName("key")),
        DataPrimitive.of(PrimitiveType.STRING, new VariableName("value")));
  }

  @Override
  public DataMap createObject2() {
    return new DataMap(
        new VariableName("var2"),
        DataPurpose.any(),
        DataValidator.empty(),
        DataPrimitive.of(PrimitiveType.STRING, new VariableName("key")),
        DataPrimitive.of(PrimitiveType.STRING, new VariableName("value")));
  }
}
