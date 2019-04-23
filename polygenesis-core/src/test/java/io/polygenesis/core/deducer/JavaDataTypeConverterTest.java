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

package io.polygenesis.core.deducer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.polygenesis.core.data.PrimitiveType;
import org.junit.Test;

/** @author Christos Tsakostas */
public class JavaDataTypeConverterTest {

  @Test
  public void shouldConvert() {
    JavaDataTypeConverter javaDataTypeConverter = new JavaDataTypeConverter();

    assertThat(javaDataTypeConverter.convert("void")).isEqualTo(PrimitiveType.VOID);
    assertThat(javaDataTypeConverter.convert("java.lang.String")).isEqualTo(PrimitiveType.STRING);
    assertThat(javaDataTypeConverter.convert("int")).isEqualTo(PrimitiveType.INTEGER);
    assertThat(javaDataTypeConverter.convert("java.lang.Integer")).isEqualTo(PrimitiveType.INTEGER);
    assertThat(javaDataTypeConverter.convert("long")).isEqualTo(PrimitiveType.LONG);
    assertThat(javaDataTypeConverter.convert("java.lang.Long")).isEqualTo(PrimitiveType.LONG);
    assertThat(javaDataTypeConverter.convert("boolean")).isEqualTo(PrimitiveType.BOOLEAN);
    assertThat(javaDataTypeConverter.convert("java.lang.Boolean")).isEqualTo(PrimitiveType.BOOLEAN);
  }

  @Test
  public void shouldFailToConvert() {
    JavaDataTypeConverter javaDataTypeConverter = new JavaDataTypeConverter();

    assertThatThrownBy(() -> javaDataTypeConverter.convert("abc"))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
