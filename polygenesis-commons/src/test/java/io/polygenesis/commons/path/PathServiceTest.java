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

package io.polygenesis.commons.path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class PathServiceTest {

  /**
   * Should fail to instantiate.
   *
   * @throws NoSuchMethodException the no such method exception
   */
  @Test
  public void shouldFailToInstantiate() throws NoSuchMethodException {
    Constructor<PathService> constructor = PathService.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    assertThatThrownBy(constructor::newInstance).isInstanceOf(InvocationTargetException.class);
  }

  /** Should successfully ensure path. */
  @Test
  public void shouldSuccessfullyEnsurePath() throws IOException {
    FileUtils.deleteDirectory(new File("tmp"));

    PathService.ensurePath(Paths.get("tmp"));
    assertThat(new File("tmp")).exists();
  }

  /** Should fail to ensure path for null input. */
  @Test
  public void shouldFailToEnsurePathForNullInput() {
    assertThatThrownBy(() -> PathService.ensurePath(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  /** Should fail to ensure path for invalid input. */
  @Test
  public void shouldFailToEnsurePathForInvalidInput() {
    StringBuilder invalidPath = new StringBuilder();
    for (int i = 0; i < 1024; i++) {
      invalidPath.append("a");
    }
    assertThatThrownBy(() -> PathService.ensurePath(Paths.get(invalidPath.toString())))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
