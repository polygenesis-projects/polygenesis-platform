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

package com.arcaiv.contexts.archiving;

import com.oregor.trinity4j.Trinity4jShared;
import com.oregor.trinity4j.Trinity4jTenantAggregateRoot;
import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataEnumeration;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.abstraction.data.EnumerationValue;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.FunctionRole;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.PurposeFunctionBuilder;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Robot {

  public static Thing create(Thing country, Thing language, Thing category,
      PackageName rootPackageName) {
    Thing robot = ThingBuilder
        .endToEnd("robot")
        .setSuperClass(Trinity4jTenantAggregateRoot.create(rootPackageName))
        .setMultiTenant(Trinity4jShared.tenantId())
        .addThingProperties(thingProperties())
        .createThing(rootPackageName);

    robot.addFunctions(
        PurposeFunctionBuilder.forThing(robot, rootPackageName)
            .withCrudFunction(createRequestData(country, language, category, rootPackageName),
                FunctionRole.userAsSet())
            .build());

    robot.addFunctions(
        PurposeFunctionBuilder.forThing(robot, rootPackageName)
            .withFunctionModifyNoReturnValue(
                "verify", "Domain", new LinkedHashSet<>(), FunctionRole.userAsSet())
            .build()
    );

    robot.addFunctions(
        PurposeFunctionBuilder.forThing(robot, rootPackageName)
            .withFunctionFetchPagedCollection(
                "fetch",
                "unverifiedDomains",
                fetchOneOrPagedCollectionData(country, language, rootPackageName),
                FunctionRole.userAsSet())
            .build()
    );

    robot.addFunctions(
        PurposeFunctionBuilder.forThing(robot, rootPackageName)
            .withFunctionFetchPagedCollection(
                "fetch",
                "verifiedEnabledDomains",
                fetchOneOrPagedCollectionData(country, language, rootPackageName),
                FunctionRole.userAsSet())
            .build()
    );

    robot.addFunctions(
        PurposeFunctionBuilder.forThing(robot, rootPackageName)
            .withFunctionModifyNoReturnValue(
                "enable", "Robot", new LinkedHashSet<>(), FunctionRole.userAsSet())
            .build()
    );

    robot.addFunctions(
        PurposeFunctionBuilder.forThing(robot, rootPackageName)
            .withFunctionModifyNoReturnValue(
                "disable", "Robot", new LinkedHashSet<>(), FunctionRole.userAsSet())
            .build()
    );

    // Entity
    robot.addChild(Path.create(robot, category, rootPackageName));

    return robot;
  }

  private static Set<Data> thingProperties() {
    return DataBuilder.create()
        .withBooleanProperty("isVerified").build()
        .withBooleanProperty("isEnabled").build()
        .build()
        .stream()
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  private static Set<Data> createRequestData(Thing country, Thing language, Thing category,
      PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject("website", website(rootPackageName))
        .build()

        // Value Object Array
        // .withArrayData(DataArray.of(robotPage(category, rootPackageName), "page"))

        .withEnumeration(periodicity(rootPackageName))
        .withEnumeration(dayOfWeek(rootPackageName))
        .withEnumeration(dailyFrequency(rootPackageName))
        .withEnumeration(dayOfMonth(rootPackageName))

        .withReferenceToThingById(rootPackageName, country, "country")
        .withReferenceToThingById(rootPackageName, language, "language")

        .withTextProperty("categoryId").build()

        .build();
  }

  private static Set<Data> fetchOneOrPagedCollectionData(Thing country, Thing language,
      PackageName rootPackageName) {
    return DataBuilder.create()
        .withTextPropertyToValueObject("website", website(rootPackageName))
        .build()
        .withEnumeration(periodicity(rootPackageName))
        .withEnumeration(dayOfWeek(rootPackageName))
        .withEnumeration(dailyFrequency(rootPackageName))
        .withReferenceToThingById(rootPackageName, country, "country")
        .withReferenceToThingById(rootPackageName, language, "language")
        .withBooleanProperty("isVerified").build()
        .withBooleanProperty("isEnabled").build()
        .build();
  }

  private static DataEnumeration periodicity(PackageName rootPackageName) {
    Set<EnumerationValue> enumerationValues = new LinkedHashSet<>();

    enumerationValues.add(EnumerationValue.ofInitial("DAILY"));
    enumerationValues.add(EnumerationValue.of("WEEKLY"));
    enumerationValues.add(EnumerationValue.of("MONTHLY"));

    return DataEnumeration.ofPurpose(
        new ObjectName("periodicity"),
        rootPackageName.withSubPackage("robot"),
        new VariableName("periodicity"),
        enumerationValues,
        DataPurpose.any());
  }

  private static DataEnumeration dayOfWeek(PackageName rootPackageName) {
    Set<EnumerationValue> enumerationValues = new LinkedHashSet<>();

    enumerationValues.add(EnumerationValue.ofInitial("SUNDAY"));
    enumerationValues.add(EnumerationValue.of("MONDAY"));
    enumerationValues.add(EnumerationValue.of("TUESDAY"));
    enumerationValues.add(EnumerationValue.of("WEDNESDAY"));
    enumerationValues.add(EnumerationValue.of("THURSDAY"));
    enumerationValues.add(EnumerationValue.of("FRIDAY"));
    enumerationValues.add(EnumerationValue.of("SATURDAY"));

    return DataEnumeration.ofPurpose(
        new ObjectName("dayOfWeek"),
        rootPackageName.withSubPackage("robot"),
        new VariableName("dayOfWeek"),
        enumerationValues,
        DataPurpose.any());
  }

  private static DataEnumeration dayOfMonth(PackageName rootPackageName) {
    Set<EnumerationValue> enumerationValues = new LinkedHashSet<>();

    enumerationValues.add(EnumerationValue.ofInitial("FIRST"));
    enumerationValues.add(EnumerationValue.of("MIDDLE"));
    enumerationValues.add(EnumerationValue.of("LAST"));

    return DataEnumeration.ofPurpose(
        new ObjectName("dayOfMonth"),
        rootPackageName.withSubPackage("robot"),
        new VariableName("dayOfMonth"),
        enumerationValues,
        DataPurpose.any());
  }

  private static DataEnumeration dailyFrequency(PackageName rootPackageName) {
    Set<EnumerationValue> enumerationValues = new LinkedHashSet<>();

    enumerationValues.add(EnumerationValue.ofInitial("ONCE"));
    enumerationValues.add(EnumerationValue.of("TWICE"));
    enumerationValues.add(EnumerationValue.of("THRICE"));
    enumerationValues.add(EnumerationValue.of("FOUR_TIMES"));
    enumerationValues.add(EnumerationValue.of("FIVE_TIMES"));
    enumerationValues.add(EnumerationValue.of("SIX_TIMES"));
    enumerationValues.add(EnumerationValue.of("SEVEN_TIMES"));
    enumerationValues.add(EnumerationValue.of("EIGHT_TIMES"));
    enumerationValues.add(EnumerationValue.of("NINE_TIMES"));
    enumerationValues.add(EnumerationValue.of("TEN_TIMES"));
    enumerationValues.add(EnumerationValue.of("ELEVEN_TIMES"));
    enumerationValues.add(EnumerationValue.of("TWELVE_TIMES"));

    return DataEnumeration.ofPurpose(
        new ObjectName("dailyFrequency"),
        rootPackageName.withSubPackage("robot"),
        new VariableName("dailyFrequency"),
        enumerationValues,
        DataPurpose.any());
  }

  public static DataObject robotPage(Thing category, PackageName rootPackageName) {
    Set<Data> data =
        DataBuilder.create()
            .withTextProperty("path")
            .build()
            .withReferenceToThingById(rootPackageName, category, "categoryId")
            .build();

    return new DataObject(
        new VariableName("page"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("page"),
        rootPackageName.withSubPackage("robot"),
        data,
        DataSourceType.DEFAULT);
  }

  public static DataObject website(PackageName rootPackageName) {
    Set<Data> data = DataBuilder.create().withTextProperty("value").build().build();

    return new DataObject(
        new VariableName("website"),
        DataPurpose.any(),
        DataValidator.empty(),
        new ObjectName("website"),
        rootPackageName.withSubPackage("robot"),
        data,
        DataSourceType.DEFAULT);
  }

}
