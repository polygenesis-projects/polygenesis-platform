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

import io.polygenesis.commons.converter.Converter;
import io.polygenesis.core.Function;
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.generators.java.shared.AbstractFunctionProjectionMaker;
import io.polygenesis.models.api.Method;
import io.polygenesis.models.apiimpl.AggregateRootConverter;
import io.polygenesis.models.domain.AggregateRoot;

/**
 * The type Api impl method projection maker.
 *
 * @author Christos Tsakostas
 */
public class ApiImplMethodProjectionConverter extends AbstractFunctionProjectionMaker
    implements Converter<Method, ApiImplMethodProjection> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final ApiImplCommandMethodImplementation apiImplCommandMethodImplementation;
  private final ApiImplQueryMethodImplementation apiImplQueryMethodImplementation;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Api impl method projection converter.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param apiImplCommandMethodImplementation the api impl command method implementation
   * @param apiImplQueryMethodImplementation the api impl query method implementation
   */
  public ApiImplMethodProjectionConverter(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      ApiImplCommandMethodImplementation apiImplCommandMethodImplementation,
      ApiImplQueryMethodImplementation apiImplQueryMethodImplementation) {
    super(fromDataTypeToJavaConverter);
    this.apiImplCommandMethodImplementation = apiImplCommandMethodImplementation;
    this.apiImplQueryMethodImplementation = apiImplQueryMethodImplementation;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ApiImplMethodProjection convert(Method method, Object... args) {
    Function function = method.getFunction();
    AggregateRoot aggregateRoot = (AggregateRoot) args[0];
    AggregateRootConverter aggregateRootConverter = (AggregateRootConverter) args[1];

    return new ApiImplMethodProjection(
        function,
        projectName(function),
        projectDescription(),
        projectReturnValue(function),
        projectArguments(function),
        projectAnnotations(),
        projectImplementation(function, aggregateRoot, aggregateRootConverter));
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Project implementation string.
   *
   * @param function the function
   * @param aggregateRoot the aggregate root
   * @param aggregateRootConverter the aggregate root converter
   * @return the string
   */
  protected String projectImplementation(
      Function function,
      AggregateRoot aggregateRoot,
      AggregateRootConverter aggregateRootConverter) {

    if (function.getGoal().isCommand()) {
      return apiImplCommandMethodImplementation.makeCommandImplementation(
          function, aggregateRoot, aggregateRootConverter);
    } else {
      return apiImplQueryMethodImplementation.makeQueryImplementation(
          function, aggregateRoot, aggregateRootConverter);
    }
  }
}
