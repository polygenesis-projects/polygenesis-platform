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

package io.polygenesis.abstraction.data.dsl;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.Set;
import org.junit.Test;

public class DataBuilderTest {

  @Test
  public void shouldCreate() {
    Set<Data> data =
        DataBuilder.create()
            .withTextProperty("description")
            .build()
            .withBooleanProperty("done")
            .build()
            .withThingIdentity(DataPrimitive.of(PrimitiveType.STRING, new VariableName("var1")))
            .build();

    assertThat(data.size()).isEqualTo(3);
  }
}
