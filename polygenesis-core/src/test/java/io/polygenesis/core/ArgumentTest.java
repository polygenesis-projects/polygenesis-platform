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

package io.polygenesis.core;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.core.data.DataBusinessType;
import io.polygenesis.core.data.IoModelPrimitive;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.core.data.VariableName;
import java.util.LinkedHashSet;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ArgumentTest extends AbstractEqualityTest<Argument> {

  @Test
  public void shouldSucceedToInstantiate() {
    Argument argument = new Argument(createIoModelPrimitive1());

    assertThat(argument).isNotNull();
    assertThat(argument.getModel()).isEqualTo(createIoModelPrimitive1());
  }

  private IoModelPrimitive createIoModelPrimitive1() {
    return new IoModelPrimitive(
        PrimitiveType.STRING,
        new VariableName("someVariableName"),
        new LinkedHashSet<>(),
        DataBusinessType.ANY);
  }

  private IoModelPrimitive createIoModelPrimitive2() {
    return new IoModelPrimitive(
        PrimitiveType.STRING,
        new VariableName("someOtherVariableName"),
        new LinkedHashSet<>(),
        DataBusinessType.ANY);
  }

  @Override
  public Argument createObject1() {
    return new Argument(createIoModelPrimitive1());
  }

  @Override
  public Argument createObject2() {
    return new Argument(createIoModelPrimitive2());
  }
}
