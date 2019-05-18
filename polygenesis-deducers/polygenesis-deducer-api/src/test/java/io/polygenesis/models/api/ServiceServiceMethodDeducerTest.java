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

package io.polygenesis.models.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.test.ThingForTesting;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ServiceServiceMethodDeducerTest {

  private PackageName rootPackageName;
  private DtoDeducer dtoDeducer;
  private ServiceMethodDeducer serviceMethodDeducer;

  @Before
  public void setUp() {
    rootPackageName = new PackageName("com.oregor");
    dtoDeducer = mock(DtoDeducer.class);
    serviceMethodDeducer = new ServiceMethodDeducer(dtoDeducer);

    given(dtoDeducer.deduceRequestDto(any(Function.class), eq(rootPackageName)))
        .willReturn(mock(Dto.class));
    given(dtoDeducer.deduceResponseDto(any(Function.class), eq(rootPackageName)))
        .willReturn(mock(Dto.class));
  }

  @Test
  public void shouldDeduceCommandMethods() {
    Set<ServiceMethod> serviceMethods = new LinkedHashSet<>();
    serviceMethodDeducer.deduceCommandMethods(
        serviceMethods, ThingForTesting.create(), rootPackageName);

    assertThat(serviceMethods).isNotNull();
    assertThat(serviceMethods.size()).isEqualTo(4);
  }

  @Test
  public void shouldDeduceQueryMethods() {
    Set<ServiceMethod> serviceMethods = new LinkedHashSet<>();
    serviceMethodDeducer.deduceQueryMethods(
        serviceMethods, ThingForTesting.create(), rootPackageName);

    assertThat(serviceMethods).isNotNull();
    assertThat(serviceMethods.size()).isEqualTo(2);
  }
}
