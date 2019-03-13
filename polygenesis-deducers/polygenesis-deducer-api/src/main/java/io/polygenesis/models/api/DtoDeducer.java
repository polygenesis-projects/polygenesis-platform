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

package io.polygenesis.models.api;

import static java.util.stream.Collectors.toCollection;

import io.polygenesis.core.Argument;
import io.polygenesis.core.Function;
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataBusinessType;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.core.data.DataPrimitive;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Dto deducer.
 *
 * @author Christos Tsakostas
 */
public class DtoDeducer {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce request dto dto.
   *
   * @param function the function
   * @return the dto
   */
  public Dto deduceRequestDto(Function function) {

    if (function.getArguments() == null || function.getArguments().isEmpty()) {
      throw new IllegalArgumentException(
          String.format(
              "Api Functions must have one argument. Thing name=%s, function=%s",
              function.getThing().getThingName().getText(), function.getName().getText()));
    }

    if (function.getArguments().size() > 1) {
      throw new IllegalArgumentException(
          String.format(
              "Only one argument is currently supported. Thing name=%s, function=%s",
              function.getThing().getThingName().getText(), function.getName().getText()));
    }

    Argument argument =
        function.getArguments().stream().findFirst().orElseThrow(IllegalArgumentException::new);

    if (!argument.getData().isDataGroup()) {
      throw new IllegalArgumentException(
          String.format(
              "Argument model must be DataGroup. Thing name=%s, function=%s",
              function.getThing().getThingName().getText(), function.getName().getText()));
    }

    DtoType dtoType;
    if (function.getGoal().isFetchCollection()) {
      dtoType = DtoType.API_COLLECTION_REQUEST;
    } else if (function.getGoal().isFetchPagedCollection()) {
      dtoType = DtoType.API_PAGED_COLLECTION_REQUEST;
    } else {
      dtoType = DtoType.API_REQUEST;
    }

    Dto dto = new Dto(dtoType, argument.getData().getAsDataGroup());

    makeAssertionsForRequestDto(dto, function);

    return dto;
  }

  /**
   * Deduce response dto dto.
   *
   * @param function the function
   * @return the dto
   */
  public Dto deduceResponseDto(Function function) {

    if (function.getReturnValue() == null) {
      throw new IllegalArgumentException(
          String.format(
              "Api Functions must have return value. Thing name=%s, function=%s",
              function.getThing().getThingName().getText(), function.getName().getText()));
    }

    if (!function.getReturnValue().getData().isDataGroup()) {
      throw new IllegalArgumentException(
          String.format(
              "ReturnValue model must be DataGroup. Thing name=%s, function=%s",
              function.getThing().getThingName().getText(), function.getName().getText()));
    }

    DtoType dtoType;
    if (function.getGoal().isFetchCollection()) {
      dtoType = DtoType.API_COLLECTION_RESPONSE;
    } else if (function.getGoal().isFetchPagedCollection()) {
      dtoType = DtoType.API_PAGED_COLLECTION_RESPONSE;
    } else {
      dtoType = DtoType.API_RESPONSE;
    }

    Dto dto = new Dto(dtoType, function.getReturnValue().getData().getAsDataGroup());

    makeAssertionsForResponseDto(dto, function);

    return dto;
  }

  /**
   * Deduce set.
   *
   * @param methods the methods
   * @return the set
   */
  public Set<Dto> deduceAllDtosInMethods(Set<Method> methods) {
    Set<Dto> dtos = new LinkedHashSet<>();

    methods
        .stream()
        .forEach(
            method -> {
              addDto(dtos, method.getRequestDto());
              addDto(dtos, method.getResponseDto());
            });

    return dtos;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Add dto.
   *
   * @param dtos the dtos
   * @param dto the dto
   */
  private void addDto(Set<Dto> dtos, Dto dto) {
    dtos.add(dto);

    if (dto.getArrayElementAsOptional().isPresent()) {
      Data arrayElement = dto.getArrayElementAsOptional().get();
      if (arrayElement.isDataGroup()) {
        addDto(dtos, new Dto(DtoType.COLLECTION_RECORD, (DataGroup) arrayElement));
      }
    }

    // Add model group children of DataGroup recursively
    dto.getOriginatingDataGroup()
        .getModels()
        .forEach(
            model -> {
              // TODO
              // if (model.isDataGroup() || model.isDataArray()) {
              if (model.isDataGroup()) {
                if (dto.getDtoType().equals(DtoType.API_COLLECTION_REQUEST)
                    || dto.getDtoType().equals(DtoType.API_PAGED_COLLECTION_REQUEST)) {
                  addDto(dtos, new Dto(DtoType.COLLECTION_RECORD, (DataGroup) model));
                } else {
                  addDto(dtos, new Dto(DtoType.INTERNAL, (DataGroup) model));
                }
              }
            });
  }

  /**
   * Make assertions for request dto.
   *
   * @param dto the dto
   * @param function the function
   */
  private void makeAssertionsForRequestDto(Dto dto, Function function) {
    if (function.getGoal().isFetchOne()) {
      assertThatDtoHasOneDataBusinessTypeOf(dto, function, DataBusinessType.THING_IDENTITY);
    }

    if (function.getGoal().isFetchPagedCollection()) {
      assertThatDtoHasOneDataBusinessTypeOf(dto, function, DataBusinessType.PAGE_NUMBER);
      assertThatDtoHasOneDataBusinessTypeOf(dto, function, DataBusinessType.PAGE_SIZE);
    }
  }

  /**
   * Make assertions for response dto.
   *
   * @param dto the dto
   * @param function the function
   */
  private void makeAssertionsForResponseDto(Dto dto, Function function) {
    if (function.getGoal().isCreate()) {
      assertThatDtoHasOneDataBusinessTypeOf(dto, function, DataBusinessType.THING_IDENTITY);
    }
  }

  /**
   * Assert that dto has one data business type of.
   *
   * @param dto the dto
   * @param function the function
   * @param dataBusinessType the data business type
   */
  private void assertThatDtoHasOneDataBusinessTypeOf(
      Dto dto, Function function, DataBusinessType dataBusinessType) {

    Set<DataPrimitive> modelPrimitives =
        dto.getOriginatingDataGroup()
            .getModels()
            .stream()
            .filter(model -> model.isDataPrimitive())
            .map(DataPrimitive.class::cast)
            .filter(model -> model.getDataBusinessType().equals(dataBusinessType))
            .collect(toCollection(LinkedHashSet::new));

    if (modelPrimitives.isEmpty()) {
      throw new IllegalStateException(
          String.format(
              "No %s found in Request Dto. Thing name=%s, function=%s",
              dataBusinessType.name(),
              function.getThing().getThingName().getText(),
              function.getName().getText()));
    }

    if (modelPrimitives.size() > 1) {
      throw new IllegalStateException(
          String.format(
              "More than one %s found in Request Dto. Thing name=%s, function=%s",
              dataBusinessType.name(),
              function.getThing().getThingName().getText(),
              function.getName().getText()));
    }
  }
}
