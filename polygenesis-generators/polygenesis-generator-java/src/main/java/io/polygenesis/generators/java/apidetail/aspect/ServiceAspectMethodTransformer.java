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

package io.polygenesis.generators.java.apidetail.aspect;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.Collections;
import java.util.Set;

/**
 * The type Service aspect method transformer.
 *
 * @author Christos Tsakostas
 */
public class ServiceAspectMethodTransformer extends AbstractMethodTransformer<Function> {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private ServiceAspectActivityRegistry serviceAspectActivityRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service aspect method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param serviceAspectActivityRegistry the service aspect activity registry
   */
  public ServiceAspectMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      ServiceAspectActivityRegistry serviceAspectActivityRegistry) {
    super(dataTypeTransformer);
    this.serviceAspectActivityRegistry = serviceAspectActivityRegistry;
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
              "\t\t\t\"execution(public * %s.*CommandServiceImpl.*(..)) \"%n",
              rootPackageName.getText()));
      stringBuilder.append(
          String.format(
              "\t\t\t\t\t+ \" || execution(* %s..*.*CommandServiceImpl.*(..))\"%n",
              rootPackageName.getText()));
      stringBuilder.append(
          String.format(
              "\t\t\t\t\t+ \" || execution(* %s..*.*.*CommandServiceImpl.*(..))\"%n",
              rootPackageName.getText()));
      stringBuilder.append(
          String.format(
              "\t\t\t\t\t+ \" || execution(* %s.*QueryServiceImpl.*(..))\"%n",
              rootPackageName.getText()));
      stringBuilder.append(
          String.format(
              "\t\t\t\t\t+ \" || execution(* %s..*.*QueryServiceImpl.*(..))\"%n",
              rootPackageName.getText()));
      stringBuilder.append(
          String.format(
              "\t\t\t\t\t+ \" || execution(* %s..*.*.*QueryServiceImpl.*(..))\")",
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
    if (serviceAspectActivityRegistry.isActivitySupportedFor(source)) {
      return serviceAspectActivityRegistry.activityFor(source, args);
    } else {
      return super.implementation(source, args);
    }
  }
}
