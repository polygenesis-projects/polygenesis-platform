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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import io.polygenesis.commons.text.Text;
import io.polygenesis.core.Function;
import io.polygenesis.core.Thing;
import io.polygenesis.core.sample.AnnotatedInterface;
import io.polygenesis.core.sample.NotAnnotatedInterface;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/** @author Christos Tsakostas */
public class FunctionIdentifierTest {

  @Test
  public void shouldReturnOneGoalWithCustomName() {
    MethodAnalyzer methodAnalyzer = mock(MethodAnalyzer.class);
    RecursiveObjectFiller recursiveObjectFiller = mock(RecursiveObjectFiller.class);
    IoModelDeducer ioModelDeducer = mock(IoModelDeducer.class);

    MethodOutputDescriptor methodOutputDescriptor = mock(MethodOutputDescriptor.class);

    Thing thing = new Thing(new Text("someFancyThing"));
    Set<Class<?>> classes = new LinkedHashSet<>();
    classes.add(AnnotatedInterface.class);

    FunctionIdentifier functionIdentifier =
        new FunctionIdentifier(methodAnalyzer, recursiveObjectFiller, ioModelDeducer);

    given(methodAnalyzer.getMethodOutput(any(Method.class))).willReturn(methodOutputDescriptor);

    Set<Function> functions = functionIdentifier.identifyGoalsOf(thing, classes);
    assertThat(functions.isEmpty()).isFalse();

    functions
        .stream()
        .findFirst()
        .ifPresent(func -> assertThat(func.getName().getOriginal()).isEqualTo("someCustomName"));
  }

  @Test
  public void shouldReturnOptionalOfEmptyIfAnnotationPGGoalIsNotPresent() {
    MethodAnalyzer methodAnalyzer = mock(MethodAnalyzer.class);
    RecursiveObjectFiller recursiveObjectFiller = mock(RecursiveObjectFiller.class);
    IoModelDeducer ioModelDeducer = mock(IoModelDeducer.class);
    Thing thing = mock(Thing.class);
    Set<Class<?>> classes = new LinkedHashSet<>();
    classes.add(NotAnnotatedInterface.class);

    FunctionIdentifier functionIdentifier =
        new FunctionIdentifier(methodAnalyzer, recursiveObjectFiller, ioModelDeducer);

    assertThat(functionIdentifier.identifyGoalsOf(thing, classes).isEmpty()).isTrue();
  }

  @Test
  public void shouldReturnOptionalOfEmptyIfAnnotationPGGoalRefersToAnotherThing() {
    MethodAnalyzer methodAnalyzer = mock(MethodAnalyzer.class);
    RecursiveObjectFiller recursiveObjectFiller = mock(RecursiveObjectFiller.class);
    IoModelDeducer ioModelDeducer = mock(IoModelDeducer.class);
    Thing thing = mock(Thing.class);
    Set<Class<?>> classes = new LinkedHashSet<>();
    classes.add(AnnotatedInterface.class);

    FunctionIdentifier functionIdentifier =
        new FunctionIdentifier(methodAnalyzer, recursiveObjectFiller, ioModelDeducer);

    assertThat(functionIdentifier.identifyGoalsOf(thing, classes).isEmpty()).isTrue();
  }
}
