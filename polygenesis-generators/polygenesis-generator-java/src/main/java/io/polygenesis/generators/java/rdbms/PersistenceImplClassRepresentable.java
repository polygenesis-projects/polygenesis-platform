/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

package io.polygenesis.generators.java.rdbms;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.Persistence;
import io.polygenesis.representations.commons.FieldRepresentation;
import io.polygenesis.representations.commons.ParameterRepresentation;
import io.polygenesis.representations.java.AbstractClassRepresentable;
import io.polygenesis.representations.java.ConstructorRepresentation;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Persistence impl class representable.
 *
 * @author Christos Tsakostas
 */
public class PersistenceImplClassRepresentable extends AbstractClassRepresentable<Persistence> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Persistence impl class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public PersistenceImplClassRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(Persistence source, Object... args) {
    return new LinkedHashSet<>();
  }

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
            TextConverter.toUpperCamel(contextName.getText()) + "DomainMessageDataRepository",
            "domainMessageDataRepository"));

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
            "\t\tsuper(repository, domainMessageDataRepository, domainMessageDataConverter);");

    return new LinkedHashSet<>(Arrays.asList(constructorRepresentation));
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Persistence source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String packageName(Persistence source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(Persistence source, Object... args) {
    PackageName rootPackageName = (PackageName) args[0];
    ObjectName contextName = (ObjectName) args[1];

    Set<String> imports = new TreeSet<>();

    String context = TextConverter.toUpperCamel(contextName.getText());

    if (source.getMultiTenant()) {
      imports.add("com.oregor.ddd4j.core.AbstractJpaPersistenceWithTenant");
    } else {
      imports.add("com.oregor.ddd4j.core.AbstractJpaPersistence");
    }

    imports.add(rootPackageName.getText() + "." + context + "DomainMessageData");
    imports.add(rootPackageName.getText() + "." + context + "DomainMessageDataConverter");
    imports.add(rootPackageName.getText() + "." + context + "DomainMessageDataRepository");
    imports.add("org.springframework.stereotype.Repository");

    return imports;
  }

  @Override
  public Set<String> annotations(Persistence source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Repository"));
  }

  @Override
  public String description(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Implementation.");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(Persistence source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(source.getObjectName().getText()));
    stringBuilder.append("Impl");

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
    stringBuilder.append("Impl");

    if (source.getMultiTenant()) {
      stringBuilder.append("\n\t\textends AbstractJpaPersistenceWithTenant<");
    } else {
      stringBuilder.append("\n\t\textends AbstractJpaPersistence<");
    }

    ObjectName contextName = (ObjectName) args[1];
    String context = TextConverter.toUpperCamel(contextName.getText());

    stringBuilder.append(
        "\n\t\t" + TextConverter.toUpperCamel(source.getAggregateRootObjectName().getText()) + ",");
    stringBuilder.append(
        "\n\t\t"
            + TextConverter.toUpperCamel(source.getAggregateRootObjectName().getText())
            + "Id,");
    stringBuilder.append("\n\t\t" + context + "DomainMessageData>");
    stringBuilder.append("\n\t\timplements ");
    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));

    return stringBuilder.toString();
  }
}
