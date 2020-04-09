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

package io.polygenesis.generators.java.rest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.generators.java.apiclients.rest.ApiClientRestMetamodelGenerator;
import io.polygenesis.generators.java.apiclients.rest.aspect.RestServiceAspectGenerator;
import io.polygenesis.generators.java.apiclients.rest.constants.RestConstantsProjectionExporter;
import io.polygenesis.generators.java.apiclients.rest.resource.ResourceGenerator;
import io.polygenesis.models.rest.Resource;
import io.polygenesis.models.rest.RestMetamodelRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ApiClientRestMetamodelGeneratorTest {

  private Path generationPath;
  private PackageName rootPackageName;
  private ContextName contextName;
  private ResourceGenerator resourceGenerator;
  private RestConstantsProjectionExporter restConstantsProjectionExporter;
  private RestServiceAspectGenerator restServiceAspectGenerator;
  private ApiClientRestMetamodelGenerator javaApiRestGenerator;

  /** Sets up. */
  @Before
  public void setUp() {
    generationPath = Paths.get("tmp");
    rootPackageName = new PackageName("com.oregor");
    contextName = new ContextName("someContext");
    resourceGenerator = mock(ResourceGenerator.class);
    restConstantsProjectionExporter = mock(RestConstantsProjectionExporter.class);
    restServiceAspectGenerator = mock(RestServiceAspectGenerator.class);

    javaApiRestGenerator =
        new ApiClientRestMetamodelGenerator(
            generationPath,
            rootPackageName,
            contextName,
            resourceGenerator,
            restConstantsProjectionExporter,
            restServiceAspectGenerator);
  }

  /** Should generate. */
  @Test
  @Ignore
  public void shouldGenerate() {
    javaApiRestGenerator.generate(createModelRepositories());

    verify(resourceGenerator).generate(any(), any());
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
