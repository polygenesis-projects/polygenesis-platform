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

package io.polygenesis.models.domain.common;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.models.domain.AggregateEntityId;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.ReferenceToAbstractAggregateRoot;
import io.polygenesis.models.domain.ReferenceToAggregateRoot;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Domain object properties deducer.
 *
 * @author Christos Tsakostas
 */
public class IdentityDomainObjectPropertiesDeducer {

  // ===============================================================================================
  // PUBLIC - AGGREGATE ENTITY
  // ===============================================================================================

  /**
   * Make entity identity domain object properties set.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<DomainObjectProperty<?>> makeEntityIdentityDomainObjectProperties(
      Thing thing, PackageName rootPackageName) {
    Set<DomainObjectProperty<?>> properties = new LinkedHashSet<>();

    properties.add(makeAggregateEntityId(thing, rootPackageName));

    if (isAbstract(thing.getOptionalParent())) {
      properties.add(
          makeReferenceToAbstractAggregateRoot(thing.getOptionalParent(), rootPackageName));
    } else {
      properties.add(makeReferenceToAggregateRoot(thing.getOptionalParent(), rootPackageName));
    }

    return properties;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private AggregateEntityId makeAggregateEntityId(Thing thing, PackageName rootPackageName) {
    String variableAndId = thing.getThingName().getText() + "Id";
    DataObject dataObject =
        new DataObject(
            new VariableName(variableAndId),
            DataPurpose.thingIdentity(),
            DataValidator.empty(),
            new ObjectName(variableAndId),
            thing.makePackageName(rootPackageName, thing),
            new LinkedHashSet<>(),
            DataSourceType.DEFAULT);

    return new AggregateEntityId(dataObject);
  }

  private ReferenceToAggregateRoot makeReferenceToAggregateRoot(
      Thing thingParent, PackageName rootPackageName) {
    return new ReferenceToAggregateRoot(
        new DataObject(
            new VariableName(thingParent.getThingName().getText()),
            DataPurpose.referenceToThingByValue(),
            DataValidator.empty(),
            new ObjectName(thingParent.getThingName().getText()),
            thingParent.makePackageName(rootPackageName, thingParent),
            new LinkedHashSet<>(),
            DataSourceType.DEFAULT));
  }

  private ReferenceToAbstractAggregateRoot makeReferenceToAbstractAggregateRoot(
      Thing thingParent, PackageName rootPackageName) {
    return new ReferenceToAbstractAggregateRoot(
        new DataObject(
            new VariableName(thingParent.getThingName().getText()),
            DataPurpose.referenceToThingByValue(),
            DataValidator.empty(),
            new ObjectName(thingParent.getThingName().getText()),
            thingParent.makePackageName(rootPackageName, thingParent),
            new LinkedHashSet<>(),
            DataSourceType.DEFAULT));
  }

  private Boolean isAbstract(Thing thing) {
    return thing.getAbstractionsScopes().contains(AbstractionScope.domainAbstractAggregateRoot());
  }
}
