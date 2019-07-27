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

package io.polygenesis.models.domain.aggregateroot;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.models.domain.AggregateEntityId;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.ReferenceToAggregateRoot;
import io.polygenesis.models.domain.shared.AbstractPropertyDeducer;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Aggregate entity property deducer.
 *
 * @author Christos Tsakostas
 */
public class AggregateEntityPropertyDeducer extends AbstractPropertyDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Aggregate entity property deducer. */
  public AggregateEntityPropertyDeducer() {
    super();
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from set.
   *
   * @param thingParent the thing parent
   * @param thingChild the thingChild
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<DomainObjectProperty<?>> deduceFrom(
      Thing thingParent, Thing thingChild, PackageName rootPackageName) {
    Set<DomainObjectProperty<?>> properties = new LinkedHashSet<>();

    assertThatThingHasIdentity(thingParent);
    assertThatThingHasIdentity(thingChild);

    properties.addAll(makeIdentityDomainObjectProperties(thingChild, rootPackageName));

    //    properties.add(makeAggregateEntityId(thingChild, rootPackageName));
    //    properties.add(makeReferenceToAggregateRoot(thingParent, rootPackageName));

    properties.addAll(
        getThingPropertiesSuitableForDomainModel(thingChild)
            .stream()
            .map(this::toDomainObjectProperty)
            .collect(Collectors.toCollection(LinkedHashSet::new)));

    return properties;
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  protected Set<DomainObjectProperty<?>> makeIdentityDomainObjectProperties(
      Thing thing, PackageName rootPackageName) {
    Set<DomainObjectProperty<?>> properties = new LinkedHashSet<>();

    properties.add(makeAggregateEntityId(thing, rootPackageName));
    properties.add(makeReferenceToAggregateRoot(thing.getOptionalParent(), rootPackageName));

    return properties;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make aggregate entity id aggregate entity id.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the aggregate entity id
   */
  protected AggregateEntityId makeAggregateEntityId(Thing thing, PackageName rootPackageName) {
    DataObject dataObject =
        new DataObject(
            new ObjectName(thing.getThingName().getText() + "Id"),
            thing.makePackageName(rootPackageName, thing));

    return new AggregateEntityId(dataObject);
  }

  /**
   * Make reference to aggregate root reference to aggregate root.
   *
   * @param thingParent the thingParent
   * @return the reference to aggregate root
   */
  private ReferenceToAggregateRoot makeReferenceToAggregateRoot(
      Thing thingParent, PackageName rootPackageName) {
    return new ReferenceToAggregateRoot(
        new DataObject(
            new ObjectName(thingParent.getThingName().getText()),
            thingParent.makePackageName(rootPackageName, thingParent),
            new VariableName(thingParent.getThingName().getText())));
  }
}
