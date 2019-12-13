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

package io.polygenesis.abstraction.data;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.Objects;

/**
 * The type Data reference to thing by value.
 *
 * @author Christos Tsakostas
 */
public class DataReferenceToThingByValue extends AbstractData {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private final Thing thing;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Of data reference to thing by value.
   *
   * @param thing the thing
   * @param variableName the variable name
   * @return the data reference to thing by value
   */
  @SuppressWarnings("CPD-START")
  public static DataReferenceToThingByValue of(Thing thing, String variableName) {
    return new DataReferenceToThingByValue(
        DataPrimaryType.THING,
        new VariableName(variableName),
        DataPurpose.referenceToThingByValue(),
        DataValidator.empty(),
        thing);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Data reference to thing by value.
   *
   * @param dataPrimaryType the data primary type
   * @param variableName the variable name
   * @param dataPurpose the data purpose
   * @param dataValidator the data validator
   * @param thing the thing
   */
  public DataReferenceToThingByValue(
      DataPrimaryType dataPrimaryType,
      VariableName variableName,
      DataPurpose dataPurpose,
      DataValidator dataValidator,
      Thing thing) {
    super(dataPrimaryType, variableName, dataPurpose, dataValidator, DataSourceType.DEFAULT);
    Assertion.isNotNull(thing, "thing is required");
    this.thing = thing;
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
  public String getDataType() {
    return TextConverter.toUpperCamel(thing.getThingName().getText());
  }

  /**
   * Gets as data primitive.
   *
   * @param rootPackageName the root package name
   * @return the as data primitive
   */
  // TODO: The following implementation needs SERIOUS refactoring
  public DataPrimitive getAsDataPrimitive(String rootPackageName) {
    DataObject thingId =
        thing.getThingIdentityAsDataObjectFromDataPrimitive(
            new PackageName(rootPackageName),
            getVariableName(),
            DataPrimitive.of(PrimitiveType.UUID, new VariableName("rootId")));

    return DataPrimitive.ofDataBusinessTypeWithDataObject(
        DataPurpose.referenceToThingById(), PrimitiveType.STRING, getVariableName(), thingId);
  }

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
    DataReferenceToThingByValue that = (DataReferenceToThingByValue) o;
    return Objects.equals(thing, that.thing);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), thing);
  }
}
