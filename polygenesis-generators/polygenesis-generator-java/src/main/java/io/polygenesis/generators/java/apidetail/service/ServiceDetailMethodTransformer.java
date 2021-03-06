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

package io.polygenesis.generators.java.apidetail.service;

import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.models.apiimpl.ServiceMethodImplementation;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Service detail method transformer.
 *
 * @author Christos Tsakostas
 */
public class ServiceDetailMethodTransformer
    extends AbstractMethodTransformer<ServiceMethodImplementation> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final ServiceDetailMethodActivityRegistry serviceDetailMethodActivityRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service detail method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param serviceDetailMethodActivityRegistry the service detail method activity registry
   */
  public ServiceDetailMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      ServiceDetailMethodActivityRegistry serviceDetailMethodActivityRegistry) {
    super(dataTypeTransformer);
    this.serviceDetailMethodActivityRegistry = serviceDetailMethodActivityRegistry;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(
      ServiceMethodImplementation source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation(
            dataTypeTransformer.convert(
                source.getServiceMethod().getRequestDto().getDataObject().getDataType()),
            source.getServiceMethod().getRequestDto().getDataObject().getVariableName().getText()));

    return parameterRepresentations;
  }

  @Override
  public String implementation(ServiceMethodImplementation source, Object... args) {
    if (serviceDetailMethodActivityRegistry.isActivitySupportedFor(source)) {
      return serviceDetailMethodActivityRegistry.activityFor(source, args);
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(super.implementation(source, args));
      stringBuilder.append(
          String.format(
              " // No impl found for purpose=%s", source.getFunction().getPurpose().getText()));
      return stringBuilder.toString();
    }
  }

  @Override
  public Set<String> annotations(ServiceMethodImplementation source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Override");

    return annotations;
  }

  @Override
  public String description(ServiceMethodImplementation source, Object... args) {
    return "";
  }
}
