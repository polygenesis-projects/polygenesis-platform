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

package io.polygenesis.core.deducer;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.Test;

/** @author Christos Tsakostas */
public class RecursiveObjectFillerTest {

  @Test
  public void fillRecursiveObject() {
    TypesAnalyzer typesAnalyzer = new TypesAnalyzer();
    FieldsInInterfaceMethodAnalyzer fieldsInInterfaceMethodAnalyzer =
        new FieldsInInterfaceMethodAnalyzer();
    RecursiveObjectFiller recursiveObjectFiller =
        new RecursiveObjectFiller(typesAnalyzer, fieldsInInterfaceMethodAnalyzer);
    MethodAnalyzer methodAnalyzer = new MethodAnalyzer();

    Set<Class<?>> classes = getClasses();

    Optional<Class<?>> optionalSomeClazz = classes.stream().findFirst();
    if (!optionalSomeClazz.isPresent()) {
      throw new IllegalStateException();
    }
    Class<?> someClazz = optionalSomeClazz.get();

    Optional<Method> optionalSomeMethod = Stream.of(someClazz.getMethods()).findFirst();
    if (!optionalSomeMethod.isPresent()) {
      throw new IllegalStateException();
    }
    Method someMethod = optionalSomeMethod.get();

    MethodOutputDescriptor methodOutputDescriptor = methodAnalyzer.getMethodOutput(someMethod);

    String name = "response";
    RecursiveObject parent = null;
    RecursiveObject recursiveObject =
        recursiveObjectFiller.fillRecursiveObject(
            methodOutputDescriptor.getGenericType(),
            methodOutputDescriptor.getDataType(),
            name,
            methodOutputDescriptor.getClazz(),
            parent);

    assertThat(recursiveObject).isNotNull();
  }

  @Test
  public void shouldSucceedToFillRecursiveObjectsForAllClassesAndMethods() {
    TypesAnalyzer typesAnalyzer = new TypesAnalyzer();
    FieldsInInterfaceMethodAnalyzer fieldsInInterfaceMethodAnalyzer =
        new FieldsInInterfaceMethodAnalyzer();
    RecursiveObjectFiller recursiveObjectFiller =
        new RecursiveObjectFiller(typesAnalyzer, fieldsInInterfaceMethodAnalyzer);
    MethodAnalyzer methodAnalyzer = new MethodAnalyzer();

    Set<Class<?>> classes = getClasses();

    classes.forEach(
        clazz ->
            Stream.of(clazz.getMethods())
                .forEach(
                    method -> {
                      MethodOutputDescriptor methodOutputDescriptor =
                          methodAnalyzer.getMethodOutput(method);

                      String name = "response";
                      RecursiveObject parent = null;
                      RecursiveObject recursiveObject =
                          recursiveObjectFiller.fillRecursiveObject(
                              methodOutputDescriptor.getGenericType(),
                              methodOutputDescriptor.getDataType(),
                              name,
                              methodOutputDescriptor.getClazz(),
                              parent);

                      assertThat(recursiveObject).isNotNull();
                    }));
  }

  private Set<Class<?>> getClasses() {
    ClassScanner classScanner = new ClassScanner();

    Set<String> packages = new LinkedHashSet<>();
    packages.add("io.polygenesis.core.sample");

    return classScanner.identify(packages, new LinkedHashSet<>(), InclusionOrExclusionType.NONE);
  }
}
