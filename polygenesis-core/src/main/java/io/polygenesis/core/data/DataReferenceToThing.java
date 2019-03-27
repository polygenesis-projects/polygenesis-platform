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

package io.polygenesis.core.data;

import io.polygenesis.core.Thing;
import java.util.Objects;

/**
 * The type Data reference to thing.
 *
 * @author Christos Tsakostas
 */
public class DataReferenceToThing extends Data {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private final Thing thing;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Data reference to thing.
   *
   * @param thing the thing
   * @param variableName the variable name
   */
  public DataReferenceToThing(Thing thing, VariableName variableName) {
    super(
        DataPrimaryType.THING,
        DataSource.user(),
        variableName,
        DataBusinessType.REFERENCE_TO_THING,
        DataValidator.empty());
    this.thing = thing;
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public String getDataType() {
    return DataPrimaryType.THING.name();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets thing.
   *
   * @return the thing
   */
  public Thing getThing() {
    return thing;
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
    DataReferenceToThing that = (DataReferenceToThing) o;
    return Objects.equals(thing, that.thing);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), thing);
  }
}
