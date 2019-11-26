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

package io.polygenesis.generators.java.apiclients.rest.aspect;

import static java.util.Collections.singletonList;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Rest service aspect method transformer.
 *
 * @author Christos Tsakostas
 */
public class RestServiceAspectMethodTransformer extends AbstractMethodTransformer<Function> {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private RestServiceAspectActivityRegistry restServiceAspectActivityRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Rest service aspect method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param restServiceAspectActivityRegistry the rest service aspect activity registry
   */
  public RestServiceAspectMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      RestServiceAspectActivityRegistry restServiceAspectActivityRegistry) {
    super(dataTypeTransformer);
    this.restServiceAspectActivityRegistry = restServiceAspectActivityRegistry;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<String> annotations(Function source, Object... args) {
    PackageName rootPackageName = (PackageName) args[0];

    if (source.getName().equals(new FunctionName("around"))) {
      StringBuilder stringBuilder = new StringBuilder();

      stringBuilder.append(String.format("@Around(%n"));
      stringBuilder.append(
          String.format(
              "\t\t\t\"execution(public * %s.*RestService.*(..)) \"%n", rootPackageName.getText()));
      stringBuilder.append(
          String.format(
              "\t\t\t\t\t+ \" || execution(* %s..*.*RestService.*(..))\"%n",
              rootPackageName.getText()));
      stringBuilder.append(
          String.format(
              "\t\t\t\t\t+ \" || execution(* %s..*.*.*RestService.*(..))\")",
              rootPackageName.getText()));

      return Collections.singleton(stringBuilder.toString());
    } else {
      return super.annotations(source, args);
    }
  }

  @Override
  public String description(Function source, Object... args) {
    return "";
  }

  @Override
  public String implementation(Function source, Object... args) {
    if (restServiceAspectActivityRegistry.isActivitySupportedFor(source)) {
      return restServiceAspectActivityRegistry.activityFor(source, args);
    } else {
      return super.implementation(source, args);
    }
  }

  @Override
  public Set<String> thrownExceptions(Function source, Object... args) {
    if (source.getName().equals(new FunctionName("around"))) {
      return new LinkedHashSet<>(singletonList("Throwable"));
    } else {
      return super.thrownExceptions(source, args);
    }
  }
}
