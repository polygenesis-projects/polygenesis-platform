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

package io.polygenesis.generators.flutter.context.ui.screen;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.metamodels.ui.screen.Screen;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.dart.AbstractDartClassTransformer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * The type Screen transformer.
 *
 * @author Christos Tsakostas
 */
public class ScreenTransformer extends AbstractDartClassTransformer<Screen, Function> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Screen transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public ScreenTransformer(
      DataTypeTransformer dataTypeTransformer, ScreenMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public TemplateData transform(Screen source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("data", create(source, args));
    return new TemplateData(dataModel, "polygenesis-dart/Class.dart.ftl");
  }

  @Override
  public Set<FieldRepresentation> staticFieldRepresentations(Screen source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(Screen source, Object... args) {
    return super.stateFieldRepresentations(source, args);
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(Screen source, Object... args) {
    return super.constructorRepresentations(source, args);
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Screen source, Object... args) {
    return super.methodRepresentations(source, args);
  }

  @Override
  public String packageName(Screen source, Object... args) {
    return super.packageName(source, args);
  }

  @Override
  public Set<String> imports(Screen source, Object... args) {
    return super.imports(source, args);
  }

  @Override
  public Set<String> annotations(Screen source, Object... args) {
    return super.annotations(source, args);
  }

  @Override
  public String description(Screen source, Object... args) {
    return super.description(source, args);
  }

  @Override
  public String modifiers(Screen source, Object... args) {
    return "";
  }

  @Override
  public String simpleObjectName(Screen source, Object... args) {
    return super.simpleObjectName(source, args);
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String fullObjectName(Screen source, Object... args) {
    return super.fullObjectName(source, args);
  }
}
