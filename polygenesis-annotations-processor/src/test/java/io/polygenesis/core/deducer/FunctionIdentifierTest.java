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

import io.polygenesis.core.Function;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingBuilder;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.core.sample.AnnotatedInterface;
import io.polygenesis.core.sample.NotAnnotatedInterface;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

/** @author Christos Tsakostas */
public class FunctionIdentifierTest {

  private MethodAnalyzer methodAnalyzer = Mockito.mock(MethodAnalyzer.class);
  private RecursiveObjectFiller recursiveObjectFiller = Mockito.mock(RecursiveObjectFiller.class);
  private DataDeducer dataDeducer = Mockito.mock(DataDeducer.class);
  private FunctionIdentifier functionIdentifier;
  private MethodOutputDescriptor methodOutputDescriptor;

  @Before
  public void setUp() {
    methodAnalyzer = Mockito.mock(MethodAnalyzer.class);
    recursiveObjectFiller = Mockito.mock(RecursiveObjectFiller.class);
    dataDeducer = Mockito.mock(DataDeducer.class);
    functionIdentifier = new FunctionIdentifier(methodAnalyzer, recursiveObjectFiller, dataDeducer);
    methodOutputDescriptor = Mockito.mock(MethodOutputDescriptor.class);
  }

  @Ignore
  @Test
  public void shouldReturnOneGoalWithCustomName() {
    Thing thing =
        ThingBuilder.generic().setThingName(new ThingName("someFancyThing")).createThing();
    Set<Class<?>> classes = new LinkedHashSet<>();
    classes.add(AnnotatedInterface.class);

    BDDMockito.given(methodAnalyzer.getMethodOutput(ArgumentMatchers.any(Method.class)))
        .willReturn(methodOutputDescriptor);
    BDDMockito.given(dataDeducer.deduceResponse(ArgumentMatchers.any(RecursiveObject.class)))
        .willReturn(Mockito.mock(DataGroup.class));

    Set<Function> functions = functionIdentifier.identifyGoalsOf(thing, classes);
    Assertions.assertThat(functions.isEmpty()).isFalse();

    functions
        .stream()
        .findFirst()
        .ifPresent(
            func ->
                Assertions.assertThat(func.getName().getOriginal()).isEqualTo("someCustomName"));
  }

  @Test
  public void shouldReturnOptionalOfEmptyIfAnnotationGGoalIsNotPresent() {
    Thing thing = Mockito.mock(Thing.class);
    Set<Class<?>> classes = new LinkedHashSet<>();
    classes.add(NotAnnotatedInterface.class);

    assertThat(functionIdentifier.identifyGoalsOf(thing, classes).isEmpty()).isTrue();
  }

  @Test
  public void shouldReturnOptionalOfEmptyIfAnnotationGGoalRefersToAnotherThing() {
    Thing thing = Mockito.mock(Thing.class);
    Set<Class<?>> classes = new LinkedHashSet<>();
    classes.add(AnnotatedInterface.class);

    assertThat(functionIdentifier.identifyGoalsOf(thing, classes).isEmpty()).isTrue();
  }
}
