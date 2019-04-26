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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.generators.java.exporters.apidetail.ServiceImplementationClassRepresentable;
import io.polygenesis.generators.java.exporters.apidetail.ServiceImplementationExporter;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceName;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.models.domain.AggregateRoot;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ServiceImplementationExporterTest {

  private Path generationPath;
  private PackageName rootPackageName;
  private ServiceImplementation serviceImplementation;
  private Service service;
  private AggregateRoot aggregateRoot;
  private FreemarkerService freemarkerService;
  private ServiceImplementationClassRepresentable serviceImplementationClassRepresentable;
  private ServiceImplementationExporter serviceImplementationExporter;

  @Before
  public void setUp() {
    generationPath = Paths.get("tmp");
    rootPackageName = new PackageName("com.oregor");
    serviceImplementation = mock(ServiceImplementation.class);
    service = mock(Service.class);
    aggregateRoot = mock(AggregateRoot.class);
    freemarkerService = mock(FreemarkerService.class);
    serviceImplementationClassRepresentable = mock(ServiceImplementationClassRepresentable.class);

    serviceImplementationExporter =
        new ServiceImplementationExporter(
            freemarkerService, serviceImplementationClassRepresentable);
  }

  @Test
  public void shouldExport() {
    given(serviceImplementation.getService()).willReturn(service);
    given(service.getPackageName()).willReturn(new PackageName("com.oregor"));
    given(service.getServiceName()).willReturn(new ServiceName("someServiceName"));

    serviceImplementationExporter.export(generationPath, serviceImplementation);

    verify(serviceImplementationClassRepresentable).create(eq(serviceImplementation));

    // TODO: test
    //    verify(freemarkerService)
    //        .export(
    //            any(HashMap.class),
    //            eq("polygenesis-representation-java/Class.java.ftl"),
    //            eq(Paths.get("tmp/src/main/java/com/oregor/SomeServiceNameImpl.java")));
  }
}
