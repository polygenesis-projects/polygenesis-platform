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

package io.polygenesis.core.dsl;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Argument;
import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.GoalType;
import io.polygenesis.core.ReturnValue;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingBuilder;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataArray;
import io.polygenesis.core.data.DataBusinessType;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.core.data.DataPrimitive;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.core.data.VariableName;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Thing builder.
 *
 * @author Christos Tsakostas
 */
public class ExperimentalThingBuilder {

  private final Thing thing;
  private final PackageName packageName;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private ExperimentalThingBuilder(
      String thingName, String parentThingName, String rootPackageName, Boolean multiTenant) {
    this.thing =
        ThingBuilder.generic()
            .setThingName(new ThingName(thingName))
            .setMultiTenant(multiTenant)
            .createThing();

    this.packageName =
        new PackageName(
            String.format(
                "%s.%s",
                rootPackageName,
                parentThingName != null ? parentThingName.toLowerCase() : thingName.toLowerCase()));
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
  public static ExperimentalThingBuilder create(String thingName, String rootPackageName) {
    return new ExperimentalThingBuilder(thingName, null, rootPackageName, false);
  }

  /**
   * Create with parant experimental thing builder.
   *
   * @param thingName the thing name
   * @param parentThingName the parent thing name
   * @param rootPackageName the root package name
   * @return the experimental thing builder
   */
  public static ExperimentalThingBuilder createWithParant(
      String thingName, String parentThingName, String rootPackageName) {
    return new ExperimentalThingBuilder(thingName, parentThingName, rootPackageName, false);
  }

  /**
   * Create multi tenant thing builder.
   *
   * @param thingName the thing name
   * @param rootPackageName the root package name
   * @return the thing builder
   */
  public static ExperimentalThingBuilder createMultiTenant(
      String thingName, String rootPackageName) {
    return new ExperimentalThingBuilder(thingName, null, rootPackageName, true);
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
  public final ExperimentalThingBuilder withFunctionCreate(Set<Data> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataGroup argumentDataGroup =
        new DataGroup(
            new ObjectName(
                String.format(
                    "Create%sRequest", TextConverter.toUpperCamel(thing.getThingName().getText()))),
            this.packageName);

    // ---------------------------------------------------------------------------------------------
    models.forEach(argumentDataGroup::addData);
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    DataGroup returnValueDataGroup =
        new DataGroup(
            new ObjectName(
                String.format(
                    "Create%sResponse",
                    TextConverter.toUpperCamel(thing.getThingName().getText()))),
            this.packageName);

    // ---------------------------------------------------------------------------------------------

    returnValueDataGroup.addData(
        DataPrimitive.ofDataBusinessType(
            DataBusinessType.THING_IDENTITY,
            PrimitiveType.STRING,
            new VariableName(
                String.format(
                    "%sId", TextConverter.toLowerCamel(thing.getThingName().getText())))));

    // ---------------------------------------------------------------------------------------------
    // FUNCTION
    // ---------------------------------------------------------------------------------------------

    Function function =
        new Function(
            thing,
            new Goal(GoalType.CREATE),
            new FunctionName("create"),
            new LinkedHashSet<>(Arrays.asList(new Argument(argumentDataGroup))),
            new ReturnValue(returnValueDataGroup));

    this.thing.addFunction(function);
    return this;
  }

  /**
   * With function modify thing builder.
   *
   * @param models the models
   * @return the thing builder
   */
  public final ExperimentalThingBuilder withFunctionModify(Set<Data> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataGroup argumentDataGroup =
        new DataGroup(
            new ObjectName(
                String.format(
                    "Modify%sRequest", TextConverter.toUpperCamel(thing.getThingName().getText()))),
            this.packageName);

    // ---------------------------------------------------------------------------------------------
    argumentDataGroup.addData(
        DataPrimitive.ofDataBusinessType(
            DataBusinessType.THING_IDENTITY,
            PrimitiveType.STRING,
            new VariableName(
                String.format(
                    "%sId", TextConverter.toLowerCamel(thing.getThingName().getText())))));

    // ---------------------------------------------------------------------------------------------
    models.forEach(argumentDataGroup::addData);
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    DataGroup returnValueDataGroup =
        new DataGroup(
            new ObjectName(
                String.format(
                    "Modify%sResponse",
                    TextConverter.toUpperCamel(thing.getThingName().getText()))),
            this.packageName);

    // ---------------------------------------------------------------------------------------------

    returnValueDataGroup.addData(
        DataPrimitive.ofDataBusinessType(
            DataBusinessType.THING_IDENTITY,
            PrimitiveType.STRING,
            new VariableName(
                String.format(
                    "%sId", TextConverter.toLowerCamel(thing.getThingName().getText())))));

    // ---------------------------------------------------------------------------------------------
    // FUNCTION
    // ---------------------------------------------------------------------------------------------

    Function function =
        new Function(
            thing,
            new Goal(GoalType.MODIFY),
            new FunctionName("modify"),
            new LinkedHashSet<>(Arrays.asList(new Argument(argumentDataGroup))),
            new ReturnValue(returnValueDataGroup));

    this.thing.addFunction(function);
    return this;
  }

  /**
   * With function fetch one thing builder.
   *
   * @param models the models
   * @return the thing builder
   */
  public final ExperimentalThingBuilder withFunctionFetchOne(Set<Data> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataGroup argumentDataGroup =
        new DataGroup(
            new ObjectName(
                String.format(
                    "Fetch%sRequest", TextConverter.toUpperCamel(thing.getThingName().getText()))),
            this.packageName);

    // ---------------------------------------------------------------------------------------------

    argumentDataGroup.addData(
        DataPrimitive.ofDataBusinessType(
            DataBusinessType.THING_IDENTITY,
            PrimitiveType.STRING,
            new VariableName(
                String.format(
                    "%sId", TextConverter.toLowerCamel(thing.getThingName().getText())))));

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    DataGroup returnValueDataGroup =
        new DataGroup(
            new ObjectName(
                String.format(
                    "Fetch%sResponse", TextConverter.toUpperCamel(thing.getThingName().getText()))),
            this.packageName);

    // ---------------------------------------------------------------------------------------------
    models.forEach(returnValueDataGroup::addData);
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FUNCTION
    // ---------------------------------------------------------------------------------------------

    Function function =
        new Function(
            thing,
            new Goal(GoalType.FETCH_ONE),
            new FunctionName("fetch"),
            new LinkedHashSet<>(Arrays.asList(new Argument(argumentDataGroup))),
            new ReturnValue(returnValueDataGroup));

    this.thing.addFunction(function);
    return this;
  }

  /**
   * Fetch paged collection thing builder.
   *
   * @param models the models
   * @return the thing builder
   */
  public final ExperimentalThingBuilder withFunctionFetchPagedCollection(Set<Data> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataGroup argumentDataGroup =
        new DataGroup(
            new ObjectName(
                String.format(
                    "Fetch%sCollectionRequest",
                    TextConverter.toUpperCamel(thing.getThingName().getText()))),
            this.packageName);

    // ---------------------------------------------------------------------------------------------

    argumentDataGroup.addData(
        DataPrimitive.ofDataBusinessType(
            DataBusinessType.PAGE_NUMBER, PrimitiveType.INTEGER, new VariableName("pageNumber")));

    argumentDataGroup.addData(
        DataPrimitive.ofDataBusinessType(
            DataBusinessType.PAGE_SIZE, PrimitiveType.INTEGER, new VariableName("pageSize")));

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    String arrayElementDataTypeAndVariableName =
        String.format(
            "%sCollectionRecord", TextConverter.toUpperCamel(thing.getThingName().getText()));

    DataGroup arrayElement =
        new DataGroup(new ObjectName(arrayElementDataTypeAndVariableName), this.packageName);

    // ---------------------------------------------------------------------------------------------
    models.forEach(arrayElement::addData);
    // ---------------------------------------------------------------------------------------------

    String arrayDataTypeAndVariableName =
        String.format(
            "Fetch%sCollectionResponse",
            TextConverter.toUpperCamel(thing.getThingName().getText()));

    DataArray dataArray = new DataArray(new VariableName("someArray"), arrayElement);

    DataGroup dataGroupReturnValue =
        new DataGroup(new ObjectName(arrayDataTypeAndVariableName), this.packageName);

    dataGroupReturnValue.addData(dataArray);

    // ---------------------------------------------------------------------------------------------
    // FUNCTION
    // ---------------------------------------------------------------------------------------------

    Function function =
        new Function(
            thing,
            new Goal(GoalType.FETCH_PAGED_COLLECTION),
            new FunctionName("fetchCollection"),
            new LinkedHashSet<>(Arrays.asList(new Argument(argumentDataGroup))),
            new ReturnValue(dataGroupReturnValue));

    this.thing.addFunction(function);
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
