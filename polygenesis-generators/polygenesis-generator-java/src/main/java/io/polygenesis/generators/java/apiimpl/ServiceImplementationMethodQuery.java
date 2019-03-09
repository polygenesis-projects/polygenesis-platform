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

package io.polygenesis.generators.java.apiimpl;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.api.Method;
import io.polygenesis.models.apiimpl.AggregateRootConverter;
import io.polygenesis.models.domain.AggregateRoot;

/**
 * The type Service implementation method query.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementationMethodQuery extends ServiceImplementationMethodShared {

  /**
   * Make query implementation string.
   *
   * @param method the method
   * @param aggregateRoot the aggregate root
   * @param aggregateRootConverter the aggregate root converter
   * @return the string
   */
  public String makeQueryImplementation(
      Method method, AggregateRoot aggregateRoot, AggregateRootConverter aggregateRootConverter) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(makeNotNullAssertion(method));

    if (method.getFunction().getGoal().isFetchOne()) {
      stringBuilder.append(restoreAggregateRoot(method, aggregateRoot));
      stringBuilder.append("\n");
      stringBuilder.append(
          makeReturnValueForFetchOne(method, aggregateRoot, aggregateRootConverter));
    }

    if (method.getFunction().getGoal().isFetchPagedCollection()) {
      stringBuilder.append(makePaginated(method, aggregateRoot));
      stringBuilder.append("\n");
      stringBuilder.append(makeReturnValueForFetchPagedCollection(method, aggregateRootConverter));
    }

    return stringBuilder.toString();
  }

  /**
   * Make not null assertion string.
   *
   * @return the string
   */
  protected String makeNotNullAssertion(Method method) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\t\t");
    stringBuilder.append("Assertion.isNotNull(");
    stringBuilder.append(
        TextConverter.toLowerCamel(
            method.getRequestDto().getOriginatingIoModelGroup().getDataType()));
    stringBuilder.append(", \"");
    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(
            method.getRequestDto().getOriginatingIoModelGroup().getDataType()));
    stringBuilder.append(" is required\");");
    stringBuilder.append("\n");
    stringBuilder.append("\n");

    return stringBuilder.toString();
  }

  /**
   * Make paginated string.
   *
   * @param method the method
   * @param aggregateRoot the aggregate root
   * @return the string
   */
  protected String makePaginated(Method method, AggregateRoot aggregateRoot) {
    StringBuilder stringBuilder = new StringBuilder();

    String request =
        TextConverter.toLowerCamel(
            method.getRequestDto().getOriginatingIoModelGroup().getDataType());

    stringBuilder.append("\t\t");
    stringBuilder.append("Paginated<");
    stringBuilder.append(TextConverter.toUpperCamel(aggregateRoot.getName().getText()));
    stringBuilder.append(">");
    stringBuilder.append(" paginated = ");
    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getName().getText()));
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
   * @param method the method
   * @param aggregateRoot the aggregate root
   * @param aggregateRootConverter the aggregate root converter
   * @return the string
   */
  protected String makeReturnValueForFetchOne(
      Method method, AggregateRoot aggregateRoot, AggregateRootConverter aggregateRootConverter) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\t\t");
    stringBuilder.append("return ");
    stringBuilder.append(aggregateRootConverter.getVariableName().getText());
    stringBuilder.append(".convertTo");
    stringBuilder.append(
        TextConverter.toUpperCamel(
            method.getResponseDto().getOriginatingIoModelGroup().getDataType()));
    stringBuilder.append("(");
    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getName().getText()));
    stringBuilder.append(");");

    return stringBuilder.toString();
  }

  /**
   * Make return value for fetch paged collection string.
   *
   * @param method the method
   * @param aggregateRootConverter the aggregate root converter
   * @return the string
   */
  protected String makeReturnValueForFetchPagedCollection(
      Method method, AggregateRootConverter aggregateRootConverter) {
    StringBuilder stringBuilder = new StringBuilder();

    String request =
        TextConverter.toLowerCamel(
            method.getRequestDto().getOriginatingIoModelGroup().getDataType());

    stringBuilder.append("\t\t");
    stringBuilder.append("return new ");
    stringBuilder.append(
        TextConverter.toUpperCamel(
            method.getResponseDto().getOriginatingIoModelGroup().getDataType()));
    stringBuilder.append("(");
    stringBuilder.append("\n");
    stringBuilder.append("\t\t\t\tStreamSupport\n");
    stringBuilder.append("\t\t\t\t\t\t.stream(paginated.getItems().spliterator(), false)\n");
    stringBuilder.append("\t\t\t\t\t\t.map(");
    stringBuilder.append(
        TextConverter.toLowerCamel(aggregateRootConverter.getObjectName().getText()));
    stringBuilder.append("::convertTo");
    stringBuilder.append(
        TextConverter.toUpperCamel(
            method
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
