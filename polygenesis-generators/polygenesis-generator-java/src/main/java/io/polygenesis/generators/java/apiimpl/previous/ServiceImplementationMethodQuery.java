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

package io.polygenesis.generators.java.apiimpl.previous;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.apiimpl.DomainObjectConverter;
import io.polygenesis.models.domain.BaseDomainObject;

/**
 * The type Service implementation method query.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementationMethodQuery extends ServiceImplementationMethodShared {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service implementation method query.
   *
   * @param freemarkerService the freemarker service
   */
  public ServiceImplementationMethodQuery(FreemarkerService freemarkerService) {
    super(freemarkerService);
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Make query implementation string.
   *
   * @param serviceMethod the method
   * @param aggregateRoot the aggregate root
   * @param domainObjectConverter the aggregate root converter
   * @return the string
   */
  public String makeQueryImplementation(
      ServiceMethod serviceMethod,
      BaseDomainObject<?> aggregateRoot,
      DomainObjectConverter domainObjectConverter) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(makeNotNullAssertion(serviceMethod));

    if (serviceMethod.getFunction().getGoal().isFetchOne()) {
      stringBuilder.append(restoreAggregateRoot(serviceMethod, aggregateRoot));
      stringBuilder.append("\n");
      stringBuilder.append(
          makeReturnValueForFetchOne(serviceMethod, aggregateRoot, domainObjectConverter));
    }

    if (serviceMethod.getFunction().getGoal().isFetchPagedCollection()) {
      stringBuilder.append(makePaginated(serviceMethod, aggregateRoot));
      stringBuilder.append("\n");
      stringBuilder.append(
          makeReturnValueForFetchPagedCollection(serviceMethod, domainObjectConverter));
    }

    return stringBuilder.toString();
  }

  /**
   * Make not null assertion string.
   *
   * @param serviceMethod the service method
   * @return the string
   */
  protected String makeNotNullAssertion(ServiceMethod serviceMethod) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\t\t");
    stringBuilder.append("Assertion.isNotNull(");
    stringBuilder.append(
        TextConverter.toLowerCamel(serviceMethod.getRequestDto().getDataGroup().getDataType()));
    stringBuilder.append(", \"");
    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(
            serviceMethod.getRequestDto().getDataGroup().getDataType()));
    stringBuilder.append(" is required\");");
    stringBuilder.append("\n");
    stringBuilder.append("\n");

    return stringBuilder.toString();
  }

  /**
   * Make paginated string.
   *
   * @param serviceMethod the method
   * @param aggregateRoot the aggregate root
   * @return the string
   */
  protected String makePaginated(ServiceMethod serviceMethod, BaseDomainObject<?> aggregateRoot) {
    StringBuilder stringBuilder = new StringBuilder();

    String request =
        TextConverter.toLowerCamel(serviceMethod.getRequestDto().getDataGroup().getDataType());

    stringBuilder.append("\t\t");
    stringBuilder.append("Paginated<");
    stringBuilder.append(TextConverter.toUpperCamel(aggregateRoot.getObjectName().getText()));
    stringBuilder.append(">");
    stringBuilder.append(" paginated = ");
    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getObjectName().getText()));
    stringBuilder.append("Persistence.findPaginated(");
    stringBuilder.append(request);
    stringBuilder.append(".getPageNumber()");
    stringBuilder.append(", ");
    stringBuilder.append(request);
    stringBuilder.append(".getPageSize()");
    stringBuilder.append(");");
    stringBuilder.append("\n");

    return stringBuilder.toString();
  }

  /**
   * Make return value for fetch one string.
   *
   * @param serviceMethod the method
   * @param aggregateRoot the aggregate root
   * @param domainObjectConverter the aggregate root converter
   * @return the string
   */
  protected String makeReturnValueForFetchOne(
      ServiceMethod serviceMethod,
      BaseDomainObject<?> aggregateRoot,
      DomainObjectConverter domainObjectConverter) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\t\t");
    stringBuilder.append("return ");
    stringBuilder.append(domainObjectConverter.getVariableName().getText());
    stringBuilder.append(".convertTo");
    stringBuilder.append(
        TextConverter.toUpperCamel(serviceMethod.getResponseDto().getDataGroup().getDataType()));
    stringBuilder.append("(");
    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getObjectName().getText()));
    stringBuilder.append(");");

    return stringBuilder.toString();
  }

  /**
   * Make return value for fetch paged collection string.
   *
   * @param serviceMethod the method
   * @param domainObjectConverter the aggregate root converter
   * @return the string
   */
  protected String makeReturnValueForFetchPagedCollection(
      ServiceMethod serviceMethod, DomainObjectConverter domainObjectConverter) {
    StringBuilder stringBuilder = new StringBuilder();

    String request =
        TextConverter.toLowerCamel(serviceMethod.getRequestDto().getDataGroup().getDataType());

    stringBuilder.append("\t\t");
    stringBuilder.append("return new ");
    stringBuilder.append(
        TextConverter.toUpperCamel(serviceMethod.getResponseDto().getDataGroup().getDataType()));
    stringBuilder.append("(");
    stringBuilder.append("\n");
    stringBuilder.append("\t\t\t\tStreamSupport\n");
    stringBuilder.append("\t\t\t\t\t\t.stream(paginated.getItems().spliterator(), false)\n");
    stringBuilder.append("\t\t\t\t\t\t.map(");
    stringBuilder.append(
        TextConverter.toLowerCamel(domainObjectConverter.getObjectName().getText()));
    stringBuilder.append("::convertTo");
    stringBuilder.append(
        TextConverter.toUpperCamel(
            serviceMethod
                .getResponseDto()
                .getArrayElementAsOptional()
                .orElseThrow(IllegalArgumentException::new)
                .getDataType()));
    stringBuilder.append(")\n");
    stringBuilder.append("\t\t\t\t\t\t.collect(Collectors.toList()),\n");
    stringBuilder.append("\t\t\t\tpaginated.getTotalPages(),\n");
    stringBuilder.append("\t\t\t\tpaginated.getTotalElements(),\n");
    stringBuilder.append(String.format("\t\t\t\t%s.getPageNumber(),\n", request));
    stringBuilder.append(String.format("\t\t\t\t%s.getPageSize()\n", request));
    stringBuilder.append("\t\t);");

    return stringBuilder.toString();
  }
}
