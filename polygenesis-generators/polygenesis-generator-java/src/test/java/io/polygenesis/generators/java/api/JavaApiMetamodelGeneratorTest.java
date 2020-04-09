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

package io.polygenesis.generators.java.api;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.api.dto.DtoGenerator;
import io.polygenesis.generators.java.api.service.ServiceGenerator;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class JavaApiMetamodelGeneratorTest {

  private Path generationPath;
  private ServiceGenerator serviceGenerator;
  private DtoGenerator dtoGenerator;
  private JavaApiMetamodelGenerator javaApiGenerator;

  @Before
  public void setUp() {
    generationPath = Paths.get("tmp");
    serviceGenerator = mock(ServiceGenerator.class);
    dtoGenerator = mock(DtoGenerator.class);
    javaApiGenerator =
        new JavaApiMetamodelGenerator(generationPath, serviceGenerator, dtoGenerator);
  }

  @Test
  public void shouldGenerate() {
    // TODO
    // javaApiGenerator.generate(createModelRepositories());

    // verify(serviceExporter).export(eq(generationPath), any(Service.class));
    // verify(dtoGenerator).generate(eq(generationPath), any(Service.class));
  }

  @Test
  public void shouldFailOnMissingServiceModelRepository() {
    assertThatThrownBy(() -> javaApiGenerator.generate(new LinkedHashSet<>()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(
            "No Metamodel Repository found for Class=io.polygenesis.models.api.ServiceMetamodelRepository "
                + "in provided modelRepositories");
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<MetamodelRepository<?>> createModelRepositories() {
    Set<Service> services = new LinkedHashSet<>();

    Service service = mock(Service.class);
    services.add(service);

    ServiceMetamodelRepository serviceModelRepository = new ServiceMetamodelRepository(services);

    return new LinkedHashSet<>(Arrays.asList(serviceModelRepository));
  }
}
