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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.stubbing.Answer;

/** @author Christos Tsakostas */
public class MethodAnalyzerTest {

  @Test
  public void shouldGetMethodOutput() {
    MethodAnalyzer methodAnalyzer = new MethodAnalyzer();

    Set<Class<?>> classes = getClasses();
    Class<?> someClazz = classes.stream().findFirst().get();
    Method someMethod = Stream.of(someClazz.getMethods()).findFirst().get();

    MethodOutputDescriptor methodOutputDescriptor = methodAnalyzer.getMethodOutput(someMethod);

    assertThat(methodOutputDescriptor).isNotNull();
  }

  @Ignore
  @Test
  public void shouldGetMethodInput() {
    MethodAnalyzer methodAnalyzer = new MethodAnalyzer();
    Parameter parameter = mock(Parameter.class);

    given(parameter.getType()).willAnswer((Answer<Class<?>>) invocation -> Object.class);
    given(parameter.getName()).willReturn("some parameter name");

    MethodInputDescriptor methodInputDescriptor = methodAnalyzer.getMethodInput(parameter);

    assertThat(methodInputDescriptor).isNotNull();
    assertThat(methodInputDescriptor.getType()).isEqualTo(Object.class);
    assertThat(methodInputDescriptor.getTypeName()).isEqualTo("java.lang.Object");
    assertThat(methodInputDescriptor.getName()).isEqualTo("some parameter name");
  }

  @Ignore
  @Test
  public void shouldFailForInvalidClass() {
    MethodAnalyzer methodAnalyzer = new MethodAnalyzer();
    Method someMethod = mock(Method.class);
    ParameterizedType type = mock(ParameterizedType.class);

    Type[] argTypes = {
      new Type() {
        @Override
        public String getTypeName() {
          return "some invalid class type";
        }
      }
    };

    given(someMethod.getGenericReturnType()).willReturn(type);
    given(type.getActualTypeArguments()).willReturn(argTypes);
    given(someMethod.getReturnType())
        .willAnswer((Answer<Class<?>>) invocation -> NoArgConstructorClass.class);

    assertThatThrownBy(() -> methodAnalyzer.getMethodOutput(someMethod))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("Cannot create class forName");
  }

  private Set<Class<?>> getClasses() {
    ClassScanner classScanner = new ClassScanner();

    Set<String> packages = new LinkedHashSet<>();
    packages.add("io.polygenesis.core.sample");

    return classScanner.identify(packages, new LinkedHashSet<>(), InclusionOrExclusionType.NONE);
  }
}
