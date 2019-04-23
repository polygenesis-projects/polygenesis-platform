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

package io.polygenesis.generators.java.domain.aggregateentity;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.domain.DomainObjectClassRepresentable;
import io.polygenesis.models.domain.AggregateEntity;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Aggregate entity class representable.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntityClassRepresentable
    extends DomainObjectClassRepresentable<AggregateEntity> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate entity class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public AggregateEntityClassRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<String> annotations(AggregateEntity source, Object... args) {
    AggregateRoot aggregateRoot = (AggregateRoot) args[1];

    Set<String> annotations = new LinkedHashSet<>();

    if (source.getInstantiationType().equals(InstantiationType.CONCRETE)) {
      annotations.add("@Entity");
      annotations.add(
          String.format(
              "@Table(name = Constants.DEFAULT_TABLE_PREFIX + \"%s_%s\")",
              TextConverter.toLowerUnderscore(aggregateRoot.getObjectName().getText()),
              TextConverter.toLowerUnderscore(
                  TextConverter.toPlural(source.getObjectName().getText()))));
    } else {
      annotations.add("@MappedSuperclass");
    }

    return annotations;
  }

  @Override
  public String description(AggregateEntity source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Aggregate Entity.");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(AggregateEntity source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(AggregateEntity source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));

    return stringBuilder.toString();
  }
}
