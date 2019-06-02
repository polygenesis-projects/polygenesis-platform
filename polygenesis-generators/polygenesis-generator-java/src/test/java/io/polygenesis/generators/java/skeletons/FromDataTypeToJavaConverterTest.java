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

package io.polygenesis.generators.java.skeletons;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import org.junit.Test;

/** @author Christos Tsakostas */
public class FromDataTypeToJavaConverterTest {

  private FromDataTypeToJavaConverter converter = new FromDataTypeToJavaConverter();

  @Test
  public void shouldGetCorrectDeclaredVariableType() {
    DataPrimitive model = DataPrimitive.of(PrimitiveType.INTEGER, new VariableName("var"));
    assertThat(converter.convert(model.getDataType())).isEqualTo("Integer");

    model = DataPrimitive.of(PrimitiveType.STRING, new VariableName("var"));
    assertThat(converter.convert(model.getDataType())).isEqualTo("String");

    model = DataPrimitive.of(PrimitiveType.BOOLEAN, new VariableName("var"));
    assertThat(converter.convert(model.getDataType())).isEqualTo("Boolean");
  }
}
