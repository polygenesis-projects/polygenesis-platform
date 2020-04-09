/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.commons.valueobjects.VariableName;
import org.junit.Test;

public class DataTest extends AbstractEqualityTest<TestData> {

  @Test
  public void shouldInitializeTestData() {
    TestData testData = new TestData(new VariableName("someVariableName"));

    assertThat(testData).isNotNull();
    assertThat(testData.getDataType()).isEqualTo(PrimitiveType.STRING.name());
    assertThat(testData.getVariableName()).isEqualTo(new VariableName("someVariableName"));
  }

  // ===============================================================================================
  // Equality and Hash
  // ===============================================================================================
  @Override
  public TestData createObject1() {
    return new TestData(new VariableName("variableName"));
  }

  @Override
  public TestData createObject2() {
    return new TestData(new VariableName("someOtherVariableName"));
  }
}
