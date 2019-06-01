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

package io.polygenesis.deducers.spreadsheet.strategydefault;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.FunctionBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingRepository;
import io.polygenesis.abstraction.thing.ThingRepositoryImpl;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.deducers.spreadsheet.SpreadsheetDeducerStrategy;
import io.polygenesis.metamodels.spreadsheet.Sheet;
import io.polygenesis.metamodels.spreadsheet.Spreadsheet;
import io.polygenesis.metamodels.spreadsheet.SpreadsheetMetamodelRepository;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class DefaultSpreadsheetDeducerStrategyTest {

  @SuppressWarnings("rawtypes")
  private Set<AbstractionRepository> abstractionRepositories;

  @SuppressWarnings("rawtypes")
  private Set<MetamodelRepository> metamodelRepositories;

  private SpreadsheetDeducerStrategy spreadsheetDeducerStrategy;

  @Before
  public void setUp() {
    abstractionRepositories = new LinkedHashSet<>(Arrays.asList(thingRepository()));
    metamodelRepositories = new LinkedHashSet<>();
    spreadsheetDeducerStrategy = new DefaultSpreadsheetDeducerStrategy();
  }

  @Test
  public void shouldDeduce() {
    SpreadsheetMetamodelRepository spreadsheetMetamodelRepository =
        spreadsheetDeducerStrategy.deduce(abstractionRepositories, metamodelRepositories);

    assertThat(spreadsheetMetamodelRepository.getItems().size()).isEqualTo(1);

    Spreadsheet spreadsheet =
        spreadsheetMetamodelRepository
            .getItems()
            .stream()
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);

    assertThat(spreadsheet.getSheets().size()).isEqualTo(1);

    Sheet sheet =
        spreadsheet.getSheets().stream().findFirst().orElseThrow(IllegalArgumentException::new);

    assertThat(sheet.getCells().size()).isEqualTo(6);
  }

  private ThingRepository thingRepository() {
    Set<Thing> things = new LinkedHashSet<>();

    Thing thing = ThingBuilder.endToEnd().setThingName("Customer").createThing();

    thing.addFunctions(
        FunctionBuilder.forThing(thing, "com.oregor")
            .withFunctionCreate(
                DataBuilder.create()
                    .withTextProperty("firstName")
                    .build()
                    .withTextProperty("lastName")
                    .build()
                    .build())
            .build());

    things.add(thing);

    return new ThingRepositoryImpl(things);
  }
}
