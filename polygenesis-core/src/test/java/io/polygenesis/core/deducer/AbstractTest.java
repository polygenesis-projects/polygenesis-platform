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

package io.polygenesis.core.deducer;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.BeforeClass;

/**
 * Base class for tests in core deducer.
 *
 * <p>Most of the tests in this module are not pure unit tests, but rather integration tests. Since
 * core deducer is heavily based on reflections it would not make sense to mock a complex hierarchy
 * of objects, but instead let the code be executed without mocking.
 *
 * <p>This abstract test instantiates #candidateClasses which contains {#link TestService}.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractTest {

  protected static Class<?> testServiceClass;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    ClassScanner classScanner = new ClassScanner();

    Set<String> packages = new LinkedHashSet<>();
    packages.add("io.polygenesis.core.sample");

    Set<String> interfaces = new LinkedHashSet<>();
    interfaces.add("io.polygenesis.core.sample.TestService");

    Set<Class<?>> candidateClasses =
        classScanner.identify(packages, interfaces, InclusionOrExclusionType.INCLUDE);
    assertThat(candidateClasses.size()).isEqualTo(1);

    Optional<Class<?>> optionalClass = candidateClasses.stream().findFirst();
    assertThat(optionalClass.isPresent()).isTrue();

    optionalClass.ifPresent(aClass -> testServiceClass = aClass);
  }

  protected static Method getMethodByName(String methodName) {
    Optional<Method> optionalMethod =
        Stream.of(testServiceClass.getMethods())
            .filter(method -> method.getName().equals(methodName))
            .findFirst();

    return optionalMethod.orElseThrow(
        () -> new IllegalArgumentException(String.format("%s not found", methodName)));
  }
}
