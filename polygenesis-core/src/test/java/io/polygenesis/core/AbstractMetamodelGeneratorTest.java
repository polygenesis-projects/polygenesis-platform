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

package io.polygenesis.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class AbstractMetamodelGeneratorTest {

  private SomeMetamodelGenerator someGenerator;

  @Before
  public void setUp() {
    someGenerator = new SomeMetamodelGenerator(Paths.get("tmp"));
  }

  @Test
  public void getGenerationPath() {
    assertThat(someGenerator.getGenerationPath()).isEqualTo(Paths.get("tmp"));
  }
}
