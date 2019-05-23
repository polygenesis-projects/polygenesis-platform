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

package io.polygenesis.craftsman;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;

/** @author Christos Tsakostas */
public class GenesisDefaultTest {

  /**
   * Should fail to instantiate.
   *
   * @throws NoSuchMethodException the no such method exception
   */
  @Test
  public void shouldFailToInstantiate() throws NoSuchMethodException {
    Constructor<GenesisDefault> constructor = GenesisDefault.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    assertThatThrownBy(constructor::newInstance).isInstanceOf(InvocationTargetException.class);
  }

  @Test
  public void javaDeducers() {
    assertThat(GenesisDefault.javaDeducers("rootPackageName").size()).isEqualTo(10);
  }

  @Test
  public void javaGenerators() {
    String exportPath = "tmp";
    String projectFolder = "project";
    String modulePrefix = "prefix";
    String context = "context";
    String tablePrefix = "tbl";
    String rootPackageName = "com.oregor";

    assertThat(
            GenesisDefault.javaGenerators(
                    exportPath, projectFolder, modulePrefix, context, tablePrefix, rootPackageName)
                .size())
        .isEqualTo(7);
  }

  @Test
  public void angularDeducers() {
    assertThat(GenesisDefault.angularDeducers().size()).isEqualTo(4);
  }

  @Test
  public void angularGenerators() {
    assertThat(GenesisDefault.angularGenerators("tmp").size()).isEqualTo(1);
  }
}
