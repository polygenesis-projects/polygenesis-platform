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

package io.polygenesis.core;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.core.data.DataBusinessType;
import io.polygenesis.core.data.DataPrimitive;
import io.polygenesis.core.data.DataSource;
import io.polygenesis.core.data.DataValidator;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.core.data.VariableName;
import java.util.LinkedHashSet;
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
    return new DataPrimitive(
        DataSource.user(),
        new VariableName("someVariableName"),
        DataBusinessType.ANY,
        DataValidator.empty(),
        PrimitiveType.STRING,
        new LinkedHashSet<>());
  }

  private DataPrimitive createDataPrimitive2() {
    return new DataPrimitive(
        DataSource.user(),
        new VariableName("someOtherVariableName"),
        DataBusinessType.ANY,
        DataValidator.empty(),
        PrimitiveType.STRING,
        new LinkedHashSet<>());
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
