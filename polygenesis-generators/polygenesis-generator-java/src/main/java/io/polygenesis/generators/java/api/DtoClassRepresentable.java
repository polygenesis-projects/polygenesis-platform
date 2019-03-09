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

package io.polygenesis.generators.java.api;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.iomodel.IoModel;
import io.polygenesis.core.iomodel.IoModelArray;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.DtoType;
import io.polygenesis.representations.commons.FieldRepresentation;
import io.polygenesis.representations.commons.ParameterRepresentation;
import io.polygenesis.representations.java.AbstractClassRepresentable;
import io.polygenesis.representations.java.ConstructorRepresentation;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * The type Dto class representable.
 *
 * @author Christos Tsakostas
 */
public class DtoClassRepresentable extends AbstractClassRepresentable<Dto> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static Map<DtoType, String> mapDtoTypeToInclude = new HashMap<>();
  private static Map<DtoType, String> mapDtoTypeToClass = new HashMap<>();

  static {
    mapDtoTypeToInclude.put(DtoType.API_REQUEST, "com.oregor.ddd4j.api.ApiRequest");
    mapDtoTypeToInclude.put(
        DtoType.API_COLLECTION_REQUEST, "com.oregor.ddd4j.api.ApiCollectionRequest");
    mapDtoTypeToInclude.put(
        DtoType.API_PAGED_COLLECTION_REQUEST, "com.oregor.ddd4j.api.ApiPagedCollectionRequest");
    mapDtoTypeToInclude.put(DtoType.API_RESPONSE, "com.oregor.ddd4j.api.ApiResponse");
    mapDtoTypeToInclude.put(
        DtoType.API_COLLECTION_RESPONSE, "com.oregor.ddd4j.api.ApiCollectionResponse");
    mapDtoTypeToInclude.put(
        DtoType.API_PAGED_COLLECTION_RESPONSE, "com.oregor.ddd4j.api.ApiPagedCollectionResponse");

    mapDtoTypeToClass.put(DtoType.API_REQUEST, "ApiRequest");
    mapDtoTypeToClass.put(DtoType.API_COLLECTION_REQUEST, "ApiCollectionRequest");
    mapDtoTypeToClass.put(DtoType.API_PAGED_COLLECTION_REQUEST, "ApiPagedCollectionRequest");
    mapDtoTypeToClass.put(DtoType.API_RESPONSE, "ApiResponse");
    mapDtoTypeToClass.put(DtoType.API_COLLECTION_RESPONSE, "ApiCollectionResponse");
    mapDtoTypeToClass.put(DtoType.API_PAGED_COLLECTION_RESPONSE, "ApiPagedCollectionResponse");
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Dto class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public DtoClassRepresentable(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(Dto source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    source
        .getOriginatingIoModelGroup()
        .getModels()
        .forEach(
            model -> {
              fieldRepresentations.add(
                  new FieldRepresentation(
                      fromDataTypeToJavaConverter.getDeclaredVariableType(model),
                      model.getVariableName().getText()));
            });

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(Dto source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    // ---------------------------------------------------------------------------------------------
    // Create create constructor
    // ---------------------------------------------------------------------------------------------
    constructorRepresentations.add(
        createConstructorWithSetters(
            source.getOriginatingIoModelGroup().getDataType().getDataTypeName().getText(),
            new LinkedHashSet<>()));

    // ---------------------------------------------------------------------------------------------
    // Create constructor with parameters
    // ---------------------------------------------------------------------------------------------
    Set<FieldRepresentation> fieldRepresentations = fieldRepresentations(source);

    constructorRepresentations.add(
        createConstructorWithSettersFromFieldRepresentations(
            source.getOriginatingIoModelGroup().getDataType().getDataTypeName().getText(),
            fieldRepresentations));

    // ---------------------------------------------------------------------------------------------
    // Create constructor for collection response
    // ---------------------------------------------------------------------------------------------
    if (source.getDtoType().equals(DtoType.API_COLLECTION_RESPONSE)
        || source.getDtoType().equals(DtoType.API_PAGED_COLLECTION_RESPONSE)) {
      constructorRepresentations.add(createConstructorForCollectionResponse(source));
    }

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Dto source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = fieldRepresentations(source);
    return methodRepresentationsForGettersAndSetters(fieldRepresentations);
  }

  @Override
  public String packageName(Dto source, Object... args) {
    return source
        .getOriginatingIoModelGroup()
        .getDataType()
        .getOptionalPackageName()
        .map(packageName -> packageName.getText())
        .orElseThrow(() -> new IllegalArgumentException());
  }

  @Override
  public Set<String> imports(Dto source, Object... args) {
    Set<String> imports = new LinkedHashSet<>();

    if (mapDtoTypeToInclude.containsKey(source.getDtoType())) {
      imports.add(mapDtoTypeToInclude.get(source.getDtoType()));
    }

    if (source.getDtoType().equals(DtoType.API_COLLECTION_RESPONSE)
        || source.getDtoType().equals(DtoType.API_PAGED_COLLECTION_RESPONSE)) {
      imports.add("java.util.List");
    }

    source
        .getOriginatingIoModelGroup()
        .getModels()
        .forEach(
            model -> {
              model
                  .getDataType()
                  .getOptionalPackageName()
                  .filter(
                      packageName ->
                          !packageName.equals(
                              source
                                  .getOriginatingIoModelGroup()
                                  .getDataType()
                                  .getOptionalPackageName()
                                  .get()))
                  .ifPresent(
                      packageName ->
                          imports.add(
                              makeCanonicalObjectName(
                                  packageName, model.getDataType().getDataTypeName())));
            });

    return imports;
  }

  @Override
  public Set<String> annotations(Dto source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String description(Dto source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(
            source.getOriginatingIoModelGroup().getDataType().getDataTypeName().getText()));

    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(Dto source, Object... args) {
    return "public";
  }

  @Override
  public String simpleObjectName(Dto source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamel(
            source.getOriginatingIoModelGroup().getDataType().getDataTypeName().getText()));

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(Dto source, Object... args) {
    if (mapDtoTypeToClass.containsKey(source.getDtoType())) {
      StringBuilder stringBuilder = new StringBuilder();

      stringBuilder.append(simpleObjectName(source));
      stringBuilder.append(" extends ");
      stringBuilder.append(mapDtoTypeToClass.get(source.getDtoType()));

      if (source.getDtoType().equals(DtoType.API_COLLECTION_RESPONSE)
          || source.getDtoType().equals(DtoType.API_PAGED_COLLECTION_RESPONSE)) {
        if (source.getOriginatingIoModelGroup().isIoModelArray()) {
          stringBuilder.append("<");
          stringBuilder.append(
              TextConverter.toUpperCamel(
                  ((IoModelArray) source.getOriginatingIoModelGroup())
                      .getArrayElement()
                      .getDataType()
                      .getDataTypeName()
                      .getText()));
          stringBuilder.append(">");
        } else {
          throw new IllegalArgumentException();
        }
      }

      return stringBuilder.toString();

    } else {
      return simpleObjectName(source);
    }
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private ConstructorRepresentation createConstructorForCollectionResponse(Dto source) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    IoModel arrayElement =
        source.getArrayElementAsOptional().orElseThrow(IllegalArgumentException::new);

    parameterRepresentations.add(
        new ParameterRepresentation(
            String.format(
                "List<%s>",
                TextConverter.toUpperCamel(arrayElement.getDataType().getDataTypeName().getText())),
            "items"));

    parameterRepresentations.add(new ParameterRepresentation("Integer", "totalPages"));

    parameterRepresentations.add(new ParameterRepresentation("Long", "totalElements"));

    parameterRepresentations.add(new ParameterRepresentation("Integer", "pageNumber"));

    parameterRepresentations.add(new ParameterRepresentation("Integer", "pageSize"));

    String description =
        String.format(
            "Instantiates a new %s.",
            TextConverter.toUpperCamelSpaces(
                source.getOriginatingIoModelGroup().getDataType().getDataTypeName().getText()));

    return new ConstructorRepresentation(
        new LinkedHashSet<>(),
        description,
        MODIFIER_PUBLIC,
        parameterRepresentations,
        "\t\tsuper(items, totalPages, totalElements, pageNumber, pageSize);");
  }
}