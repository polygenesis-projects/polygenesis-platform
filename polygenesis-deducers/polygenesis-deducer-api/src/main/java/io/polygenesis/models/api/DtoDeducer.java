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

package io.polygenesis.models.api;

import static java.util.stream.Collectors.toCollection;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.thing.Argument;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
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
   * Deduce request dto.
   *
   * @param function the function
   * @param rootPackageName the root package name
   * @return the dto
   */
  public Dto deduceRequestDto(Function function, PackageName rootPackageName) {
    DataObject originatingDataObject = null;
    Boolean virtual = false;

    if (function.getArguments() != null && function.getArguments().size() == 1) {
      Argument argument =
          function.getArguments().stream().findFirst().orElseThrow(IllegalArgumentException::new);
      if (argument.getData().isDataGroup()) {
        originatingDataObject = argument.getData().getAsDataObject();
      }
    }

    if (originatingDataObject == null) {
      virtual = true;

      DataObject finalDataObject =
          new DataObject(
              new ObjectName(
                  String.format(
                      "%s%sRequest",
                      TextConverter.toUpperCamel(function.getName().getText()),
                      TextConverter.toUpperCamel(function.getThing().getThingName().getText()))),
              function.getThing().makePackageName(rootPackageName, function.getThing()),
              new VariableName("request"));

      if (function.getArguments() != null) {
        function.getArguments().forEach(argument -> finalDataObject.addData(argument.getData()));
      }

      originatingDataObject = finalDataObject;
    }

    DtoType dtoType;
    if (function.getPurpose().isFetchCollection()) {
      dtoType = DtoType.API_COLLECTION_REQUEST;
    } else if (function.getPurpose().isFetchPagedCollection()) {
      dtoType = DtoType.API_PAGED_COLLECTION_REQUEST;
    } else {
      dtoType = DtoType.API_REQUEST;
    }

    Dto dto =
        new Dto(function.getThing(), dtoType, originatingDataObject.getAsDataObject(), virtual);

    makeAssertionsForRequestDto(dto, function);

    return dto;
  }

  /**
   * Deduce response dto.
   *
   * @param function the function
   * @param rootPackageName the root package name
   * @return the dto
   */
  public Dto deduceResponseDto(Function function, PackageName rootPackageName) {

    DataObject originatingDataObject = null;
    Boolean virtual = false;

    if (function.getReturnValue() != null && function.getReturnValue().getData().isDataGroup()) {
      originatingDataObject = function.getReturnValue().getData().getAsDataObject();
    }

    if (originatingDataObject == null) {
      virtual = true;

      DataObject virtualResponseDataObject =
          new DataObject(
              new ObjectName(
                  String.format(
                      "%s%sResponse",
                      TextConverter.toUpperCamel(function.getName().getText()),
                      TextConverter.toUpperCamel(function.getThing().getThingName().getText()))),
              function.getThing().makePackageName(rootPackageName, function.getThing()));

      if (function.getReturnValue() != null) {
        virtualResponseDataObject.addData(function.getReturnValue().getData());
      }

      originatingDataObject = virtualResponseDataObject;
    }

    DtoType dtoType;
    if (function.getPurpose().isFetchCollection()) {
      dtoType = DtoType.API_COLLECTION_RESPONSE;
    } else if (function.getPurpose().isFetchPagedCollection()) {
      dtoType = DtoType.API_PAGED_COLLECTION_RESPONSE;
    } else {
      dtoType = DtoType.API_RESPONSE;
    }

    Dto dto = new Dto(function.getThing(), dtoType, originatingDataObject, virtual);

    makeAssertionsForResponseDto(dto, function);

    return dto;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Make assertions for request dto.
   *
   * @param dto the dto
   * @param function the function
   */
  private void makeAssertionsForRequestDto(Dto dto, Function function) {
    if (function.getPurpose().isFetchOne()) {
      assertThatDtoHasOneDataBusinessTypeOf(dto, function, DataPurpose.thingIdentity());
    }

    if (function.getPurpose().isFetchPagedCollection()) {
      assertThatDtoHasOneDataBusinessTypeOf(dto, function, DataPurpose.pageNumber());
      assertThatDtoHasOneDataBusinessTypeOf(dto, function, DataPurpose.pageSize());
    }
  }

  /**
   * Make assertions for response dto.
   *
   * @param dto the dto
   * @param function the function
   */
  private void makeAssertionsForResponseDto(Dto dto, Function function) {
    if (function.getPurpose().isCreate()) {
      assertThatDtoHasOneDataBusinessTypeOf(dto, function, DataPurpose.thingIdentity());
    }
  }

  /**
   * Assert that dto has one data business type of.
   *
   * @param dto the dto
   * @param function the function
   * @param dataPurpose the data business type
   */
  private void assertThatDtoHasOneDataBusinessTypeOf(
      Dto dto, Function function, DataPurpose dataPurpose) {

    Set<DataPrimitive> modelPrimitives =
        dto.getDataObject()
            .getModels()
            .stream()
            .filter(Data::isDataPrimitive)
            .map(DataPrimitive.class::cast)
            .filter(model -> model.getDataPurpose().equals(dataPurpose))
            .collect(toCollection(LinkedHashSet::new));

    // TODO: is this check necessary?
    //    if (modelPrimitives.isEmpty()) {
    //      throw new IllegalStateException(
    //          String.format(
    //              "No %s found in Dto=%s. Thing name=%s, function=%s",
    //              dataPurpose.getText(),
    //              dto.getDataObject().getObjectName().getText(),
    //              function.getThing().getThingName().getText(),
    //              function.getName().getText()));
    //    }

    if (modelPrimitives.size() > 1) {
      throw new IllegalStateException(
          String.format(
              "More than one %s found in Dto=%s. Thing name=%s, function=%s",
              dataPurpose.getText(),
              dto.getDataObject().getObjectName().getText(),
              function.getThing().getThingName().getText(),
              function.getName().getText()));
    }
  }
}
