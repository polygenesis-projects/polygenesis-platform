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

package io.polygenesis.generators.java.apiimpl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.polygenesis.commons.text.Name;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceModelRepository;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.DomainModelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/**
 * The type Java api impl generator test.
 *
 * @author Christos Tsakostas
 */
public class JavaApiImplGeneratorTest {

  private Path generationPath;
  private PackageName rootPackageName;
  private ApiImplServiceExporter apiImplServiceExporter;
  private ApiImplServiceTestExporter apiImplServiceTestExporter;
  private JavaApiImplGenerator javaApiImplGenerator;

  /**
   * Sets up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() {
    generationPath = Paths.get("tmp");
    rootPackageName = new PackageName("com.oregor");
    apiImplServiceExporter = mock(ApiImplServiceExporter.class);
    apiImplServiceTestExporter = mock(ApiImplServiceTestExporter.class);
    javaApiImplGenerator =
        new JavaApiImplGenerator(
            generationPath, rootPackageName, apiImplServiceExporter, apiImplServiceTestExporter);
  }

  /** Should generate. */
  @Test
  public void shouldGenerate() {
    javaApiImplGenerator.generate(createModelRepositories());

    verify(apiImplServiceExporter)
        .export(
            eq(generationPath), eq(rootPackageName), any(Service.class), any(AggregateRoot.class));
  }

  /** Should fail on missing service model repository. */
  @Test
  public void shouldFailOnMissingServiceModelRepository() {

    assertThatThrownBy(() -> javaApiImplGenerator.generate(new LinkedHashSet<>()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(
            "No Model Repository found for Class=io.polygenesis.models.api.ServiceModelRepository "
                + "in provided modelRepositories");
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<ModelRepository> createModelRepositories() {
    return new LinkedHashSet<>(
        Arrays.asList(createServiceModelRepository(), createDomainModelRepository()));
  }

  private ServiceModelRepository createServiceModelRepository() {
    Set<Service> services = new LinkedHashSet<>();

    Service service = mock(Service.class);
    given(service.getThingName()).willReturn(new ThingName("someThing"));
    services.add(service);

    return new ServiceModelRepository(services);
  }

  private DomainModelRepository createDomainModelRepository() {
    Set<AggregateRoot> aggregateRoots = new LinkedHashSet<>();

    AggregateRoot aggregateRoot = mock(AggregateRoot.class);
    given(aggregateRoot.getName()).willReturn(new Name("someThing"));
    aggregateRoots.add(aggregateRoot);

    return new DomainModelRepository(aggregateRoots);
  }
}