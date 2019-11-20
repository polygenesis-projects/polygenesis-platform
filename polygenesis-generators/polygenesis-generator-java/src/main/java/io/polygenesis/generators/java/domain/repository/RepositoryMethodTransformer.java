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

package io.polygenesis.generators.java.domain.repository;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Repository method transformer.
 *
 * @author Christos Tsakostas
 */
public class RepositoryMethodTransformer extends AbstractMethodTransformer<Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Repository method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   */
  public RepositoryMethodTransformer(DataTypeTransformer dataTypeTransformer) {
    super(dataTypeTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  /**
   * Modifiers string.
   *
   * @param source the source
   * @param args the args
   * @return the string
   */
  @Override
  public String modifiers(Function source, Object... args) {
    return "";
  }

  /**
   * Imports set.
   *
   * @param source the source
   * @param args the args
   * @return the set
   */
  @Override
  public Set<String> imports(Function source, Object... args) {
    Set<String> imports = new TreeSet<>();

    // TODO
    imports.add("java.time.LocalDateTime");

    imports.addAll(super.imports(source, args));

    return imports;
  }
}
