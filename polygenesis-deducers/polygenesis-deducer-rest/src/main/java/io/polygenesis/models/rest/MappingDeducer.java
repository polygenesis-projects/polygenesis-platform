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

package io.polygenesis.models.rest;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.commons.text.TextConverter;
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
   * @param httpMethod the http method
   * @return the set
   */
  public Set<Mapping> deduceFrom(Function function, HttpMethod httpMethod) {
    Set<Mapping> mappings = new LinkedHashSet<>();

    Function functionToUse = function;

    if (function.getDelegatesToFunction() != null) {
      functionToUse = function.getDelegatesToFunction();
    }

    switch (httpMethod) {
      case GET:
        mappings.add(mappingForGet(functionToUse));
        break;
      case POST:
        mappings.add(getMappingForResourceWithoutId(functionToUse));
        break;
      case PUT:
      case DELETE:
        mappings.add(getMappingForResourceWithId(functionToUse.getThing()));
        break;
      default:
        throw new UnsupportedOperationException();
    }

    return mappings;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Gets mapping for get.
   *
   * @param function the function
   * @return the mapping for get
   */
  private Mapping mappingForGet(Function function) {
    if (function.getPurpose().isFetchOne() || function.getPurpose().isEntityFetch()) {
      return getMappingForResourceWithId(function.getThing());
    } else if (function.getPurpose().isFetchCollection()
        || function.getPurpose().isFetchPagedCollection()
        || function.getPurpose().isEntityFetchAll()) {
      return getMappingForResourceWithoutId(function);
    } else {
      throw new IllegalStateException(
          String.format("Cannot deduce REST mapping for=%s", function.getPurpose().getText()));
    }
  }

  /**
   * Gets mapping for resource with id.
   *
   * @param thing the thing
   * @return the mapping for resource with id
   */
  private Mapping getMappingForResourceWithId(Thing thing) {
    Set<PathContent> pathContents = new LinkedHashSet<>();

    fillParentPathContent(pathContents, thing);

    pathContents.addAll(
        new LinkedHashSet<>(
            Arrays.asList(
                new PathConstant(
                    TextConverter.toLowerHyphen(
                        TextConverter.toPlural(thing.getThingName().getText()))),
                new PathVariable(
                    TextConverter.toLowerCamel(thing.getThingName().getText() + "Id")))));

    return new Mapping(pathContents);
  }

  /**
   * Gets mapping for resource without Id.
   *
   * @param function the Function
   * @return the mapping for resource
   */
  private Mapping getMappingForResourceWithoutId(Function function) {
    Set<PathContent> pathContents = new LinkedHashSet<>();

    fillParentPathContent(pathContents, function.getThing());

    pathContents.add(
        new PathConstant(
            TextConverter.toLowerHyphen(
                TextConverter.toPlural(function.getThing().getThingName().getText()))));

    if (!function.getName().getObject().isEmpty()
        && !function
            .getName()
            .getObject()
            .equalsIgnoreCase(PurposeFunctionBuilder.FUNCTION_OBJECT_PAGED_COLLECTION)
        && (function.getPurpose().isFetchCollection()
            || function.getPurpose().isFetchPagedCollection())) {
      pathContents.add(
          new PathConstant(
              TextConverter.toLowerHyphen(TextConverter.toPlural(function.getName().getObject()))));
    }

    return new Mapping(pathContents);
  }

  /**
   * Fill parent path content.
   *
   * @param pathContents the path contents
   * @param thing the thing
   */
  private void fillParentPathContent(Set<PathContent> pathContents, Thing thing) {
    if (thing.getOptionalParent() != null) {
      pathContents.addAll(getMappingForResourceWithId(thing.getOptionalParent()).getPathContents());
    }
  }
}
