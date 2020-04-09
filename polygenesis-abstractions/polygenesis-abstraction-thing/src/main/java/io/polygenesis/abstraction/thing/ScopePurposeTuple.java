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

package io.polygenesis.abstraction.thing;

import io.polygenesis.core.AbstractionScope;
import java.util.Objects;

/**
 * The type Scope purpose tuple.
 *
 * @author Christos Tsakostas
 */
public class ScopePurposeTuple {

  private AbstractionScope abstractionScope;
  private Purpose purpose;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Scope purpose tuple.
   *
   * @param abstractionScope the abstraction scope
   * @param purpose the purpose
   */
  public ScopePurposeTuple(AbstractionScope abstractionScope, Purpose purpose) {
    this.abstractionScope = abstractionScope;
    this.purpose = purpose;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScopePurposeTuple that = (ScopePurposeTuple) o;
    return Objects.equals(abstractionScope, that.abstractionScope)
        && Objects.equals(purpose, that.purpose);
  }

  @Override
  public int hashCode() {
    return Objects.hash(abstractionScope, purpose);
  }
}
