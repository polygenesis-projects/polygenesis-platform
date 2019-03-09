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

package io.polygenesis.codegen;

import io.polygenesis.annotations.core.GoalType;
import io.polygenesis.core.Argument;
import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.ReturnValue;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.data.DataBusinessType;
import io.polygenesis.core.data.IoModelGroup;
import io.polygenesis.core.data.IoModelPrimitive;
import io.polygenesis.core.data.ObjectName;
import io.polygenesis.core.data.PackageName;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.core.data.VariableName;
import java.util.Arrays;
import java.util.LinkedHashSet;

/** @author Christos Tsakostas */
public class JavaGenesisThingBusiness {

  public static Thing create() {
    Thing business = new Thing(new ThingName("business"));

    business.appendFunction(functionCreate(business));
    business.appendFunction(functionFetchDetail(business));

    return business;
  }

  // ===============================================================================================
  // CREATE
  // ===============================================================================================

  private static Function functionCreate(Thing business) {
    // ARGUMENTS
    IoModelGroup argumentIoModelGroup =
        new IoModelGroup(
            new ObjectName("CreateBusinessRequest"),
            new PackageName("com.oregor.microservice.account.business"));

    // ARGUMENT - NAME
    argumentIoModelGroup.addIoModelPrimitive(
        IoModelPrimitive.of(PrimitiveType.STRING, new VariableName("name")));

    // ARGUMENT - POSTAL ADDRESS
    argumentIoModelGroup.addIoModelGroup(postalAddress());

    // RETURN VALUE
    IoModelGroup returnValueIoModelGroup =
        new IoModelGroup(
            new ObjectName("CreateBusinessResponse"),
            new PackageName("com.oregor.microservice.account.business"));

    // RETURN VALUE - BUSINESSID
    returnValueIoModelGroup.addIoModelPrimitive(
        IoModelPrimitive.ofDataBusinessType(
            DataBusinessType.THING_IDENTITY, PrimitiveType.STRING, new VariableName("businessId")));

    return new Function(
        business,
        new Goal(GoalType.CREATE),
        new FunctionName("create"),
        new LinkedHashSet<>(Arrays.asList(new Argument(argumentIoModelGroup))),
        new ReturnValue(returnValueIoModelGroup));
  }

  // ===============================================================================================
  // FETCH DETAIL
  // ===============================================================================================

  private static Function functionFetchDetail(Thing business) {
    // ARGUMENTS
    IoModelGroup argumentIoModelGroup =
        new IoModelGroup(
            new ObjectName("FetchBusinessRequest"),
            new PackageName("com.oregor.microservice.account.business"));

    // ARGUMENT - BusinessID
    argumentIoModelGroup.addIoModelPrimitive(
        IoModelPrimitive.ofDataBusinessType(
            DataBusinessType.THING_IDENTITY, PrimitiveType.STRING, new VariableName("businessId")));

    // RETURN VALUE
    IoModelGroup returnValueIoModelGroup =
        new IoModelGroup(
            new ObjectName("FetchBusinessResponse"),
            new PackageName("com.oregor.microservice.account.business"));

    return new Function(
        business,
        new Goal(GoalType.FETCH_ONE),
        new FunctionName("fetch"),
        new LinkedHashSet<>(Arrays.asList(new Argument(argumentIoModelGroup))),
        new ReturnValue(returnValueIoModelGroup));
  }

  // ===============================================================================================
  // SHARED
  // ===============================================================================================

  // POSTAL ADDRESS
  private static IoModelGroup postalAddress() {
    IoModelGroup postalAddress =
        new IoModelGroup(
            new ObjectName("PostalAddressDto"),
            new PackageName("com.oregor.microservice.account.shared"));

    postalAddress.addIoModelPrimitive(
        IoModelPrimitive.of(PrimitiveType.STRING, new VariableName("streetAddress1")));

    postalAddress.addIoModelPrimitive(
        IoModelPrimitive.of(PrimitiveType.STRING, new VariableName("streetAddress2")));

    postalAddress.addIoModelPrimitive(
        IoModelPrimitive.of(PrimitiveType.STRING, new VariableName("city")));

    return postalAddress;
  }
}
