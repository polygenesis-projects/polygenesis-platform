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

import io.polygenesis.abstraction.thing.CqsType;
import io.polygenesis.abstraction.thing.ThingName;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ServiceMetamodelRepositoryTest {

  private ServiceMetamodelRepository serviceMetamodelRepository;

  @Before
  public void setUp() {
    serviceMetamodelRepository = new ServiceMetamodelRepository(items());
  }

  @Test
  public void getServicesBy() {
    assertThat(serviceMetamodelRepository.getServicesBy(new ThingName("someThingName")))
        .isNotNull();
  }

  private Set<Service> items() {
    return new LinkedHashSet<>(
        Arrays.asList(
            new Service(
                new PackageName("com.oregor"),
                new ServiceName("someServiceName"),
                new LinkedHashSet<>(),
                CqsType.COMMAND,
                new ThingName("someThingName"),
                new LinkedHashSet<>())));
  }
}
