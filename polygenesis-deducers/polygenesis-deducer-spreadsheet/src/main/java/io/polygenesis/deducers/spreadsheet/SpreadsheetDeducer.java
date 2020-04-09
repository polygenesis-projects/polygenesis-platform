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

package io.polygenesis.deducers.spreadsheet;

import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.deducers.spreadsheet.strategydefault.DefaultSpreadsheetDeducerStrategy;
import io.polygenesis.metamodels.spreadsheet.SpreadsheetMetamodelRepository;
import java.util.Set;

public class SpreadsheetDeducer implements Deducer<SpreadsheetMetamodelRepository> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Spreadsheet deducer. */
  public SpreadsheetDeducer() {
    super();
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  @Override
  public SpreadsheetMetamodelRepository deduce(
      Set<AbstractionRepository<?>> abstractionRepositories,
      Set<MetamodelRepository<?>> metamodelRepositories) {
    SpreadsheetDeducerStrategy spreadsheetDeducerStrategy = new DefaultSpreadsheetDeducerStrategy();
    return spreadsheetDeducerStrategy.deduce(abstractionRepositories, metamodelRepositories);
  }
}
