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

package io.polygenesis.models.domain;

/**
 * The enum DomainObjectProperty type.
 *
 * @author Christos Tsakostas
 */
public enum PropertyType {
  /** Abstract aggregate root id property type. */
  ABSTRACT_AGGREGATE_ROOT_ID,
  /** Aggregate root id property type. */
  AGGREGATE_ROOT_ID,
  /** Aggregate entity property type. */
  AGGREGATE_ENTITY,
  /** Aggregate entity collection property type. */
  AGGREGATE_ENTITY_COLLECTION,
  /** Primitive property type. */
  PRIMITIVE,
  /** Primitive collection property type. */
  PRIMITIVE_COLLECTION,
  /** Reference to aggregate root property type. */
  REFERENCE_TO_AGGREGATE_ROOT,
  /** Reference property type. */
  REFERENCE,
  /** Supportive entity id property type. */
  SUPPORTIVE_ENTITY_ID,
  /** Value object property type. */
  VALUE_OBJECT,
  /** Value object collection property type. */
  VALUE_OBJECT_COLLECTION;
}
