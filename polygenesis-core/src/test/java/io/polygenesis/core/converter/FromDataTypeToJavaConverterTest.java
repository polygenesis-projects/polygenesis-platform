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

package io.polygenesis.core.converter;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.core.datatype.DataTypeName;
import io.polygenesis.core.datatype.PrimaryType;
import io.polygenesis.core.datatype.PrimitiveDataType;
import io.polygenesis.core.iomodel.IoModelPrimitive;
import io.polygenesis.core.iomodel.VariableName;
import org.junit.Test;

/** @author Christos Tsakostas */
public class FromDataTypeToJavaConverterTest {

  private FromDataTypeToJavaConverter converter = new FromDataTypeToJavaConverter();

  @Test
  public void shouldGetCorrectDeclaredVariableType() {
    IoModelPrimitive model =
        IoModelPrimitive.of(
            new PrimitiveDataType(new DataTypeName("xxx")), new VariableName("var"));
    assertThat(converter.getDeclaredVariableType(model)).isEqualTo("Xxx");

    model =
        IoModelPrimitive.of(
            new PrimitiveDataType(new DataTypeName(PrimaryType.STRING.name())),
            new VariableName("var"));
    assertThat(converter.getDeclaredVariableType(model)).isEqualTo("String");

    model =
        IoModelPrimitive.of(
            new PrimitiveDataType(new DataTypeName(PrimaryType.BOOLEAN.name())),
            new VariableName("var"));
    assertThat(converter.getDeclaredVariableType(model)).isEqualTo("java.lang.Boolean");
  }
}
