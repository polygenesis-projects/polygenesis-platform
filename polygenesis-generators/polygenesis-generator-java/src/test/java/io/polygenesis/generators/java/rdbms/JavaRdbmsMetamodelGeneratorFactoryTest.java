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

package io.polygenesis.generators.java.rdbms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.generators.java.exporters.rdbms.JavaRdbmsGeneratorFactory;
import io.polygenesis.generators.java.exporters.rdbms.JavaRdbmsMetamodelGenerator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import org.junit.Test;

/** @author Christos Tsakostas */
public class JavaRdbmsMetamodelGeneratorFactoryTest {

  @Test
  public void shouldFailToInstantiate() throws NoSuchMethodException {
    Constructor<JavaRdbmsGeneratorFactory> constructor =
        JavaRdbmsGeneratorFactory.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    assertThatThrownBy(constructor::newInstance).isInstanceOf(InvocationTargetException.class);
  }

  @Test
  public void shouldCreateNewInstance() {
    JavaRdbmsMetamodelGenerator javaApiGenerator =
        JavaRdbmsGeneratorFactory.newInstance(
            Paths.get("tmp"), new PackageName("com.oregor"), new ObjectName("account"));
    assertThat(javaApiGenerator).isNotNull();
  }
}
