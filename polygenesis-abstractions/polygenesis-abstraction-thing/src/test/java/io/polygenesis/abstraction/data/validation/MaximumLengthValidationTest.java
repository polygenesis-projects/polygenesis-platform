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

package io.polygenesis.abstraction.data.validation;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.test.AbstractEqualityTest;
import org.junit.Test;

public class MaximumLengthValidationTest extends AbstractEqualityTest<MaximumLengthValidation> {

  @Test
  public void shouldInstantiate() {
    MaximumLengthValidation maximumLengthValidation = createObject1();

    assertThat(maximumLengthValidation.getValidationType())
        .isEqualTo(ValidationType.MAXIMUM_LENGTH);
    assertThat(maximumLengthValidation.getMaximumLength()).isEqualTo(100);
  }

  @Override
  public MaximumLengthValidation createObject1() {
    return new MaximumLengthValidation(100);
  }

  @Override
  public MaximumLengthValidation createObject2() {
    return new MaximumLengthValidation(200);
  }
}
