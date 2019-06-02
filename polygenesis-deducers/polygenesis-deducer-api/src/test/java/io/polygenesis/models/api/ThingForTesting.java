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

package io.polygenesis.models.api;

import io.polygenesis.abstraction.data.DataGroup;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.FunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;

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
    Thing business = ThingBuilder.endToEnd().setThingName(BUSINESS).createThing();

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

    return FunctionBuilder.of(business, "create", Purpose.create())
        .setReturnValue(returnValueDataGroup)
        .addArgument(argumentDataGroup)
        .build();
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
    return FunctionBuilder.of(business, "fetchOne", Purpose.fetchOne())
        .setReturnValue(returnValueDataGroup)
        .addArgument(argumentDataGroup)
        .build();
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
    return FunctionBuilder.of(business, "fetchCollection", Purpose.fetchCollection())
        .setReturnValue(returnValueDataGroup)
        .addArgument(argumentDataGroup)
        .build();
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

    return FunctionBuilder.of(
            business, "functionWithNoReturnValueAndManyArguments", Purpose.create())
        .addArgument(argumentDataGroup1)
        .addArgument(argumentDataGroup2)
        .build();
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

    return FunctionBuilder.of(business, "functionWithNoArguments", Purpose.create())
        .setReturnValue(returnValueDataGroup)
        .build();
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

    return FunctionBuilder.of(business, "functionWithPrimitives", Purpose.create())
        .setReturnValue(dataPrimitive)
        .addArgument(dataPrimitive)
        .build();
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
