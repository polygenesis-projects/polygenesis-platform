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
import io.polygenesis.generators.java.apidetail.service.activity.ServiceMethodActivityRegistry;
import io.polygenesis.models.apiimpl.ServiceMethodImplementation;
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

  private final ServiceMethodActivityRegistry serviceMethodActivityRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service detail method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param serviceMethodActivityRegistry the service method activity registry
   */
  public ServiceDetailMethodTransformer(
      DataTypeTransformer dataTypeTransformer,
      ServiceMethodActivityRegistry serviceMethodActivityRegistry) {
    super(dataTypeTransformer);
    this.serviceMethodActivityRegistry = serviceMethodActivityRegistry;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public String implementation(ServiceMethodImplementation source, Object... args) {
    if (serviceMethodActivityRegistry.isActivitySupportedFor(source)) {
      return serviceMethodActivityRegistry.activityFor(source, args);
    } else {
      return super.implementation(source, args);
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
