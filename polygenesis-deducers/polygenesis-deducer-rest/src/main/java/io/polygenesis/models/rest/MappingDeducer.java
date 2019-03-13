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

package io.polygenesis.models.rest;

import io.polygenesis.annotations.core.GoalType;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Function;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Mapping deducer.
 *
 * @author Christos Tsakostas
 */
public class MappingDeducer {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from set.
   *
   * @param function the function
   * @return the set
   */
  public Set<Mapping> deduceFrom(Function function, HttpMethod httpMethod) {
    Set<Mapping> mappings = new LinkedHashSet<>();

    switch (httpMethod) {
      case GET:
        mappings.add(getMappingForGet(function));
        break;
      case POST:
        mappings.add(getMappingForPost(function));
        break;
      case PUT:
        mappings.add(getMappingForPut(function));
        break;
      case DELETE:
        mappings.add(getMappingForDelete(function));
        break;
      default:
        break;
    }

    return mappings;
  }

  // ===============================================================================================
  // PRIVATE - GET
  // ===============================================================================================

  private Mapping getMappingForGet(Function function) {
    String goalType = TextConverter.toUpperUnderscore(function.getGoal().getText());

    if (goalType.equals(GoalType.FETCH_ONE.name())) {
      return getMappingForGetFetchOne(function);
    } else if (goalType.equals(GoalType.FETCH_COLLECTION.name())) {
      return getMappingForGetFetchCollection(function);
    } else if (goalType.equals(GoalType.FETCH_PAGED_COLLECTION.name())) {
      return getMappingForGetFetchCollection(function);
    } else {
      throw new IllegalStateException(
          String.format("Cannot deduce REST mapping for=%s", function.getGoal().getText()));
    }
  }

  private Mapping getMappingForGetFetchOne(Function function) {
    return new Mapping(
        new LinkedHashSet<>(
            Arrays.asList(
                new PathConstant(
                    TextConverter.toLowerHyphen(
                        TextConverter.toPlural(function.getThing().getThingName().getText()))),
                new PathVariable(
                    TextConverter.toLowerCamel(
                        function.getThing().getThingName().getText() + "Id")))));
  }

  private Mapping getMappingForGetFetchCollection(Function function) {
    return new Mapping(
        new LinkedHashSet<>(
            Arrays.asList(
                new PathConstant(
                    TextConverter.toLowerHyphen(
                        TextConverter.toPlural(function.getThing().getThingName().getText()))))));
  }

  // ===============================================================================================
  // PRIVATE - POST
  // ===============================================================================================

  private Mapping getMappingForPost(Function function) {
    return new Mapping(
        new LinkedHashSet<>(
            Arrays.asList(
                new PathConstant(
                    TextConverter.toLowerHyphen(
                        TextConverter.toPlural(function.getThing().getThingName().getText()))))));
  }

  // ===============================================================================================
  // PRIVATE - PUT
  // ===============================================================================================

  private Mapping getMappingForPut(Function function) {
    return new Mapping(
        new LinkedHashSet<>(
            Arrays.asList(
                new PathConstant(
                    TextConverter.toLowerHyphen(
                        TextConverter.toPlural(function.getThing().getThingName().getText()))),
                new PathVariable(
                    TextConverter.toLowerCamel(
                        function.getThing().getThingName().getText() + "Id")))));
  }

  // ===============================================================================================
  // PRIVATE - DELETE
  // ===============================================================================================

  private Mapping getMappingForDelete(Function function) {
    return new Mapping(
        new LinkedHashSet<>(
            Arrays.asList(
                new PathConstant(
                    TextConverter.toLowerHyphen(
                        TextConverter.toPlural(function.getThing().getThingName().getText()))),
                new PathVariable(
                    TextConverter.toLowerCamel(
                        function.getThing().getThingName().getText() + "Id")))));
  }
}
