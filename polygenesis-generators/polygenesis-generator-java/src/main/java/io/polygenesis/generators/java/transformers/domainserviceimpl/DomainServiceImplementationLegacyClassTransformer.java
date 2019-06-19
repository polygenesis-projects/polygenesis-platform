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

package io.polygenesis.generators.java.transformers.domainserviceimpl;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.shared.transformer.AbstractLegacyClassTransformer;
import io.polygenesis.generators.java.shared.transformer.FromDataTypeToJavaConverter;
import io.polygenesis.models.domain.DomainService;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Domain service implementation class representable.
 *
 * @author Christos Tsakostas
 */
public class DomainServiceImplementationLegacyClassTransformer
    extends AbstractLegacyClassTransformer<DomainService> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain service implementation class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public DomainServiceImplementationLegacyClassTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(DomainService source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      DomainService source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(DomainService source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String packageName(DomainService source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(DomainService source, Object... args) {
    Set<String> imports = new LinkedHashSet<>();

    imports.add("org.springframework.stereotype.Service");

    return imports;
  }

  @Override
  public Set<String> annotations(DomainService source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Service");

    return annotations;
  }

  @Override
  public String description(DomainService source, Object... args) {
    return String.format(
        "The %s Domain Service Implementation.",
        TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));
  }

  @Override
  public String modifiers(DomainService source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(DomainService source, Object... args) {
    return String.format(
        "%sDomainServiceImpl", TextConverter.toUpperCamel(source.getObjectName().getText()));
  }

  @Override
  public String fullObjectName(DomainService source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        String.format(
            "%sDomainServiceImpl", TextConverter.toUpperCamel(source.getObjectName().getText())));
    stringBuilder.append(" implements ");
    stringBuilder.append(
        String.format(
            "%sDomainService", TextConverter.toUpperCamel(source.getObjectName().getText())));

    return stringBuilder.toString();
  }
}
