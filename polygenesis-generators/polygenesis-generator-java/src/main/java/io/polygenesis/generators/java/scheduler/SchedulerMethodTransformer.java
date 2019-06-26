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

package io.polygenesis.generators.java.scheduler;

import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.generators.java.shared.transformer.AbstractMethodTransformer;
import io.polygenesis.models.api.ServiceMethod;

/**
 * The type Service method transformer.
 *
 * @author Christos Tsakostas
 */
public class SchedulerMethodTransformer extends AbstractMethodTransformer<ServiceMethod> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   */
  public SchedulerMethodTransformer(DataTypeTransformer dataTypeTransformer) {
    super(dataTypeTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public String modifiers(ServiceMethod source, Object... args) {
    return "";
  }
}
