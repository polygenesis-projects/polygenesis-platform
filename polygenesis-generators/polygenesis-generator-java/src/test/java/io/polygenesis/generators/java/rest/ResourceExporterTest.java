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

package io.polygenesis.generators.java.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.Name;
import io.polygenesis.core.data.PackageName;
import io.polygenesis.models.rest.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

/**
 * The type Resource exporter test.
 *
 * @author Christos Tsakostas
 */
public class ResourceExporterTest {

  private Path generationPath;
  private PackageName rootPackageName;
  private Resource resource;
  private FreemarkerService freemarkerService;
  private ResourceClassRepresentable resourceClassRepresentable;
  private ResourceExporter resourceExporter;

  /** Sets up. */
  @Before
  public void setUp() {
    generationPath = Paths.get("tmp");
    rootPackageName = new PackageName("com.oregor");
    resource = mock(Resource.class);
    freemarkerService = mock(FreemarkerService.class);
    resourceClassRepresentable = mock(ResourceClassRepresentable.class);
    resourceExporter = new ResourceExporter(freemarkerService, resourceClassRepresentable);
  }

  /** Should export. */
  @Test
  public void shouldExport() {
    given(resource.getPackageName()).willReturn(new PackageName("com.oregor"));
    given(resource.getName()).willReturn(new Name("someResource"));

    resourceExporter.export(generationPath, resource, rootPackageName);

    verify(freemarkerService)
        .export(
            any(HashMap.class),
            eq("polygenesis-representation-java/Class.java.ftl"),
            eq(Paths.get("tmp/src/main/java/com/oregor/SomeResourceRestService.java")));
  }
}
