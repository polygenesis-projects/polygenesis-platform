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

package io.polygenesis.codegen.annotatedapi;

import io.polygenesis.annotations.core.GFunction;
import io.polygenesis.annotations.core.GoalType;
import java.util.List;

// TODO: import org.springframework.data.domain.Page;

/** @author Christos Tsakostas */
public interface TestService {

  String THING_NAME = "someThing";

  @GFunction(goal = GoalType.CALCULATE, thingName = THING_NAME)
  int add(int a, int b);

  @GFunction(goal = GoalType.FETCH_COLLECTION, thingName = THING_NAME)
  List<String> getListOfString(String input);

  @GFunction(goal = GoalType.FETCH_COLLECTION, thingName = THING_NAME)
  void passListOfString(List<String> input);

  @GFunction(goal = GoalType.FETCH_COLLECTION, thingName = THING_NAME)
  List<CollectionDto> getListOfCollectionDto(String input);

  // TODO: @GFunction(goal = GoalType.QRY_COLLECTION, thingName = THING_NAME)
  // TODO: Page<CollectionDto> getSpringPageCollectionDto(String input);

  @GFunction(goal = GoalType.FETCH_COLLECTION, thingName = THING_NAME)
  CollectionApiPageResponse getCollectionApiPageResponse(CollectionApiPageRequest request);

  @GFunction(goal = GoalType.CREATE, thingName = THING_NAME)
  CreateResponse create(CreateRequest request);
}
