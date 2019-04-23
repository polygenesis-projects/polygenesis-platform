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

import io.polygenesis.annotations.core.GoalType;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Function;
import io.polygenesis.core.Thing;
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

    switch (httpMethod) {
      case GET:
        mappings.add(getMappingForGet(function));
        break;
      case POST:
        mappings.add(getMappingForResource(function.getThing()));
        break;
      case PUT:
      case DELETE:
        mappings.add(getMappingForResourceWithId(function.getThing()));
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
  private Mapping getMappingForGet(Function function) {
    String goalType = TextConverter.toUpperUnderscore(function.getGoal().getText());

    if (goalType.equals(GoalType.FETCH_ONE.name())) {
      return getMappingForResourceWithId(function.getThing());
    } else if (goalType.equals(GoalType.FETCH_COLLECTION.name())
        || goalType.equals(GoalType.FETCH_PAGED_COLLECTION.name())) {
      return getMappingForResource(function.getThing());
    } else {
      throw new IllegalStateException(
          String.format("Cannot deduce REST mapping for=%s", function.getGoal().getText()));
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
   * Gets mapping for resource.
   *
   * @param thing the thing
   * @return the mapping for resource
   */
  private Mapping getMappingForResource(Thing thing) {
    Set<PathContent> pathContents = new LinkedHashSet<>();

    fillParentPathContent(pathContents, thing);

    pathContents.addAll(
        new LinkedHashSet<>(
            Arrays.asList(
                new PathConstant(
                    TextConverter.toLowerHyphen(
                        TextConverter.toPlural(thing.getThingName().getText()))))));

    return new Mapping(pathContents);
  }

  /**
   * Fill parent path content.
   *
   * @param pathContents the path contents
   * @param thing the thing
   */
  private void fillParentPathContent(Set<PathContent> pathContents, Thing thing) {
    if (thing.getOptionalParent().isPresent()) {
      pathContents.addAll(
          getMappingForResourceWithId(thing.getOptionalParent().get()).getPathContents());
    }
  }
}
