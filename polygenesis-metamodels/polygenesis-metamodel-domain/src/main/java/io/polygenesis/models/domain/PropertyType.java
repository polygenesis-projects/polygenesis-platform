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

package io.polygenesis.models.domain;

/**
 * The enum DomainObjectProperty type.
 *
 * @author Christos Tsakostas
 */
public enum PropertyType {
  ABSTRACT_AGGREGATE_ROOT_ID,
  AGGREGATE_ROOT_ID,
  ABSTRACT_AGGREGATE_ENTITY,
  AGGREGATE_ENTITY,
  AGGREGATE_ENTITY_ID,
  AGGREGATE_ENTITY_COLLECTION,
  MAP,
  PRIMITIVE,
  PRIMITIVE_COLLECTION,
  PROJECTION_ID,
  REFERENCE_TO_AGGREGATE_ROOT,
  REFERENCE_TO_ABSTRACT_AGGREGATE_ROOT,
  REFERENCE_BY_ID,
  REFERENCE_BY_VALUE,
  SUPPORTIVE_ENTITY_ID,
  TENANT_ID,
  VALUE_OBJECT,
  VALUE_OBJECT_COLLECTION;
}
