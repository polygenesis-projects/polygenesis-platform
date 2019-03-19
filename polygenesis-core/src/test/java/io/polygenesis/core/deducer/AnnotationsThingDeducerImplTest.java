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

import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.ThingRepository;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class AnnotationsThingDeducerImplTest {

  private ClassScanner classScanner;
  private ThingScanner thingScanner;
  private MethodAnalyzer methodAnalyzer;
  private TypesAnalyzer typesAnalyzer;
  private FieldsInInterfaceMethodAnalyzer fieldsInInterfaceMethodAnalyzer;
  private RecursiveObjectFiller recursiveObjectFiller;
  private JavaDataTypeConverter javaDataTypeConverter;
  private DataDeducer dataDeducer;
  private FunctionIdentifier functionIdentifier;
  private ThingDeducer thingDeducer;
  private ThingRepository repository;

  @Before
  public void setUp() {
    classScanner = new ClassScanner();
    thingScanner = new ThingScanner();
    methodAnalyzer = new MethodAnalyzer();
    typesAnalyzer = new TypesAnalyzer();
    fieldsInInterfaceMethodAnalyzer = new FieldsInInterfaceMethodAnalyzer();
    recursiveObjectFiller =
        new RecursiveObjectFiller(typesAnalyzer, fieldsInInterfaceMethodAnalyzer);
    javaDataTypeConverter = new JavaDataTypeConverter();
    dataDeducer = new DataDeducer(javaDataTypeConverter);
    functionIdentifier = new FunctionIdentifier(methodAnalyzer, recursiveObjectFiller, dataDeducer);
    thingDeducer = new AnnotationsThingDeducerImpl(classScanner, thingScanner, functionIdentifier);
  }

  @Test
  public void deduce() {
    Set<String> packages = new LinkedHashSet<>();
    packages.add("io.polygenesis.core.sample");

    Set<String> interfaces = new LinkedHashSet<>();
    interfaces.add("io.polygenesis.core.sample.TestService");

    ThingDeducerRequest request =
        new ThingDeducerRequest(packages, interfaces, InclusionOrExclusionType.INCLUDE);

    repository = thingDeducer.deduce(request);

    // =============================================================================================
    // BASIC ASSERTIONS
    // =============================================================================================
    assertThat(repository).isNotNull();
    assertThat(repository.getApiThings()).isNotNull();
    assertThat(repository.getApiThings().size()).isEqualTo(1);
    assertThat(repository.getThingByName(new ThingName("someThing"))).isPresent();

    // =============================================================================================
    // ASSERTIONS FOR THING: SOMETHING
    // =============================================================================================
    Optional<Thing> optionalSomeThing = repository.getThingByName(new ThingName("someThing"));
    if (!optionalSomeThing.isPresent()) {
      throw new IllegalStateException();
    }

    Thing someThing = optionalSomeThing.get();

    assertThat(someThing.getFunctions().size()).isEqualTo(5);

    assertionsForGoalCalculation(someThing);
  }

  // ===============================================================================================
  // ASSERTIONS FOR THING METHOD: ADD / CALCULATION
  // ===============================================================================================
  private void assertionsForGoalCalculation(Thing someThing) {
    Optional<Function> optionalGoalCalculation =
        repository.getThingFunction(
            someThing.getThingName(), new FunctionName("calculateSomeThing"));

    if (!optionalGoalCalculation.isPresent()) {
      throw new IllegalStateException();
    }

    Function functionCalculation = optionalGoalCalculation.get();

    assertThat(functionCalculation.getReturnValue()).isNotNull();
    assertThat(functionCalculation.getArguments().size()).isEqualTo(2);
  }
}
