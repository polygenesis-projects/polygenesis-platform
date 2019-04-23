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

package io.polygenesis.core;

import io.polygenesis.core.deducer.AnnotationsThingDeducerImpl;
import io.polygenesis.core.deducer.ClassScanner;
import io.polygenesis.core.deducer.DataDeducer;
import io.polygenesis.core.deducer.FieldsInInterfaceMethodAnalyzer;
import io.polygenesis.core.deducer.FunctionIdentifier;
import io.polygenesis.core.deducer.JavaDataTypeConverter;
import io.polygenesis.core.deducer.MethodAnalyzer;
import io.polygenesis.core.deducer.RecursiveObjectFiller;
import io.polygenesis.core.deducer.ThingDeducer;
import io.polygenesis.core.deducer.ThingScanner;
import io.polygenesis.core.deducer.TypesAnalyzer;

/**
 * Singletons of PolyGenesis Core Services and Components.
 *
 * @author Christos Tsakostas
 */
public final class CoreRegistry {

  private CoreRegistry() {
    throw new IllegalStateException("Utility class");
  }

  private static final ThingDeducer thingDeducer;
  private static final ModelRepositoryResolver modelRepositoryResolver;

  static {
    ClassScanner classScanner = new ClassScanner();

    ThingScanner thingScanner = new ThingScanner();

    MethodAnalyzer methodAnalyzer = new MethodAnalyzer();

    TypesAnalyzer typesAnalyzer = new TypesAnalyzer();

    FieldsInInterfaceMethodAnalyzer fieldsInInterfaceMethodAnalyzer =
        new FieldsInInterfaceMethodAnalyzer();

    RecursiveObjectFiller recursiveObjectFiller =
        new RecursiveObjectFiller(typesAnalyzer, fieldsInInterfaceMethodAnalyzer);

    JavaDataTypeConverter javaDataTypeConverter = new JavaDataTypeConverter();

    DataDeducer dataDeducer = new DataDeducer(javaDataTypeConverter);

    FunctionIdentifier functionIdentifier =
        new FunctionIdentifier(methodAnalyzer, recursiveObjectFiller, dataDeducer);

    thingDeducer = new AnnotationsThingDeducerImpl(classScanner, thingScanner, functionIdentifier);

    modelRepositoryResolver = new ModelRepositoryResolver();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets thing deducer.
   *
   * @return the thing deducer
   */
  public static ThingDeducer getThingDeducer() {
    return thingDeducer;
  }

  /**
   * Gets model repository resolver.
   *
   * @return the model repository resolver
   */
  public static ModelRepositoryResolver getModelRepositoryResolver() {
    return modelRepositoryResolver;
  }
}
