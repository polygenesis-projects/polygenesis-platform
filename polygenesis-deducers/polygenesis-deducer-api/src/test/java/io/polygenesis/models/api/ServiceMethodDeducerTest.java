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

package io.polygenesis.models.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import io.polygenesis.core.Function;
import io.polygenesis.core.test.ThingForTesting;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ServiceMethodDeducerTest {

  private DtoDeducer dtoDeducer;
  private ServiceMethodDeducer serviceMethodDeducer;

  @Before
  public void setUp() {
    dtoDeducer = mock(DtoDeducer.class);
    serviceMethodDeducer = new ServiceMethodDeducer(dtoDeducer);

    given(dtoDeducer.deduceRequestDto(any(Function.class))).willReturn(mock(Dto.class));
    given(dtoDeducer.deduceResponseDto(any(Function.class))).willReturn(mock(Dto.class));
  }

  @Test
  public void shouldDeduceCommandMethods() {
    Set<Method> methods = serviceMethodDeducer.deduceCommandMethods(ThingForTesting.create());

    assertThat(methods).isNotNull();
    assertThat(methods.size()).isEqualTo(4);
  }

  @Test
  public void shouldDeduceQueryMethods() {
    Set<Method> methods = serviceMethodDeducer.deduceQueryMethods(ThingForTesting.create());

    assertThat(methods).isNotNull();
    assertThat(methods.size()).isEqualTo(2);
  }
}
