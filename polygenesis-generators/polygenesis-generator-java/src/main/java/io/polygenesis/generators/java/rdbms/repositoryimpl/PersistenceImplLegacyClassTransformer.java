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

package io.polygenesis.generators.java.rdbms.repositoryimpl;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.generators.java.rdbms.AbstractRepositoryImplLegacyClassTransformer;
import io.polygenesis.generators.java.shared.transformer.FromDataTypeToJavaConverter;
import io.polygenesis.models.domain.Persistence;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Persistence impl class representable.
 *
 * @author Christos Tsakostas
 */
public class PersistenceImplLegacyClassTransformer
    extends AbstractRepositoryImplLegacyClassTransformer {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Persistence impl class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public PersistenceImplLegacyClassTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      Persistence source, Object... args) {
    ObjectName contextName = (ObjectName) args[1];
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation(
            TextConverter.toUpperCamel(source.getAggregateRootObjectName().getText())
                + "Repository",
            "repository"));

    parameterRepresentations.add(
        new ParameterRepresentation(
            TextConverter.toUpperCamel(contextName.getText()) + "SpringDomainMessageDataRepository",
            "springDomainMessageDataRepository"));

    parameterRepresentations.add(
        new ParameterRepresentation(
            TextConverter.toUpperCamel(contextName.getText()) + "DomainMessageDataConverter",
            "domainMessageDataConverter"));

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
                "\t\tsuper(%s.class, %s, %s, %s);",
                TextConverter.toUpperCamel(source.getAggregateRootIdObjectName().getText()),
                "repository",
                "springDomainMessageDataRepository",
                "domainMessageDataConverter"));

    return new LinkedHashSet<>(Arrays.asList(constructorRepresentation));
  }

  @Override
  public Set<String> imports(Persistence source, Object... args) {
    PackageName rootPackageName = (PackageName) args[0];
    ObjectName contextName = (ObjectName) args[1];

    Set<String> imports = new TreeSet<>();

    String context = TextConverter.toUpperCamel(contextName.getText());

    if (source.getMultiTenant()) {
      imports.add("com.oregor.trinity4j.domain.AbstractJpaTenantRepository");
    } else {
      imports.add("com.oregor.trinity4j.domain.AbstractJpaRepository");
    }

    imports.add(rootPackageName.getText() + "." + context + "DomainMessageData");
    imports.add(rootPackageName.getText() + "." + context + "DomainMessageDataConverter");
    imports.add(rootPackageName.getText() + "." + context + "SpringDomainMessageDataRepository");
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
      stringBuilder.append("extends AbstractJpaTenantRepository<");
    } else {
      stringBuilder.append("extends AbstractJpaRepository<");
    }

    ObjectName contextName = (ObjectName) args[1];
    String context = TextConverter.toUpperCamel(contextName.getText());

    stringBuilder.append(
        changeLineAndTwoTabs
            + TextConverter.toUpperCamel(source.getAggregateRootObjectName().getText())
            + ",");
    stringBuilder.append(
        changeLineAndTwoTabs
            + TextConverter.toUpperCamel(source.getAggregateRootObjectName().getText())
            + "Id,");
    stringBuilder.append(changeLineAndTwoTabs + context + "DomainMessageData>");
    stringBuilder.append(changeLineAndTwoTabs + "implements ");
    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));

    return stringBuilder.toString();
  }
}
