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

package io.polygenesis.generators.java.common;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.apiimpl.ServiceMethodImplementation;

/**
 * The type Parent calling child data service.
 *
 * @author Christos Tsakostas
 */
public class ParentCallingChildDataService {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Get parent calling child data.
   *
   * @param source the source
   * @param aggregateEntityThing the aggregate entity thing
   * @return the parent calling child data
   */
  public ParentCallingChildData get(
      ServiceMethodImplementation source, Thing aggregateEntityThing) {
    return new ParentCallingChildData(getParentMethodName(source, aggregateEntityThing));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private String getParentMethodName(
      ServiceMethodImplementation source, Thing aggregateEntityThing) {
    String entityName = TextConverter.toUpperCamel(aggregateEntityThing.getThingName().getText());

    if (source.getFunction().getPurpose().isCreate()) {
      return String.format("add%s", entityName);
    } else if (source.getFunction().getPurpose().isModify()) {
      return String.format("%sId", entityName);
    } else if (source.getFunction().getPurpose().isFetchOne()) {
      return String.format("%sId", entityName);
    } else if (source.getFunction().getPurpose().isFetchPagedCollection()) {
      return String.format("%sId", entityName);
    } else if (source.getFunction().getPurpose().isFetchCollection()) {
      return String.format("%sId", entityName);
    } else {
      throw new IllegalStateException(
          String.format(
              "Cannot getParentMethodName for functions=%s",
              source.getFunction().getName().getText()));
    }
  }
}
