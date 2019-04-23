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

/**
 * The enum Dto type.
 *
 * @author Christos Tsakostas
 */
public enum DtoType {
  /** Api request dto type. */
  API_REQUEST,
  /** Api collection request dto type. */
  API_COLLECTION_REQUEST,
  /** Api paged collection request dto type. */
  API_PAGED_COLLECTION_REQUEST,
  /** Api response dto type. */
  API_RESPONSE,
  /** Api collection response dto type. */
  API_COLLECTION_RESPONSE,
  /** Api paged collection response dto type. */
  API_PAGED_COLLECTION_RESPONSE,
  /** Collection record dto type. */
  COLLECTION_RECORD,
  /** Internal dto type. */
  INTERNAL;
}
