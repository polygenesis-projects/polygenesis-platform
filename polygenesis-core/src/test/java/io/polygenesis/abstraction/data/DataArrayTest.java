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
public class DataArrayTest extends AbstractEqualityTest<DataArray> {

  @Test
  public void shouldInitializeDataArray() {
    DataArray dataArray = createObject1();

    assertThat(dataArray).isNotNull();
    assertThat(dataArray.getDataType()).isEqualTo(DataPrimaryType.ARRAY.name());
    assertThat(dataArray.getVariableName()).isEqualTo(new VariableName("someVariableNames"));
    assertThat(dataArray.getArrayElement())
        .isEqualTo(DataPrimitive.of(PrimitiveType.STRING, new VariableName("var1")));

    assertThat(dataArray.getAsDataArray()).isNotNull();

    assertThatThrownBy(() -> dataArray.getAsDataObject()).isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> dataArray.getAsDataPrimitive())
        .isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> dataArray.getAsDataMap()).isInstanceOf(IllegalStateException.class);
  }

  @Override
  public DataArray createObject1() {
    return new DataArray(
        new VariableName("someVariableName"),
        DataPrimitive.of(PrimitiveType.STRING, new VariableName("var1")));
  }

  @Override
  public DataArray createObject2() {
    return new DataArray(
        new VariableName("someVariableName"),
        DataPrimitive.of(PrimitiveType.STRING, new VariableName("var2")));
  }
}
