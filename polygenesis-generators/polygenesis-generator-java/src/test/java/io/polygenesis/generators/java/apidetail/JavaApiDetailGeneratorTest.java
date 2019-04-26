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

package io.polygenesis.generators.java.apidetail;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.core.ThingName;
import io.polygenesis.generators.java.exporters.apidetail.DomainObjectConverterExporter;
import io.polygenesis.generators.java.exporters.apidetail.JavaApiDetailGenerator;
import io.polygenesis.generators.java.exporters.apidetail.ServiceImplementationExporter;
import io.polygenesis.generators.java.exporters.apidetail.ServiceImplementationTestExporter;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import io.polygenesis.models.apiimpl.DomainEntityConverter;
import io.polygenesis.models.apiimpl.DomainEntityConverterMetamodelRepository;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.apiimpl.ServiceImplementationMetamodelRepository;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.DomainMetamodelRepository;
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
public class JavaApiDetailGeneratorTest {

  private Path generationPath;
  private PackageName rootPackageName;
  private ServiceImplementationExporter serviceImplementationExporter;
  private ServiceImplementationTestExporter serviceImplementationTestExporter;
  private DomainObjectConverterExporter domainObjectConverterExporter;
  private JavaApiDetailGenerator javaApiDetailGenerator;

  /**
   * Sets up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() {
    generationPath = Paths.get("tmp");
    rootPackageName = new PackageName("com.oregor");
    serviceImplementationExporter = mock(ServiceImplementationExporter.class);
    serviceImplementationTestExporter = mock(ServiceImplementationTestExporter.class);
    domainObjectConverterExporter = mock(DomainObjectConverterExporter.class);
    javaApiDetailGenerator =
        new JavaApiDetailGenerator(
            generationPath,
            rootPackageName,
            serviceImplementationExporter,
            serviceImplementationTestExporter,
            domainObjectConverterExporter);
  }

  /** Should generate. */
  @Test
  public void shouldGenerate() {
    javaApiDetailGenerator.generate(createModelRepositories());

    verify(serviceImplementationExporter)
        .export(eq(generationPath), any(ServiceImplementation.class));
  }

  /** Should fail on missing service model repository. */
  @Test
  public void shouldFailOnMissingServiceModelRepository() {

    assertThatThrownBy(() -> javaApiDetailGenerator.generate(new LinkedHashSet<>()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No Metamodel Repository found for");
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  private Set<MetamodelRepository> createModelRepositories() {
    return new LinkedHashSet<>(
        Arrays.asList(
            createServiceModelRepository(),
            createDomainModelRepository(),
            createDomainEntityConverterModelRepository(),
            createServiceImplementationModelRepository()));
  }

  private ServiceMetamodelRepository createServiceModelRepository() {
    Set<Service> services = new LinkedHashSet<>();

    Service service = mock(Service.class);
    given(service.getThingName()).willReturn(new ThingName("someThing"));
    services.add(service);

    return new ServiceMetamodelRepository(services);
  }

  private DomainMetamodelRepository createDomainModelRepository() {
    Set<AggregateRoot> aggregateRoots = new LinkedHashSet<>();

    AggregateRoot aggregateRoot = mock(AggregateRoot.class);
    given(aggregateRoot.getObjectName()).willReturn(new ObjectName("someThing"));
    aggregateRoots.add(aggregateRoot);

    return new DomainMetamodelRepository(aggregateRoots);
  }

  private DomainEntityConverterMetamodelRepository createDomainEntityConverterModelRepository() {
    Set<DomainEntityConverter> domainEntityConverters = new LinkedHashSet<>();

    return new DomainEntityConverterMetamodelRepository(domainEntityConverters);
  }

  private ServiceImplementationMetamodelRepository createServiceImplementationModelRepository() {
    Set<ServiceImplementation> serviceImplementations = new LinkedHashSet<>();

    ServiceImplementation serviceImplementation = mock(ServiceImplementation.class);
    Service service = mock(Service.class);
    given(service.getThingName()).willReturn(new ThingName("someThing"));
    given(serviceImplementation.getService()).willReturn(service);

    serviceImplementations.add(serviceImplementation);

    return new ServiceImplementationMetamodelRepository(serviceImplementations);
  }
}
