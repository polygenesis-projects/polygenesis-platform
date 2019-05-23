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

package io.polygenesis.generators.java.exporters.rdbms.projection;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.commons.representations.ParameterRepresentation;
import io.polygenesis.generators.java.exporters.rdbms.shared.AbstractRepositoryImplClassRepresentable;
import io.polygenesis.generators.java.skeletons.ConstructorRepresentation;
import io.polygenesis.generators.java.skeletons.FromDataTypeToJavaConverter;
import io.polygenesis.models.domain.Persistence;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Projection repository impl class representable.
 *
 * @author Christos Tsakostas
 */
public class ProjectionRepositoryImplClassRepresentable
    extends AbstractRepositoryImplClassRepresentable {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Projection repository impl class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public ProjectionRepositoryImplClassRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

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
            MODIFIER_PUBLIC,
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
