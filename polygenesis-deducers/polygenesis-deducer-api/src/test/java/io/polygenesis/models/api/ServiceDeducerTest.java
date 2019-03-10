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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import io.polygenesis.core.Thing;
import io.polygenesis.core.data.PackageName;
import io.polygenesis.core.test.ThingForTesting;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ServiceDeducerTest {

  private Thing thing;
  private ServiceMethodDeducer serviceMethodDeducer;
  private DtoDeducer dtoDeducer;
  private ServiceDeducer serviceDeducer;

  @Before
  public void setUp() {
    thing = ThingForTesting.create();
    serviceMethodDeducer = mock(ServiceMethodDeducer.class);
    dtoDeducer = mock(DtoDeducer.class);
    serviceDeducer = new ServiceDeducer(serviceMethodDeducer, dtoDeducer);
  }

  @Test
  public void shouldDeduceFromThing() {
    Method methodCommand = mock(Method.class);
    given(serviceMethodDeducer.deduceCommandMethods(thing))
        .willReturn(new LinkedHashSet<>(Arrays.asList(methodCommand)));

    Method methodQuery = mock(Method.class);
    given(serviceMethodDeducer.deduceQueryMethods(thing))
        .willReturn(new LinkedHashSet<>(Arrays.asList(methodQuery)));

    Set<Service> services =
        serviceDeducer.deduceFrom(thing, new PackageName(ThingForTesting.ROOT_PACKAGE));

    assertThat(services).isNotNull();
    assertThat(services.size()).isEqualTo(2);
  }
}
