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

package io.polygenesis.generators.java.batchprocess.query;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.generators.java.batchprocess.query.activity.ProcessQueryActivityGenerator;
import io.polygenesis.generators.java.shared.transformer.AbstractMethodTransformer;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.periodicprocess.BatchProcessMetamodel;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Batch process query method transformer.
 *
 * @author Christos Tsakostas
 */
public class BatchProcessQueryMethodTransformer extends AbstractMethodTransformer<ServiceMethod> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final ProcessQueryActivityGenerator processQueryActivityGenerator;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public BatchProcessQueryMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      ProcessQueryActivityGenerator processQueryActivityGenerator) {
    super(dataTypeTransformer);
    this.processQueryActivityGenerator = processQueryActivityGenerator;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<String> imports(ServiceMethod source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.api.ApiPagedCollectionResponse");

    return imports;
  }

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
    return "fetchPagedCollection";
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(
      ServiceMethod source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(new ParameterRepresentation("Integer", "pageNumber"));

    parameterRepresentations.add(new ParameterRepresentation("Integer", "pageSize"));

    return parameterRepresentations;
  }

  @Override
  public String returnValue(ServiceMethod source, Object... args) {
    BatchProcessMetamodel batchProcessMetamodel = (BatchProcessMetamodel) args[0];

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("ApiPagedCollectionResponse");
    stringBuilder.append("<");
    stringBuilder.append(
        TextConverter.toUpperCamel(
            batchProcessMetamodel
                .getQueryCollectionItem()
                .getDataObject()
                .getObjectName()
                .getText()));
    stringBuilder.append(">");

    return stringBuilder.toString();
  }

  @Override
  public String implementation(ServiceMethod source, Object... args) {
    return processQueryActivityGenerator.generate(source);
  }
}
