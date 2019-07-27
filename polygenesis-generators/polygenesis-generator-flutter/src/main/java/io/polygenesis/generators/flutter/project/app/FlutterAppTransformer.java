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

package io.polygenesis.generators.flutter.project.app;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Project;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.flutter.project.FlutterProjectDataExtractor;
import io.polygenesis.metamodels.appflutter.FlutterApp;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.dart.AbstractDartClassTransformer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * The type Flutter app transformer.
 *
 * @author Christos Tsakostas
 */
public class FlutterAppTransformer extends AbstractDartClassTransformer<FlutterApp, Function> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final FlutterProjectDataExtractor flutterProjectDataExtractor;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Flutter app transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   * @param flutterProjectDataExtractor the flutter project data extractor
   */
  public FlutterAppTransformer(
      DataTypeTransformer dataTypeTransformer,
      FlutterAppMethodTransformer methodTransformer,
      FlutterProjectDataExtractor flutterProjectDataExtractor) {
    super(dataTypeTransformer, methodTransformer);
    this.flutterProjectDataExtractor = flutterProjectDataExtractor;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public TemplateData transform(FlutterApp source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("data", create(source, args));
    return new TemplateData(dataModel, "polygenesis-dart/Class.dart.ftl");
  }

  @Override
  public Set<FieldRepresentation> staticFieldRepresentations(FlutterApp source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<FieldRepresentation> fieldRepresentations(FlutterApp source, Object... args) {
    return super.fieldRepresentations(source, args);
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      FlutterApp source, Object... args) {
    return super.constructorRepresentations(source, args);
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(FlutterApp source, Object... args) {
    return source
        .getFunctions()
        .stream()
        .map(method -> methodTransformer.create(method, args))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  @Override
  public String packageName(FlutterApp source, Object... args) {
    return super.packageName(source, args);
  }

  @Override
  public Set<String> imports(FlutterApp source, Object... args) {
    Project project = (Project) args[0];

    Set<String> imports = new TreeSet<>();

    imports.add("package:flutter/material.dart");
    imports.add("package:provider/provider.dart");

    flutterProjectDataExtractor.screens(project).forEach(screen -> imports.add(screen.toString()));

    return imports;
  }

  @Override
  public Set<String> annotations(FlutterApp source, Object... args) {
    return super.annotations(source, args);
  }

  @Override
  public String description(FlutterApp source, Object... args) {
    return super.description(source, args);
  }

  @Override
  public String modifiers(FlutterApp source, Object... args) {
    return "";
  }

  @Override
  public String simpleObjectName(FlutterApp source, Object... args) {
    return super.simpleObjectName(source, args);
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String fullObjectName(FlutterApp source, Object... args) {
    return String.format("%s extends StatelessWidget", simpleObjectName(source, args));
  }
}
