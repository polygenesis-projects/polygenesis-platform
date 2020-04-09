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

package io.polygenesis.generators.flutter.project.app;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.dart.AbstractDartMethodTransformer;
import java.util.LinkedHashSet;
import java.util.Set;

public class FlutterAppMethodTransformer extends AbstractDartMethodTransformer<Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Flutter app method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param activityRegistry the activity registry
   */
  public FlutterAppMethodTransformer(
      DataTypeTransformer dataTypeTransformer, FlutterAppActivityRegistry activityRegistry) {
    super(dataTypeTransformer, activityRegistry);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public MethodRepresentationType methodType(Function source, Object... args) {
    return super.methodType(source, args);
  }

  @Override
  public Set<String> imports(Function source, Object... args) {
    return super.imports(source, args);
  }

  @Override
  public Set<String> annotations(Function source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@override");

    return annotations;
  }

  @Override
  public String description(Function source, Object... args) {
    return super.description(source, args);
  }

  @Override
  public String modifiers(Function source, Object... args) {
    return "";
  }

  @Override
  public String methodName(Function source, Object... args) {
    return super.methodName(source, args);
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(Function source, Object... args) {
    return super.parameterRepresentations(source, args);
  }

  @Override
  public String returnValue(Function source, Object... args) {
    return super.returnValue(source, args);
  }
}
