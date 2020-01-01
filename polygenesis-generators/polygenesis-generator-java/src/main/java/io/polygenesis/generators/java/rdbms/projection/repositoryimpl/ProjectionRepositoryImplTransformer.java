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

package io.polygenesis.generators.java.rdbms.projection.repositoryimpl;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.models.domain.Persistence;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Projection repository impl transformer.
 *
 * @author Christos Tsakostas
 */
public class ProjectionRepositoryImplTransformer
    extends AbstractClassTransformer<Persistence, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Projection repository impl transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public ProjectionRepositoryImplTransformer(
      DataTypeTransformer dataTypeTransformer,
      ProjectionRepositoryImplMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(Persistence source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));
    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public String packageName(Persistence source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      Persistence source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation(
            TextConverter.toUpperCamel(source.getAggregateRootObjectName().getText())
                + "SpringDataRepository",
            "springDataRepository"));

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("The ");
    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));
    stringBuilder.append(" Implementation.");

    ConstructorRepresentation constructorRepresentation =
        new ConstructorRepresentation(
            new LinkedHashSet<>(),
            stringBuilder.toString(),
            dataTypeTransformer.getModifierPublic(),
            parameterRepresentations,
            String.format(
                "\t\tsuper(%s.class, %s);",
                TextConverter.toUpperCamel(source.getAggregateRootIdObjectName().getText()),
                "springDataRepository"));

    return new LinkedHashSet<>(Arrays.asList(constructorRepresentation));
  }

  @Override
  public Set<String> imports(Persistence source, Object... args) {
    Set<String> imports = new TreeSet<>();

    if (source.getMultiTenant()) {
      imports.add("com.oregor.trinity4j.domain.AbstractJpaTenantProjectionRepository");
    } else {
      imports.add("com.oregor.trinity4j.domain.AbstractJpaProjectionRepository");
    }

    imports.add("org.springframework.stereotype.Repository");

    return imports;
  }

  @Override
  public String simpleObjectName(Persistence source, Object... args) {
    return String.format("%sImpl", super.simpleObjectName(source, args));
  }

  @Override
  public String fullObjectName(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();
    String changeLineAndTwoTabs = "\n\t\t";

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
    stringBuilder.append("Impl");

    stringBuilder.append(changeLineAndTwoTabs);
    if (source.getMultiTenant()) {
      stringBuilder.append("extends AbstractJpaTenantProjectionRepository<");
    } else {
      stringBuilder.append("extends AbstractJpaProjectionRepository<");
    }

    stringBuilder.append(
        changeLineAndTwoTabs
            + TextConverter.toUpperCamel(source.getAggregateRootObjectName().getText())
            + ",");
    stringBuilder.append(
        changeLineAndTwoTabs
            + TextConverter.toUpperCamel(source.getAggregateRootObjectName().getText())
            + "Id>");
    stringBuilder.append(changeLineAndTwoTabs + "implements ");
    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));

    return stringBuilder.toString();
  }
}
