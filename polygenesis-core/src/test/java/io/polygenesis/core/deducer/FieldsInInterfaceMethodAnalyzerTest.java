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
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class FieldsInInterfaceMethodAnalyzerTest extends AbstractTest {

  private TypesAnalyzer typesAnalyzer;
  private FieldsInInterfaceMethodAnalyzer fieldsInInterfaceMethodAnalyzer;
  private MethodAnalyzer methodAnalyzer;

  @Before
  public void setUp() {
    typesAnalyzer = new TypesAnalyzer();
    fieldsInInterfaceMethodAnalyzer = new FieldsInInterfaceMethodAnalyzer();
    methodAnalyzer = new MethodAnalyzer();
  }

  @Test
  public void extractFieldsFromInterfaceMethods() {
    // TODO: assertionsForGetSpringPageCollectionDto();
    assertionsForGetCollectionApiPageResponse();
  }

  private void assertionsForGetSpringPageCollectionDto() {
    Method method = getMethodByName("getSpringPageCollectionDto");

    MethodOutputDescriptor methodOutputDescriptor = methodAnalyzer.getMethodOutput(method);

    RecursiveObject recursiveObject =
        new RecursiveObject(
            methodOutputDescriptor.getGenericType(),
            methodOutputDescriptor.getDataType(),
            "response",
            null);

    Class<?> returnType = method.getReturnType();

    Set<Class<?>> typeClasses =
        typesAnalyzer.getAllExtendedOrImplementedTypesRecursively(returnType);

    assertThat(typeClasses.size()).isEqualTo(5);

    typeClasses.forEach(
        extendedClazz -> {
          Method[] methods = extendedClazz.getDeclaredMethods();
          fieldsInInterfaceMethodAnalyzer.extractFieldsFromInterfaceMethods(
              methods, recursiveObject, methodOutputDescriptor.getDataType());
        });
  }

  private void assertionsForGetCollectionApiPageResponse() {
    Method method = getMethodByName("getCollectionApiPageResponse");

    MethodOutputDescriptor methodOutputDescriptor = methodAnalyzer.getMethodOutput(method);

    RecursiveObject recursiveObject =
        new RecursiveObject(
            methodOutputDescriptor.getGenericType(),
            methodOutputDescriptor.getDataType(),
            "response",
            null);

    Class<?> returnType = method.getReturnType();

    Set<Class<?>> typeClasses =
        typesAnalyzer.getAllExtendedOrImplementedTypesRecursively(returnType);

    assertThat(typeClasses.size()).isEqualTo(4);

    typeClasses.forEach(
        extendedClazz -> {
          Method[] methods = extendedClazz.getDeclaredMethods();
          fieldsInInterfaceMethodAnalyzer.extractFieldsFromInterfaceMethods(
              methods, recursiveObject, methodOutputDescriptor.getDataType());
        });
  }
}
