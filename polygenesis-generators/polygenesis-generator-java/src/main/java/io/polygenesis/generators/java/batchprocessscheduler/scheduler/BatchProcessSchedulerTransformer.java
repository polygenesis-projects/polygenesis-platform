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
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.shared.transformer.AbstractClassTransformer;
import io.polygenesis.models.periodicprocess.BatchProcessMetamodel;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The type Batch process scheduler transformer.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessSchedulerTransformer
    extends AbstractClassTransformer<BatchProcessMetamodel, Function> {

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
  public TemplateData transform(BatchProcessMetamodel source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> fieldRepresentations(
      BatchProcessMetamodel source, Object... args) {
    return super.fieldRepresentations(source, args);
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      BatchProcessMetamodel source, Object... args) {
    return super.constructorRepresentations(source, args);
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      BatchProcessMetamodel source, Object... args) {
    return super.methodRepresentations(source, args);
  }

  @Override
  public String packageName(BatchProcessMetamodel source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(BatchProcessMetamodel source, Object... args) {
    return super.imports(source, args);
  }

  @Override
  public Set<String> annotations(BatchProcessMetamodel source, Object... args) {
    return super.annotations(source, args);
  }

  @Override
  public String description(BatchProcessMetamodel source, Object... args) {
    return super.description(source, args);
  }

  @Override
  public String modifiers(BatchProcessMetamodel source, Object... args) {
    return super.modifiers(source, args);
  }

  @Override
  public String simpleObjectName(BatchProcessMetamodel source, Object... args) {
    return String.format("%sScheduler", super.simpleObjectName(source, args));
  }

  @Override
  public String fullObjectName(BatchProcessMetamodel source, Object... args) {
    return super.fullObjectName(source, args);
  }
}
