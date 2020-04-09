/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.generators.flutter.context.ui.widget;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.metamodels.ui.widget.Widget;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.dart.AbstractDartClassTransformer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class WidgetTransformer extends AbstractDartClassTransformer<Widget, Function> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Widget transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public WidgetTransformer(
      DataTypeTransformer dataTypeTransformer, WidgetMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public TemplateData transform(Widget source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("data", create(source, args));
    return new TemplateData(dataModel, "polygenesis-dart/Class.dart.ftl");
  }

  @Override
  public Set<FieldRepresentation> staticFieldRepresentations(Widget source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(Widget source, Object... args) {
    return super.stateFieldRepresentations(source, args);
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(Widget source, Object... args) {
    return super.constructorRepresentations(source, args);
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Widget source, Object... args) {
    return super.methodRepresentations(source, args);
  }

  @Override
  public String packageName(Widget source, Object... args) {
    return super.packageName(source, args);
  }

  @Override
  public Set<String> imports(Widget source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("package:flutter/material.dart");

    return imports;
  }

  @Override
  public Set<String> annotations(Widget source, Object... args) {
    return super.annotations(source, args);
  }

  @Override
  public String description(Widget source, Object... args) {
    return String.format(
        "The %s Widget.", TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));
  }

  @Override
  public String modifiers(Widget source, Object... args) {
    return "";
  }

  @Override
  public String simpleObjectName(Widget source, Object... args) {
    return String.format("%sWidget", super.simpleObjectName(source, args));
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String fullObjectName(Widget source, Object... args) {
    return String.format("%s extends StatelessWidget", simpleObjectName(source, args));
  }
}
