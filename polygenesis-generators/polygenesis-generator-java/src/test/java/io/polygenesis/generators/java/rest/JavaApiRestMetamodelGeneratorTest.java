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

package io.polygenesis.generators.java.rest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.rest.constants.RestConstantsProjectionExporter;
import io.polygenesis.generators.java.rest.resource.ResourceExporter;
import io.polygenesis.generators.java.rest.resource.testing.ResourceTestExporter;
import io.polygenesis.models.rest.Resource;
import io.polygenesis.models.rest.RestMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/**
 * The type Java api rest generator test.
 *
 * @author Christos Tsakostas
 */
public class JavaApiRestMetamodelGeneratorTest {

  private Path generationPath;
  private PackageName rootPackageName;
  private ObjectName contextName;
  private ResourceExporter resourceExporter;
  private ResourceTestExporter resourceTestExporter;
  private RestConstantsProjectionExporter restConstantsProjectionExporter;
  private JavaApiRestMetamodelGenerator javaApiRestGenerator;

  /** Sets up. */
  @Before
  public void setUp() {
    generationPath = Paths.get("tmp");
    rootPackageName = new PackageName("com.oregor");
    contextName = new ObjectName("someContext");
    resourceExporter = mock(ResourceExporter.class);
    resourceTestExporter = mock(ResourceTestExporter.class);
    restConstantsProjectionExporter = mock(RestConstantsProjectionExporter.class);
    javaApiRestGenerator =
        new JavaApiRestMetamodelGenerator(
            generationPath,
            rootPackageName,
            contextName,
            resourceExporter,
            resourceTestExporter,
            restConstantsProjectionExporter);
  }

  /** Should generate. */
  @Test
  public void shouldGenerate() {
    javaApiRestGenerator.generate(createModelRepositories());

    verify(resourceExporter).export(eq(generationPath), any(Resource.class), eq(rootPackageName));
  }

  /** Should fail on missing rest model repository. */
  @Test
  public void shouldFailOnMissingRestModelRepository() {

    assertThatThrownBy(() -> javaApiRestGenerator.generate(new LinkedHashSet<>()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(
            "No Metamodel Repository found for Class=io.polygenesis.models.rest.RestMetamodelRepository "
                + "in provided modelRepositories");
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<MetamodelRepository<?>> createModelRepositories() {
    Set<Resource> resources = new LinkedHashSet<>();

    Resource resource = mock(Resource.class);
    resources.add(resource);

    RestMetamodelRepository restModelRepository = new RestMetamodelRepository(resources);

    return new LinkedHashSet<>(Arrays.asList(restModelRepository));
  }
}
