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

package io.polygenesis.generators.java.api;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.DtoType;
import io.polygenesis.representations.commons.FieldRepresentation;
import io.polygenesis.representations.commons.ParameterRepresentation;
import io.polygenesis.representations.java.AbstractClassRepresentable;
import io.polygenesis.representations.java.ConstructorRepresentation;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import io.polygenesis.representations.java.MethodRepresentation;
import java.util.Collections;
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

  private static final String INTEGER = "Integer";
  private static final String LONG = "Long";

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static Map<DtoType, String> mapDtoTypeToInclude = new HashMap<>();
  private static Map<DtoType, String> mapDtoTypeToClass = new HashMap<>();

  static {
    mapDtoTypeToInclude.put(DtoType.API_REQUEST, "com.oregor.trinity4j.api.ApiRequest");
    mapDtoTypeToInclude.put(
        DtoType.API_COLLECTION_REQUEST, "com.oregor.trinity4j.api.ApiCollectionRequest");
    mapDtoTypeToInclude.put(
        DtoType.API_PAGED_COLLECTION_REQUEST, "com.oregor.trinity4j.api.ApiPagedCollectionRequest");
    mapDtoTypeToInclude.put(DtoType.API_RESPONSE, "com.oregor.trinity4j.api.ApiResponse");
    mapDtoTypeToInclude.put(
        DtoType.API_COLLECTION_RESPONSE, "com.oregor.trinity4j.api.ApiCollectionResponse");
    mapDtoTypeToInclude.put(
        DtoType.API_PAGED_COLLECTION_RESPONSE,
        "com.oregor.trinity4j.api.ApiPagedCollectionResponse");

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
        .getDataGroup()
        .getModels()
        .forEach(
            model -> {
              fieldRepresentations.add(
                  new FieldRepresentation(
                      makeVariableDataType(
                          model.isDataGroup() ? model.getAsDataGroup().asDto() : model),
                      makeVariableName(model)));
            });

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(Dto source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    // ---------------------------------------------------------------------------------------------
    // Create empty constructor
    // ---------------------------------------------------------------------------------------------
    constructorRepresentations.add(
        createEmptyConstructorWithImplementation(
            source.getDataGroup().getObjectName().getText(),
            new LinkedHashSet<>(Collections.singletonList("@SuppressWarnings(\"CPD-START\")")),
            "\t\tsuper();"));

    // ---------------------------------------------------------------------------------------------
    // Create constructor with parameters
    // ---------------------------------------------------------------------------------------------
    Set<FieldRepresentation> fieldRepresentations = fieldRepresentations(source);

    constructorRepresentations.add(
        createConstructorWithSettersFromFieldRepresentations(
            source.getDataGroup().getObjectName().getText(), fieldRepresentations));

    // ---------------------------------------------------------------------------------------------
    // Create constructor for collection response
    // ---------------------------------------------------------------------------------------------
    if ((source.getDtoType().equals(DtoType.API_COLLECTION_RESPONSE)
            || source.getDtoType().equals(DtoType.API_PAGED_COLLECTION_RESPONSE))
        // TODO: needs more investigation
        && source.getArrayElementAsOptional().isPresent()) {
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
    return source.getDataGroup().getPackageName().getText();
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
        .getDataGroup()
        .getModels()
        .stream()
        .filter(model -> model.isDataArray())
        .findFirst()
        .ifPresent(model -> imports.add("java.util.List"));

    source
        .getDataGroup()
        .getModels()
        .stream()
        .filter(model -> model.isDataMap())
        .findFirst()
        .ifPresent(model -> imports.add("java.util.Map"));

    source
        .getDataGroup()
        .getModels()
        .stream()
        .filter(model -> model.isDataGroup())
        .map(DataGroup.class::cast)
        .map(dataGroup -> dataGroup.asDto())
        .forEach(
            dataGroup -> {
              if (!dataGroup.getPackageName().equals(source.getDataGroup().getPackageName())) {
                imports.add(
                    makeCanonicalObjectName(dataGroup.getPackageName(), dataGroup.getDataType()));
              }
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
        TextConverter.toUpperCamelSpaces(source.getDataGroup().getObjectName().getText()));

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
        TextConverter.toUpperCamel(source.getDataGroup().getObjectName().getText()));

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(Dto source, Object... args) {
    // TODO: needs more investigation
    if ((source.getDtoType().equals(DtoType.API_COLLECTION_RESPONSE)
            || source.getDtoType().equals(DtoType.API_PAGED_COLLECTION_RESPONSE))
        && !source.getArrayElementAsOptional().isPresent()) {
      return simpleObjectName(source, args);
    }

    if (mapDtoTypeToClass.containsKey(source.getDtoType())) {
      StringBuilder stringBuilder = new StringBuilder();

      stringBuilder.append(simpleObjectName(source));
      stringBuilder.append(" extends ");
      stringBuilder.append(mapDtoTypeToClass.get(source.getDtoType()));

      if (source.getDtoType().equals(DtoType.API_COLLECTION_RESPONSE)
          || source.getDtoType().equals(DtoType.API_PAGED_COLLECTION_RESPONSE)) {
        if (source.getArrayElementAsOptional().isPresent()) {

          stringBuilder.append("<");

          stringBuilder.append(
              TextConverter.toUpperCamel(
                  source
                      .getArrayElementAsOptional()
                      .orElseThrow(IllegalArgumentException::new)
                      .getDataType()));

          stringBuilder.append(">");
        } else {
          throw new IllegalArgumentException("No ArrayElement found");
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

  /**
   * Create constructor for collection response constructor representation.
   *
   * @param source the source
   * @return the constructor representation
   */
  private ConstructorRepresentation createConstructorForCollectionResponse(Dto source) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    Data arrayElement =
        source.getArrayElementAsOptional().orElseThrow(IllegalArgumentException::new);

    parameterRepresentations.add(
        new ParameterRepresentation(
            String.format("List<%s>", TextConverter.toUpperCamel(arrayElement.getDataType())),
            "items"));

    parameterRepresentations.add(new ParameterRepresentation(INTEGER, "totalPages"));

    parameterRepresentations.add(new ParameterRepresentation(LONG, "totalElements"));

    parameterRepresentations.add(new ParameterRepresentation(INTEGER, "pageNumber"));

    parameterRepresentations.add(new ParameterRepresentation(INTEGER, "pageSize"));

    String description =
        String.format(
            "Instantiates a new %s.",
            TextConverter.toUpperCamelSpaces(source.getDataGroup().getObjectName().getText()));

    return new ConstructorRepresentation(
        new LinkedHashSet<>(),
        description,
        MODIFIER_PUBLIC,
        parameterRepresentations,
        "\t\tsuper(items, totalPages, totalElements, pageNumber, pageSize);");
  }
}
