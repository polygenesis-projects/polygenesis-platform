/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

public enum DomainObjectType {
  /** Abstract aggregate root domain object type. */
  ABSTRACT_AGGREGATE_ROOT,
  /** Abstract aggregate entity domain object type. */
  ABSTRACT_AGGREGATE_ENTITY,
  /** Abstract value object domain object type. */
  ABSTRACT_VALUE_OBJECT,
  /** Abstract domain event domain object type. */
  ABSTRACT_DOMAIN_EVENT,
  /** Abstract domain command domain object type. */
  ABSTRACT_DOMAIN_COMMAND,
  /** Aggregate root domain object type. */
  AGGREGATE_ROOT,
  /** Aggregate entity domain object type. */
  AGGREGATE_ENTITY,
  /** Domain event domain object type. */
  DOMAIN_EVENT,
  /** Domain command domain object type. */
  DOMAIN_COMMAND,
  /** Supportive entity domain object type. */
  SUPPORTIVE_ENTITY,
  /** Value object domain object type. */
  VALUE_OBJECT,
  /** Projection domain object type. */
  PROJECTION
}
