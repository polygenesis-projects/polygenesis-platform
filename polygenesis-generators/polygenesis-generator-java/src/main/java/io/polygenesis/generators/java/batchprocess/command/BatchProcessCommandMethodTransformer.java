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

package io.polygenesis.generators.java.batchprocess.command;

import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.generators.java.batchprocess.command.activity.ProcessCommandActivityGenerator;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Batch process command method transformer.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessCommandMethodTransformer extends AbstractMethodTransformer<ServiceMethod> {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private final ProcessCommandActivityGenerator processCommandActivityGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Batch process command method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param processCommandActivityGenerator the process command activity generator
   */
  public BatchProcessCommandMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      ProcessCommandActivityGenerator processCommandActivityGenerator) {
    super(dataTypeTransformer);
    this.processCommandActivityGenerator = processCommandActivityGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public String description(ServiceMethod source, Object... args) {
    return "";
  }

  @Override
  public Set<String> annotations(ServiceMethod source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Override"));
  }

  @Override
  public String modifiers(ServiceMethod source, Object... args) {
    return super.modifiers(source, args);
  }

  @Override
  public String methodName(ServiceMethod source, Object... args) {
    return "processForId";
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(
      ServiceMethod source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation("BatchProcessMessage", "batchProcessMessage"));

    return parameterRepresentations;
  }

  @Override
  public String returnValue(ServiceMethod source, Object... args) {
    return "void";
  }

  @Override
  public String implementation(ServiceMethod source, Object... args) {
    return processCommandActivityGenerator.generate(source);
  }
}
