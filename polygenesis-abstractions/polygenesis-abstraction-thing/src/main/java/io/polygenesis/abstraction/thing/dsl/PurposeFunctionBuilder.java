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

package io.polygenesis.abstraction.thing.dsl;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataArray;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingProperty;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.AbstractionScope;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Purpose function builder.
 *
 * @author Christos Tsakostas
 */
public class PurposeFunctionBuilder {

  private final PackageName rootPackageNameVo;
  private final Thing thing;
  private final Set<Function> functions;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private PurposeFunctionBuilder(Thing thing, String rootPackageName) {
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
  public static PurposeFunctionBuilder forThing(Thing thing, String rootPackageName) {
    return new PurposeFunctionBuilder(thing, rootPackageName);
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
  public final PurposeFunctionBuilder withCrudFunction(Set<Data> models) {
    withFunctionCreate(models);
    withFunctionModify("modify", models);
    withFunctionFetchOne(models);
    withFunctionFetchPagedCollection(models);
    return this;
  }

  /**
   * With function create no return value purpose function builder.
   *
   * @param functionName the function name
   * @param models the models
   * @return the purpose function builder
   */
  public final PurposeFunctionBuilder withFunctionCreateNoReturnValue(
      String functionName, Set<Data> models) {
    return withFunctionCreate(functionName, models, false);
  }

  /**
   * With function create purpose function builder.
   *
   * @param models the models
   * @return the purpose function builder
   */
  public final PurposeFunctionBuilder withFunctionCreate(Set<Data> models) {
    return withFunctionCreate("create", models, true);
  }

  /**
   * With function create purpose function builder.
   *
   * @param functionName the function name
   * @param models the models
   * @return the purpose function builder
   */
  public final PurposeFunctionBuilder withFunctionCreate(String functionName, Set<Data> models) {
    return withFunctionCreate(functionName, models, true);
  }

  /**
   * With function create thing builder.
   *
   * @param functionName the function name
   * @param models the models
   * @return the thing builder
   */
  @SuppressWarnings("CPD-START")
  private PurposeFunctionBuilder withFunctionCreate(
      String functionName, Set<Data> models, Boolean returnValue) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    DataObject argumentDataObject =
        new DataObject(
            new ObjectName(
                String.format(
                    "%s%sRequest",
                    functionName, TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageNameVo, thing));

    // ---------------------------------------------------------------------------------------------
    // Add Parent Thing Identity if Any
    if (thing.getOptionalParent() != null) {
      argumentDataObject.addData(makeParentThingIdentity(thing.getOptionalParent()));
    }

    // ---------------------------------------------------------------------------------------------
    // Add Thing Identity
    if (!thingHasIdentity(thing)) {
      argumentDataObject.addData(makeThingIdentity(thing));
    }

    // ---------------------------------------------------------------------------------------------
    models.forEach(argumentDataObject::addData);
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    if (returnValue) {
      DataObject returnValueDataObject =
          new DataObject(
              new ObjectName(
                  String.format(
                      "%s%sResponse",
                      functionName, TextConverter.toUpperCamel(thing.getThingName().getText()))),
              thing.makePackageName(rootPackageNameVo, thing));

      returnValueDataObject.addData(
          DataPrimitive.ofDataBusinessType(
              DataPurpose.thingIdentity(),
              PrimitiveType.STRING,
              new VariableName(
                  String.format(
                      "%sId", TextConverter.toLowerCamel(thing.getThingName().getText())))));

      // -------------------------------------------------------------------------------------------
      // FUNCTION
      Function function =
          FunctionBuilder.of(thing, functionName, Purpose.create())
              .setReturnValue(returnValueDataObject)
              .addArgument(argumentDataObject)
              .build();

      this.functions.add(function);
    } else {
      Function function =
          FunctionBuilder.of(thing, functionName, Purpose.create())
              .addArgument(argumentDataObject)
              .build();

      this.functions.add(function);
    }
    return this;
  }

  /**
   * With function ensure existence purpose function builder.
   *
   * @return the purpose function builder
   */
  public PurposeFunctionBuilder withFunctionEnsureExistence() {

    String functionName = "ensureExistence";

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    DataObject argumentDataObject =
        new DataObject(
            new ObjectName(
                String.format(
                    "%s%sRequest",
                    functionName, TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageNameVo, thing));

    // ---------------------------------------------------------------------------------------------
    // Add Parent Thing Identity if Any
    if (thing.getOptionalParent() != null) {
      argumentDataObject.addData(makeParentThingIdentity(thing.getOptionalParent()));
    }

    // ---------------------------------------------------------------------------------------------
    // Add Thing Identity
    if (!thingHasIdentity(thing)) {
      argumentDataObject.addData(makeThingIdentity(thing));
    }

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    Function function =
        FunctionBuilder.of(thing, functionName, Purpose.ensureExistence())
            .addArgument(argumentDataObject)
            .build();

    this.functions.add(function);

    return this;
  }

  /**
   * With function modify no return value purpose function builder.
   *
   * @param functionName the function name
   * @param models the models
   * @return the purpose function builder
   */
  public final PurposeFunctionBuilder withFunctionModifyNoReturnValue(
      String functionName, Set<Data> models) {
    return withFunctionModify(functionName, models, false, thing.getAbstractionsScopes());
  }

  /**
   * With function modify no return value purpose function builder.
   *
   * @param functionName the function name
   * @param models the models
   * @param abstractionScopes the abstraction scopes
   * @return the purpose function builder
   */
  public final PurposeFunctionBuilder withFunctionModifyNoReturnValue(
      String functionName, Set<Data> models, Set<AbstractionScope> abstractionScopes) {
    return withFunctionModify(functionName, models, false, abstractionScopes);
  }

  /**
   * With function modify purpose function builder.
   *
   * @param functionName the function name
   * @param models the models
   * @return the purpose function builder
   */
  public final PurposeFunctionBuilder withFunctionModify(String functionName, Set<Data> models) {
    return withFunctionModify(functionName, models, true, thing.getAbstractionsScopes());
  }

  /**
   * With function modify thing builder.
   *
   * @param functionName the function name
   * @param models the models
   * @return the thing builder
   */
  private PurposeFunctionBuilder withFunctionModify(
      String functionName,
      Set<Data> models,
      Boolean returnValue,
      Set<AbstractionScope> abstractionScopes) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataObject argumentDataObject =
        new DataObject(
            new ObjectName(
                String.format(
                    "%s%sRequest",
                    functionName, TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageNameVo, thing));

    // ---------------------------------------------------------------------------------------------
    // Add Parent Thing Identity if Any
    if (thing.getOptionalParent() != null) {
      argumentDataObject.addData(makeParentThingIdentity(thing.getOptionalParent()));
    }

    // ---------------------------------------------------------------------------------------------
    // Add Thing Identity
    if (!thingHasIdentity(thing)) {
      argumentDataObject.addData(makeThingIdentity(thing));
    }

    // ---------------------------------------------------------------------------------------------
    models.forEach(argumentDataObject::addData);
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    if (returnValue) {
      DataObject returnValueDataObject =
          new DataObject(
              new ObjectName(
                  String.format(
                      "%s%sResponse",
                      functionName, TextConverter.toUpperCamel(thing.getThingName().getText()))),
              thing.makePackageName(rootPackageNameVo, thing));

      // -------------------------------------------------------------------------------------------

      returnValueDataObject.addData(
          DataPrimitive.ofDataBusinessType(
              DataPurpose.thingIdentity(),
              PrimitiveType.STRING,
              new VariableName(
                  String.format(
                      "%sId", TextConverter.toLowerCamel(thing.getThingName().getText())))));

      // -------------------------------------------------------------------------------------------
      // FUNCTION
      // -------------------------------------------------------------------------------------------

      Function function =
          FunctionBuilder.of(thing, functionName, Purpose.modify())
              .setReturnValue(returnValueDataObject)
              .addArgument(argumentDataObject)
              .setAbstractionScopes(abstractionScopes)
              .build();

      this.functions.add(function);
    } else {
      // -------------------------------------------------------------------------------------------
      // FUNCTION
      // -------------------------------------------------------------------------------------------

      Function function =
          FunctionBuilder.of(thing, functionName, Purpose.modify())
              .addArgument(argumentDataObject)
              .setAbstractionScopes(abstractionScopes)
              .build();

      this.functions.add(function);
    }

    return this;
  }

  /**
   * With function batch process purpose function builder.
   *
   * @return the purpose function builder
   */
  public PurposeFunctionBuilder withFunctionBatchProcess() {
    return withFunctionModifyNoReturnValue("batchProcess", new LinkedHashSet<>());
  }

  /**
   * With function fetch one thing builder.
   *
   * @param models the models
   * @return the thing builder
   */
  @SuppressWarnings("CPD-END")
  public final PurposeFunctionBuilder withFunctionFetchOne(Set<Data> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataObject argumentDataObject =
        new DataObject(
            new ObjectName(
                String.format(
                    "Fetch%sRequest", TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageNameVo, thing));

    // ---------------------------------------------------------------------------------------------
    // Add Parent Thing Identity if Any
    if (thing.getOptionalParent() != null) {
      argumentDataObject.addData(makeParentThingIdentity(thing.getOptionalParent()));
    }

    // ---------------------------------------------------------------------------------------------
    // Add Thing Identity
    if (!thingHasIdentity(thing)) {
      argumentDataObject.addData(makeThingIdentity(thing));
    }

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    DataObject returnValueDataObject =
        new DataObject(
            new ObjectName(
                String.format(
                    "Fetch%sResponse", TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageNameVo, thing));

    // ---------------------------------------------------------------------------------------------
    models.forEach(returnValueDataObject::addData);
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FUNCTION
    // ---------------------------------------------------------------------------------------------

    Function function =
        FunctionBuilder.of(thing, "fetch", Purpose.fetchOne())
            .setReturnValue(returnValueDataObject)
            .addArgument(argumentDataObject)
            .build();

    this.functions.add(function);
    return this;
  }

  /**
   * Fetch paged collection thing builder.
   *
   * @param models the models
   * @return the thing builder
   */
  public final PurposeFunctionBuilder withFunctionFetchPagedCollection(Set<Data> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataObject argumentDataObject =
        new DataObject(
            new ObjectName(
                String.format(
                    "Fetch%sCollectionRequest",
                    TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageNameVo, thing));

    // ---------------------------------------------------------------------------------------------
    // Add Parent Thing Identity if Any
    if (thing.getOptionalParent() != null) {
      argumentDataObject.addData(makeParentThingIdentity(thing.getOptionalParent()));
    }

    // ---------------------------------------------------------------------------------------------
    argumentDataObject.addData(
        DataPrimitive.ofDataBusinessType(
            DataPurpose.pageNumber(), PrimitiveType.INTEGER, new VariableName("pageNumber")));

    argumentDataObject.addData(
        DataPrimitive.ofDataBusinessType(
            DataPurpose.pageSize(), PrimitiveType.INTEGER, new VariableName("pageSize")));

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    String arrayElementDataTypeAndVariableName =
        String.format(
            "%sCollectionRecord", TextConverter.toUpperCamel(thing.getThingName().getText()));

    DataObject arrayElement =
        new DataObject(
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

    DataObject dataObjectReturnValue =
        new DataObject(
            new ObjectName(arrayDataTypeAndVariableName),
            thing.makePackageName(rootPackageNameVo, thing));

    dataObjectReturnValue.addData(dataArray);

    // ---------------------------------------------------------------------------------------------
    // FUNCTION
    // ---------------------------------------------------------------------------------------------

    Function function =
        FunctionBuilder.of(thing, "fetchPagedCollection", Purpose.fetchPagedCollection())
            .setReturnValue(dataObjectReturnValue)
            .addArgument(argumentDataObject)
            .build();

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

  protected boolean thingHasIdentity(Thing thing) {
    Set<Data> data =
        thing
            .getThingProperties()
            .stream()
            .map(ThingProperty::getData)
            .filter(this::isDataThingIdentity)
            .collect(Collectors.toSet());

    return data.size() == 1 ? true : false;
  }

  private boolean isDataThingIdentity(Data data) {
    return data.getDataPurpose().equals(DataPurpose.thingIdentity());
  }

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
