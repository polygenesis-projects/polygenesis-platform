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

package io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publishdtoconverter;

import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.AbstractNameablePackageable;
import java.util.LinkedHashSet;

/**
 * The type Domain message publish dto converter.
 *
 * @author Christos Tsakostas
 */
public class DomainMessagePublishDtoConverter extends AbstractNameablePackageable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Function getContext;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message publish dto converter.
   *
   * @param objectName the object name
   * @param packageName the package name
   */
  public DomainMessagePublishDtoConverter(ObjectName objectName, PackageName packageName) {
    super(objectName, packageName);
    getContext = makeGetContextFunction();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets get context.
   *
   * @return the get context
   */
  public Function getGetContext() {
    return getContext;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Function makeGetContextFunction() {
    Thing thing =
        ThingBuilder.domainDetailDomainMessagePublisher("domainMessagePublishDtoConverter")
            .createThing();

    return new Function(
        thing,
        Purpose.domainMessagePublisherGetContext(),
        new FunctionName("getContext"),
        DataPrimitive.of(PrimitiveType.STRING, VariableName.response()),
        new LinkedHashSet<>(),
        Activity.empty(),
        thing.getAbstractionsScopes());
  }
}
