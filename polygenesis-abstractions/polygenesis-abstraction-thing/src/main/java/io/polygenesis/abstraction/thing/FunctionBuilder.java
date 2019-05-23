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

package io.polygenesis.abstraction.thing;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataArray;
import io.polygenesis.abstraction.data.DataGroup;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.data.VariableName;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Function builder.
 *
 * @author Christos Tsakostas
 */
public class FunctionBuilder {

  private final PackageName rootPackageNameVo;
  private final Thing thing;
  private final Set<Function> functions;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private FunctionBuilder(Thing thing, String rootPackageName) {
    this.rootPackageNameVo = new PackageName(rootPackageName);
    this.thing = thing;
    functions = new LinkedHashSet<>();
  }

  // ===============================================================================================
  // START
  // ===============================================================================================

  /**
   * For thing function builder.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the function builder
   */
  public static FunctionBuilder forThing(Thing thing, String rootPackageName) {
    return new FunctionBuilder(thing, rootPackageName);
  }

  // ===============================================================================================
  // WITH
  // ===============================================================================================

  /**
   * With crud function function builder.
   *
   * @param models the models
   * @return the function builder
   */
  public final FunctionBuilder withCrudFunction(Set<Data> models) {
    withFunctionCreate(models);
    withFunctionModify(models);
    withFunctionFetchOne(models);
    withFunctionFetchPagedCollection(models);
    return this;
  }

  public final FunctionBuilder withFunctionCreate(Set<Data> models) {
    return withFunctionCreate("create", models);
  }

  /**
   * With function create thing builder.
   *
   * @param models the models
   * @return the thing builder
   */
  @SuppressWarnings("CPD-START")
  public final FunctionBuilder withFunctionCreate(String functionName, Set<Data> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataGroup argumentDataGroup =
        new DataGroup(
            new ObjectName(
                String.format(
                    "%s%sRequest",
                    functionName, TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageNameVo, thing));

    // ---------------------------------------------------------------------------------------------
    // Add Parent Thing Identity if Any
    if (thing.getOptionalParent() != null) {
      argumentDataGroup.addData(makeParentThingIdentity(thing.getOptionalParent()));
    }

    // ---------------------------------------------------------------------------------------------
    // Add Thing Identity
    argumentDataGroup.addData(makeThingIdentity(thing));

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
                    "%s%sResponse",
                    functionName, TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageNameVo, thing));

    // ---------------------------------------------------------------------------------------------

    returnValueDataGroup.addData(
        DataPrimitive.ofDataBusinessType(
            DataPurpose.thingIdentity(),
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
            Purpose.create(),
            new FunctionName(functionName),
            new LinkedHashSet<>(Arrays.asList(new Argument(argumentDataGroup))),
            new ReturnValue(returnValueDataGroup));

    this.functions.add(function);
    return this;
  }

  public final FunctionBuilder withFunctionModify(Set<Data> models) {
    return withFunctionModify("modify", models);
  }

  /**
   * With function modify thing builder.
   *
   * @param models the models
   * @return the thing builder
   */
  @SuppressWarnings("CPD-END")
  public final FunctionBuilder withFunctionModify(String functionName, Set<Data> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataGroup argumentDataGroup =
        new DataGroup(
            new ObjectName(
                String.format(
                    "%s%sRequest",
                    functionName, TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageNameVo, thing));

    // ---------------------------------------------------------------------------------------------
    // Add Parent Thing Identity if Any
    if (thing.getOptionalParent() != null) {
      argumentDataGroup.addData(makeParentThingIdentity(thing.getOptionalParent()));
    }

    // ---------------------------------------------------------------------------------------------
    // Add Thing Identity
    argumentDataGroup.addData(makeThingIdentity(thing));

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
                    "%s%sResponse",
                    functionName, TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageNameVo, thing));

    // ---------------------------------------------------------------------------------------------

    returnValueDataGroup.addData(
        DataPrimitive.ofDataBusinessType(
            DataPurpose.thingIdentity(),
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
            Purpose.modify(),
            new FunctionName(functionName),
            new LinkedHashSet<>(Arrays.asList(new Argument(argumentDataGroup))),
            new ReturnValue(returnValueDataGroup));

    this.functions.add(function);
    return this;
  }

  /**
   * With function fetch one thing builder.
   *
   * @param models the models
   * @return the thing builder
   */
  public final FunctionBuilder withFunctionFetchOne(Set<Data> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataGroup argumentDataGroup =
        new DataGroup(
            new ObjectName(
                String.format(
                    "Fetch%sRequest", TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageNameVo, thing));

    // ---------------------------------------------------------------------------------------------
    // Add Parent Thing Identity if Any
    if (thing.getOptionalParent() != null) {
      argumentDataGroup.addData(makeParentThingIdentity(thing.getOptionalParent()));
    }

    // ---------------------------------------------------------------------------------------------
    // Add Thing Identity
    argumentDataGroup.addData(makeThingIdentity(thing));

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    DataGroup returnValueDataGroup =
        new DataGroup(
            new ObjectName(
                String.format(
                    "Fetch%sResponse", TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageNameVo, thing));

    // ---------------------------------------------------------------------------------------------
    models.forEach(returnValueDataGroup::addData);
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FUNCTION
    // ---------------------------------------------------------------------------------------------

    Function function =
        new Function(
            thing,
            Purpose.fetchOne(),
            new FunctionName("fetch"),
            new LinkedHashSet<>(Arrays.asList(new Argument(argumentDataGroup))),
            new ReturnValue(returnValueDataGroup));

    this.functions.add(function);
    return this;
  }

  /**
   * Fetch paged collection thing builder.
   *
   * @param models the models
   * @return the thing builder
   */
  public final FunctionBuilder withFunctionFetchPagedCollection(Set<Data> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataGroup argumentDataGroup =
        new DataGroup(
            new ObjectName(
                String.format(
                    "Fetch%sCollectionRequest",
                    TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageNameVo, thing));

    // ---------------------------------------------------------------------------------------------
    // Add Parent Thing Identity if Any
    if (thing.getOptionalParent() != null) {
      argumentDataGroup.addData(makeParentThingIdentity(thing.getOptionalParent()));
    }

    // ---------------------------------------------------------------------------------------------
    argumentDataGroup.addData(
        DataPrimitive.ofDataBusinessType(
            DataPurpose.pageNumber(), PrimitiveType.INTEGER, new VariableName("pageNumber")));

    argumentDataGroup.addData(
        DataPrimitive.ofDataBusinessType(
            DataPurpose.pageSize(), PrimitiveType.INTEGER, new VariableName("pageSize")));

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    String arrayElementDataTypeAndVariableName =
        String.format(
            "%sCollectionRecord", TextConverter.toUpperCamel(thing.getThingName().getText()));

    DataGroup arrayElement =
        new DataGroup(
            new ObjectName(arrayElementDataTypeAndVariableName),
            thing.makePackageName(rootPackageNameVo, thing));

    // ---------------------------------------------------------------------------------------------
    arrayElement.addData(
        DataPrimitive.ofDataBusinessType(
            DataPurpose.thingIdentity(), PrimitiveType.STRING, new VariableName("rootId")));

    models.forEach(arrayElement::addData);
    // ---------------------------------------------------------------------------------------------

    String arrayDataTypeAndVariableName =
        String.format(
            "Fetch%sCollectionResponse",
            TextConverter.toUpperCamel(thing.getThingName().getText()));

    DataArray dataArray = new DataArray(new VariableName("someArray"), arrayElement);

    DataGroup dataGroupReturnValue =
        new DataGroup(
            new ObjectName(arrayDataTypeAndVariableName),
            thing.makePackageName(rootPackageNameVo, thing));

    dataGroupReturnValue.addData(dataArray);

    // ---------------------------------------------------------------------------------------------
    // FUNCTION
    // ---------------------------------------------------------------------------------------------

    Function function =
        new Function(
            thing,
            Purpose.fetchPagedCollection(),
            new FunctionName("fetchCollection"),
            new LinkedHashSet<>(Arrays.asList(new Argument(argumentDataGroup))),
            new ReturnValue(dataGroupReturnValue));

    this.functions.add(function);
    return this;
  }

  // ===============================================================================================
  // END
  // ===============================================================================================

  /**
   * Build set.
   *
   * @return the set
   */
  public final Set<Function> build() {
    return functions;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make thing identity data primitive.
   *
   * @param thing the thing
   * @return the data primitive
   */
  private DataPrimitive makeThingIdentity(Thing thing) {
    return DataPrimitive.ofDataBusinessType(
        DataPurpose.thingIdentity(),
        PrimitiveType.STRING,
        new VariableName(
            String.format("%sId", TextConverter.toLowerCamel(thing.getThingName().getText()))));
  }

  /**
   * Make parent thing identity data primitive.
   *
   * @param parentThing the parent thing
   * @return the data primitive
   */
  private DataPrimitive makeParentThingIdentity(Thing parentThing) {
    return DataPrimitive.ofDataBusinessType(
        DataPurpose.parentThingIdentity(),
        PrimitiveType.STRING,
        new VariableName(
            String.format(
                "%sId", TextConverter.toLowerCamel(parentThing.getThingName().getText()))));
  }
}
