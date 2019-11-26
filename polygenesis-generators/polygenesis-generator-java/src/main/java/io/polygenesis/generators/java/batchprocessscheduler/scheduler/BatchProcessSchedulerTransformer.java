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

package io.polygenesis.generators.java.batchprocessscheduler.scheduler;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Batch process scheduler transformer.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessSchedulerTransformer
    extends AbstractClassTransformer<BatchProcessSchedulerRoute, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process scheduler transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public BatchProcessSchedulerTransformer(
      DataTypeTransformer dataTypeTransformer,
      BatchProcessMethodSchedulerTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(BatchProcessSchedulerRoute source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(
      BatchProcessSchedulerRoute source, Object... args) {
    return super.stateFieldRepresentations(source, args);
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      BatchProcessSchedulerRoute source, Object... args) {
    return super.constructorRepresentations(source, args);
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      BatchProcessSchedulerRoute source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(methodTransformer.create(source.getConfigure(), args));

    return methodRepresentations;
  }

  @Override
  public String packageName(BatchProcessSchedulerRoute source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(BatchProcessSchedulerRoute source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.shared.camel.DeadLetterRouteBuilder");
    imports.add("org.springframework.stereotype.Component");
    imports.add("org.springframework.beans.factory.annotation.Value");

    return imports;
  }

  @Override
  public Set<String> annotations(BatchProcessSchedulerRoute source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Component"));
  }

  @Override
  public String description(BatchProcessSchedulerRoute source, Object... args) {
    return String.format(
        "The %s.", TextConverter.toUpperCamelSpaces(simpleObjectName(source, args)));
  }

  @Override
  public String modifiers(BatchProcessSchedulerRoute source, Object... args) {
    return super.modifiers(source, args);
  }

  @Override
  public String simpleObjectName(BatchProcessSchedulerRoute source, Object... args) {
    return String.format("%sSchedulerRoute", super.simpleObjectName(source, args));
  }

  @Override
  public String fullObjectName(BatchProcessSchedulerRoute source, Object... args) {
    return String.format("%s extends DeadLetterRouteBuilder", simpleObjectName(source, args));
  }
}
