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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.generators.java.apiclients.rest.JavaApiRestGeneratorFactory;
import io.polygenesis.generators.java.apiclients.rest.JavaApiRestMetamodelGenerator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import org.junit.Test;

/**
 * The type Java api rest generator factory test.
 *
 * @author Christos Tsakostas
 */
public class JavaApiRestMetamodelGeneratorFactoryTest {

  /**
   * Should fail to instantiate.
   *
   * @throws NoSuchMethodException the no such method exception
   */
  @Test
  public void shouldFailToInstantiate() throws NoSuchMethodException {
    Constructor<JavaApiRestGeneratorFactory> constructor =
        JavaApiRestGeneratorFactory.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    assertThatThrownBy(constructor::newInstance).isInstanceOf(InvocationTargetException.class);
  }

  /** Should create new instance. */
  @Test
  public void shouldCreateNewInstance() {
    JavaApiRestMetamodelGenerator javaApiGenerator =
        JavaApiRestGeneratorFactory.newInstance(
            Paths.get("tmp"), new PackageName("com.oregor"), new ContextName("someContext"));
    assertThat(javaApiGenerator).isNotNull();
  }
}
