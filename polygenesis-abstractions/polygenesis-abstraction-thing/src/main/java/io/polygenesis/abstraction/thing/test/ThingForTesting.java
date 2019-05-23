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

package io.polygenesis.abstraction.thing.test;

import io.polygenesis.abstraction.data.DataGroup;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.data.VariableName;
import io.polygenesis.abstraction.thing.Argument;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.ReturnValue;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingBuilder;
import io.polygenesis.abstraction.thing.ThingName;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * Convenient class defining a Thing and its Functions for testing in other modules.
 *
 * @author Christos Tsakostas
 */
public class ThingForTesting {

  public static final String BUSINESS = "business";
  public static final String BUSINESS_ID = "businessId";
  public static final String NAME = "name";
  public static final String ROOT_PACKAGE = "com.oregor.trinity4j.example";

  private ThingForTesting() {
    super();
  }

  /**
   * Create thing.
   *
   * @return the thing
   */
  public static Thing create() {
    Thing business = ThingBuilder.endToEnd().setThingName(new ThingName(BUSINESS)).createThing();

    business.addFunction(functionCreate(business));
    business.addFunction(functionFetchOne(business));
    business.addFunction(functionFetchCollection(business));

    business.addFunction(functionWithNoReturnValueAndManyArguments(business));
    business.addFunction(functionWithNoArguments(business));
    business.addFunction(functionWithPrimitives(business));

    return business;
  }

  // ===============================================================================================
  // FUNCTION CREATE
  // ===============================================================================================

  private static Function functionCreate(Thing business) {
    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataGroup argumentDataGroup =
        new DataGroup(
            new ObjectName("CreateBusinessRequest"),
            new PackageName(ROOT_PACKAGE + "." + BUSINESS));

    // name
    argumentDataGroup.addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName(NAME)));

    // postal address
    argumentDataGroup.addData(postalAddress());

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    DataGroup returnValueDataGroup =
        new DataGroup(
            new ObjectName("CreateBusinessResponse"),
            new PackageName(ROOT_PACKAGE + "." + BUSINESS));

    returnValueDataGroup.addData(
        DataPrimitive.ofDataBusinessType(
            DataPurpose.thingIdentity(), PrimitiveType.STRING, new VariableName(BUSINESS_ID)));

    // ---------------------------------------------------------------------------------------------

    return new Function(
        business,
        Purpose.create(),
        new FunctionName("create"),
        new LinkedHashSet<>(Arrays.asList(new Argument(argumentDataGroup))),
        new ReturnValue(returnValueDataGroup));
  }

  // ===============================================================================================
  // FUNCTION FETCH ONE
  // ===============================================================================================

  private static Function functionFetchOne(Thing business) {
    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataGroup argumentDataGroup =
        new DataGroup(
            new ObjectName("FetchBusinessRequest"), new PackageName(ROOT_PACKAGE + "." + BUSINESS));

    // ---------------------------------------------------------------------------------------------

    argumentDataGroup.addData(
        DataPrimitive.ofDataBusinessType(
            DataPurpose.thingIdentity(), PrimitiveType.STRING, new VariableName(BUSINESS_ID)));

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    DataGroup returnValueDataGroup =
        new DataGroup(
            new ObjectName("FetchBusinessResponse"),
            new PackageName(ROOT_PACKAGE + "." + BUSINESS));

    // ---------------------------------------------------------------------------------------------

    returnValueDataGroup.addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName(NAME)));

    // ---------------------------------------------------------------------------------------------
    return new Function(
        business,
        Purpose.fetchOne(),
        new FunctionName("fetchOne"),
        new LinkedHashSet<>(Arrays.asList(new Argument(argumentDataGroup))),
        new ReturnValue(returnValueDataGroup));
  }

  // ===============================================================================================
  // FUNCTION FETCH COLLECTION
  // ===============================================================================================

  private static Function functionFetchCollection(Thing business) {
    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataGroup argumentDataGroup =
        new DataGroup(
            new ObjectName("FetchBusinessCollectionRequest"),
            new PackageName(ROOT_PACKAGE + "." + BUSINESS));

    // ---------------------------------------------------------------------------------------------

    argumentDataGroup.addData(
        DataPrimitive.ofDataBusinessType(
            DataPurpose.thingIdentity(), PrimitiveType.STRING, new VariableName(BUSINESS_ID)));

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    DataGroup returnValueDataGroup =
        new DataGroup(
            new ObjectName("FetchBusinessCollectionResponse"),
            new PackageName(ROOT_PACKAGE + "." + BUSINESS));

    // ---------------------------------------------------------------------------------------------

    returnValueDataGroup.addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName(NAME)));

    // ---------------------------------------------------------------------------------------------
    return new Function(
        business,
        Purpose.fetchCollection(),
        new FunctionName("fetchCollection"),
        new LinkedHashSet<>(Arrays.asList(new Argument(argumentDataGroup))),
        new ReturnValue(returnValueDataGroup));
  }

  // ===============================================================================================
  // FUNCTION WITH NO RETURN VALUE AND MANY ARGUMENTS
  // ===============================================================================================

  private static Function functionWithNoReturnValueAndManyArguments(Thing business) {
    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS - 1
    // ---------------------------------------------------------------------------------------------
    DataGroup argumentDataGroup1 =
        new DataGroup(
            new ObjectName("CreateBusinessRequest1"),
            new PackageName(ROOT_PACKAGE + "." + BUSINESS));

    // name
    argumentDataGroup1.addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName(NAME)));

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS - 2
    // ---------------------------------------------------------------------------------------------
    DataGroup argumentDataGroup2 =
        new DataGroup(
            new ObjectName("CreateBusinessRequest2"),
            new PackageName(ROOT_PACKAGE + "." + BUSINESS));

    // name
    argumentDataGroup2.addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName(NAME)));

    // ---------------------------------------------------------------------------------------------

    return new Function(
        business,
        Purpose.create(),
        new FunctionName("functionWithNoReturnValueAndManyArguments"),
        new LinkedHashSet<>(
            Arrays.asList(new Argument(argumentDataGroup1), new Argument(argumentDataGroup2))));
  }

  // ===============================================================================================
  // FUNCTION WITH NO ARGUMENTS
  // ===============================================================================================

  private static Function functionWithNoArguments(Thing business) {
    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    DataGroup returnValueDataGroup =
        new DataGroup(
            new ObjectName("FetchBusinessCollectionResponse"),
            new PackageName(ROOT_PACKAGE + "." + BUSINESS));

    // ---------------------------------------------------------------------------------------------

    returnValueDataGroup.addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName(NAME)));

    // ---------------------------------------------------------------------------------------------

    return new Function(
        business,
        Purpose.create(),
        new FunctionName("functionWithNoArguments"),
        new ReturnValue(returnValueDataGroup));
  }

  // ===============================================================================================
  // FUNCTION WITH PRIMITIVES
  // ===============================================================================================

  private static Function functionWithPrimitives(Thing business) {
    // ---------------------------------------------------------------------------------------------
    // PRIMITIVE
    // ---------------------------------------------------------------------------------------------
    DataPrimitive dataPrimitive = DataPrimitive.of(PrimitiveType.STRING, new VariableName(NAME));

    // ---------------------------------------------------------------------------------------------

    return new Function(
        business,
        Purpose.create(),
        new FunctionName("functionWithPrimitives"),
        new LinkedHashSet<>(Arrays.asList(new Argument(dataPrimitive))),
        new ReturnValue(dataPrimitive));
  }

  // ===============================================================================================
  // DTOs
  // ===============================================================================================

  // ===============================================================================================
  // POSTAL ADDRESS
  // ===============================================================================================

  private static DataGroup postalAddress() {
    DataGroup postalAddress =
        new DataGroup(
            new ObjectName("PostalAddressDto"),
            new PackageName("com.oregor.trinity4j.example.shared"));

    postalAddress.addData(
        DataPrimitive.of(PrimitiveType.STRING, new VariableName("streetAddress1")));

    postalAddress.addData(
        DataPrimitive.of(PrimitiveType.STRING, new VariableName("streetAddress2")));

    postalAddress.addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName("city")));

    return postalAddress;
  }
}
