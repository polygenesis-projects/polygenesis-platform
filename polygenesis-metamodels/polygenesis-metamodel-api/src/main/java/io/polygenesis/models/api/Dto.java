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

package io.polygenesis.models.api;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataArray;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Generatable;
import io.polygenesis.core.Nameable;
import java.util.Objects;
import java.util.Optional;

/**
 * The type Dto.
 *
 * @author Christos Tsakostas
 */
public class Dto implements Generatable, Nameable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Thing relatedThing;
  private DtoType dtoType;
  private DataObject dataObject;
  private Boolean virtual;
  private Dto parent;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Dto.
   *
   * @param relatedThing the related thing
   * @param dtoType the dto type
   * @param dataObject the data group
   * @param virtual the virtual
   */
  public Dto(Thing relatedThing, DtoType dtoType, DataObject dataObject, Boolean virtual) {
    setRelatedThing(relatedThing);
    setDtoType(dtoType);
    setDataObject(dataObject);
    setVirtual(virtual);
  }

  /**
   * Instantiates a new Dto.
   *
   * @param relatedThing the related thing
   * @param dtoType the dto type
   * @param dataObject the data object
   * @param virtual the virtual
   * @param parent the parent
   */
  public Dto(
      Thing relatedThing, DtoType dtoType, DataObject dataObject, Boolean virtual, Dto parent) {
    setRelatedThing(relatedThing);
    setDtoType(dtoType);
    setDataObject(dataObject);
    setVirtual(virtual);
    setParent(parent);
  }

  // ===============================================================================================
  // MUTATION
  // ===============================================================================================

  /**
   * With variable name equal to object name dto.
   *
   * @return the dto
   */
  public Dto withVariableNameEqualToObjectName() {
    return new Dto(
        getRelatedThing(),
        getDtoType(),
        getDataObject().withVariableNameEqualToObjectName(),
        getVirtual());
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets related thing.
   *
   * @return the related thing
   */
  public Thing getRelatedThing() {
    return relatedThing;
  }

  /**
   * Gets dto type.
   *
   * @return the dto type
   */
  public DtoType getDtoType() {
    return dtoType;
  }

  /**
   * Gets data group.
   *
   * @return the data group
   */
  public DataObject getDataObject() {
    return dataObject;
  }

  /**
   * Gets virtual.
   *
   * @return the virtual
   */
  public Boolean getVirtual() {
    return virtual;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Gets array element as optional.
   *
   * @return the array element as optional
   */
  public Optional<Data> getArrayElementAsOptional() {
    return getDataObject()
        .getModels()
        .stream()
        .filter(Data::isDataArray)
        .map(DataArray.class::cast)
        .filter(dataArray -> dataArray.getArrayElement() != null)
        .map(DataArray::getArrayElement)
        .findFirst();
  }

  /**
   * Gets thing identity as optional.
   *
   * @return the optional thing identity
   */
  public Optional<Data> getThingIdentityAsOptional() {
    return getDataObject().getModels().stream().filter(Data::isThingIdentity).findFirst();
  }

  /**
   * Gets parent thing identity as optional.
   *
   * @return the parent thing identity as optional
   */
  public Optional<Data> getParentThingIdentityAsOptional() {
    return getDataObject().getModels().stream().filter(Data::isParentThingIdentity).findFirst();
  }

  /**
   * Gets parent.
   *
   * @return the parent
   */
  public Dto getParent() {
    return parent;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets related thing.
   *
   * @param relatedThing the related thing
   */
  private void setRelatedThing(Thing relatedThing) {
    Assertion.isNotNull(relatedThing, "relatedThing is required");
    this.relatedThing = relatedThing;
  }

  /**
   * Sets dto type.
   *
   * @param dtoType the dto type
   */
  private void setDtoType(DtoType dtoType) {
    Assertion.isNotNull(dtoType, "dtoType is required");
    this.dtoType = dtoType;
  }

  /**
   * Sets data group.
   *
   * @param dataObject the data group
   */
  public void setDataObject(DataObject dataObject) {
    Assertion.isNotNull(dataObject, "dataObject is required");
    this.dataObject = dataObject;
  }

  /**
   * Sets virtual.
   *
   * @param virtual the virtual
   */
  private void setVirtual(Boolean virtual) {
    Assertion.isNotNull(virtual, "virtual is required");
    this.virtual = virtual;
  }

  /**
   * Sets parent.
   *
   * @param parent the parent
   */
  private void setParent(Dto parent) {
    Assertion.isNotNull(parent, "parent is required");
    this.parent = parent;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return dataObject.getObjectName();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Dto dto = (Dto) o;
    return Objects.equals(relatedThing, dto.relatedThing)
        && dtoType == dto.dtoType
        && Objects.equals(dataObject, dto.dataObject)
        && Objects.equals(virtual, dto.virtual)
        && Objects.equals(parent, dto.parent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(relatedThing, dtoType, dataObject, virtual, parent);
  }
}
