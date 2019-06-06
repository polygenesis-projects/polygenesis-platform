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

package io.polygenesis.models.domain.supportiveentity;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.SupportiveEntityId;
import io.polygenesis.models.domain.shared.AbstractPropertyDeducer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Supportive entity property deducer.
 *
 * @author Christos Tsakostas
 */
public class SupportiveEntityPropertyDeducer extends AbstractPropertyDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Supportive entity property deducer. */
  public SupportiveEntityPropertyDeducer() {
    super();
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @SuppressWarnings("rawtypes")
  @Override
  protected Set<DomainObjectProperty> makeIdentityDomainObjectProperties(
      Thing thing, PackageName rootPackageName) {
    Set<DomainObjectProperty> properties = new LinkedHashSet<>();

    properties.add(makeSupportiveEntityId(thing, rootPackageName));

    return properties;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make supportive entity id supportive entity id.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the supportive entity id
   */
  protected SupportiveEntityId makeSupportiveEntityId(Thing thing, PackageName rootPackageName) {
    DataObject dataObject =
        new DataObject(
            new ObjectName(thing.getThingName().getText() + "Id"),
            thing.makePackageName(rootPackageName, thing));

    return new SupportiveEntityId(dataObject);
  }
}
