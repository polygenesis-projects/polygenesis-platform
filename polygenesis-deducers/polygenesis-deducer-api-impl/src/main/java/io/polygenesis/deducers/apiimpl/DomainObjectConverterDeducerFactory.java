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

package io.polygenesis.deducers.apiimpl;

/**
 * The type Domain object converter deducer factory.
 *
 * @author Christos Tsakostas
 */
public class DomainObjectConverterDeducerFactory {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private static AggregateRootConverterDeducer aggregateRootConverterDeducer;
  private static ProjectionConverterDeducer projectionConverterDeducer;

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  static {
    ValueObjectDtoDeducer valueObjectDtoDeducer = new ValueObjectDtoDeducer();
    CollectionRecordDeducer collectionRecordDeducer = new CollectionRecordDeducer();

    aggregateRootConverterDeducer =
        new AggregateRootConverterDeducer(valueObjectDtoDeducer, collectionRecordDeducer);

    projectionConverterDeducer =
        new ProjectionConverterDeducer(valueObjectDtoDeducer, collectionRecordDeducer);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================
  private DomainObjectConverterDeducerFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * New instance domain object converter deducer.
   *
   * @return the domain object converter deducer
   */
  public static DomainObjectConverterDeducer newInstance() {
    return new DomainObjectConverterDeducer(
        aggregateRootConverterDeducer, projectionConverterDeducer);
  }
}
