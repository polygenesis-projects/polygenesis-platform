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

package io.polygenesis.generators.java.domain.repository;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.models.domain.Persistence;
import io.polygenesis.transformers.java.AbstractInterfaceTransformer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Repository transformer.
 *
 * @author Christos Tsakostas
 */
public class RepositoryTransformer extends AbstractInterfaceTransformer<Persistence, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Repository transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public RepositoryTransformer(
      DataTypeTransformer dataTypeTransformer, RepositoryMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(Persistence source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));
    return new TemplateData(dataModel, "polygenesis-representation-java/Interface.java.ftl");
  }

  @Override
  public String packageName(Persistence source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(Persistence source, Object... args) {
    Set<String> imports = new TreeSet<>();

    if (source.getMultiTenant()) {
      imports.add("com.oregor.trinity4j.domain.TenantRepository");
    } else {
      imports.add("com.oregor.trinity4j.domain.Repository");
    }

    return imports;
  }

  @Override
  public String description(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Database Agnostic Contract.");

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    if (source.getMultiTenant()) {
      stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
      stringBuilder.append(" extends ");
      stringBuilder.append("TenantRepository<");
      stringBuilder.append(
          TextConverter.toUpperCamel(source.getAggregateRootObjectName().getText()));
      stringBuilder.append(", ");
      stringBuilder.append(
          TextConverter.toUpperCamel(source.getAggregateRootIdObjectName().getText()));
      stringBuilder.append(">");
    } else {
      stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
      stringBuilder.append(" extends ");
      stringBuilder.append("Repository<");
      stringBuilder.append(
          TextConverter.toUpperCamel(source.getAggregateRootObjectName().getText()));
      stringBuilder.append(", ");
      stringBuilder.append(
          TextConverter.toUpperCamel(source.getAggregateRootIdObjectName().getText()));
      stringBuilder.append(">");
    }

    return stringBuilder.toString();
  }
}
