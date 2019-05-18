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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.abstraction.thing.ThingRepositoryImpl;
import io.polygenesis.abstraction.thing.test.ThingForTesting;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.MetamodelRepository;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ApiDeducerTest {

  private PackageName rootPackageName;
  private ServiceDeducer serviceDeducer;
  private Thing thing;
  private ThingRepository thingRepository;

  @SuppressWarnings("rawtypes")
  private Set<AbstractionRepository> abstractionRepositories;

  @SuppressWarnings("rawtypes")
  private Set<MetamodelRepository> modelRepositories;

  private ApiDeducer apiDeducer;

  @Before
  public void setUp() {
    rootPackageName = new PackageName(ThingForTesting.ROOT_PACKAGE);
    serviceDeducer = mock(ServiceDeducer.class);
    thing = ThingForTesting.create();
    thingRepository = new ThingRepositoryImpl(new LinkedHashSet<>(Arrays.asList(thing)));
    modelRepositories = new LinkedHashSet<>();
    apiDeducer = new ApiDeducer(rootPackageName, serviceDeducer);
    abstractionRepositories = new LinkedHashSet<>(Arrays.asList(thingRepository));
  }

  @Test
  public void shouldDeduce() {
    Service service = mock(Service.class);
    given(serviceDeducer.deduceFrom(thing, rootPackageName))
        .willReturn(new LinkedHashSet<>(Arrays.asList(service)));

    ServiceMetamodelRepository serviceModelRepository =
        apiDeducer.deduce(abstractionRepositories, modelRepositories);

    verify(serviceDeducer).deduceFrom(eq(thing), eq(rootPackageName));

    assertThat(serviceModelRepository).isNotNull();
    assertThat(serviceModelRepository.getItems()).isNotNull();
    assertThat(serviceModelRepository.getItems().size()).isEqualTo(1);
  }

  @Test
  public void shouldFailToDeduceForEmptyThingRepository() {
    assertThatThrownBy(
            () ->
                apiDeducer.deduce(
                    new LinkedHashSet<>(
                        Arrays.asList(new ThingRepositoryImpl(new LinkedHashSet<>()))),
                    modelRepositories))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
