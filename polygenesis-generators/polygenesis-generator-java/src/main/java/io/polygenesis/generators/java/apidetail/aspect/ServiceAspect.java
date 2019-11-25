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

package io.polygenesis.generators.java.apidetail.aspect;

import io.polygenesis.abstraction.data.DataArray;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Activity;
import io.polygenesis.abstraction.thing.Argument;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.ReturnValue;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.Nameable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Service aspect.
 *
 * @author Christos Tsakostas
 */
public class ServiceAspect implements Nameable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private ContextName contextName;
  private PackageName rootPackageName;
  private Name name;
  private Function around;
  private Function getReturnValue;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service aspect.
   *
   * @param contextName the context name
   * @param rootPackageName the root package name
   */
  public ServiceAspect(ContextName contextName, PackageName rootPackageName) {
    this.contextName = contextName;
    this.rootPackageName = rootPackageName;
    this.name = new Name(String.format("%sServiceAspect", contextName.getText()));
    this.around = makeAround();
    this.getReturnValue = makeGetReturnValue();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets context name.
   *
   * @return the context name
   */
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

  /**
   * Gets get return value.
   *
   * @return the get return value
   */
  public Function getGetReturnValue() {
    return getReturnValue;
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

    Thing thing = ThingBuilder.apiDetailServiceAspect().setThingName("serviceAspect").createThing();

    return new Function(
        thing,
        Purpose.apiDetailServiceAspectAround(),
        new FunctionName("around"),
        new ReturnValue(
            new DataObject(
                new ObjectName("Object"), new PackageName("java.lang"), VariableName.response())),
        new LinkedHashSet<>(
            Arrays.asList(
                new Argument(
                    new DataObject(
                        new ObjectName("ProceedingJoinPoint"),
                        new PackageName("org.aspectj.lang"),
                        new VariableName("proceedingJoinPoint"))))),
        Activity.keyValues(keyValues),
        thing.getAbstractionsScopes());
  }

  private Function makeGetReturnValue() {
    Thing thing = ThingBuilder.apiDetailServiceAspect().setThingName("serviceAspect").createThing();

    Set<KeyValue> keyValues = new LinkedHashSet<>();

    return new Function(
        thing,
        Purpose.apiDetailServiceAspectGetReturnValue(),
        new FunctionName("getReturnValue"),
        new ReturnValue(
            new DataObject(
                new ObjectName("Object"), new PackageName("java.lang"), VariableName.response())),
        new LinkedHashSet<>(
            Arrays.asList(
                new Argument(
                    new DataObject(
                        new ObjectName("Class<?>"),
                        new PackageName("java.lang"),
                        new VariableName("returnType"))),
                new Argument(
                    new DataObject(
                        new ObjectName("ApiRequest"),
                        new PackageName("com.oregor.trinity4j.api"),
                        new VariableName("apiRequest"))),
                new Argument(DataPrimitive.of(PrimitiveType.STRING, new VariableName("errorCode"))),
                new Argument(
                    new DataArray(
                        new VariableName("argument"),
                        new DataObject(
                            new ObjectName("Object"),
                            new PackageName("java.lang"),
                            new VariableName("object")))))),
        Activity.keyValues(keyValues),
        thing.getAbstractionsScopes());
  }
}
