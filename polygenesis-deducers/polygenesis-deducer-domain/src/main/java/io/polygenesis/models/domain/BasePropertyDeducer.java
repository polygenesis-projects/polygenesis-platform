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

import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingProperty;
import io.polygenesis.core.data.DataBusinessType;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Base property deducer.
 *
 * @author Christos Tsakostas
 */
public abstract class BasePropertyDeducer {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Ensure thing properties.
   *
   * @param thing the thing
   */
  protected void ensureThingProperties(Thing thing) {
    if (thing.getThingProperties().isEmpty()) {
      Set<ThingProperty> thingProperties = new LinkedHashSet<>();

      thing
          .getFunctions()
          .stream()
          .map(function -> function.getArguments())
          .flatMap(arguments -> arguments.stream().map(argument -> argument.getData()))
          .filter(data -> data.isDataGroup())
          .map(data -> data.getAsDataGroup())
          .flatMap(dataGroup -> dataGroup.getModels().stream())
          .filter(data -> data.getDataBusinessType().equals(DataBusinessType.ANY))
          .forEach(data -> thingProperties.add(new ThingProperty(data)));

      thing.assignThingProperties(thingProperties);
    }
  }

  /**
   * Thing property to base property base property.
   *
   * @param thingProperty the thing property
   * @return the base property
   */
  protected BaseProperty thingPropertyToBaseProperty(ThingProperty thingProperty) {

    switch (thingProperty.getData().getDataPrimaryType()) {
      case PRIMITIVE:
        return new Primitive(thingProperty.getData().getAsDataPrimitive());
      case OBJECT:
        return new ValueObject(thingProperty.getData().getAsDataGroup());
      case ARRAY:
        switch (thingProperty.getData().getAsDataArray().getArrayElement().getDataPrimaryType()) {
          case PRIMITIVE:
            return new PrimitiveCollection(thingProperty.getData().getAsDataArray());
          case OBJECT:
            return new ValueObjectCollection(thingProperty.getData().getAsDataArray());
          default:
            throw new UnsupportedOperationException();
        }
      default:
        throw new UnsupportedOperationException();
    }
  }
}
