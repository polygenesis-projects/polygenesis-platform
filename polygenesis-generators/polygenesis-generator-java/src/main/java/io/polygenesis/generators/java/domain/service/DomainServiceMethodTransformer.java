/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.generators.java.domain.service;

import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.models.domain.DomainServiceMethod;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.Set;
import java.util.TreeSet;

public class DomainServiceMethodTransformer extends AbstractMethodTransformer<DomainServiceMethod> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain service method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   */
  public DomainServiceMethodTransformer(DataTypeTransformer dataTypeTransformer) {
    super(dataTypeTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public String modifiers(DomainServiceMethod source, Object... args) {
    return "";
  }

  @Override
  public Set<String> imports(DomainServiceMethod source, Object... args) {
    Set<String> imports = new TreeSet<>();

    // TODO
    imports.add("java.time.LocalDateTime");

    imports.addAll(super.imports(source, args));

    return imports;
  }
}
