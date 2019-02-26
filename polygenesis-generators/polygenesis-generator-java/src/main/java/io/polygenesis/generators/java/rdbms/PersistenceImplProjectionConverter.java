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

import io.polygenesis.commons.converter.Converter;
import io.polygenesis.commons.text.Name;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.generators.java.shared.AbstractObjectProjectionMaker;
import io.polygenesis.generators.java.shared.ArgumentProjection;
import io.polygenesis.generators.java.shared.ConstructorProjection;
import io.polygenesis.generators.java.shared.FunctionProjection;
import io.polygenesis.generators.java.shared.ObjectProjection;
import io.polygenesis.models.domain.Persistence;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Persistence impl projection converter.
 *
 * @author Christos Tsakostas
 */
public class PersistenceImplProjectionConverter extends AbstractObjectProjectionMaker
    implements Converter<Persistence, ObjectProjection> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Persistence impl projection converter.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public PersistenceImplProjectionConverter(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectProjection convert(Persistence source, Object... args) {
    PackageName rootPackageName = (PackageName) args[0];
    Name contextName = (Name) args[1];

    return new ObjectProjection(
        source.getPackageName().getText(),
        projectImports(rootPackageName, contextName),
        projectDescription(source),
        projectObjectName(source),
        projectObjectNameWithOptionalExtendsImplements(source, contextName),
        projectVariables(),
        projectConstructors(source, contextName),
        projectMethods());
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Project imports set.
   *
   * @param rootPackageName the root package name
   * @param contextName the context name
   * @return the set
   */
  protected Set<String> projectImports(PackageName rootPackageName, Name contextName) {
    Set<String> imports = new TreeSet<>();

    String context = TextConverter.toUpperCamel(contextName.getText());

    imports.add("com.oregor.ddd4j.core.AbstractJpaPersistence");
    imports.add(rootPackageName.getText() + "." + context + "DomainMessageData");
    imports.add(rootPackageName.getText() + "." + context + "DomainMessageDataConverter");
    imports.add(rootPackageName.getText() + "." + context + "DomainMessageDataRepository");
    imports.add("org.springframework.stereotype.Repository");

    return imports;
  }

  /**
   * Project description string.
   *
   * @param persistence the persistence
   * @return the string
   */
  protected String projectDescription(Persistence persistence) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(persistence.getName().getText()));

    stringBuilder.append(" Implementation.");

    return stringBuilder.toString();
  }

  /**
   * Project object name string.
   *
   * @param persistence the persistence
   * @return the string
   */
  protected String projectObjectName(Persistence persistence) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(persistence.getName().getText()));
    stringBuilder.append("Impl");

    return stringBuilder.toString();
  }

  /**
   * Project object name with optional extends implements string.
   *
   * @param persistence the persistence
   * @param contextName the context name
   * @return the string
   */
  protected String projectObjectNameWithOptionalExtendsImplements(
      Persistence persistence, Name contextName) {
    StringBuilder stringBuilder = new StringBuilder();

    String context = TextConverter.toUpperCamel(contextName.getText());

    stringBuilder.append(TextConverter.toUpperCamel(persistence.getName().getText()));
    stringBuilder.append("Impl");
    stringBuilder.append("\n\t\textends AbstractJpaPersistence<");
    stringBuilder.append(
        "\n\t\t" + TextConverter.toUpperCamel(persistence.getAggregateRootName().getText()) + ",");
    stringBuilder.append(
        "\n\t\t"
            + TextConverter.toUpperCamel(persistence.getAggregateRootName().getText())
            + "Id,");
    stringBuilder.append("\n\t\t" + context + "DomainMessageData>");
    stringBuilder.append("\n\t\timplements ");
    stringBuilder.append(TextConverter.toUpperCamel(persistence.getName().getText()));

    return stringBuilder.toString();
  }

  /**
   * Project variables set.
   *
   * @return the set
   */
  protected Set<ArgumentProjection> projectVariables() {
    return new LinkedHashSet<>();
  }

  /**
   * Project cnstructors set.
   *
   * @param persistence the persistence
   * @param contextName the context name
   * @return the set
   */
  protected Set<ConstructorProjection> projectConstructors(
      Persistence persistence, Name contextName) {
    return new LinkedHashSet<>(
        Arrays.asList(
            new ConstructorProjection(
                new LinkedHashSet<>(
                    Arrays.asList(
                        new ArgumentProjection(
                            TextConverter.toUpperCamel(persistence.getAggregateRootName().getText())
                                + "Repository",
                            "repository"),
                        new ArgumentProjection(
                            TextConverter.toUpperCamel(contextName.getText())
                                + "DomainMessageDataRepository",
                            "domainMessageDataRepository"),
                        new ArgumentProjection(
                            TextConverter.toUpperCamel(contextName.getText())
                                + "DomainMessageDataConverter",
                            "domainMessageDataConverter"))),
                "super(repository, domainMessageDataRepository, domainMessageDataConverter);")));
  }

  /**
   * Project methods set.
   *
   * @return the set
   */
  protected Set<FunctionProjection> projectMethods() {
    // TODO
    return new LinkedHashSet<>();
  }
}
