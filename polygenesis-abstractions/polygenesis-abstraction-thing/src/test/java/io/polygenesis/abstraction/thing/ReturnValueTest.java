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

package io.polygenesis.abstraction.thing;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.commons.valueobjects.VariableName;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ReturnValueTest extends AbstractEqualityTest<ReturnValue> {

  @Test
  public void shouldSucceedToInstantiate() {
    ReturnValue returnValue = new ReturnValue(createDataPrimitive1());

    assertThat(returnValue).isNotNull();
    assertThat(returnValue.getData()).isEqualTo(createDataPrimitive1());
  }

  private DataPrimitive createDataPrimitive1() {
    return DataPrimitive.of(PrimitiveType.STRING, new VariableName("someVariableName"));
  }

  private DataPrimitive createDataPrimitive2() {
    return DataPrimitive.of(PrimitiveType.STRING, new VariableName("someOtherVariableName"));
  }

  @Override
  public ReturnValue createObject1() {
    return new ReturnValue(createDataPrimitive1());
  }

  @Override
  public ReturnValue createObject2() {
    return new ReturnValue(createDataPrimitive2());
  }
}
