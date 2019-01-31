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

import io.polygenesis.annotations.core.GGoalStandardType;
import io.polygenesis.core.Argument;
import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.ReturnValue;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.datatype.ClassDataType;
import io.polygenesis.core.datatype.DataTypeName;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.datatype.PrimaryType;
import io.polygenesis.core.datatype.PrimitiveDataType;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.core.iomodel.IoModelPrimitive;
import io.polygenesis.core.iomodel.VariableName;
import java.util.Arrays;
import java.util.LinkedHashSet;

/** @author Christos Tsakostas */
public class JavaGenesisThingCustomer {

  public static Thing create() {
    Thing customer = new Thing(new ThingName("customer"));

    customer.appendFunction(functionCreate(customer));

    return customer;
  }

  private static Function functionCreate(Thing customer) {
    // ARGUMENTS
    IoModelGroup argumentIoModelGroup =
        new IoModelGroup(
            new ClassDataType(
                new DataTypeName("CreateCustomerRequest"),
                new PackageName("com.oregor.microservice.account.customer")));

    argumentIoModelGroup.addIoModelPrimitive(
        IoModelPrimitive.of(
            new PrimitiveDataType(new DataTypeName(PrimaryType.STRING.name())),
            new VariableName("name")));

    // RETURN
    IoModelGroup returnValueIoModelGroup =
        new IoModelGroup(
            new ClassDataType(
                new DataTypeName("CreateCustomerResponse"),
                new PackageName("com.oregor.microservice.account.customer")));

    returnValueIoModelGroup.addIoModelPrimitive(
        IoModelPrimitive.ofThingIdentity(
            new PrimitiveDataType(new DataTypeName(PrimaryType.STRING.name())),
            new VariableName("customerId")));

    return new Function(
        customer,
        new Goal(GGoalStandardType.CMD_CREATE),
        new FunctionName("create"),
        new LinkedHashSet<>(Arrays.asList(new Argument(argumentIoModelGroup))),
        new ReturnValue(returnValueIoModelGroup));
  }
}
