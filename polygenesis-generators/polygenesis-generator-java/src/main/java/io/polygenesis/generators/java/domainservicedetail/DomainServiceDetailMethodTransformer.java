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

package io.polygenesis.generators.java.domainservicedetail;

import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.models.domain.DomainServiceMethod;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Domain service detail method transformer.
 *
 * @author Christos Tsakostas
 */
public class DomainServiceDetailMethodTransformer
    extends AbstractMethodTransformer<DomainServiceMethod> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain service detail method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   */
  public DomainServiceDetailMethodTransformer(DataTypeTransformer dataTypeTransformer) {
    super(dataTypeTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<String> annotations(DomainServiceMethod source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Override"));
  }

  @Override
  public String description(DomainServiceMethod source, Object... args) {
    return "";
  }

  @Override
  public String modifiers(DomainServiceMethod source, Object... args) {
    return MODIFIER_PUBLIC;
  }
}
