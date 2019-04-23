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

import java.lang.reflect.Method;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class TypesAnalyzerTest extends AbstractTest {

  private TypesAnalyzer typesAnalyzer;

  @Before
  public void setUp() throws Exception {
    typesAnalyzer = new TypesAnalyzer();
  }

  @Test
  public void shouldSucceedToGetAllExtendedOrImplementedTypesRecursively() {
    assertionsForGetListOfString();
    assertionsForGetListOfCollectionDto();
    // TODO: assertionsForGetSpringPageCollectionDto();
    assertionsForGetCollectionApiPageResponse();
  }

  @Test
  public void shouldThrowExceptionForNullInput() {
    TypesAnalyzer typesAnalyzer = new TypesAnalyzer();

    Assertions.assertThatThrownBy(
            () -> typesAnalyzer.getAllExtendedOrImplementedTypesRecursively(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  private void assertionsForGetListOfString() {
    Method methodGetCollectionDtoAsSpringPage = getMethodByName("getListOfString");
    Class<?> returnType = methodGetCollectionDtoAsSpringPage.getReturnType();
    Set<Class<?>> typeClasses =
        typesAnalyzer.getAllExtendedOrImplementedTypesRecursively(returnType);
    Assertions.assertThat(typeClasses.size()).isEqualTo(0);
  }

  private void assertionsForGetListOfCollectionDto() {
    Method methodGetCollectionDtoAsSpringPage = getMethodByName("getListOfCollectionDto");
    Class<?> returnType = methodGetCollectionDtoAsSpringPage.getReturnType();
    Set<Class<?>> typeClasses =
        typesAnalyzer.getAllExtendedOrImplementedTypesRecursively(returnType);
    Assertions.assertThat(typeClasses.size()).isEqualTo(0);
  }

  private void assertionsForGetSpringPageCollectionDto() {
    Method methodGetCollectionDtoAsSpringPage = getMethodByName("getSpringPageCollectionDto");
    Class<?> returnType = methodGetCollectionDtoAsSpringPage.getReturnType();
    Set<Class<?>> typeClasses =
        typesAnalyzer.getAllExtendedOrImplementedTypesRecursively(returnType);
    Assertions.assertThat(typeClasses.size()).isEqualTo(5);
  }

  private void assertionsForGetCollectionApiPageResponse() {
    Method methodGetCollectionDtoAsSpringPage = getMethodByName("getCollectionApiPageResponse");
    Class<?> returnType = methodGetCollectionDtoAsSpringPage.getReturnType();
    Set<Class<?>> typeClasses =
        typesAnalyzer.getAllExtendedOrImplementedTypesRecursively(returnType);
    Assertions.assertThat(typeClasses.size()).isEqualTo(4);
  }
}
