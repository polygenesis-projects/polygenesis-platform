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

package io.polygenesis.models.domain.projection;

import io.polygenesis.abstraction.data.DataGroup;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.ProjectionId;
import io.polygenesis.models.domain.shared.AbstractPropertyDeducer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Projection property deducer.
 *
 * @author Christos Tsakostas
 */
public class ProjectionPropertyDeducer extends AbstractPropertyDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

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

    properties.add(makeProjectionId(thing, rootPackageName));

    return properties;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make projection id projection id.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the projection id
   */
  private ProjectionId makeProjectionId(Thing thing, PackageName rootPackageName) {
    DataGroup dataGroup =
        new DataGroup(
            new ObjectName(thing.getThingName().getText() + "Id"),
            thing.makePackageName(rootPackageName, thing));

    return new ProjectionId(dataGroup);
  }
}
