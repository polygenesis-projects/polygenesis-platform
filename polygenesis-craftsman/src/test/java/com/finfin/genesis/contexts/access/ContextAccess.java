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

package com.finfin.genesis.contexts.access;

import com.finfin.genesis.contexts.access.subscribers.OnSignUpConfirmed;
import com.oregor.trinity4j.Trinity4jAggregateRoot;
import com.oregor.trinity4j.Trinity4jTenantAggregateRoot;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingContext;
import io.polygenesis.abstraction.thing.ThingContextBuilder;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.ContextGenerator;
import io.polygenesis.core.Deducer;
import java.util.Set;

/** @author Christos Tsakostas */
public class ContextAccess {

  public static ThingContext get(
      PackageName rootPackageName, ContextGenerator contextGenerator, Set<Deducer<?>> deducers) {
    Thing confirmation = Confirmation.create(rootPackageName);
    Thing signUp = SignUp.create(confirmation, rootPackageName);
    Thing user = User.create(rootPackageName);

    return ThingContextBuilder.of("access", contextGenerator)
        .withDeducers(deducers)

        // Trinity4J Abstract Aggregate Root
        .addThing(Trinity4jAggregateRoot.create(rootPackageName))
        .addThing(Trinity4jTenantAggregateRoot.create(rootPackageName))

        // Abstract Aggregate Roots
        .addThing(confirmation)

        // Aggregate Roots
        .addThing(signUp)
        .addThing(user)
        .addThing(Tenant.create(rootPackageName, user))

        // Domain Services
        .addThing(ConfirmationDomainService.create(rootPackageName))
        .addThing(PasswordDomainService.create(rootPackageName))
        .addThing(SignInDomainService.create(rootPackageName))
        .addThing(SignUpDomainService.create(signUp, rootPackageName))

        // Subscribers
        .addThing(OnSignUpConfirmed.create(user, rootPackageName))
        .build();
  }
}
