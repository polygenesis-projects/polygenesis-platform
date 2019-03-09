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

package io.polygenesis.core.dsl;

import io.polygenesis.annotations.core.GoalType;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Argument;
import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.ReturnValue;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.data.ObjectName;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.datatype.PrimitiveType;
import io.polygenesis.core.iomodel.DataBusinessType;
import io.polygenesis.core.iomodel.IoModel;
import io.polygenesis.core.iomodel.IoModelArray;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.core.iomodel.IoModelPrimitive;
import io.polygenesis.core.iomodel.VariableName;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Thing builder.
 *
 * @author Christos Tsakostas
 */
public class ThingBuilder {

  private final Thing thing;
  private final PackageName packageName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private ThingBuilder(String thingName, String rootPackageName, Boolean multiTenant) {
    this.thing = new Thing(new ThingName(thingName), multiTenant);
    this.packageName =
        new PackageName(String.format("%s.%s", rootPackageName, thingName.toLowerCase()));
  }

  // ===============================================================================================
  // START
  // ===============================================================================================

  /**
   * Create thing builder.
   *
   * @param thingName the thing name
   * @param rootPackageName the root package name
   * @return the thing builder
   */
  public static ThingBuilder create(String thingName, String rootPackageName) {
    return new ThingBuilder(thingName, rootPackageName, false);
  }

  /**
   * Create multi tenant thing builder.
   *
   * @param thingName the thing name
   * @param rootPackageName the root package name
   * @return the thing builder
   */
  public static ThingBuilder createMultiTenant(String thingName, String rootPackageName) {
    return new ThingBuilder(thingName, rootPackageName, true);
  }

  /**
   * Multi tenant thing builder.
   *
   * @return the thing builder
   */
  // ===============================================================================================
  // WITH
  // ===============================================================================================

  /**
   * With function create thing builder.
   *
   * @param models the models
   * @return the thing builder
   */
  public final ThingBuilder withFunctionCreate(Set<IoModel> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    IoModelGroup argumentIoModelGroup =
        new IoModelGroup(
            new ObjectName(
                String.format(
                    "Create%sRequest", TextConverter.toUpperCamel(thing.getName().getText()))),
            this.packageName);

    // ---------------------------------------------------------------------------------------------
    models.forEach(model -> argumentIoModelGroup.addIoModel(model));
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    IoModelGroup returnValueIoModelGroup =
        new IoModelGroup(
            new ObjectName(
                String.format(
                    "Create%sResponse", TextConverter.toUpperCamel(thing.getName().getText()))),
            this.packageName);

    // ---------------------------------------------------------------------------------------------

    returnValueIoModelGroup.addIoModelPrimitive(
        IoModelPrimitive.ofDataBusinessType(
            DataBusinessType.THING_IDENTITY,
            PrimitiveType.STRING,
            new VariableName(
                String.format("%sId", TextConverter.toLowerCamel(thing.getName().getText())))));

    // ---------------------------------------------------------------------------------------------
    // FUNCTION
    // ---------------------------------------------------------------------------------------------

    Function function =
        new Function(
            thing,
            new Goal(GoalType.CREATE),
            new FunctionName("create"),
            new LinkedHashSet<>(Arrays.asList(new Argument(argumentIoModelGroup))),
            new ReturnValue(returnValueIoModelGroup));

    this.thing.appendFunction(function);
    return this;
  }

  /**
   * With function modify thing builder.
   *
   * @param models the models
   * @return the thing builder
   */
  public final ThingBuilder withFunctionModify(Set<IoModel> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    IoModelGroup argumentIoModelGroup =
        new IoModelGroup(
            new ObjectName(
                String.format(
                    "Modify%sRequest", TextConverter.toUpperCamel(thing.getName().getText()))),
            this.packageName);

    // ---------------------------------------------------------------------------------------------
    argumentIoModelGroup.addIoModelPrimitive(
        IoModelPrimitive.ofDataBusinessType(
            DataBusinessType.THING_IDENTITY,
            PrimitiveType.STRING,
            new VariableName(
                String.format("%sId", TextConverter.toLowerCamel(thing.getName().getText())))));

    // ---------------------------------------------------------------------------------------------
    models.forEach(model -> argumentIoModelGroup.addIoModel(model));
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    IoModelGroup returnValueIoModelGroup =
        new IoModelGroup(
            new ObjectName(
                String.format(
                    "Modify%sResponse", TextConverter.toUpperCamel(thing.getName().getText()))),
            this.packageName);

    // ---------------------------------------------------------------------------------------------

    returnValueIoModelGroup.addIoModelPrimitive(
        IoModelPrimitive.ofDataBusinessType(
            DataBusinessType.THING_IDENTITY,
            PrimitiveType.STRING,
            new VariableName(
                String.format("%sId", TextConverter.toLowerCamel(thing.getName().getText())))));

    // ---------------------------------------------------------------------------------------------
    // FUNCTION
    // ---------------------------------------------------------------------------------------------

    Function function =
        new Function(
            thing,
            new Goal(GoalType.MODIFY),
            new FunctionName("modify"),
            new LinkedHashSet<>(Arrays.asList(new Argument(argumentIoModelGroup))),
            new ReturnValue(returnValueIoModelGroup));

    this.thing.appendFunction(function);
    return this;
  }

  /**
   * With function fetch one thing builder.
   *
   * @param models the models
   * @return the thing builder
   */
  public final ThingBuilder withFunctionFetchOne(Set<IoModel> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    IoModelGroup argumentIoModelGroup =
        new IoModelGroup(
            new ObjectName(
                String.format(
                    "Fetch%sRequest", TextConverter.toUpperCamel(thing.getName().getText()))),
            this.packageName);

    // ---------------------------------------------------------------------------------------------

    argumentIoModelGroup.addIoModelPrimitive(
        IoModelPrimitive.ofDataBusinessType(
            DataBusinessType.THING_IDENTITY,
            PrimitiveType.STRING,
            new VariableName(
                String.format("%sId", TextConverter.toLowerCamel(thing.getName().getText())))));

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    IoModelGroup returnValueIoModelGroup =
        new IoModelGroup(
            new ObjectName(
                String.format(
                    "Fetch%sResponse", TextConverter.toUpperCamel(thing.getName().getText()))),
            this.packageName);

    // ---------------------------------------------------------------------------------------------
    models.forEach(model -> returnValueIoModelGroup.addIoModel(model));
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FUNCTION
    // ---------------------------------------------------------------------------------------------

    Function function =
        new Function(
            thing,
            new Goal(GoalType.FETCH_ONE),
            new FunctionName("fetch"),
            new LinkedHashSet<>(Arrays.asList(new Argument(argumentIoModelGroup))),
            new ReturnValue(returnValueIoModelGroup));

    this.thing.appendFunction(function);
    return this;
  }

  /**
   * Fetch paged collection thing builder.
   *
   * @param models the models
   * @return the thing builder
   */
  public final ThingBuilder withFunctionFetchPagedCollection(Set<IoModel> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    IoModelGroup argumentIoModelGroup =
        new IoModelGroup(
            new ObjectName(
                String.format(
                    "Fetch%sCollectionRequest",
                    TextConverter.toUpperCamel(thing.getName().getText()))),
            this.packageName);

    // ---------------------------------------------------------------------------------------------

    argumentIoModelGroup.addIoModelPrimitive(
        IoModelPrimitive.ofDataBusinessType(
            DataBusinessType.PAGE_NUMBER,
            PrimitiveType.INTEGER,
            new VariableName("pageNumber")));

    argumentIoModelGroup.addIoModelPrimitive(
        IoModelPrimitive.ofDataBusinessType(
            DataBusinessType.PAGE_SIZE,
            PrimitiveType.INTEGER,
            new VariableName("pageSize")));

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    String arrayElementDataTypeAndVariableName =
        String.format("%sCollectionRecord", TextConverter.toUpperCamel(thing.getName().getText()));

    IoModelGroup arrayElement =
        new IoModelGroup(new ObjectName(arrayElementDataTypeAndVariableName), this.packageName);

    // ---------------------------------------------------------------------------------------------
    models.forEach(model -> arrayElement.addIoModel(model));
    // ---------------------------------------------------------------------------------------------

    String arrayDataTypeAndVariableName =
        String.format(
            "Fetch%sCollectionResponse", TextConverter.toUpperCamel(thing.getName().getText()));

    IoModelArray returnValueIoModelGroup =
        new IoModelArray(
            arrayElement,
            new VariableName(arrayDataTypeAndVariableName));

    // ---------------------------------------------------------------------------------------------
    // FUNCTION
    // ---------------------------------------------------------------------------------------------

    Function function =
        new Function(
            thing,
            new Goal(GoalType.FETCH_PAGED_COLLECTION),
            new FunctionName("fetchCollection"),
            new LinkedHashSet<>(Arrays.asList(new Argument(argumentIoModelGroup))),
            new ReturnValue(returnValueIoModelGroup));

    this.thing.appendFunction(function);
    return this;
  }

  // ===============================================================================================
  // END
  // ===============================================================================================

  /**
   * Get thing.
   *
   * @return the thing
   */
  public final Thing get() {
    return thing;
  }
}
