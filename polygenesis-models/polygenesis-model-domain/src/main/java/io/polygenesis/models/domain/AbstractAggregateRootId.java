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

package io.polygenesis.models.domain;

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataGroup;
import java.util.Objects;

/**
 * The type Abstract aggregate root id.
 *
 * @author Christos Tsakostas
 */
public class AbstractAggregateRootId extends BaseProperty<AbstractAggregateRootId, DataGroup> {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private GenericTypeParameter genericTypeParameter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract aggregate root id.
   *
   * @param data the data
   * @param genericTypeParameter the generic type parameter
   */
  public AbstractAggregateRootId(DataGroup data, GenericTypeParameter genericTypeParameter) {
    super(PropertyType.ABSTRACT_AGGREGATE_ROOT_ID, data);
    setGenericTypeParameter(genericTypeParameter);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets generic type parameter.
   *
   * @return the generic type parameter
   */
  public GenericTypeParameter getGenericTypeParameter() {
    return genericTypeParameter;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets generic type parameter.
   *
   * @param genericTypeParameter the generic type parameter
   */
  private void setGenericTypeParameter(GenericTypeParameter genericTypeParameter) {
    Assertion.isNotNull(genericTypeParameter, "genericTypeParameter is required");
    this.genericTypeParameter = genericTypeParameter;
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public Data getTypeParameterData() {
    throw new UnsupportedOperationException();
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
    if (!super.equals(o)) {
      return false;
    }
    AbstractAggregateRootId that = (AbstractAggregateRootId) o;
    return Objects.equals(genericTypeParameter, that.genericTypeParameter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), genericTypeParameter);
  }
}
