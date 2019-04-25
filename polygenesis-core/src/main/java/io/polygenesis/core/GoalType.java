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

package io.polygenesis.core;

/**
 * Goal type.
 *
 * @author Christos Tsakostas
 */
public enum GoalType {
  // ===============================================================================================
  // COMMANDS
  // ===============================================================================================

  CREATE(CqsType.COMMAND),

  MODIFY(CqsType.COMMAND),

  DELETE(CqsType.COMMAND),

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  FETCH_ONE(CqsType.QUERY),

  FETCH_COLLECTION(CqsType.QUERY),

  FETCH_PAGED_COLLECTION(CqsType.QUERY),

  // ===============================================================================================
  // SUPPORTIVE
  // ===============================================================================================

  CALCULATE(CqsType.SUPPORTIVE),

  SYNCHRONIZE(CqsType.SUPPORTIVE),

  VALIDATE(CqsType.SUPPORTIVE),

  // ===============================================================================================
  // AGGREGATE ROOTS
  // ===============================================================================================

  AGGREGATE_ROOT_CREATE_ENTITY(CqsType.MUTATION),
  AGGREGATE_ROOT_UPDATE_ENTITY(CqsType.MUTATION),
  AGGREGATE_ROOT_DELETE_ENTITY(CqsType.MUTATION),
  AGGREGATE_ROOT_FETCH_ONE_ENTITY(CqsType.QUERY),
  AGGREGATE_ROOT_FETCH_ENTITY_COLLECTION(CqsType.QUERY),

  // ===============================================================================================
  // DOMAIN SERVICE METHOD
  // ===============================================================================================

  DOMAIN_SERVICE_METHOD(CqsType.SUPPORTIVE);

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private CqsType cqsType;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  GoalType(CqsType cqsType) {
    this.cqsType = cqsType;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets cqs type.
   *
   * @return the cqs type
   */
  public CqsType getCqsType() {
    return cqsType;
  }
}
