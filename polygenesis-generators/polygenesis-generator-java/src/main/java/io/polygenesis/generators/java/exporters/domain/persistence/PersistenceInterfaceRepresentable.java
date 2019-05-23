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

package io.polygenesis.generators.java.exporters.domain.persistence;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.exporters.domain.shared.AbstractRepositoryInterfaceRepresentable;
import io.polygenesis.generators.java.skeletons.FromDataTypeToJavaConverter;
import io.polygenesis.generators.java.skeletons.FunctionToMethodRepresentationConverter;
import io.polygenesis.models.domain.Persistence;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Persistence interface representable.
 *
 * @author Christos Tsakostas
 */
public class PersistenceInterfaceRepresentable extends AbstractRepositoryInterfaceRepresentable {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Persistence interface representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param functionToMethodRepresentationConverter the function to method representation converter
   */
  @SuppressWarnings("CPD-START")
  public PersistenceInterfaceRepresentable(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      FunctionToMethodRepresentationConverter functionToMethodRepresentationConverter) {
    super(fromDataTypeToJavaConverter, functionToMethodRepresentationConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

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
