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

package io.polygenesis.generators.java.domain.projection.repository;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.models.domain.Projection;
import io.polygenesis.transformers.java.AbstractInterfaceTransformer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Projection repository transformer.
 *
 * @author Christos Tsakostas
 */
public class ProjectionRepositoryTransformer
    extends AbstractInterfaceTransformer<Projection, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Projection repository transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public ProjectionRepositoryTransformer(
      DataTypeTransformer dataTypeTransformer,
      ProjectionRepositoryMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(Projection source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Interface.java.ftl");
  }

  @Override
  public String packageName(Projection source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(Projection source, Object... args) {
    Set<String> imports = new TreeSet<>();

    if (source.getMultiTenant()) {
      imports.add("com.oregor.trinity4j.domain.TenantProjectionRepository");
    } else {
      imports.add("com.oregor.trinity4j.domain.ProjectionRepository");
    }

    return imports;
  }

  @Override
  public String simpleObjectName(Projection source, Object... args) {
    return String.format("%sRepository", super.simpleObjectName(source, args));
  }

  @Override
  public String fullObjectName(Projection source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    if (source.getMultiTenant()) {
      stringBuilder.append(simpleObjectName(source, args));
      stringBuilder.append(" extends ");
      stringBuilder.append("TenantProjectionRepository<");
      stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
      stringBuilder.append(", ");
      stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
      stringBuilder.append(">");
    } else {
      stringBuilder.append(simpleObjectName(source, args));
      stringBuilder.append(" extends ");
      stringBuilder.append("ProjectionRepository<");
      stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
      stringBuilder.append(", ");
      stringBuilder.append(
          String.format("%sId", TextConverter.toUpperCamel(source.getObjectName().getText())));
      stringBuilder.append(">");
    }

    return stringBuilder.toString();
  }
}
