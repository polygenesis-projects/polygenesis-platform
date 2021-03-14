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

import com.arcaiv.contexts.archiving.batch.RobotBatchProcess;
import com.arcaiv.contexts.archiving.domainservice.ArchiveDomainService;
import com.arcaiv.contexts.archiving.domainservice.StorageDomainService;
import com.arcaiv.contexts.archiving.subscribers.OnArchiveCreated;
import com.oregor.trinity4j.Trinity4jAggregateEntity;
import com.oregor.trinity4j.Trinity4jAggregateRoot;
import com.oregor.trinity4j.Trinity4jTenantAggregateRoot;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingContext;
import io.polygenesis.abstraction.thing.ThingContextBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ContextGenerator;
import io.polygenesis.core.Deducer;
import java.util.Set;

public class ContextArchiving {

  public static ThingContext get(
      PackageName rootPackageName, ContextGenerator contextGenerator, Set<Deducer<?>> deducers) {
    Thing country = Country.create(rootPackageName);
    Thing language = Language.create(rootPackageName);
    Thing category = Category.create(rootPackageName);
    Thing archive = Archive.create(rootPackageName);
    Thing robot = Robot.create(country, language, category, rootPackageName);

    return ThingContextBuilder.of("archiving", contextGenerator)
        .withDeducers(deducers)

        // Trinity4J Abstract Aggregate Root
        .addThing(Trinity4jAggregateRoot.create(rootPackageName))
        .addThing(Trinity4jTenantAggregateRoot.create(rootPackageName))
        .addThing(Trinity4jAggregateEntity.create(rootPackageName))

        // Things
        .addThing(archive)
        .addThing(robot)

        // Supportive
        .addThing(country)
        .addThing(language)
        .addThing(category)

        // Domain Services
        .addThing(ArchiveDomainService.create(archive, rootPackageName))
        .addThing(StorageDomainService.create(archive, rootPackageName))

        // Projections

        // Subscribers
        .addThing(OnArchiveCreated.create(archive, rootPackageName))

        // Batch Processes
        // .addThing(RobotBatchProcess.create(robot))

        // Build
        .build();
  }
}
