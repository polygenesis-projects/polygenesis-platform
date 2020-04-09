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

package io.polygenesis.generators.angular.context.model.dto;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataArray;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.models.api.Dto;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.typescript.AbstractTypescriptClassTransformer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ModelTransformer extends AbstractTypescriptClassTransformer<Dto, ModelMethod> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Model transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public ModelTransformer(
      DataTypeTransformer dataTypeTransformer, ModelMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(Dto source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("data", create(source, args));
    return new TemplateData(dataModel, "polygenesis-dart/Class.dart.ftl");
  }

  @Override
  public Set<FieldRepresentation> stateFieldRepresentations(Dto source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    source
        .getDataObject()
        .getModels()
        .forEach(
            model ->
                fieldRepresentations.add(
                    FieldRepresentation.withModifiers(
                        makeVariableDataType(
                            model.isDataGroup() ? model.getAsDataObject().asDto() : model),
                        makeVariableName(model),
                        dataTypeTransformer.getModifierPrivate())));

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(Dto source, Object... args) {
    return super.constructorRepresentations(source, args);
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Dto source, Object... args) {
    return super.methodRepresentations(source, args);
  }

  @Override
  public String packageName(Dto source, Object... args) {
    return super.packageName(source, args);
  }

  @Override
  public Set<String> imports(Dto source, Object... args) {
    Set<String> imports = new TreeSet<>();

    source.getDataObject().getModels().stream()
        .filter(Data::isDataArray)
        .map(DataArray.class::cast)
        .map(dataArray -> dataArray.getArrayElement())
        .filter(Data::isDataGroup)
        .map(DataObject.class::cast)
        .forEach(
            dataGroup ->
                imports.add(
                    makeImport(
                        dataGroup.getPackageName(),
                        dataGroup.getPackageName(),
                        dataGroup.getDataType())));

    source.getDataObject().getModels().stream()
        .filter(Data::isDataGroup)
        .map(DataObject.class::cast)
        .map(DataObject::asDto)
        .forEach(
            dataGroup ->
                imports.add(
                    makeImport(
                        dataGroup.getPackageName(),
                        dataGroup.getPackageName(),
                        dataGroup.getDataType())));

    return imports;
  }

  @Override
  public Set<String> annotations(Dto source, Object... args) {
    return super.annotations(source, args);
  }

  @Override
  public String description(Dto source, Object... args) {
    return super.description(source, args);
  }

  @Override
  public String modifiers(Dto source, Object... args) {
    return "";
  }

  @Override
  public String simpleObjectName(Dto source, Object... args) {
    return super.simpleObjectName(source, args);
  }

  @Override
  public String fullObjectName(Dto source, Object... args) {
    return super.fullObjectName(source, args);
  }
}
