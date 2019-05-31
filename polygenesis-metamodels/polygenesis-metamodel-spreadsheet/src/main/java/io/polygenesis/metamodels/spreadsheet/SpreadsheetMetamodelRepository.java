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

package io.polygenesis.metamodels.spreadsheet;

import io.polygenesis.core.AbstractMetamodelRepository;
import io.polygenesis.core.MetamodelRepository;
import java.util.Set;

/**
 * The type Spreadsheet metamodel repository.
 *
 * @author Christos Tsakostas
 */
public class SpreadsheetMetamodelRepository extends AbstractMetamodelRepository<Spreadsheet>
    implements MetamodelRepository<Spreadsheet> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Spreadsheet metamodel repository.
   *
   * @param items the items
   */
  public SpreadsheetMetamodelRepository(Set<Spreadsheet> items) {
    super(items);
  }
}