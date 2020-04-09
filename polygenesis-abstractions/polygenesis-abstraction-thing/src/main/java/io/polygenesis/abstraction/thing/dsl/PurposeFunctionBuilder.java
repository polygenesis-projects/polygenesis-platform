/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.AbstractionScope;
import java.util.LinkedHashSet;
import java.util.Set;

public class PurposeFunctionBuilder {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /** The constant FUNCTION_OBJECT_PAGED_COLLECTION. */
  public static final String FUNCTION_OBJECT_PAGED_COLLECTION = "pagedCollection";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final PackageName rootPackageNameVo;
  private final Thing thing;
  private final Set<Function> functions;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private PurposeFunctionBuilder(Thing thing, PackageName rootPackageName) {
    this.rootPackageNameVo = rootPackageName;
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
    return new PurposeFunctionBuilder(thing, new PackageName(rootPackageName));
  }

  /**
   * For thing purpose function builder.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the purpose function builder
   */
  public static PurposeFunctionBuilder forThing(Thing thing, PackageName rootPackageName) {
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
    withFunctionModify("modify", "", models);
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
    return withFunctionCreate(functionName, models, new LinkedHashSet<>(), false);
  }

  /**
   * With function create purpose function builder.
   *
   * @param arguments the arguments
   * @return the purpose function builder
   */
  public final PurposeFunctionBuilder withFunctionCreate(Set<Data> arguments) {
    return withFunctionCreate("create", arguments, new LinkedHashSet<>(), true);
  }

  /**
   * With function create purpose function builder.
   *
   * @param arguments the arguments
   * @param outputs the outputs
   * @return the purpose function builder
   */
  public final PurposeFunctionBuilder withFunctionCreate(Set<Data> arguments, Set<Data> outputs) {
    return withFunctionCreate("create", arguments, outputs, true);
  }

  /**
   * With function create purpose function builder.
   *
   * @param functionName the function name
   * @param models the models
   * @return the purpose function builder
   */
  public final PurposeFunctionBuilder withFunctionCreate(String functionName, Set<Data> models) {
    return withFunctionCreate(functionName, models, new LinkedHashSet<>(), true);
  }

  /**
   * With function create thing builder.
   *
   * @param functionVerb the function name
   * @param arguments the arguments
   * @param outputs the outputs
   * @param returnValue the return value
   * @return the thing builder
   */
  @SuppressWarnings("CPD-START")
  private PurposeFunctionBuilder withFunctionCreate(
      String functionVerb, Set<Data> arguments, Set<Data> outputs, Boolean returnValue) {

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    if (returnValue) {
      DataObject returnValueDataObject =
          new DataObject(
              new ObjectName(
                  String.format(
                      "%s%sResponse",
                      functionVerb, TextConverter.toUpperCamel(thing.getThingName().getText()))),
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
      outputs.forEach(returnValueDataObject::addData);
      // -------------------------------------------------------------------------------------------

      // -------------------------------------------------------------------------------------------
      // FUNCTION
      Function function =
          FunctionBuilder.of(thing, functionVerb, "", Purpose.create())
              .setReturnValue(returnValueDataObject)
              .addArguments(generateArgumentsData(thing, arguments))
              .build();

      this.functions.add(function);
    } else {
      Function function =
          FunctionBuilder.of(thing, functionVerb, "", Purpose.create())
              .addArguments(generateArgumentsData(thing, arguments))
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
    Function function =
        FunctionBuilder.of(thing, "ensure", "existence", Purpose.ensureExistence())
            .addArguments(generateArgumentsData(thing))
            .build();

    this.functions.add(function);

    return this;
  }

  /**
   * With function modify no return value purpose function builder.
   *
   * @param functionVerb the function verb
   * @param functionObject the function object
   * @param models the models
   * @return the purpose function builder
   */
  public final PurposeFunctionBuilder withFunctionModifyNoReturnValue(
      String functionVerb, String functionObject, Set<Data> models) {
    return withFunctionModify(
        functionVerb, functionObject, models, false, thing.getAbstractionsScopes());
  }

  /**
   * With function modify no return value purpose function builder.
   *
   * @param functionVerb the function verb
   * @param functionObject the function object
   * @param models the models
   * @param abstractionScopes the abstraction scopes
   * @return the purpose function builder
   */
  public final PurposeFunctionBuilder withFunctionModifyNoReturnValue(
      String functionVerb,
      String functionObject,
      Set<Data> models,
      Set<AbstractionScope> abstractionScopes) {
    return withFunctionModify(functionVerb, functionObject, models, false, abstractionScopes);
  }

  /**
   * With function modify purpose function builder.
   *
   * @param functionVerb the function verb
   * @param functionObject the function object
   * @param models the models
   * @return the purpose function builder
   */
  public final PurposeFunctionBuilder withFunctionModify(
      String functionVerb, String functionObject, Set<Data> models) {
    return withFunctionModify(
        functionVerb, functionObject, models, true, thing.getAbstractionsScopes());
  }

  /**
   * With function modify purpose function builder.
   *
   * @param functionVerb the function name
   * @param arguments the arguments
   * @param returnValue the return value
   * @param abstractionScopes the abstraction scopes
   * @return the purpose function builder
   */
  private PurposeFunctionBuilder withFunctionModify(
      String functionVerb,
      String functionObject,
      Set<Data> arguments,
      Boolean returnValue,
      Set<AbstractionScope> abstractionScopes) {

    // ---------------------------------------------------------------------------------------------
    // RETURN VALUE
    // ---------------------------------------------------------------------------------------------
    if (returnValue) {
      DataObject returnValueDataObject =
          new DataObject(
              new ObjectName(
                  String.format(
                      "%s%sResponse",
                      functionVerb, TextConverter.toUpperCamel(thing.getThingName().getText()))),
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
          FunctionBuilder.of(thing, functionVerb, functionObject, Purpose.modify())
              .setReturnValue(returnValueDataObject)
              .addArguments(generateArgumentsData(thing, arguments))
              .setAbstractionScopes(abstractionScopes)
              .build();

      this.functions.add(function);
    } else {
      // -------------------------------------------------------------------------------------------
      // FUNCTION
      // -------------------------------------------------------------------------------------------

      Function function =
          FunctionBuilder.of(thing, functionVerb, functionObject, Purpose.modify())
              .addArguments(generateArgumentsData(thing, arguments))
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
    return withFunctionModifyNoReturnValue("batchProcess", "", new LinkedHashSet<>());
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
            thing.makePackageName(rootPackageNameVo, thing),
            new VariableName("request"));

    // ---------------------------------------------------------------------------------------------
    // Add Parent Thing Identity if Any
    if (thing.getOptionalParent() != null) {
      argumentDataObject.addData(getParentThingIdentity(thing));
    }

    // ---------------------------------------------------------------------------------------------
    // Add Thing Identity
    argumentDataObject.addData(getThingIdentity(thing));

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
        FunctionBuilder.of(thing, "fetch", "", Purpose.fetchOne())
            .setReturnValue(returnValueDataObject)
            .addArguments(argumentDataObject.getModels())
            .build();

    this.functions.add(function);
    return this;
  }

  /**
   * With function fetch paged collection purpose function builder.
   *
   * @param models the models
   * @return the purpose function builder
   */
  public final PurposeFunctionBuilder withFunctionFetchPagedCollection(Set<Data> models) {
    return withFunctionFetchPagedCollection("fetch", FUNCTION_OBJECT_PAGED_COLLECTION, models);
  }

  /**
   * Fetch paged collection thing builder.
   *
   * @param functionVerb the function verb
   * @param functionObject the function object
   * @param models the models
   * @return the thing builder
   */
  public final PurposeFunctionBuilder withFunctionFetchPagedCollection(
      String functionVerb, String functionObject, Set<Data> models) {

    // ---------------------------------------------------------------------------------------------
    // ARGUMENTS
    // ---------------------------------------------------------------------------------------------
    DataObject argumentDataObject =
        new DataObject(
            new ObjectName(
                String.format(
                    "Fetch%sCollectionRequest",
                    TextConverter.toUpperCamel(thing.getThingName().getText()))),
            thing.makePackageName(rootPackageNameVo, thing),
            new VariableName("request"));

    // ---------------------------------------------------------------------------------------------
    // Add Parent Thing Identity if Any
    if (thing.getOptionalParent() != null) {
      argumentDataObject.addData(getParentThingIdentity(thing));
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
        FunctionBuilder.of(thing, functionVerb, functionObject, Purpose.fetchPagedCollection())
            .setReturnValue(dataObjectReturnValue)
            .addArguments(argumentDataObject.getModels())
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

  private Set<Data> generateArgumentsData(Thing thing) {
    return generateArgumentsData(thing, new LinkedHashSet<>());
  }

  private Set<Data> generateArgumentsData(Thing thing, Set<Data> data) {
    Set<Data> argumentsData = new LinkedHashSet<>();

    if (!thing.supportsAbstractionScope(AbstractionScope.externallyProvided())) {
      // -------------------------------------------------------------------------------------------
      // Add Parent Thing Identity if Any
      if (thing.getOptionalParent() != null) {
        argumentsData.add(getParentThingIdentity(thing));
      }

      // -------------------------------------------------------------------------------------------
      // Add Thing Identity
      argumentsData.add(getThingIdentity(thing));

      // -------------------------------------------------------------------------------------------
      // Add Tenant Identity
      if (thing.getMultiTenant()) {
        argumentsData.add(getTenantIdentity(thing));
      }
    }

    // ---------------------------------------------------------------------------------------------
    // Add the provided data
    argumentsData.addAll(data);

    return argumentsData;
  }

  private Data getThingIdentity(Thing thing) {
    return thing.getThingProperties().getData().stream()
        .filter(this::isDataThingIdentity)
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalStateException(
                    String.format(
                        "Thing with name='%s' is not uniquely identified",
                        thing.getThingName().getText())));
  }

  private Data getParentThingIdentity(Thing thing) {
    return thing.getThingProperties().getData().stream()
        .filter(this::isDataParentThingIdentity)
        .findFirst()
        .orElseThrow();
  }

  private Data getTenantIdentity(Thing thing) {
    return thing.getThingProperties().getData().stream()
        .filter(this::isDataTenantIdentity)
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalStateException(
                    String.format(
                        "Thing with name='%s' is not uniquely identified",
                        thing.getThingName().getText())));
  }

  private boolean isDataThingIdentity(Data data) {
    return data.getDataPurpose().equals(DataPurpose.thingIdentity());
  }

  private boolean isDataParentThingIdentity(Data data) {
    return data.getDataPurpose().equals(DataPurpose.parentThingIdentity());
  }

  private boolean isDataTenantIdentity(Data data) {
    return data.getDataPurpose().equals(DataPurpose.tenantIdentity());
  }
}
