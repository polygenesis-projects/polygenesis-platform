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
import io.polygenesis.core.Function;
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
   * @param function the function
   * @param aggregateRoot the aggregate root
   * @param aggregateRootConverter the aggregate root converter
   * @return the string
   */
  public String makeQueryImplementation(
      Function function,
      AggregateRoot aggregateRoot,
      AggregateRootConverter aggregateRootConverter) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(restoreAggregateRoot(function, aggregateRoot));

    stringBuilder.append("\n");

    if (function.getGoal().isFetchOne()) {
      stringBuilder.append(makeReturnValueForFetchOne(aggregateRoot, aggregateRootConverter));
    }

    return stringBuilder.toString();
  }

  /**
   * Make return value for fetch one string.
   *
   * @param aggregateRoot the aggregate root
   * @param aggregateRootConverter the aggregate root converter
   * @return the string
   */
  protected String makeReturnValueForFetchOne(
      AggregateRoot aggregateRoot, AggregateRootConverter aggregateRootConverter) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\t\t");
    stringBuilder.append("return ");
    stringBuilder.append(aggregateRootConverter.getVariableName().getText());
    stringBuilder.append(".convert(");
    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getName().getText()));
    stringBuilder.append(".get()");
    stringBuilder.append(");");

    return stringBuilder.toString();
  }
}
