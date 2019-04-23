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

package io.polygenesis.models.domain;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Metamodel;
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataBusinessType;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.core.data.DataSource;
import io.polygenesis.core.data.DataValidator;
import io.polygenesis.core.data.VariableName;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Supportive entity.
 *
 * @author Christos Tsakostas
 */
public class SupportiveEntity extends BaseDomainEntity
    implements DomainObjectProperty<DataGroup>, Metamodel {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Supportive entity.
   *
   * @param objectName the object name
   * @param packageName the package name
   * @param properties the properties
   * @param constructors the constructors
   */
  @SuppressWarnings("rawtypes")
  public SupportiveEntity(
      ObjectName objectName,
      PackageName packageName,
      Set<DomainObjectProperty> properties,
      Set<Constructor> constructors) {
    super(
        DomainObjectType.HELPER_ENTITY,
        InstantiationType.CONCRETE,
        objectName,
        packageName,
        properties,
        constructors,
        false);
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public boolean hasSuperclass() {
    return false;
  }

  @Override
  public BaseDomainObject getSuperClass() {
    // TODO
    return null;
  }

  @Override
  public PropertyType getPropertyType() {
    return PropertyType.REFERENCE;
  }

  @Override
  public DataGroup getData() {
    Set<Data> models = new LinkedHashSet<>();

    return new DataGroup(
        DataSource.user(),
        new VariableName(getObjectName().getText()),
        DataBusinessType.ANY,
        DataValidator.empty(),
        getObjectName(),
        getPackageName(),
        models);
  }

  @Override
  public Data getTypeParameterData() {
    throw new UnsupportedOperationException();
  }
}
