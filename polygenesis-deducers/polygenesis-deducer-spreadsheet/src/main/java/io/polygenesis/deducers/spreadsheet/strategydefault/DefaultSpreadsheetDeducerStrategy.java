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

package io.polygenesis.deducers.spreadsheet.strategydefault;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.deducers.spreadsheet.SpreadsheetDeducerStrategy;
import io.polygenesis.metamodels.spreadsheet.Cell;
import io.polygenesis.metamodels.spreadsheet.ColumnIndex;
import io.polygenesis.metamodels.spreadsheet.RowIndex;
import io.polygenesis.metamodels.spreadsheet.Sheet;
import io.polygenesis.metamodels.spreadsheet.Spreadsheet;
import io.polygenesis.metamodels.spreadsheet.SpreadsheetMetamodelRepository;
import io.polygenesis.metamodels.spreadsheet.Value;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultSpreadsheetDeducerStrategy implements SpreadsheetDeducerStrategy {

  @Override
  public SpreadsheetMetamodelRepository deduce(
      Set<AbstractionRepository<?>> abstractionRepositories,
      Set<MetamodelRepository<?>> metamodelRepositories) {
    Set<Spreadsheet> items = new LinkedHashSet<>();

    items.add(
        deduceSpreadSheetForAllThings(
            CoreRegistry.getAbstractionRepositoryResolver()
                .resolve(abstractionRepositories, ThingRepository.class)
                .getAllAbstractionItems()));

    return new SpreadsheetMetamodelRepository(items);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Deduce spread sheet for all things.
   *
   * @param things the things
   * @return the spread sheet
   */
  private Spreadsheet deduceSpreadSheetForAllThings(Set<Thing> things) {
    Set<Sheet> sheets = new LinkedHashSet<>();

    things.forEach(thing -> sheets.add(deduceSheetForThing(thing)));

    return new Spreadsheet(new ObjectName("xxx"), sheets);
  }

  /**
   * Deduce sheet for thing.
   *
   * @param thing the thing
   * @return the sheet
   */
  private Sheet deduceSheetForThing(Thing thing) {
    Set<Cell> cells = new LinkedHashSet<>();
    AtomicInteger atomicIndex = new AtomicInteger();

    thing
        .getThingProperties()
        .getData()
        .forEach(data -> cells.addAll(deduceCellsForData(data, atomicIndex.getAndIncrement())));

    return new Sheet(new Name(thing.getThingName().getText()), cells);
  }

  /**
   * Deduce cells for thing property set.
   *
   * @param data the thing property
   * @param index the index
   * @return the set
   */
  private Set<Cell> deduceCellsForData(Data data, Integer index) {
    return new LinkedHashSet<>(
        Arrays.asList(
            new Cell(
                new RowIndex(index),
                new ColumnIndex(0),
                new Value(data.getVariableName().getText())),
            new Cell(
                new RowIndex(index),
                new ColumnIndex(1),
                new Value(TextConverter.toUpperCamelSpaces(data.getVariableName().getText())))));
  }
}
