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

package io.polygenesis.generators.java.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.PackageName;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/** @author Christos Tsakostas */
public class JavaDomainMetamodelGeneratorFactoryTest {

  @Test
  public void shouldFailToInstantiate() throws NoSuchMethodException {
    Constructor<JavaDomainMetamodelGeneratorFactory> constructor =
        JavaDomainMetamodelGeneratorFactory.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    assertThatThrownBy(constructor::newInstance).isInstanceOf(InvocationTargetException.class);
  }

  @Test
  public void shouldCreateNewInstance() {
    JavaDomainMetamodelGenerator javaApiGenerator =
        JavaDomainMetamodelGeneratorFactory.newInstance(
            Paths.get("tmp"), new ContextName("context"), new PackageName("com.oregor"), "prf_");
    Assertions.assertThat(javaApiGenerator).isNotNull();
  }
}
