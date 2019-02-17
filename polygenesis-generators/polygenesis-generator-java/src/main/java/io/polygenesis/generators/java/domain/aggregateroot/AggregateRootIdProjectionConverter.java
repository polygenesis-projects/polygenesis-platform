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

package io.polygenesis.generators.java.domain.aggregateroot;

import io.polygenesis.commons.converter.Converter;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.generators.java.shared.AbstractObjectProjectionMaker;
import io.polygenesis.generators.java.shared.ConstructorProjection;
import io.polygenesis.generators.java.shared.MethodProjection;
import io.polygenesis.generators.java.shared.ObjectProjection;
import io.polygenesis.models.domain.AggregateRoot;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Aggregate root projection converter.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootIdProjectionConverter extends AbstractObjectProjectionMaker
    implements Converter<AggregateRoot, ObjectProjection> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root projection converter.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public AggregateRootIdProjectionConverter(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectProjection convert(AggregateRoot source, Object... arg) {
    return new ObjectProjection(
        source.getPackageName().getText(),
        projectImports(),
        projectDescription(source),
        projectObjectName(source),
        projectObjectNameWithOptionalExtendsImplements(source),
        projectVariables(),
        projectConstructors(),
        projectMethods());
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Project imports set.
   *
   * @return the set
   */
  protected Set<String> projectImports() {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.ddd4j.core.AggregateRootId");
    imports.add("javax.persistence.Embeddable");
    imports.add("java.util.UUID");

    return imports;
  }

  /**
   * Project description string.
   *
   * @param aggregateRoot the aggregate root
   * @return the string
   */
  protected String projectDescription(AggregateRoot aggregateRoot) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(aggregateRoot.getName().getText()));

    stringBuilder.append(" Aggregate Root Id.");

    return stringBuilder.toString();
  }

  /**
   * Project object name string.
   *
   * @param aggregateRoot the aggregate root
   * @return the string
   */
  protected String projectObjectName(AggregateRoot aggregateRoot) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getName().getText()));
    stringBuilder.append("Id");

    return stringBuilder.toString();
  }

  /**
   * Project object name with optional extends implements string.
   *
   * @param aggregateRoot the aggregate root
   * @return the string
   */
  protected String projectObjectNameWithOptionalExtendsImplements(AggregateRoot aggregateRoot) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(aggregateRoot.getName().getText()));
    stringBuilder.append("Id");
    stringBuilder.append(" extends ");
    stringBuilder.append("AggregateRootId");

    return stringBuilder.toString();
  }

  /**
   * Project variables set.
   *
   * @return the set
   */
  protected Set<KeyValue> projectVariables() {
    return new LinkedHashSet<>();
  }

  protected Set<ConstructorProjection> projectConstructors() {
    // TODO
    return new LinkedHashSet<>();
  }

  /**
   * Project methods set.
   *
   * @return the set
   */
  protected Set<MethodProjection> projectMethods() {
    // TODO
    return new LinkedHashSet<>();
  }
}
