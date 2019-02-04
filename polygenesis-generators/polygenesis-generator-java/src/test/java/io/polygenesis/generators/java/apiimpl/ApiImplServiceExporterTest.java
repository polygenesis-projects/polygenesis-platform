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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.generators.java.domain.aggregateroot.AggregateRootProjectionConverter;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceName;
import io.polygenesis.models.domain.AggregateRoot;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ApiImplServiceExporterTest {

  private Path generationPath;
  private PackageName rootPackageName;
  private Service service;
  private AggregateRoot aggregateRoot;
  private FreemarkerService freemarkerService;
  private ApiImplServiceProjectionConverter apiImplServiceProjectionConverter;
  private ApiImplServiceExporter apiImplServiceExporter;
  private AggregateRootProjectionConverter aggregateRootProjectionConverter;

  @Before
  public void setUp() {
    generationPath = Paths.get("tmp");
    rootPackageName = new PackageName("com.oregor");
    service = mock(Service.class);
    aggregateRoot = mock(AggregateRoot.class);
    freemarkerService = mock(FreemarkerService.class);
    apiImplServiceProjectionConverter = mock(ApiImplServiceProjectionConverter.class);
    aggregateRootProjectionConverter = mock(AggregateRootProjectionConverter.class);
    apiImplServiceExporter =
        new ApiImplServiceExporter(
            freemarkerService, apiImplServiceProjectionConverter, aggregateRootProjectionConverter);
  }

  @Test
  public void shouldExport() {
    given(service.getPackageName()).willReturn(new PackageName("com.oregor"));
    given(service.getServiceName()).willReturn(new ServiceName("someServiceName"));

    apiImplServiceExporter.export(generationPath, rootPackageName, service, aggregateRoot);

    verify(apiImplServiceProjectionConverter).make(eq(service));
    verify(freemarkerService)
        .export(
            any(HashMap.class),
            eq("polygenesis-generator-java-api-impl/ApiImplService.java.ftl"),
            eq(Paths.get("tmp/src/main/java/com/oregor/SomeServiceNameImpl.java")));
  }
}
