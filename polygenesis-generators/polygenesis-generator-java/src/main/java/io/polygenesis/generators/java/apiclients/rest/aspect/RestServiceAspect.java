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

package io.polygenesis.generators.java.apiclients.rest.aspect;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataRepository;
import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.Generatable;
import io.polygenesis.core.Nameable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Rest service aspect.
 *
 * @author Christos Tsakostas
 */
public class RestServiceAspect implements Generatable, Nameable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private ContextName contextName;
  private PackageName rootPackageName;
  private Name name;
  private Function around;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Rest service aspect.
   *
   * @param contextName the context name
   * @param rootPackageName the root package name
   */
  public RestServiceAspect(ContextName contextName, PackageName rootPackageName) {
    this.contextName = contextName;
    this.rootPackageName = rootPackageName;
    this.name = new Name(String.format("%sRestServiceAspect", contextName.getText()));
    this.around = makeAround();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets context name.
   *
   * @return the context name
   */
  @SuppressWarnings("CPD-START")
  public ContextName getContextName() {
    return contextName;
  }

  /**
   * Gets root package name.
   *
   * @return the root package name
   */
  public PackageName getRootPackageName() {
    return rootPackageName;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public Name getName() {
    return name;
  }

  /**
   * Gets around.
   *
   * @return the around
   */
  public Function getAround() {
    return around;
  }

  @Override
  public ObjectName getObjectName() {
    return new ObjectName(getName().getText());
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Function makeAround() {
    Set<KeyValue> keyValues = new LinkedHashSet<>();

    Thing thing = ThingBuilder.apiClientRest("restServiceAspect").createThing();

    return new Function(
        thing,
        Purpose.aspectAround(),
        new FunctionName("around"),
        new DataObject(
            new ObjectName("Object"), new PackageName("java.lang"), VariableName.response()),
        new DataRepository(
            new DataObject(
                new ObjectName("ProceedingJoinPoint"),
                new PackageName("org.aspectj.lang"),
                new VariableName("proceedingJoinPoint"))),
        Activity.keyValues(keyValues),
        thing.getAbstractionsScopes());
  }
}
